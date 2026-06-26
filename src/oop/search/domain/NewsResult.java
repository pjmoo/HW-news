package oop.search.domain;

// Value Object
// Immutable -> Java '변화' -> Heap 메모리의 변화를 통해 대부분 감지
// 기본 자바객체는 불변객체로 만드는 과정을 거쳐야함 -> Record는 원래 immutable하고 setter X. 멤버변수를 한 번 생성자로 만들면 수정 X.
// 1. 불러오는 API를 바탕 ***
// 2. 개념적 설계
// record 레코드명(멤버변수를 생성자의 패러미터처럼 넣는다)
// https://developers.naver.com/docs/serviceapi/search/news/news.md#%EC%9D%91%EB%8B%B5-%EC%98%88
public record NewsResult(
        String title,
        String description,
        String url,
        String pubDate
        // original url은 생략
) {
    public static void main(String[] args) {
        NewsResult result = new NewsResult("환율 1600원 되나?", "현재 1550원 간당간당하고 혹시 달러로 구독중이면 조심하세요", "https://naver.com", "2026.12.32");
        System.out.println("result = " + result);
        System.out.println("result.title = " + result.title());
//        result.title = "2000원 되나?"; // 막혀있다
    }
}