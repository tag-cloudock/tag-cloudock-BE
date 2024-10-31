package gachonherald.domain.readInfo.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gachonherald.domain.book.domain.Book;
import gachonherald.domain.book.exception.BookNotFoundException;
import gachonherald.domain.book.repository.BookRepository;
import gachonherald.domain.follow.domain.Follow;
import gachonherald.domain.follow.domain.RequestStatus;
import gachonherald.domain.follow.repository.FollowRepository;
import gachonherald.domain.note.application.NoteService;
import gachonherald.domain.note.domain.Note;
import gachonherald.domain.note.domain.NoteType;
import gachonherald.domain.note.exception.NoteNotFountException;
import gachonherald.domain.note.repository.NoteRepository;
import gachonherald.domain.readInfo.domain.ReadInfo;
import gachonherald.domain.readInfo.domain.ReadStatus;
import gachonherald.domain.readInfo.dto.BookImgDTO;
import gachonherald.domain.readInfo.dto.ReadFriendInfoDTO;
import gachonherald.domain.readInfo.dto.ReadInfoDTO;
import gachonherald.domain.readInfo.dto.req.AddReadInfoRequest;
import gachonherald.domain.readInfo.dto.req.UpdateDateRequest;
import gachonherald.domain.readInfo.dto.req.UpdatePageRequest;
import gachonherald.domain.readInfo.dto.req.UpdateStatusRequest;
import gachonherald.domain.readInfo.dto.res.ReadInfoByBookResponse;
import gachonherald.domain.readInfo.dto.res.ReadInfoResponse;
import gachonherald.domain.readInfo.dto.res.ReadingAndPinedBooksResponse;
import gachonherald.domain.readInfo.dto.res.ReadingAndReadBooksResponse;
import gachonherald.domain.readInfo.exception.PinNotAllowedException;
import gachonherald.domain.readInfo.exception.ReadInfoNotFountException;
import gachonherald.domain.readInfo.repository.ReadInfoRepository;
import gachonherald.domain.user.application.UserService;
import gachonherald.domain.user.domain.User;
import gachonherald.domain.user.exception.UserNotFountException;
import gachonherald.domain.user.repository.UserRepository;
import gachonherald.global.config.exception.IllegalArgumentException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReadInfoService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ReadInfoRepository readInfoRepository;
    private final FollowRepository followRepository;
    private final NoteRepository noteRepository;
    private final NoteService noteService;
    private final UserService userService;

    public ReadInfoResponse startRead(AddReadInfoRequest request, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Book book = bookRepository.findByIsbn(request.getIsbn()).orElseThrow(BookNotFoundException::new);
        ReadInfo readInfo;
        if (!readInfoRepository.existsByBookAndUser(book, user)){
            readInfo = createNewReadInfo(book, user, ReadStatus.READING);
        }else {
            readInfo = readInfoRepository.findByBookAndUserAndIsLatest(book, user, true).orElseThrow(ReadInfoNotFountException::new);
            if (readInfo.getReadStatus().equals(ReadStatus.PINNED) || readInfo.getReadStatus().equals(ReadStatus.RE_PINNED)){
                readInfo = changeToReading(readInfo);
            }else{
                changeToOld(readInfo);
                readInfo = createNewReadInfo(book, user, ReadStatus.READING);
            }
        }
        noteService.createStartNote(user, book);
        return new ReadInfoResponse(readInfo);
    }

    private ReadInfo createNewReadInfo(Book book, User user, ReadStatus readStatus) {
        ReadInfo readInfo = ReadInfo.builder()
                .book(book)
                .user(user)
                .readCount(0L)
                .currentPage(0L)
                .isLatest(true)
                .readStatus(readStatus)
                .hasReview(false)
                .startDate(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        return readInfoRepository.save(readInfo);
    }

    private ReadInfo changeToOld(ReadInfo readInfo) {
        readInfo.changeToOld();
        return readInfoRepository.save(readInfo);
    }

    private ReadInfo changeToReading(ReadInfo readInfo) {
        readInfo.start();
        return readInfoRepository.save(readInfo);
    }

    public ReadInfoResponse updateDate(UpdateDateRequest request, String userId) {
        ReadInfo readInfo = readInfoRepository.findById(request.getReadId()).orElseThrow(ReadInfoNotFountException::new);
        userService.validateOwnership(readInfo.getUser().getUserId(), userId);
        validateDate(request.getStartDate(), request.getCompletionDate());
        readInfo.setStartDate(request.getStartDate());
        readInfo.setCompletionDate(request.getCompletionDate());
        readInfo = readInfoRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadInfoResponse pin(AddReadInfoRequest request, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Book book = bookRepository.findByIsbn(request.getIsbn()).orElseThrow(BookNotFoundException::new);

        ReadStatus readStatus = ReadStatus.PINNED;

        if (readInfoRepository.existsByBookAndUser(book, user)){
            ReadInfo latestReadInfo = readInfoRepository.findByBookAndUserAndIsLatest(book, user, true).orElseThrow(ReadInfoNotFountException::new);
            checkPinAllowed(latestReadInfo);
            latestReadInfo.changeToOld();
            readStatus = ReadStatus.RE_PINNED;
        }
        ReadInfo readInfo = createNewReadInfo(book, user, readStatus);
        readInfo = readInfoRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadInfoResponse stop(UpdateStatusRequest request, String userId) {
        ReadInfo readInfo = readInfoRepository.findById(request.getReadId()).orElseThrow(ReadInfoNotFountException::new);
        userService.validateOwnership(readInfo.getUser().getUserId(),userId);
        readInfo.stop();
        readInfo = readInfoRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadInfoResponse done(UpdateStatusRequest request, String userId) {
        ReadInfo readInfo = readInfoRepository.findById(request.getReadId()).orElseThrow(ReadInfoNotFountException::new);
        userService.validateOwnership(readInfo.getUser().getUserId(),userId);
        readInfo.done();
        readInfo = readInfoRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadInfoResponse pageUpdate(UpdatePageRequest request, String userId) {
        ReadInfo readInfo = readInfoRepository.findById(request.getReadId()).orElseThrow(ReadInfoNotFountException::new);
        userService.validateOwnership(readInfo.getUser().getUserId(),userId);
        readInfo.setCurrentPage(request.getPage());
        readInfo = readInfoRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadingAndPinedBooksResponse getReadingAndPinedBooks(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<ReadInfo> reading = readInfoRepository.findAllByUserAndReadStatus(user, ReadStatus.READING);
        List<ReadInfo> pined = readInfoRepository.findAllByUserAndReadStatus(user, ReadStatus.PINNED);
        List<ReadInfo> rePined = readInfoRepository.findAllByUserAndReadStatus(user, ReadStatus.RE_PINNED);
        pined.addAll(rePined);
        List<BookImgDTO> readingDTOs = reading.stream().map(BookImgDTO::new).collect(Collectors.toList());
        List<BookImgDTO> pinedDTOs = pined.stream().map(BookImgDTO::new).collect(Collectors.toList());
        return new ReadingAndPinedBooksResponse(readingDTOs, pinedDTOs);
    }

    public ReadingAndReadBooksResponse getReadingAndReadBooks(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<ReadInfo> readInfos = readInfoRepository.findAllByUserAndIsLatest(user, true);

        List<BookImgDTO> readingDTOs = new ArrayList<>();
        List<BookImgDTO> readDTOs = new ArrayList<>();
        for(ReadInfo readInfo : readInfos){
            BookImgDTO dto = new BookImgDTO(readInfo);
            if (readInfo.getReadStatus().equals(ReadStatus.READING) ||
                    readInfo.getReadStatus().equals(ReadStatus.STOPPED))
                readingDTOs.add(dto);
            else if (readInfo.getReadStatus().equals(ReadStatus.READ) ||
                    readInfo.getReadStatus().equals(ReadStatus.RE_PINNED))
                readDTOs.add(dto);
        }
        return new ReadingAndReadBooksResponse(readingDTOs, readDTOs);
    }

    public List<ReadFriendInfoDTO> getFriends(String isbn, String userId) {
        List<ReadFriendInfoDTO> dtos = new ArrayList<>();
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<Follow> follows = followRepository.findAllByFollowerAndRequestStatus(user, RequestStatus.ACCEPTED);

        for(Follow follow : follows){
            User followee = userRepository.findByUserId(follow.getFollowee().getUserId()).orElseThrow(UserNotFountException::new);
            if (isFriendRead(book, followee)){
                ReadInfo readInfo = readInfoRepository.findByBookAndUserAndIsLatest(book, followee, true).orElseThrow(ReadInfoNotFountException::new);
                dtos.add(new ReadFriendInfoDTO(readInfo));
            }
        }
        return dtos;
    }

    public Boolean isFriendRead(Book book, User friend) {
        return readInfoRepository.existsByBookAndUser(book, friend);
    }

    public ReadInfoByBookResponse getByBook(String isbn, String userId, String requestedUserId) {
        List<ReadInfoDTO> dtos = new ArrayList<>();
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<ReadInfo> readInfos = readInfoRepository.findAllByBookAndUserOrderByCreatedAtDesc(book, user);
        Boolean isOwner = userService.isOwner(userId, requestedUserId);
        if (readInfos.isEmpty())
            return new ReadInfoByBookResponse();
        for(ReadInfo readInfo : readInfos){
            ReadInfoDTO dto;
            if (readInfo.getHasReview()){
                Note note = noteRepository.findByReadInfoAndType(readInfo, NoteType.REVIEW).orElseThrow(NoteNotFountException::new);
                if (isOwner || !note.getIsPrivate())
                    dto = new ReadInfoDTO(readInfo, note.getRating(), note.getContent());
                else
                    dto = new ReadInfoDTO(readInfo, note.getRating());
            }else{
                dto = new ReadInfoDTO(readInfo);
            }
            dtos.add(dto);
        }
        ReadInfo lastReadInfo = readInfos.get(0);
        ReadStatus currentStatus = lastReadInfo.getReadStatus();
        Long currentPage = lastReadInfo.getCurrentPage();
        return new ReadInfoByBookResponse(lastReadInfo, lastReadInfo.getReadInfoId(), currentPage, currentStatus, dtos);
    }

    public void checkPinAllowed(ReadInfo latestReadInfo) {
        if (!(latestReadInfo.getReadStatus().equals(ReadStatus.READ) || latestReadInfo.getReadStatus().equals(ReadStatus.STOPPED)))
            throw new PinNotAllowedException();
    }

    public void validateDate(LocalDateTime startDate, LocalDateTime completionDate) {
        if (startDate.isAfter(completionDate))
            throw new IllegalArgumentException();
    }

    public void delete(Long readId) {
        ReadInfo readInfo = readInfoRepository.findById(readId).orElseThrow(ReadInfoNotFountException::new);;
        List<Note> notes = noteRepository.findAllByReadInfo(readInfo);
        noteRepository.deleteAll(notes);
        readInfoRepository.deleteById(readId);
    }
}