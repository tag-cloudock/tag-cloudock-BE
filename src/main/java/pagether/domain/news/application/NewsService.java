package pagether.domain.news.application;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pagether.domain.news.domain.News;
import pagether.domain.news.dto.req.AddNewsRequest;
import pagether.domain.news.dto.res.NewsResponse;
import pagether.domain.news.dto.res.NewsResponses;
import pagether.domain.news.exception.NewsNotFoundException;
import pagether.domain.news.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    public static final int PAGE_SIZE = 10;

    public NewsResponse save(AddNewsRequest request) {
        News news = News.builder()
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .build();
        news = newsRepository.save(news);
        return new NewsResponse(news);
    }
    public NewsResponses getAll(String userId, Long cursor) {
        Pageable pageable = PageRequest.of(0, PAGE_SIZE);
        List<News> newsDatas = newsRepository.findAllByNewsIdLessThanOrderByNewsIdDesc(cursor, pageable);
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        Long lastSeenNewsId = user.getLastSeenNewsId();

        List<NewsResponse> newsResponses = newsDatas.stream().map(NewsResponse::new).toList();
        List<NewsResponse> readNews = filterNewsByReadStatus(newsResponses, lastSeenNewsId,true);
        List<NewsResponse> unreadNews = filterNewsByReadStatus(newsResponses, lastSeenNewsId,false);
        updateLastSeenNewsId(user, newsDatas);
        return new NewsResponses(readNews, unreadNews, newsDatas.get(newsDatas.size()-1).getNewsId());
    }

    private List<NewsResponse>  filterNewsByReadStatus(List<NewsResponse> newsResponses, Long lastSeenNewsId, Boolean isGetReadAlert) {
        return newsResponses.stream()
                .filter(newsResponse -> isGetReadAlert ? newsResponse.getNewsId() <= lastSeenNewsId : newsResponse.getNewsId() > lastSeenNewsId)
                .collect(Collectors.toList());
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