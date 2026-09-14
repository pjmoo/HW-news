# 객체 지향 설계 기반 뉴스 스크래퍼(News Scraper) 홈워크 📰🤖

<!-- workspace-readme-learning:start -->
## 파일과 연결한 학습 안내

아래 설명은 이 폴더의 실제 소스와 빌드 설정을 기준으로 정리했습니다. 기존 소개의 기능 설명은 연결된 파일과 함께 확인할 수 있습니다.

### 주요 파일과 역할

| 파일 | 역할과 읽을 내용 |
| --- | --- |
| [src/oop/search/application/NewsService.java](<src/oop/search/application/NewsService.java>) | 업무 처리와 외부 의존성 호출 — `search` |
| [src/oop/search/application/NewsProvider.java](<src/oop/search/application/NewsProvider.java>) | 구현체가 따라야 하는 인터페이스 |
| [src/oop/search/application/NewsPublisher.java](<src/oop/search/application/NewsPublisher.java>) | 구현체가 따라야 하는 인터페이스 |
| [src/oop/search/domain/NewsCategory.java](<src/oop/search/domain/NewsCategory.java>) | main에서 실행하는 Java 실습 — `main`, `useCategory` |
| [src/oop/search/domain/NewsResult.java](<src/oop/search/domain/NewsResult.java>) | main에서 실행하는 Java 실습 — `NewsResult`, `main` |
| [src/oop/search/infrastructure/AbstractHttpClient.java](<src/oop/search/infrastructure/AbstractHttpClient.java>) | Java 타입과 동작 정의 |
| [src/oop/search/infrastructure/GitHubNewsPublisher.java](<src/oop/search/infrastructure/GitHubNewsPublisher.java>) | Java 타입과 동작 정의 — `publish` |
| [src/oop/search/infrastructure/NaverNewsProvider.java](<src/oop/search/infrastructure/NaverNewsProvider.java>) | main에서 실행하는 Java 실습 — `fetchNews`, `cutText`, `main` |
| [src/oop/search/presentation/ConsoleNewsApp.java](<src/oop/search/presentation/ConsoleNewsApp.java>) | main에서 실행하는 Java 실습 — `run`, `main` |
| [src/oop/search/presentation/ConsoleNewsPublisher.java](<src/oop/search/presentation/ConsoleNewsPublisher.java>) | main에서 실행하는 Java 실습 — `publish`, `main` |
| [src/oop/search/presentation/GitHubNewsApp.java](<src/oop/search/presentation/GitHubNewsApp.java>) | main에서 실행하는 Java 실습 — `run`, `main` |

### 실행과 설정 확인

- [src/oop/search/domain/NewsCategory.java](<src/oop/search/domain/NewsCategory.java>)의 main부터 IDE에서 실행합니다. 패키지 선언과 사용하는 JDK API를 확인합니다.
- 코드·설정에서 참조하는 환경 변수 이름: `GITHUB_REPOSITORY`, `GITHUB_TOKEN`, `NAVER_CLIENT_ID`, `NAVER_CLIENT_SECRET`, `NEWS_CATEGORY`, `NEWS_DISPLAY`, `NEWS_QUERY`. 기본값과 필수 여부는 각 참조 위치에서 확인합니다.

### 관련 PDF와 보충 설명

- [6/26 강의](<../260629_ex/새 폴더/6-26/README.md>): 예외 전달·자원 수명·파일 및 HTTP 입출력을 연결합니다.
- [7/2 강의](<../260629_ex/새 폴더/7-2/README.md>): 계층형·클린 아키텍처와 상태 관리의 책임 분리를 연결합니다.

이 링크는 구현을 이해하기 위한 관련 기초 자료입니다. 해당 강의가 이 저장소의 모든 기능이나 이후 버전의 API를 설명한다는 뜻은 아닙니다.

### 읽는 순서와 복습

