package tagCloudock.domain.news.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import tagCloudock.domain.news.dto.NewsDTO;
import tagCloudock.domain.news.dto.res.NewsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class NewsService {

    private final ObjectMapper objectMapper;
    public static final String clientId = "McKRDuzTjsGIXWqY8gzO"; //애플리케이션 클라이언트 아이디
    public static final String clientSecret = "jbEB7LaBJL"; //애플리케이션 클라이언트 시크릿
    public static final int PAGE_SIZE = 10;

//    public NewsResponse getList(String tag, int pageNumber) {
//        List<NewsDTO> response = new ArrayList<>();
//        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
//        int totalCount = 10;
//        int pageCount = (totalCount-1)/PAGE_SIZE+1;
//        response.add(new NewsDTO("url", "title"));
//        return new NewsResponse(response, pageCount);
//    }

    public NewsResponse getNews(String tag, int start, int display) {

        String text = null;
        try {
            text = URLEncoder.encode(tag, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }
        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text + "&start=" + start + "&display=" + display + "&sort=sim";    // JSON 결과
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);
        System.out.println(responseBody);
        return convertJsonToObject(responseBody);
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }

    public NewsResponse convertJsonToObject(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, NewsResponse.class);
        } catch (IOException e) {
            throw new RuntimeException("Json을 Object로 변환하는데 실패했습니다.", e);
        }
    }

}