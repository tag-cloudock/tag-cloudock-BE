package tagCloudock.domain.news.application;

import tagCloudock.domain.news.dto.NewsDTO;
import tagCloudock.domain.news.dto.res.NewsResponse;
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
public class NewsService {
    public static final int PAGE_SIZE = 10;

    public NewsResponse getList(String tag, int pageNumber) {
        List<NewsDTO> response = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        int totalCount = 10;
        int pageCount = (totalCount-1)/PAGE_SIZE+1;
        response.add(new NewsDTO(1L, "url", "title"));
        return new NewsResponse(response, pageCount);
    }
}