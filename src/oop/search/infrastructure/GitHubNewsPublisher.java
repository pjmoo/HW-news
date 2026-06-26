package oop.search.infrastructure;

import oop.search.application.NewsPublisher;
import oop.search.domain.NewsResult;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class GitHubNewsPublisher extends AbstractHttpClient implements NewsPublisher {

    private static final String GITHUB_API_URL = "https://api.github.com/repos/%s/issues";
    // %s : GITHUB_REPOSITORY <- GitHub Actions가 주는 것을 그대로 쓸 예정
    private final String token; // 환경변수로 주어질 것. GitHub Actions가 주는 것을 그대로 쓸 예정

    public GitHubNewsPublisher() {
        super(GITHUB_API_URL
                .formatted(System.getenv("GITHUB_REPOSITORY") != null ? System.getenv("GITHUB_REPOSITORY").trim() : "")
        );
        String rawRepo = System.getenv("GITHUB_REPOSITORY");
        String repo = rawRepo != null ? rawRepo.trim() : null;
        if (repo == null || repo.isEmpty()) {
            throw new IllegalArgumentException("환경 변수 'GITHUB_REPOSITORY'가 설정되지 않았거나 비어 있습니다.");
        }
        String rawToken = System.getenv("GITHUB_TOKEN");
        this.token = rawToken != null ? rawToken.trim() : null;
        if (this.token == null || this.token.isEmpty()) {
            throw new IllegalArgumentException("환경 변수 'GITHUB_TOKEN'이 설정되지 않았거나 비어 있습니다.");
        }
    }

    @Override
    public void publish(String topic, List<NewsResult> newsResults) {
        String url = endpoint;

        StringBuilder sb = new StringBuilder();
        sb.append("# 📰 ").append(topic).append(" 뉴스 검색 결과\n\n");
        for (NewsResult result : newsResults) {
            sb.append("### [").append(result.title()).append("](").append(result.url()).append(")\n");
            sb.append("- **요약**: ").append(result.description()).append("\n");
            sb.append("- **발행일**: ").append(result.pubDate()).append("\n\n");
            sb.append("---\n\n");
        }
        String markdownBody = sb.toString();

        String title = "%s (%s)".formatted(topic, ZonedDateTime.now(ZoneId.of("Asia/Seoul")));

        String payload = """
                {
                "title": "%s",
                "body": "%s"
                }
                """.formatted(
                escapeJson(title),
                escapeJson(markdownBody)
        ).trim();
        HttpRequest request = HttpRequest.newBuilder()
//                .GET()
                .POST(HttpRequest.BodyPublishers.ofString(payload, StandardCharsets.UTF_8))
                .uri(URI.create(url))
//                .header("X-Naver-Client-Id", clientId)
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/vnd.github+json")
                .header("X-GitHub-Api-Version", "2022-11-28")
                .header("Content-Type", "application/json; charset=UTF-8")
                .build();

        try {
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            System.out.println("GitHub API Response Status Code: " + response.statusCode());
            if (response.statusCode() != 201) {
                System.err.println("이슈 생성 실패! HTTP 상태 코드: " + response.statusCode());
                System.err.println("응답 본문: " + response.body());
                throw new RuntimeException("GitHub API returned status code " + response.statusCode() + " instead of 201 Created.");
            } else {
                System.out.println("이슈 생성 성공!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if (ch < ' ') {
                        String t = "000" + Integer.toHexString(ch);
                        sb.append("\\u").append(t.substring(t.length() - 4));
                    } else {
                        sb.append(ch);
                    }
            }
        }
        return sb.toString();
    }
}