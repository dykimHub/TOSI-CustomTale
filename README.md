# :rabbit: TOSI(The Only Story In the world)를 소개합니다. :tada:

<div align="center">
<img src="assets/tosi.png" width="" height="150"></img>

**TOSI는 어린이들이 혼자서도 재미있게 독서할 수 있는 인터랙티브 동화 구연 서비스입니다.** <br>
저희 서비스를 통해 '토씨 하나 빼놓지 않고' 이야기에 집중할 수 있습니다. <br>
**TOSI-CustomTale**은 토씨에서 커스텀 동화와 관련된 기능을 **커스텀 동화 서비스**로 분리한 프로젝트 입니다.

</div>

# :sparkle: 서비스 목표

동화 **등장인물의 이름을 아이의 이름으로 바꿔** TTS로 읽어주며, <br>
아이 혼자서도 동화를 즐길 수 있어서서 미디어 소비 대신 독서 습관 형성을 돕습니다.

동화가 끝나면 OpenAI API를 활용해 **원하는 등장인물과 채팅**하면서, <br>
이야기에 능동적으로 참여하여 더 몰입감 있는 독서 경험을 제공합니다.

**원하는 키워드와 배경**으로 OpenAI API를 활용해 **커스텀 동화를 제작**하면서, <br>
매번 새로운 이야기와 삽화로 아이의 상상력을 자극합니다.

</div>

# :date: 기간

_SSAFY 10기 공통 프로젝트_  
**2024.01.03 - 2024.02.16 (7주)**  
 우수상🏆

_1차 리팩토링_  
**24.09.09 - 24.10.18**

_2차 리팩토링_  
**25.02.06 - 25.02.23**

## :computer: Team. 먼똑귀

| 이름         | 역할          | 기능                                                                                                                                                                                                |
| ------------ | ------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| 천우진(팀장) | BE, FE        | 일반 동화 관리, 메인페이지 UI 구성                                                                                                                                                                  |
| 우지민       | BE, FE        | TTS 재생/정지/배속/볼륨 적용, TTS 제어 UI 구성                                                                                                                                                      |
| 김다윤       | BE, FE, INFRA | 등장인물을 회원 이름으로 변경, 회원 관심 동화 관리, 이름 선택 및 동화책 UI 구성 <br> 1차 리팩토링: MSA 마이그레이션, 쿠버네티스 도입, AWS EKS 배포<br> 2차 리팩토링: Redis 도입, MSA 공통 로직 설계 |
| 양성주       | BE, FE        | 커스텀 동화 생성 및 저장, (비)공개 커스텀 동화 관리, 커스텀 동화 제작 UI 구성                                                                                                                       |
| 이아진       | BE, FE, INFRA | 등장인물과의 채팅, 채팅방 UI 구성, AWS EC2 배포                                                                                                                                                     |
| 김소연       | BE, FE        | JWT 기반 회원 인증 및 인가, 로그인/회원가입/마이페이지 UI 구성                                                                                                                                      |

## :pushpin: TOSI 서비스 아키텍처

<img src="assets/tosi_msa_customtale.png" alt="System Architecture" width="700"/>

## :deciduous_tree: TOSI-CustomTale 기술 스택

| Section      | Stack                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| ------------ | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Backend**  | ![Spring Boot](https://img.shields.io/badge/spring%20boot-%236DB33F.svg?style=for-the-badge&logo=springboot&logoColor=white) ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![OpenAI](https://img.shields.io/badge/OpenAI_API-F4F4F5?style=for-the-badge&logo=openai&logoColor=black) ![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white) ![querydsl](https://img.shields.io/badge/QueryDSL-007ACC.svg?style=for-the-badge&logo=&logoColor=white)                                                                                                                          |
| **Database** | ![MySQL](https://img.shields.io/badge/MySQL-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white) ![Redis](https://img.shields.io/badge/Redis-DC382D.svg?style=for-the-badge&logo=redis&logoColor=white)                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| **Infra**    | ![Docker](https://img.shields.io/badge/Docker-2496ED.svg?style=for-the-badge&logo=docker&logoColor=white) ![Kubernetes](https://img.shields.io/badge/kubernetes-%23326CE5.svg?style=for-the-badge&logo=kubernetes&logoColor=white) ![Amazon RDS](https://img.shields.io/badge/Amazon%20RDS-527FFF?style=for-the-badge&logo=amazonrds&logoColor=white) ![Amazon S3](https://img.shields.io/badge/Amazon%20S3-569A31.svg?style=for-the-badge&logo=amazons3&logoColor=white) ![Route 53](https://img.shields.io/badge/Route%2053-6A34D1.svg?style=for-the-badge&logo=amazonroute53&logoColor=white) ![AWS EKS](https://img.shields.io/badge/AWS%20EKS-%23FF9900.svg?style=for-the-badge&logo=amazoneks&logoColor=white) |
| **DevTool**  | ![GitHub](https://img.shields.io/badge/GitHub-181717.svg?style=for-the-badge&logo=github&logoColor=white) ![GitHub Actions](https://img.shields.io/badge/GitHub%20Actions-2088FF.svg?style=for-the-badge&logo=githubactions&logoColor=white) ![OpenAPI](https://img.shields.io/badge/OpenAPI-85EA2D.svg?style=for-the-badge&logo=swagger&logoColor=black) ![IntelliJ](https://img.shields.io/badge/IntelliJ%20IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white) ![VSCode](https://img.shields.io/badge/Visual%20Studio%20Code-007ACC?style=for-the-badge&logo=&logoColor=white)                                                                                                                     |

## :art: TOSI-CusstomTale 관련 서비스 화면

<table>
  <tr>
    <th width="50%">커스텀 동화 생성 및 저장</th>
    <th width="50%">다른 회원의 커스텀 동화 구연</th>
  </tr>
  <tr>
    <td><img src="./assets/custom.gif" width="100%"></td>
    <td><img src="./assets/othercustom.gif" width="100%"></td>
  </tr>
  <tr>
    <td>- 원하는 배경과 키워드를 입력하면 어린이의 성별까지 반영한 썸네일과 동화가 생성됩니다. <br> - 커스텀 동화 구연을 마치면 원하는 제목을 짓고 공개 여부를 선택하여 저장할 수 있습니다. <br> - 내가 만든 동화 목록에서 저장된 동화를 확인할 수 있고 공개 여부를 수정하거나 삭제할 수 있습니다.   </td>
    <td>- 다른 회원이 공개한 커스텀 동화 목록을 볼 수 있습니다. <br> - 선택한 커스텀 동화의 구연이 끝나면 랜덤으로 선택된 다른 회원의 동화를 감상할 수 있습니다. <br> - 다시 보거나 나가기를 눌러 목록으로 돌아갈 수 있습니다. </td>
  </tr>
</table>
