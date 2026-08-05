# 객체 지향 설계 기반 뉴스 스크래퍼(News Scraper) 홈워크 📰🤖

인터넷 포털에서 뉴스 기사 정보를 긁어모아 가공하는 자바 콘솔 기반 프로그램으로, 깃허브 액션(GitHub Actions) CI/CD 워크플로우를 이용해 자동화 배포 및 통합 테스트를 연습하는 과제입니다.

---

## ?뱛 ?숈뒿 ?뚯씪 援ъ꽦 (Files)

- [src/oop/search/](file:///C:/workspace/HW-news/src/oop/search/) : 신문사 검색 및 파싱 알고리즘 자바 소스코드
- [.github/workflows/news-scraper.yml](file:///C:/workspace/HW-news/.github/workflows/news-scraper.yml) : 코드가 푸시될 때마다 GitHub 서버에서 자동으로 자바 컴파일 및 테스트를 돌리는 자동화 스크립트

---

## ?썱 諛곗슫 ?듭떖 媛쒕뀗 (What We Learned)

- **CI/CD 파이프라인**: 깃허브 원격 저장소와 연동하여 자동으로 코드 검증을 실행하는 지속적 통합(CI) 작동 원리를 이해합니다.
- **객체 지향적 모듈화**: 뉴스 기사, 크롤러, 필터 모듈을 인터페이스 구조로 정갈하게 격리 설계합니다.

---

## ?? ?ㅽ뻾 諛??뺤씤 諛⑸쾿 (How to Run)

1. 로컬 환경에서 `src/oop/search` 디렉토리 내의 메인 실행 클래스를 켭니다.
2. 터미널 콘솔창에 뉴스 검색 결과 리스트가 정상 파싱되어 출력되는지 확인합니다.