- 자원 생성 → 사용 → 실패 전달 → 정리 순으로 확인합니다. 파일 부재·문자 인코딩·외부 요청 실패를 구분하고 예외 원인이 보존되는지 확인합니다.
- 요청 처리 → 유스케이스·서비스 → 포트·저장소·외부 API의 의존 방향을 읽습니다. 외부 구현을 교체할 때 바뀌는 코드와 업무 규칙을 가진 코드가 구분되는지 확인합니다.

<!-- workspace-readme-learning:end -->

인터넷 포털에서 뉴스 기사 정보를 긁어모아 가공하는 자바 콘솔 기반 프로그램으로, 깃허브 액션(GitHub Actions) CI/CD 워크플로우를 이용해 자동화 배포 및 통합 테스트를 연습하는 과제입니다.

---

## 📂 학습 파일 구성 (Files)

- [src/oop/search/](<src/oop/search>) : 신문사 검색 및 파싱 알고리즘 자바 소스코드
- [.github/workflows/news-scraper.yml](<.github/workflows/news-scraper.yml>) : 코드가 푸시될 때마다 GitHub 서버에서 자동으로 자바 컴파일 및 테스트를 돌리는 자동화 스크립트

---

## 🛠 배운 핵심 개념 (What We Learned)

- **CI/CD 파이프라인**: 깃허브 원격 저장소와 연동하여 자동으로 코드 검증을 실행하는 지속적 통합(CI) 작동 원리를 이해합니다.
- **객체 지향적 모듈화**: 뉴스 기사, 크롤러, 필터 모듈을 인터페이스 구조로 정갈하게 격리 설계합니다.

---

## 🚀 실행 및 확인 방법 (How to Run)

1. 로컬 환경에서 `src/oop/search` 디렉토리 내의 메인 실행 클래스를 켭니다.
2. 터미널 콘솔창에 뉴스 검색 결과 리스트가 정상 파싱되어 출력되는지 확인합니다.

<!-- pdf-til-supplement:start -->
## TIL 부연 설명 — PDF와 연결하기

기존 실습 내용을 이해하기 위한 PDF 기반 부연 설명이다. 아래 예시는 개념을 설명하기 위한 것이며, 이 프로젝트에서 실행해 관찰한 결과와는 구분한다. 페이지 번호는 표지를 포함한 PDF 순서다.

함께 읽을 파일: [src/oop/search/application/NewsService.java](<src/oop/search/application/NewsService.java>) · [src/oop/search/application/NewsProvider.java](<src/oop/search/application/NewsProvider.java>) · [src/oop/search/application/NewsPublisher.java](<src/oop/search/application/NewsPublisher.java>)

### 예외를 처리할 위치와 원인 보존

예외를 잡았다고 문제가 해결되는 것은 아니다. 현재 계층에서 복구할 수 있으면 처리하고, 그렇지 않으면 호출자가 판단할 수 있는 형태로 전달한다. 체크 예외를 업무 예외로 바꿀 때 원인 예외를 함께 보관하면 최초 실패 지점을 추적할 수 있다.

**예시로 이해하기:** 파일 읽기 실패를 빈 문자열로 바꾸면 “파일이 비어 있음”과 “읽지 못함”을 구분할 수 없다. 실패 원인과 작업 맥락을 남기고 사용자에게는 필요한 안내를 전달한다. catch에서 성공처럼 계속 진행하는 코드가 없는지 확인한다.

