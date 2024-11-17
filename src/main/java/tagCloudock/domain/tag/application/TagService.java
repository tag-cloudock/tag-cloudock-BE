package tagCloudock.domain.tag.application;

import tagCloudock.domain.tag.dto.res.TagsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    public TagsResponse getTags(int stockId) {
        return new TagsResponse();
    }
}