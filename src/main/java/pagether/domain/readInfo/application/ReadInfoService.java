package pagether.domain.readInfo.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.book.domain.Book;
import pagether.domain.book.exception.BookNotFoundException;
import pagether.domain.book.repository.BookRepository;
import pagether.domain.follow.domain.Follow;
import pagether.domain.follow.repository.FollowRepository;
import pagether.domain.note.application.NoteService;
import pagether.domain.note.domain.Note;
import pagether.domain.note.domain.NoteType;
import pagether.domain.note.dto.req.AddNoteRequest;
import pagether.domain.note.exception.NoteNotFountException;
import pagether.domain.note.repository.NoteRepository;
import pagether.domain.readInfo.domain.ReadInfo;
import pagether.domain.readInfo.domain.ReadStatus;
import pagether.domain.readInfo.dto.BookImgDTO;
import pagether.domain.readInfo.dto.ReadFriendInfoDTO;
import pagether.domain.readInfo.dto.ReadInfoDTO;
import pagether.domain.readInfo.dto.req.AddReadInfoRequest;
import pagether.domain.readInfo.dto.req.UpdatePageRequest;
import pagether.domain.readInfo.dto.req.UpdateStatusRequest;
import pagether.domain.readInfo.dto.res.ReadInfoByBookResponse;
import pagether.domain.readInfo.dto.res.ReadInfoResponse;
import pagether.domain.readInfo.dto.res.ReadingAndPinedBooksResponse;
import pagether.domain.readInfo.dto.res.ReadingAndReadBooksResponse;
import pagether.domain.readInfo.exception.ReadInfoNotFountException;
import pagether.domain.readInfo.exception.UnauthorizedAccessException;
import pagether.domain.readInfo.repository.ReadInfoRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public ReadInfoResponse startRead(AddReadInfoRequest request, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Book book = bookRepository.findByIsbn(request.getIsbn()).orElseThrow(BookNotFoundException::new);
        ReadInfo readInfo;
        if (readInfoRepository.existsByBookAndUserAndReadStatus(book, user, ReadStatus.PINNED)){
            readInfo = readInfoRepository.findByBookAndUserAndReadStatus(book, user, ReadStatus.PINNED).orElseThrow(ReadInfoNotFountException::new);
            readInfo.start();
        }else{
            if (readInfoRepository.existsByBookAndUser(book, user)){
                ReadInfo latestReadInfo = readInfoRepository.findByBookAndUserAndIsLatest(book, user, true).orElseThrow(ReadInfoNotFountException::new);
                latestReadInfo.changeToOld();
                readInfoRepository.save(latestReadInfo);
            }

            readInfo = ReadInfo.builder()
                    .book(book)
                    .user(user)
                    .readCount(0L)
                    .currentPage(0L)
                    .isLatest(true)
                    .readStatus(ReadStatus.READING)
                    .hasReview(false)
                    .startDate(LocalDateTime.now())
                    .createdAt(LocalDateTime.now())
                    .build();
        }
        noteService.newBook(user, book);
        readInfo = readInfoRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadInfoResponse pin(AddReadInfoRequest request, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Book book = bookRepository.findByIsbn(request.getIsbn()).orElseThrow(BookNotFoundException::new);

        ReadStatus readStatus = ReadStatus.PINNED;
        if (readInfoRepository.existsByBookAndUser(book, user)){
            ReadInfo latestReadInfo = readInfoRepository.findByBookAndUserAndIsLatest(book, user, true).orElseThrow(ReadInfoNotFountException::new);
            latestReadInfo.changeToOld();
            readInfoRepository.save(latestReadInfo);
            readStatus = ReadStatus.RE_PINNED;
        }

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
        readInfo = readInfoRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadInfoResponse stop(UpdateStatusRequest request, String userId) {
        ReadInfo readInfo = readInfoRepository.findById(request.getReadId()).orElseThrow(ReadInfoNotFountException::new);
        if (!readInfo.getUser().getUserId().equals(userId)){
            throw new UnauthorizedAccessException();
        }
        readInfo.stop();
        readInfo = readInfoRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadInfoResponse done(UpdateStatusRequest request, String userId) {
        ReadInfo readInfo = readInfoRepository.findById(request.getReadId()).orElseThrow(ReadInfoNotFountException::new);
        if (!readInfo.getUser().getUserId().equals(userId)){
            throw new UnauthorizedAccessException();
        }
        readInfo.done();
        readInfo = readInfoRepository.save(readInfo);
        return new ReadInfoResponse(readInfo);
    }

    public ReadInfoResponse pageUpdate(UpdatePageRequest request, String userId) {
        ReadInfo readInfo = readInfoRepository.findById(request.getReadId()).orElseThrow(ReadInfoNotFountException::new);
        if (!readInfo.getUser().getUserId().equals(userId)){
            throw new UnauthorizedAccessException();
        }
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

        List<BookImgDTO> readingDTOs = new ArrayList<>();
        List<BookImgDTO> pinedDTOs = new ArrayList<>();

        for(ReadInfo readInfo : reading){
            BookImgDTO dto = new BookImgDTO(readInfo);
            readingDTOs.add(dto);
        }
        for(ReadInfo readInfo : pined){
            BookImgDTO dto = new BookImgDTO(readInfo);
            pinedDTOs.add(dto);
        }
        return new ReadingAndPinedBooksResponse(readingDTOs, pinedDTOs);
    }

    public ReadingAndReadBooksResponse getReadingAndReadBooks(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<ReadInfo> readInfos = readInfoRepository.findAllByUserAndIsLatest(user, true);


        List<BookImgDTO> readingDTOs = new ArrayList<>();
        List<BookImgDTO> readDTOs = new ArrayList<>();

        for(ReadInfo readInfo : readInfos){
            BookImgDTO dto = new BookImgDTO(readInfo);
            if (readInfo.getReadStatus().equals(ReadStatus.READING)){
                readingDTOs.add(dto);
            }else if (readInfo.getReadStatus().equals(ReadStatus.STOPPED)){
                readingDTOs.add(dto);
            }else if (readInfo.getReadStatus().equals(ReadStatus.READ)){
                readDTOs.add(dto);
            }else if (readInfo.getReadStatus().equals(ReadStatus.RE_PINNED)){
                readDTOs.add(dto);
            }
        }

        return new ReadingAndReadBooksResponse(readingDTOs, readDTOs);
    }

    public List<ReadFriendInfoDTO> getFriends(String isbn, String userId) {
        List<ReadFriendInfoDTO> dtos = new ArrayList<>();
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<Follow> follows = followRepository.findAllByFollower(user);

        for(Follow follow : follows){
            User followee = userRepository.findByUserId(follow.getFollowee().getUserId()).orElseThrow(UserNotFountException::new);
            Boolean isFriendRead = readInfoRepository.existsByBookAndUser(book, followee);
            if (isFriendRead){
                ReadInfo readInfo = readInfoRepository.findByBookAndUserAndIsLatest(book, followee, true).orElseThrow(ReadInfoNotFountException::new);
                ReadFriendInfoDTO dto = new ReadFriendInfoDTO(readInfo);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    public ReadInfoByBookResponse getByBook(String isbn, String userId, String requestedUserId) {
        List<ReadInfoDTO> dtos = new ArrayList<>();
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        List<ReadInfo> readInfos = readInfoRepository.findAllByBookAndUserAndIsLatestAndReadStatusNotInOrderByCreatedAtDesc(book, user,true, Arrays.asList(ReadStatus.PINNED, ReadStatus.RE_PINNED));

        Boolean isMe = userId.equals(requestedUserId);
        if (readInfos.isEmpty()){
            return new ReadInfoByBookResponse();
        }

        for(ReadInfo readInfo : readInfos){
            ReadInfoDTO dto;
            if (readInfo.getHasReview()){
                Note note = noteRepository.findByReadInfo(readInfo).orElseThrow(NoteNotFountException::new);
                if (isMe){
                    dto = new ReadInfoDTO(readInfo, note.getRating(), note.getContent());
                }else{
                    dto = new ReadInfoDTO(readInfo, note.getRating());
                }
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

    public void delete(Long readId) {
        if (!bookRepository.existsById(readId)) {
            throw new ReadInfoNotFountException();
        }
        bookRepository.deleteById(readId);
    }
}