근거: 222-1 Java 예외 처리 — [12쪽](<../260629_ex/새 폴더/6-26/222-1_Java_예외_처리.pdf#page=12>) · [14쪽](<../260629_ex/새 폴더/6-26/222-1_Java_예외_처리.pdf#page=14>) · [15쪽](<../260629_ex/새 폴더/6-26/222-1_Java_예외_처리.pdf#page=15>) · [16쪽](<../260629_ex/새 폴더/6-26/222-1_Java_예외_처리.pdf#page=16>)

### 문자 인코딩과 자원 수명

바이트 스트림은 원시 데이터를, Reader·Writer는 문자 데이터를 다룬다. 텍스트 파일의 바이트와 Java 문자열 사이에는 인코딩·디코딩 단계가 있으므로 양쪽 문자셋을 맞춰야 한다. 이미지 파일에 문자 스트림을 사용하면 원본 바이트가 변형될 수 있다.

**예시로 이해하기:** 텍스트를 UTF-8로 저장했다면 읽을 때도 UTF-8을 지정한다. try-with-resources로 닫을 자원을 묶으면 성공·실패 양쪽에서 정리할 수 있다. 큰 파일은 한 번에 메모리에 올리는 방식과 버퍼로 나누어 읽는 방식의 메모리 사용 차이를 생각해 본다.

근거: 222-2 Java File I O — [4쪽](<../260629_ex/새 폴더/6-26/222-2_Java_File_I_O.pdf#page=4>) · [5쪽](<../260629_ex/새 폴더/6-26/222-2_Java_File_I_O.pdf#page=5>) · [8쪽](<../260629_ex/새 폴더/6-26/222-2_Java_File_I_O.pdf#page=8>)

### MVC와 계층형 설계의 역할 차이

MVC는 입력 제어·데이터·화면의 역할을 나누고, 계층형 설계는 웹 처리·업무 규칙·저장소 접근의 책임을 나눈다. 따라서 MVC와 Controller–Service–Repository 구조를 함께 사용할 수 있다. 클린 아키텍처에서는 업무 규칙이 외부 구현을 직접 참조하지 않도록 의존 방향을 조정한다.

**예시로 이해하기:** 컨트롤러는 “요청이 어떤 형식인가”, 서비스는 “이 작업이 허용되는가”, 저장소는 “어떻게 읽고 쓰는가”를 맡도록 생각한다. 외부 AI 제공자를 교체할 때 요청 API까지 바꿔야 한다면 제공자 전용 타입이 경계를 넘는지 살펴본다.

근거: 232 소프트웨어 아키텍처 패턴 — [4쪽](<../260629_ex/새 폴더/7-2/232_소프트웨어_아키텍처_패턴.pdf#page=4>) · [12쪽](<../260629_ex/새 폴더/7-2/232_소프트웨어_아키텍처_패턴.pdf#page=12>) · [15쪽](<../260629_ex/새 폴더/7-2/232_소프트웨어_아키텍처_패턴.pdf#page=15>) · [19쪽](<../260629_ex/새 폴더/7-2/232_소프트웨어_아키텍처_패턴.pdf#page=19>)

### 쿠키는 전달 수단, 세션은 서버 상태

쿠키는 브라우저가 조건에 맞는 요청에 실어 보내는 값이고 세션은 서버가 식별자에 연결해 보관하는 상태다. 세션 방식도 브라우저가 세션 ID를 제시하므로 식별자 보호와 만료 처리가 필요하다. 인증으로 신원을 확인한 후 실제 자원에 대한 인가를 별도로 판단한다.

**예시로 이해하기:** 로그인 → 세션 생성 → 이후 요청의 세션 조회 → 로그아웃 시 무효화 순서로 읽는다. HttpOnly는 자바스크립트의 쿠키 읽기를 제한하지만 브라우저의 자동 전송은 막지 않는다. Secure·SameSite·CSRF 정책은 요청을 보내는 방식과 함께 이해한다.

근거: 233-1 쿠키와 세션 — [4쪽](<../260629_ex/새 폴더/7-2/233-1_쿠키와_세션.pdf#page=4>) · [6쪽](<../260629_ex/새 폴더/7-2/233-1_쿠키와_세션.pdf#page=6>) · [10쪽](<../260629_ex/새 폴더/7-2/233-1_쿠키와_세션.pdf#page=10>) · [12쪽](<../260629_ex/새 폴더/7-2/233-1_쿠키와_세션.pdf#page=12>) · [17쪽](<../260629_ex/새 폴더/7-2/233-1_쿠키와_세션.pdf#page=17>)

<!-- pdf-til-supplement:end -->
