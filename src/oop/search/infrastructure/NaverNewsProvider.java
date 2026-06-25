package oop.search.infrastructure;

import oop.search.application.NewsProvider;
import oop.search.domain.NewsCategory;
import oop.search.domain.NewsResult;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

//public class NaverNewsProvider extends AbstractHttpScraper {
public class NaverNewsProvider extends AbstractHttpClient implements NewsProvider {
    //    protected AbstractHttpScraper(String endpoint) {
//        this.endpoint = endpoint;
//    }
    // 생성자 레벨에서 사용할 상수는 static
    private static final String NEWS_API_URL = "https://openapi.naver.com/v1/search/news.json";
    private final String clientId;
    private final String clientSecret;
    private final NewsCategory category;

    // clientId, clientSecret, category
    public NaverNewsProvider() {
        super(NEWS_API_URL);
        this.clientId = System.getenv("NAVER_CLIENT_ID");
        this.clientSecret = System.getenv("NAVER_CLIENT_SECRET");
        
        if (this.clientId == null || this.clientId.isEmpty()) {
            throw new IllegalArgumentException("환경 변수 'NAVER_CLIENT_ID'가 설정되지 않았거나 비어 있습니다.");
        }
        if (this.clientSecret == null || this.clientSecret.isEmpty()) {
            throw new IllegalArgumentException("환경 변수 'NAVER_CLIENT_SECRET'가 설정되지 않았거나 비어 있습니다.");
        }

        String categoryEnv = System.getenv("NEWS_CATEGORY");
        if (categoryEnv == null || categoryEnv.isEmpty()) {
            throw new IllegalArgumentException("환경 변수 'NEWS_CATEGORY'가 설정되지 않았거나 비어 있습니다.");
        }
        try {
            this.category = NewsCategory.valueOf(categoryEnv);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("NEWS_CATEGORY 값은 'SIM' 또는 'DATE'여야 합니다. 현재 값: " + categoryEnv);
        }

        System.out.println("clientId = " + (clientId.length() >= 3 ? clientId.substring(0, 3) + "..." : clientId));
        System.out.println("clientSecret = " + (clientSecret.length() >= 3 ? clientSecret.substring(0, 3) + "..." : clientSecret));
        System.out.println("category = " + category);
    }

    @Override
    public List<NewsResult> fetchNews(String searchQuery, int limit) {
        String url = endpoint + "?query="
                + URLEncoder.encode(searchQuery, StandardCharsets.UTF_8)
                + "&display=" + limit
                + "&sort=" + category.getQueryValue()
                + "&start=1";
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("X-Naver-Client-Id", clientId)
                .header("X-Naver-Client-Secret", clientSecret)
                .build();

        List<NewsResult> results = new ArrayList<>();
        try {
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            String body = response.body();
//            System.out.println("body = " + body);

            // items
            String items = body.split("items")[1]; // 0 <-> 1
            // <- items ->
//            System.out.println("items = " + items);
            String[] itemArr = items.split("},");
            for (String item : itemArr) {
//                System.out.println("item = " + item);
//                String title = item
//                        .split("\"title\":\"")[1] // 0 <-> 1 -> ["title":"]
//                        .split("\",")[0]; // ",
                String title = cleanText(cutText(item, "\"title\":\"", "\",\n"));
                String link = cleanText(cutText(item, "\"link\":\"", "\",\n"));
                String description = cleanText(cutText(item, "\"description\":\"", "\",\n"));
                // pubDate는 문자열 ""가 추가적으로 들어갈 염려가 없기 때문에 바로 "로 구분
                String pubDate = cleanText(cutText(item, "\"pubDate\":\"", "\""));
                NewsResult result = new NewsResult(title, description, link, pubDate);
                results.add(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        return List.of();
        return results;
    }

    public String cutText(String original, String prefix, String suffix) {
        return original
                .split(prefix)[1]
                .split(suffix)[0];
    }

    private String cleanText(String text) {
        if (text == null) {
            return "";
        }
        return text
                .replaceAll("<[^>]*>", "") // HTML 태그 제거
                .replace("\\/", "/") // 이스케이프된 슬래시 제거
                .replace("&quot;", "\"")
                .replace("&apos;", "'")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&");
    }

    public static void main(String[] args) {
        NewsProvider provider = new NaverNewsProvider();
        List<NewsResult> results = provider.fetchNews("프리티걸", 10);
//        System.out.println("results = " + results);
        for (NewsResult newsItem : results) {
            System.out.println("newsItem = " + newsItem);
        }
    }
}