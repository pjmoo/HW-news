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
                .formatted(System.getenv("GITHUB_REPOSITORY"))
        );
        String repo = System.getenv("GITHUB_REPOSITORY");
        if (repo == null || repo.isEmpty()) {
            throw new IllegalArgumentException("환경 변수 'GITHUB_REPOSITORY'가 설정되지 않았거나 비어 있습니다.");
        }
        this.token = System.getenv("GITHUB_TOKEN");
        if (this.token == null || this.token.isEmpty()) {
            throw new IllegalArgumentException("환경 변수 'GITHUB_TOKEN'이 설정되지 않았거나 비어 있습니다.");
        }
    }

    @Override
    public void publish(String topic, List<NewsResult> newsResults) {
//        httpClient
        String url = endpoint;
        String payload = """
                {
                "title": "%s",
                "body": "%s"
                }
                """.formatted(
                // %s -> topic. %s -> 한국기준 현재 시간
                "%s (%s)".formatted(topic, ZonedDateTime.now(ZoneId.of("Asia/Seoul"))),
                newsResults
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
}