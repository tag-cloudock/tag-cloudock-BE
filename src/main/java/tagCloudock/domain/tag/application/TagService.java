package tagCloudock.domain.tag.application;

import org.apache.commons.lang3.StringUtils;
import org.openkoreantext.processor.phrase_extractor.KoreanPhraseExtractor;
import tagCloudock.domain.news.application.NewsService;
import tagCloudock.domain.news.dto.NewsDTO;
import tagCloudock.domain.news.dto.res.NewsResponse;
import tagCloudock.domain.oauth.application.OAuthService;
import tagCloudock.domain.tag.dto.TagDTO;
import tagCloudock.domain.tag.dto.res.TagsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.openkoreantext.processor.KoreanTokenJava;
import org.openkoreantext.processor.OpenKoreanTextProcessorJava;
import org.openkoreantext.processor.tokenizer.KoreanTokenizer;

import scala.collection.Seq;

import java.util.*;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final NewsService newsService;

    public TagsResponse getTags(String stockName) {
        NewsResponse news = newsService.getNews(stockName, 1, 50);

        Map<String, Integer> wordCountMap = new HashMap<>();

        for (NewsDTO item : news.getItems()) {
            String title = item.getTitle();
            String description = item.getDescription();

            countWords(wordCountMap, title);
            countWords(wordCountMap, description);
        }

        List<Map.Entry<String, Integer>> sortedWordList = wordCountMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // 빈도수 내림차순 정렬
                .collect(Collectors.toList());

        List<TagDTO> dtos = new ArrayList<>();
        int id = 1;
        int limit = Math.min(sortedWordList.size(), 25);

        for (int i = 0; i < limit; i++) {
            Map.Entry<String, Integer> tag = sortedWordList.get(i);
            if (id++ % 2 == 1) {
                dtos.add(new TagDTO(tag.getKey(), tag.getValue()));
            } else {
                dtos.add(0, new TagDTO(tag.getKey(), tag.getValue()));
            }
        }
        return new TagsResponse(dtos);
    }

    private void countWords(Map<String, Integer> wordCountMap, String text) {
        if (StringUtils.isBlank(text)) {
            return;
        }
        CharSequence normalized = OpenKoreanTextProcessorJava.normalize(text);
        Seq<KoreanTokenizer.KoreanToken> tokens = OpenKoreanTextProcessorJava.tokenize(normalized);
        List<KoreanPhraseExtractor.KoreanPhrase> phrases = OpenKoreanTextProcessorJava.extractPhrases(tokens, true, true);

        List<String> nouns = new ArrayList<>();
        for (KoreanPhraseExtractor.KoreanPhrase token : phrases) {
            if (token.pos().id()==0) {
                nouns.add(token.text());
            }
        }
        for (String noun : nouns) {
            if (StringUtils.isNotBlank(noun)) {
                wordCountMap.put(noun, wordCountMap.getOrDefault(noun, 0) + 1);
            }
        }
    }
}