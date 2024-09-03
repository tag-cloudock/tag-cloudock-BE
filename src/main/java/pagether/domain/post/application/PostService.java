package pagether.domain.post.application;

import pagether.domain.chat.repository.ChatRoomRepository;
import pagether.domain.count.repository.CountRepository;
import pagether.domain.image.application.ImageService;
import pagether.domain.post.domain.Post;
import pagether.domain.post.dto.req.AddPostRequest;
import pagether.domain.post.dto.req.UpdatePostRequest;
import pagether.domain.post.dto.res.PageResponse;
import pagether.domain.post.dto.res.PostResponse;
import pagether.domain.post.dto.res.PostsResponse;
import pagether.domain.post.exception.PostNotFoundException;
import pagether.domain.post.repository.PostRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {


    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CountRepository visitCountRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ImageService imageService;

    private static final int PAGE_SIZE = 10;

    public PostResponse save(AddPostRequest request, String userName, MultipartFile pic) {
        User user = userRepository.findByUserId(userName).get();

        String imageFileName = imageService.save(pic, false);
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .location(request.getLocation())
                .locationDetail(request.getLocationDetail())
                .rentalFee(request.getRentalFee())
                .security(request.getSecurity())
                .createdAt(LocalDateTime.now())
                .needAt(request.getNeedAt())
                .returnAt(request.getReturnAt())
                .isClose(false)
                .isLenderWriteReview(false)
                .user(user)
                .imgPath(imageFileName)
                .build();
        Post savedPost = postRepository.save(post);

        return new PostResponse(savedPost);
    }


    public PostResponse get(Integer id) {
        if (!postRepository.existsByPostId(id)) {
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(id).get();
        Integer chatCnt = chatRoomRepository.countByPost(post);
        return new PostResponse(post, chatCnt);
    }

    public List<PostsResponse> get3ByUser(String userId) {
        if (!userRepository.existsUserById(userId)) {
            throw new UserNotFountException();
        }
        User user = userRepository.findById(userId).get();

        List<PostsResponse> postsDTO = new ArrayList<>();
        List<Post> posts = postRepository.findTop3ByUserOrderByCreatedAtDesc(user);

        for (Post post : posts) {
            PostsResponse dto = new PostsResponse();
            dto.setPostId(post.getPostId());
            dto.setUserId(post.getUser().getUserId());
            dto.setTitle(post.getTitle());
            dto.setLocation(post.getLocation());
            dto.setLocationDetail(post.getLocationDetail());
            dto.setRentalFee(post.getRentalFee());
            dto.setSecurity(post.getSecurity());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setClose(post.getIsClose());
            dto.setPostImgPath(post.getImgPath());
//            dto.setCommentCount(commentRepository.countByPost(post));

            postsDTO.add(dto);
        }
        return postsDTO;
    }

    public PageResponse getAllByLocation(String location, int page) {
        List<PostsResponse> postsDTO = new ArrayList<>();
        int pageNumber = page;
        Sort sort = Sort.by("createdAt").ascending();
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, sort);

        List<Post> posts = postRepository.findAllByLocationOrderByIsCloseAscCreatedAtDesc(location, pageable);

        for (Post post : posts) {
            Integer chatCnt = chatRoomRepository.countByPost(post);
            PostsResponse dto = new PostsResponse();
            dto.setPostId(post.getPostId());
            dto.setUserId(post.getUser().getUserId());
            dto.setTitle(post.getTitle());
            dto.setLocation(post.getLocation());
            dto.setLocationDetail(post.getLocationDetail());
            dto.setRentalFee(post.getRentalFee());
            dto.setSecurity(post.getSecurity());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setClose(post.getIsClose());
            dto.setPostImgPath(post.getImgPath());
            dto.setChatCount(chatCnt);
            postsDTO.add(dto);
        }

        PageResponse pageDTO = new PageResponse();
        Long postsCount = postRepository.countAllByLocation(location);
        boolean isLast = false;
        if ((int) ((postsCount - 1) / 10) == page) {
            isLast = true;
        }
        pageDTO.setLast(isLast);
        pageDTO.setPosts(postsDTO);

        return pageDTO;
    }

    public PageResponse getAllPostByCampus(String campus, int page) {
        List<PostsResponse> postsDTO = new ArrayList<>();

        int pageNumber = page;
        Sort sort = Sort.by("createdAt").ascending();
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE, sort);

        List<Post> posts = postRepository.findAllByLocationStartingWithOrderByCreatedAtDesc(campus.equals("global") ? "G" : "M", pageable);
        for (Post post : posts) {
            Integer chatCnt = chatRoomRepository.countByPost(post);
            PostsResponse dto = new PostsResponse();
            dto.setPostId(post.getPostId());
            dto.setUserId(post.getUser().getUserId());
            dto.setTitle(post.getTitle());
            dto.setLocation(post.getLocation());
            dto.setLocationDetail(post.getLocationDetail());
            dto.setRentalFee(post.getRentalFee());
            dto.setSecurity(post.getSecurity());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setClose(post.getIsClose());
            dto.setPostImgPath(post.getImgPath());
            dto.setChatCount(chatCnt);
            postsDTO.add(dto);
        }

        PageResponse pageDTO = new PageResponse();

        Long postsCount = postRepository.countAllByLocationStartingWith(campus.equals("global") ? "G" : "M");

        boolean isLast = false;
        if ((int) ((postsCount - 1) / 10) == page) {
            isLast = true;
        }
        pageDTO.setLast(isLast);
        pageDTO.setPosts(postsDTO);

        return pageDTO;
    }

    public List<PostsResponse> get8(String campus) {
        List<PostsResponse> postsDTO = new ArrayList<>();
        List<Post> posts = postRepository.findTop8ByLocationStartingWithOrderByCreatedAtDesc(campus.equals("global") ? "G" : "M");
        for (Post post : posts) {
            Integer chatCnt = chatRoomRepository.countByPost(post);
            PostsResponse dto = new PostsResponse();
            dto.setPostId(post.getPostId());
            dto.setUserId(post.getUser().getUserId());
            dto.setTitle(post.getTitle());
            dto.setLocation(post.getLocation());
            dto.setLocationDetail(post.getLocationDetail());
            dto.setRentalFee(post.getRentalFee());
            dto.setSecurity(post.getSecurity());
            dto.setCreatedAt(post.getCreatedAt());
            dto.setClose(post.getIsClose());
            dto.setChatCount(chatCnt);
            postsDTO.add(dto);
        }
        return postsDTO;
    }

    public void delete(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        postRepository.deleteByPostId(postId);
    }

    public void update(Integer postId, UpdatePostRequest request) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(postId).get();
        post.setTitle(request.getTitle());
        post.setLocation(request.getLocation());
        post.setLocationDetail(request.getLocationDetail());
        post.setNeedAt(request.getNeedAt());
        post.setReturnAt(request.getReturnAt());
        post.setRentalFee(request.getRentalFee());
        post.setSecurity(request.getSecurity());
        postRepository.save(post);
    }

    public void doneAndCount(Integer postId, String lenderId, String borrowerId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(postId).get();
        post.setIsClose(true);
        post.setLenderId(lenderId);
        postRepository.save(post);

        if (!userRepository.existsUserById(borrowerId)) {
            throw new UserNotFountException();
        }
        User borrower = userRepository.findById(borrowerId).get();
        borrower.setBorrowCount(borrower.getBorrowCount() + 1);
        userRepository.save(borrower);

        if (!userRepository.existsUserById(lenderId)) {
            throw new UserNotFountException();
        }
        User lender = userRepository.findById(lenderId).get();
        lender.setLendCount(lender.getLendCount() + 1);
        userRepository.save(lender);
    }

    public void done(Integer postId) {
        if (!postRepository.existsById(postId)) {
            throw new PostNotFoundException();
        }
        Post post = postRepository.findById(postId).get();
        post.setIsClose(true);
        postRepository.save(post);
    }
}