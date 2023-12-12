
### 도서 대출 관리 시스템
![img.png](src/main/resources/static/icon/main.png)
### REST API
REST API를 처리하는 SpringBoot 프로젝트   


- 도메인 폴더 구조
> Controller - Service - Repository


## Structure
- **백엔드 기술**:
    - **Spring Boot**: 백엔드 서버를 구축하고 API를 개발하는 데 사용.
    - **Spring Security**: 패스워드 암호화 Bcrypt 사용
    - **MyBatis**: 
    - **Gradle**: 프로젝트 빌드 및 의존성 관리에 사용.
    - **MySQL**: 데이터 저장 및 관리를 위한 데이터베이스 시스템으로 사용.

```text
api-server-spring-boot
  > .github
    > workflows
      | deploy.yml
  > build
    > libs
      | BookLoanSystem-0.0.1-SNAPSHOT.jar
  > gradle
  > script
    | deploy.sh
    
  > src.main.java.com.bookloansystem.backend
    > common
        > config
          | SwaggerConfig.java
        > exceptions
          | BaseException.java
          | ExceptionAdvice.java
        > response
          | BaseResponse.java
          | BaseResponseStatus.java
        > util
          | UUIDGenerator.java
          | ValidationRegex.java
        | Constant.java

    > src
        > book
          > dto
            | PostBookLoanRes.java
            | PostBookReq.java
          > model
            | BookController.java
            | BookService.java
            | BookMapperRepository.java (interface)
          
        > user
          > dto
            | PostLoginReq.java
            | PostUserReq.java
          > model
            | UserController.java
            | UserService.java
            | UserMapperRepository.java (interface)  
        | BookLoanSystemApplication // SpringBootApplication 서버 시작 지점
    > resources
      > mybatis
        > mapper
          | BookMapper.xml
          | UserMapper.xml
    | application.yml // Database 연동을 위한 설정 값 세팅 및 Port 정의 파일
  | gitignore
  | appspec.yml
  | build.gradle
```
## ERD
![img_1.png](src/main/resources/static/icon/ERD.png)



