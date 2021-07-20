# 🌳우목이[우린 목표를 다 이룰 거야]

<img src = "https://user-images.githubusercontent.com/38427646/126250991-a5e359ca-0938-42cb-b074-7c7856dbdc11.png" width="400px">




## 🌳  프로젝트 개요

<b>프로젝트 기간 : 2021.01.08 ~ 2021.02.19</b>

**자신의 목표 설정을 넘어 다함께 목표를 설정하고 및 인증하는 웹 서비스**

- 자신의 도전,목표를 즐겁고 끝까지 달성하고 싶은 사람
- 같은 목표를  가진 사람들과 함께 외롭지 않게 이루고 싶은 사람
- 다양한 사람들의 도전을 보며 자극을 받으며 나아가고 싶은 사람을 위한 서비스

**디자인 컨셉** : 도전,목표를 하나의 씨앗으로 시작하여 거대한 나무를 키우는 것처럼 목표를 이룸 </b>



## 👩🏻‍💻👨🏻‍💻 우목이를 만든 사람들

| 팀원   | 역할    |
| ------ | ------- |
| 최은선 | BE      |
| 표기동 | BE      |
| 김효진 | FE      |
| 김용민 | FE,     |
| 홍지희 | FE,팀장 |



### 🌱 Tech Log  &  Cooperation

 저희의 진행 상황은 스크럼과 회의록을 통해 진행하고 있습니다. 

 스크럼은  1일 1스크럼으로  노션에 올려 이슈들을 공유하고 있으며 

 회의를 진행할 때마다 회의록을 작성하여 일차별로 정리하고 있습니다.

[프로젝트 공유문서](https://www.notion.so/A303-1d48727b951b41a18886118e55d04fb8)



### 🌱 Tech Stack

![기술스택](https://user-images.githubusercontent.com/38427646/126262711-ae506989-798f-4143-978a-882827bdd6ef.png)



### 🌱 system architecture

![시스템_구조도](/uploads/521385d54611d84f99e3e0572a5edf9f/시스템_구조도.PNG)

**## 🌳 개발목표**

<br><br>



**#### 🌱문제인식**  



새해 목표를 꾸준히 지민 성인남녀 28% 달성률이 낮은 것을 볼 수 있습니다. 

![문제인식수정1](/uploads/7b05c08559365a3cbcfbec1ea3ed5400/문제인식수정1.PNG)

![문제인식수정2](/uploads/72e9fe7a2d5dcce3c44762bab2811a10/문제인식수정2.PNG)

![해결방안](/uploads/02e6e22d840c07803e810f7097312b95/해결방안.PNG)





**#### 🌱주요 서비스 기능**

![핵심기능](/uploads/ae84ca71747ea6b30661fd8c802af08a/핵심기능.PNG)

![서비스_기능](/uploads/17b64fcc5f11b79c63f2acc7dcd5bb03/서비스_기능.PNG)

**### <summary><b>🌱ERD</b></summary>**



![erd이미지_최종](/uploads/93dd266aae0b4d6e79298ebca9fecbe8/erd이미지_최종.PNG)

</details>

<br><br> </br></br>



<br><br><br>

**#### 🌱기능**



**-** 소셜 로그인 및 회원가입 가능



**-** 목표관리 등록, 수정, 삭제 기능



**-** 인증제를 통한 목표관리 기능



**-** SNS 형식으로 팔로우, 팔로잉 기능



**-** 관심 카테고리의 챌린지가 메인에 보이는 기능



**-** 내 정보 관리기능



**-** 알림 기능 



<br><br><br>

**# [ 🌳 상세페이지 이미지 ](https://www.notion.so/7156847a4022484da2f3002e27f20941)**

<br><br>

**# [ 🌳 시연영상 ](https://www.notion.so/No-04c62b36acc64a9c9cbcb02ec6668851)**

<br><br>

**# [ 🌳 실제 홈페이지 ](http://i4a303.p.ssafy.io/)**http://i4a303.p.ssafy.io/)

<details>

 [🌱 컨벤션](https://www.notion.so/332cddb89bff4354b3aee8bc1d2746a8)

<br><br>

 [🌱 BE-Api](https://www.notion.so/a4d59235ba6f4795b0c7677414e0d766)

<br><br></br></br>



**### 🌱실행 방법** 

**##### 프론트엔드 부분**

**-** Vue.js [Vue.js 설치](https://kr.vuejs.org/v2/guide/index.html)를 통해 설치할 수 있습니다. 

**-**  ``` $npm install ```

**-** ```npm run serve``` 를 통해 실행할 수 있습니다. 

**-** localhost:8080으로 실행할 수 있습니다.



**##### 백엔드 부분** 

**-** Java(openjdk version "1.8.0_282") 설치합니다.

**-** gradle 설치

**-** DB 테이블 생성

**-** ```appication.properties``` 파일 생성  후

아래 내용에 맞게 입력

\```spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver

   spring.datasource.url=(내용 입력)

   spring.datasource.username=(내용 입력)

   spring.datasource.password=(내용 입력)

\```

**-** /backend/src/main/resource/ 아래 위치 파일을 넣습니다. 

**-** /backend/ 안에   ->   $ ./gradlew build 빌드 후

**-** /backend/build/lib 안에 

-```$ java -jar ssafy_pjt2-0.0.1-SNAPSHOT.war --server.servlet.context-path=(실행할 서버주소)```

**-** 실행



