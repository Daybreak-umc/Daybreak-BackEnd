# 🌅 Daybreak - AI 기반 목표 달성 플랫폼

UMC 9기 해커톤 프로젝트로 개발된 AI 기반 개인 목표 달성 지원 플랫폼입니다.

## 📋 프로젝트 개요

Daybreak는 사용자가 설정한 목표를 AI가 분석하여 체계적인 미션과 계획을 제공하는 서비스입니다. 
OpenAI와 Upstage LLM을 활용하여 개인화된 목표 달성 로드맵을 생성하고, 카카오 소셜 로그인을 통해 간편한 사용자 경험을 제공합니다.

## 🛠 기술 스택

### Backend
- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Security** (JWT + OAuth2)
- **Spring Data JPA**
- **MySQL 8.0**

### AI/ML
- **OpenAI GPT-4o-mini**
- **Upstage LLM API**

### Infrastructure
- **Docker & Docker Compose**
- **Gradle**

### Documentation
- **Swagger/OpenAPI 3.0**

## 🏗 프로젝트 구조

```
src/
├── main/
│   ├── java/umc9th_hackathon/daybreak/
│   │   ├── domain/
│   │   │   ├── member/          # 회원 관리
│   │   │   └── mission/         # 미션/목표 관리
│   │   │       ├── controller/  # REST API 컨트롤러
│   │   │       ├── service/     # 비즈니스 로직
│   │   │       ├── entity/      # JPA 엔티티
│   │   │       ├── repository/  # 데이터 접근 계층
│   │   │       ├── dto/         # 데이터 전송 객체
│   │   │       └── converter/   # DTO 변환기
│   │   └── global/
│   │       ├── config/          # 설정 클래스
│   │       ├── jwt/             # JWT 인증
│   │       ├── oauth/           # OAuth2 설정
│   │       ├── apiPayload/      # API 응답 형식
│   │       └── exception/       # 예외 처리
│   └── resources/
│       ├── application.yml      # 애플리케이션 설정
│       └── templates/           # Thymeleaf 템플릿
```

## 🚀 주요 기능

### 1. 사용자 인증
- 카카오 소셜 로그인
- JWT 기반 인증 시스템
- 자동 회원가입

### 2. AI 기반 목표 설정
- 사용자 목표 분석
- 카테고리별 목표 분류
- AI 추천 목표 생성

### 3. 미션 관리
- 개인화된 미션 생성
- 미션 완료 추적
- 타임라인 기반 진행상황 조회

### 4. 계획 수립
- AI 기반 주간/일간 계획 생성
- 목표 달성을 위한 단계별 로드맵
- 진행상황 모니터링

## 🔧 설치 및 실행

### 1. 사전 요구사항
- Java 17+
- Docker & Docker Compose
- MySQL 8.0

### 2. 환경 설정

```bash
# 저장소 클론
git clone <repository-url>
cd daybreak

# 환경변수 설정
cp .env.example .env
```

`.env` 파일에서 다음 값들을 설정하세요:

```env
# Database Configuration
DB_URL=jdbc:mysql://mysql:3306/hackaton_team_a
DB_USER=root
DB_PW=your_database_password

# Kakao OAuth Configuration
REST_API_KEY=your_kakao_rest_api_key
KAKAO_REDIRECT_URI=http://localhost:8080/callback

# OpenAI API Configuration
OPENAI_API_KEY=your_openai_api_key

# Upstage API Configuration
UPSTAGE_API_KEY=your_upstage_api_key

# Server Configuration
SERVER_BASE_URL=http://localhost:8080
```

### 3. Docker Compose로 실행

```bash
# 애플리케이션 빌드 및 실행
docker-compose up --build

# 백그라운드 실행
docker-compose up -d
```

### 4. 로컬 개발 환경

```bash
# Gradle 빌드
./gradlew build

# 애플리케이션 실행
./gradlew bootRun
```

## 📚 API 문서

애플리케이션 실행 후 다음 URL에서 API 문서를 확인할 수 있습니다:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

### 주요 API 엔드포인트

```
# 인증
POST /api/v1/auth/login          # 로그인
POST /api/v1/auth/signup         # 회원가입

# 미션 관리
GET  /api/v1/missions            # 미션 목록 조회
PATCH /api/v1/missions/complete  # 미션 완료 처리
DELETE /api/v1/missions/delete   # 목표 삭제

# 계획 관리
POST /api/v1/mission/plan        # 계획 생성
POST /api/v1/mission/random      # 랜덤 목표 생성
GET  /api/v1/mission/timeline    # 타임라인 조회

# 주간 미션
POST /api/v1/weekly-mission      # 주간 미션 생성
GET  /api/v1/weekly-mission      # 주간 미션 조회
```

## 🗄 데이터베이스 스키마

### 주요 엔티티
- **Member**: 사용자 정보
- **Category**: 목표 카테고리
- **MissionSelection**: 사용자 목표 선택
- **Plan**: 목표 달성 계획
- **Mission**: 개별 미션

## 🔐 보안

- JWT 토큰 기반 인증
- OAuth2 소셜 로그인 (카카오)
- 토큰 블랙리스트 관리
- CORS 설정
- 환경변수를 통한 민감정보 관리

## 🧪 테스트

```bash
# 전체 테스트 실행
./gradlew test

# 특정 테스트 클래스 실행
./gradlew test --tests "DaybreakApplicationTests"
```

## 📝 개발 가이드

### 코드 컨벤션
- Java 17 기준
- Lombok 사용으로 보일러플레이트 코드 최소화
- RESTful API 설계 원칙 준수
- 계층형 아키텍처 (Controller → Service → Repository)

### 브랜치 전략
- `main`: 프로덕션 브랜치
- `develop`: 개발 브랜치
- `feature/*`: 기능 개발 브랜치

## 🤝 기여하기

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.

## 👥 팀 정보

UMC 9기 해커톤 Team A

## 📞 문의

프로젝트에 대한 문의사항이 있으시면 이슈를 생성해 주세요.

---

⭐ 이 프로젝트가 도움이 되었다면 스타를 눌러주세요!