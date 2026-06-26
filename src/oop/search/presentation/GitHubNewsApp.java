package oop.search.presentation;

import oop.search.application.NewsService;
import oop.search.infrastructure.GitHubNewsPublisher;
import oop.search.infrastructure.NaverNewsProvider;

public class GitHubNewsApp {
    private final NewsService newsService;

    public GitHubNewsApp(NewsService newsService) {
        this.newsService = newsService;
    }

    public void run() {
        String rawKeyword = System.getenv("NEWS_QUERY");
        String rawLimit = System.getenv("NEWS_DISPLAY");
        String keyword = rawKeyword != null ? rawKeyword.trim() : null;
        String limit = rawLimit != null ? rawLimit.trim() : null;

        if (keyword == null || keyword.isEmpty()) {
            throw new IllegalArgumentException("환경 변수 'NEWS_QUERY'가 설정되지 않았거나 비어 있습니다.");
        }
        if (limit == null || limit.isEmpty()) {
            throw new IllegalArgumentException("환경 변수 'NEWS_DISPLAY'가 설정되지 않았거나 비어 있습니다.");
        }

        int limitInt;
        try {
            limitInt = Integer.parseInt(limit);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("NEWS_DISPLAY 값은 숫자여야 합니다. 현재 값: " + limit);
        }

        newsService.search(keyword, limitInt);
    }

    public static void main(String[] args) {
        NewsService newsService = new NewsService(
                new NaverNewsProvider(), // 그대로 두고
//                new ConsoleNewsPublisher()
                new GitHubNewsPublisher()
        );
        GitHubNewsApp app = new GitHubNewsApp(newsService);
        app.run();
    }
}