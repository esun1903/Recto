# 🤳 RECTO (RECord your moment in phoTO) 
![RECTO](https://i.imgur.com/zWTrjIc.png)

### ❓Recto 서비스란?
**움직이는 AR 포토카드 제작 안드로이드 애플리케이션**
- 아날로그의 감성과 디지털의 편리함을 동시에 향유하는 서비스
- 사용자가 자신의 이야기를 움직이는 포토카드에 담을 수 있도록 도와주는 서비스

<br>

###  📅프로젝트 기간 
**2021.04.12 - 2021.05.28 (총 7주)** 
2021. 05. 21. 서울 2반 최종발표 <br>
2021. 05. 28. 서울 지역 본선발표<br>
2021. 06. 01. 전국 결선발표<br>

<br>

### 팀 소개 - 가린지선다
**삼성청년SW아카데미 SSAFY 4기 2학기 자율프로젝트 서울 2반 1등 (전국 4등)**

| 팀원   | 역할 | Github 
| ------ | ---- | ------ |
|최은선 | 팀장, BE, AR, 🐻 안드로이드 앱 기획 및 개발 |[@esun1903](https://github.com/esun1903)   |
|김현지 | FE, 🔧 안드로이드 앱 기획 및 개발, 최종·본선·결선 발표 |[@khyunji](https://github.com/khyunji)   |        |
|박예린 | FE, AR, 👻 안드로이드 앱 기획 및 개발 |[@linibell](https://github.com/linibell)   |
|신가영 | FE, 🔧 안드로이드 앱 기획 및 개발, Jira 담당자, UCC |[@rkdud5253](https://github.com/rkdud5253)|
|최다애 | BE, AR, 🐻 안드로이드 앱 기획 및 개발 |  [@DAAECHOI](https://github.com/DAAECHOI)   |



## 📑 목차

### 💻 [프로젝트 소개](#프로젝트-소개)

### ✨ [주요 기능](#주요기능)

### 💡 [서비스 아키텍쳐](#서비스-아키텍쳐)

### 🕹 [구성 요소](#구성-요소)
- Frontend
- Backend

### 🛠 [코드 실행 방법](#코드-실행-방법)
- Android App
- Web

### 📌 [Git Branch 전략](#Git-Branch-전략)

<br>

## 프로젝트 소개
<div align=center>
    <img src="https://img.shields.io/badge/Platform- Andorid App, Web-darkred">
    <img src="https://img.shields.io/badge/Framework-Android Studio-orange">
    <img src="https://img.shields.io/badge/Framework-AR Core, Mobile Vision-yellow">
    <img src="https://img.shields.io/badge/Framework-SpringBoot-green">
    <img src="https://img.shields.io/badge/Database-MySQL-darkgreen">
    <img src="https://img.shields.io/badge/Server-AWS-blue">
    <img src="https://img.shields.io/badge/Language-Java, Kotlin-navy">
    <img src="https://img.shields.io/badge/Swagger-valid-purple">
</div>

<br>

### 🌱주요기능
---
**1. 포토카드 제작**🖼: 유저가 원하는 사진과 동영상, 문구를 등록하면 디자인을 입혀 커스텀 AR 포토카드를 제작해준다.<br>
**2. 포토카드 인식**🤳: 해당 포토카드를 애플리케이션 내 카메라로 비추면, 사진을 인식하여 등록된 영상을 재생해준다.<br>
**3. 포토카드 선물**🎁: 포토카드를 제작한 유저와 인식한 유저가 다를 시, 자동으로 선물받은 MOMENT에 저장된다.<br>

<br>

### 🌱구글 플레이 스토어 앱 출시
---
![RECTO1](https://i.imgur.com/R3u65np.png)
![RECTO2](https://i.imgur.com/znj46T8.png)
![RECTO3](https://i.imgur.com/DZLNWCO.png)

<br>

### 🎞UCC
---
[YouTube - RECTO UCC 영상 보러가기](https://youtu.be/VdVEbeXGlbk)
<br>
[YouTube - RECTO 시연 영상 보러가기](https://youtu.be/7mLLfbH1-f4)
<br>
<br>

### 🌱와이어프레임
---
![와이어프레임](https://i.imgur.com/GyW0c85.png)
[Figma - RECTO 와이어프레임 보러가기](https://www.figma.com/file/UgeCtS5c4S3Cr1uAkOTlIZ/RECTO?node-id=0%3A1)

<br>

### 🌱상세 페이지
---
![메인화면](https://i.imgur.com/srUjpAh.png)
- 메인 탭
    - 로그인한 사용자 전용 화면 : 가장 최근에 제작한 다섯 장의 포토카드가 최신순으로 노출
    - 비로그인 사용자 전용 화면 : 렉토에서 기본적으로 제공하는 자료실 포토카드 다섯장 노출
<br>

![카드제작1](https://i.imgur.com/yf4Lrho.png)
- 카드 제작 탭
    - private/public 옵션 선택 가능
    - private 선택 시, 비밀번호가 설정된 포토카드 제작
    - public 선택 시, 비밀번호가 없는 포토카드 제작
<br>

![카드제작2](https://i.imgur.com/P4OZbn5.png)
- 카드 제작 탭
    - 확장형/문구형 옵션 선택 가능
    - 확장형 선택 시, 문구가 없는 포토카드 제작
    - 문구형 선택 시, 문구가 있는 포토카드 제작
<br>

![카드제작3](https://i.imgur.com/rhFCbNZ.png)
- 카드 제작 탭
    - 포토카드 인식 시, 재생될 영상 등록
<br>

![카드제작4](https://i.imgur.com/ykGFehH.png)
- 카드 제작 탭
    - 포토카드에 출력될 사진 등록
<br>

![카드제작5](https://i.imgur.com/2rr2rob.png)
- 카드 제작 탭
    - 이전 단계 선택에 따라 옵션이 변경됨
<br>

![카드제작6](https://i.imgur.com/GiWvSiY.png)
- 카드 제작 탭
    - 카드 제작 완성본
<br>

![마이페이지1](https://i.imgur.com/ZB7rgiP.png)
- 마이페이지 탭 (YOU MADE IT! -> 당신의 MOMENT 로 변경)
    - 카드 제작이 완료되면 메인 페이지에 최신 카드 노출
    - 마이페이지에서 내가 만든 카드 반영
<br>

![마이페이지2](https://i.imgur.com/0FZb7b4.png)
- 마이페이지 탭
    - 원하는 카드 선택하면 카드 상세 페이지로 이동
    - 좌측 하단의 DOWNLOAD 버튼을 눌러 스마트폰 갤러리에 저장
    - 우측 하단의 DELETE 버튼을 눌러 삭제 가능
<br>

![카드인식1](https://i.imgur.com/JndCAaw.png)
- 카드인식 탭
    - 포토카드의 고유번호 인식 후, 고유번호가 동일하다면 NEXT 버튼 클릭
    - public 포토카드의 경우, 비밀번호 없이 포토카드의 사진 위에 동영상 재생
<br>

![카드인식2](https://i.imgur.com/yeCnO1V.png)
- 카드인식 탭
    - 포토카드의 고유번호 인식 후, 고유번호가 동일하다면 NEXT 버튼 클릭
    - private 포토카드의 경우, 비밀번호 입력 후, 포토카드의 사진 위에 동영상 재생
<br>

![자료실](https://i.imgur.com/E1Qvr9M.png)
- 자료실 탭
    - 자료실은 Recto에서 무료로 제공하는 포토카드들이 있음
    - 로그인을 하지 않은 경우에도 둘러볼 수 있다
    - 자유롭게 다운로드 받고 인식이 가능
<br>

![선물하기1](https://i.imgur.com/b7aQpq2.png)
- 마이페이지 탭 (YOU MADE IT! -> 당신의 MOMENT 로 변경)
   - 사용자가 직접 제작한 카드들을 모두 확인 가능
- 마이페이지 탭 (SOMEONE GAVE YOU! -> 선물받은 MOMENT 로 변경)
   - 포토카드를 제작한 유저와 인식한 유저가 다르다면 자동으로 선물 받은 포토카드로 저장
<br>

![선물하기2](https://i.imgur.com/CSnbclG.png)
- 카드인식 탭, 마이페이지 탭
    - 카드인식 탭에서 선물 받은 카드를 인식하면 마이페이지 탭의 선물받은 MOMENT에 자동으로 저장
    - 선물 받은 카드 상세 페이지에서 선물을 준 유저의 닉네임 확인 가능
    - 하단의 DELETE 버튼을 눌러 삭제 가능
<br>



## 💡서비스 아키텍쳐
![image](https://user-images.githubusercontent.com/38427646/124543380-1e35c880-de60-11eb-875d-720781dba3a1.png)

<br>

## ERD
![Recto-Erd](https://user-images.githubusercontent.com/38427646/124544206-a8caf780-de61-11eb-84e4-957add6dea9e.png)

<br>


## 🕹구성 요소

### Frontend

|   Android 라이브러리   | Version | Comment                                   |
| :----------------: | :-----: | :---------------------------------------|
|  OpenJDK   |     1.8       | Java                    |
|  Kotlin   |     1.5.0       | AR Core 및 Mobile Vision 구현 언어        |
| AR Core | -  | 증강현실 애플리케이션 구축             |
| Mobile Vision |  3.2.0  | 포토카드의 고유번호 인식 |
| retrofit |  2.8.1  | 서버로 요청 전송                  |
| firebase |  21.0.1  | 회원 기능 구현         |
| Lombok |  -  | Getter, Setter 등 빠른 구현 |


### Backend

| 기술 스택   |   Version   | Comment                                    |
| :--------: | :---------: | :----------------------------------------- |
|  OpenJDK   |     1.8       | Java                    |
| SpringBoot |    2.4.5    | 웹 애플리케이션 개발              |
|   Maven    |    4.0.0    | 프로젝트 빌드 및 라이브러리 관리 도구 |
|   MySQL    |    4.4.3    | DataBase           |
|   Mybatis  |    2.1.4    | 서버와 DB의 연동              |
|   Swagger    |   1.5.21    | RESTful API를 문서화하고 관리                |
|   Ubuntu   | 20.04.2 LTS | 서비스 제공을 위한 리눅스 서버 구축         |
|   Docker   |   20.10.2   | 컨테이너화 된 애플리케이션 관리            |
|   Nginx    |   1.19.6    |  클라이언트 웹 서버 사용                |


|  Spring 라이브러리   | Version | Comment                     |
| :-----------------: | :-----: | :-------------------------- |
|     Spring Web      |    -    | Rest API 서버 구축          |
| Spring Data MySQL   |    -    | MySQL 연결                |
|   Spring Data JPA   |    -    | DB와의 연결 관리            |
|       Lombok        |    -    | Getter, Setter 등 빠른 구현 |
|       Gson        |   2.8.6    | Java 오브젝트를 쉽게 JSON으로 변환 |
|       JWT      |    -    | Json 포맷을 이용하여 사용자 속성을 저장하는 Claim 기반의 Web Token |



<br>

## 🛠코드 실행 방법

### Android 설정

- 프로젝트는 Android 7.0 이상 버전에서 테스트 진행
- Clone Repository

```bash
git clone https://lab.ssafy.com/s04-final/s04p31a204.git
cd s04p31a204/android
```

- 빌드

    usb로 안드로이드 기기 연결 후, 안드로이드 스튜디오 내 build 버튼 클릭
    [연결 방법](https://jamesdreaming.tistory.com/207) 

### Server 설정

- 데이터베이스 설치, 실행

```bash
docker run --name recto -p 3306:3306 -e MYSQL_ROOT_PASSWORD=비밀번호 -d mysql
```

- Docker 네트워크 생성

```bash
docker network create recto
```

- Dockerfile 작성

```
FROM openjdk:8
VOLUME /tmp
EXPOSE 8080
ADD target/recto-0.0.1-SNAPSHOT.jar recto.jar
ENTRYPOINT ["java", "-jar", "/recto.jar"]
```

- 빌드 및 실행

```bash
# .jar file build
./mvnw package

cp target/*.jar <Dockerfile 경로>/target/recto.jar

cd <Dockerfile 경로>

# docker image build
docker build -t springboot .

# docker run
docker run -p 8000:8000 --name rectoa204 --network recto -v springboot
```
 
---
<br>

## 📌 Git Branch 전략

### branch

```markdown
(master) -> (develop) -> (feature/[frontend/backend]/feature name)
```

* master : 배포 가능한 상태로 유지
* develop : 개발용 최상위 branch
* fetaure : 기능별 branch

### commit
: commit message를 정형화하고 효율적으로 관리하기 위하여 **commit 자동화 라이브러리 `git-cz`** 도입

```javascript
"types": {
  "feat": {
    "description": "새로운 기능 추가",
    "emoji": "✨",
    "value": "feat"
  },
  "chore": {
      "description": "자잘한 작업",
      "emoji": "🧵",
      "value": "chore"
  },
  "fix": {
      "description": "버그 수정",
      "emoji": "👾",
      "value": "fix"
    },
  "refactor": {
    "description": "성능 개선, 리팩토링",
    "emoji": "🐙",
    "value": "refactor"
  },
  "style": {
    "description": "코드에 영향을 주지 않는 UI/UX/디자인 변경사항",
    "emoji": "🎨",
    "value": "style"
  },
  "docs": {
    "description": "문서 수정",
    "emoji": "📝",
    "value": "docs"
  },
  "build": {
    "description": "시스템 또는 외부 종속성에 영향을 미치는 변경사항 (npm, gulp, yarn 레벨)",
    "emoji": "🧱",
    "value": "build"
  }
}
```
<br>

1. git-cz 설치
```bash
npm install -g git-cz
```
2. `changelog.config.js` 파일을 생성한 뒤 커스텀
> 이 때, 파일이 부모 폴더에 위치하여야 한다.
3. commit 시 git add 후 git commit 대신 `git cz` 명령어 사용


### merge

```markdown
- Merge Request 요청 전 현재 작업 진행 상황 공유
- 권한은 모두가 가지고 있지만 웬만하면 담당자가 직접 merge
- 다른 사람의 MR에 적극적으로 리뷰하고, 댓글 남기기 (ex. LGTM😊)
- merge 후 불필요한 branch 지우기 (✅ Remove source branch when merge request is accepted. 옵션 체크하기)
```