package gachonherald.domain.comment.application;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.article.domain.ArticleStatus;
import gachonherald.domain.article.dto.ArticleDTO;
import gachonherald.domain.article.dto.req.AddArticleRequest;
import gachonherald.domain.article.dto.res.ArticleResponse;
import gachonherald.domain.article.dto.res.ArticlesResponse;
import gachonherald.domain.article.exception.ArticleNotFoundException;
import gachonherald.domain.article.repository.ArticleRepository;
import gachonherald.domain.comment.domain.Comment;
import gachonherald.domain.comment.dto.CommentDTO;
import gachonherald.domain.comment.dto.req.AddCommentRequest;
import gachonherald.domain.comment.dto.res.CommentResponse;
import gachonherald.domain.comment.dto.res.CommentsResponse;
import gachonherald.domain.comment.exception.CommentNotFoundException;
import gachonherald.domain.comment.repository.CommentRepository;
import gachonherald.domain.section.domain.Section;
import gachonherald.domain.section.exception.SectionNotFoundException;
import gachonherald.domain.user.domain.Position;
import gachonherald.domain.user.domain.User;
import gachonherald.domain.user.exception.UserNotFountException;
import gachonherald.domain.user.repository.UserRepository;
import gachonherald.global.config.exception.UnauthorizedAccessException;
import gachonherald.global.config.mail.MailSendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    public static final int PAGE_SIZE_LARGE = 10;
    public static final int PAGE_SIZE_SMALL = 5;
    public CommentResponse save(AddCommentRequest request, String userId) {
        User commenter = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Article article = articleRepository.findById(request.getArticleId()).orElseThrow(SectionNotFoundException::new);


        Comment comment = Comment.builder()
                .commenter(commenter)
                .content(request.getContent())
                .article(article)
                .createdAt(LocalDateTime.now())
                .build();
        comment = commentRepository.save(comment);
        return new CommentResponse(comment);
    }

    public CommentsResponse getByArticle(Long articleId, int pageNumber) {
        List<CommentDTO> commentsResponse = new ArrayList<>();
        Article article = articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE_SMALL);
        List<Comment> comments = commentRepository.findAllByArticleOrderByCreatedAtDesc(article, pageable);
        int totalCount = commentRepository.countAllByArticle(article);
        int pageCount = (totalCount-1)/PAGE_SIZE_SMALL+1;
        for(Comment comment : comments) commentsResponse.add(new CommentDTO(comment));
        return new CommentsResponse(commentsResponse, pageCount);
    }

    public CommentsResponse getAll(int pageNumber) {
        List<CommentDTO> commentsResponse = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE_LARGE);
        List<Comment> comments = commentRepository.findAllByOrderByCreatedAtDesc(pageable);
        int totalCount = commentRepository.countAllBy();
        int pageCount = (totalCount-1)/PAGE_SIZE_LARGE+1;
        for(Comment comment : comments) commentsResponse.add(new CommentDTO(comment));
        return new CommentsResponse(commentsResponse, pageCount);
    }

    public void delete(Long commentId, String userId) {
        if (!commentRepository.existsById(commentId))
            throw new CommentNotFoundException();
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        if (comment.getCommenter().getUserId().equals(userId) || user.getPosition().equals(Position.EDITOR_IN_CHIEF)){
            commentRepository.deleteById(commentId);
            return;
        }
        throw new UnauthorizedAccessException();
    }
}