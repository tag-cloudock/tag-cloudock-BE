package pagether.domain.news.application;

import org.springframework.scheduling.annotation.EnableAsync;
import pagether.domain.news.domain.News;
import pagether.domain.news.dto.req.AddNewsRequest;
import pagether.domain.news.dto.res.NewsResponse;
import pagether.domain.news.dto.res.SeparatedNewsResponse;
import pagether.domain.news.exception.NewsNotFoundException;
import pagether.domain.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    public NewsResponse save(AddNewsRequest request) {
        News news = News.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        news = newsRepository.save(news);
        return new NewsResponse(news);
    }
    public SeparatedNewsResponse getAll(String userId) {
        List<News> newsDatas = newsRepository.findTop20ByOrderByCreatedAtDesc();

        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Long lastSeenNewsId = user.getLastSeenNewsId();

        List<NewsResponse> newsResponses = newsDatas.stream()
                .map(NewsResponse::new)
                .toList();

        List<NewsResponse> readNews = newsResponses.stream()
                .filter(newsResponse -> newsResponse.getNewsId() <= lastSeenNewsId)
                .collect(Collectors.toList());
        List<NewsResponse> unreadNews = newsResponses.stream()
                .filter(newsResponse -> newsResponse.getNewsId() > lastSeenNewsId)
                .collect(Collectors.toList());

        updateLastSeenNewsId(user, newsDatas);
        return new SeparatedNewsResponse(readNews, unreadNews);
    }

    private void updateLastSeenNewsId(User user, List<News> newsDatas) {
        Long latestNewsId = newsDatas.isEmpty() ? user.getLastSeenNewsId() : newsDatas.get(0).getNewsId();
        user.setLastSeenNewsId(latestNewsId);
        userRepository.save(user);
    }

    public void delete(Long newsId) {
        if (!newsRepository.existsById(newsId)) {
            throw new NewsNotFoundException();
        }
        newsRepository.deleteById(newsId);
    }
}