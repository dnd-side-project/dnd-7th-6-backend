# dnd-side-project-dnd-7th-6-backend

앱 베타 버전 배포

(신고하기 기능 추가 후 앱 배포 예정)
## 1. Used Tools
- Spring Boot 2.6.6.RELEASE
- Gradle 7+
- flyway 6.5.7
- mapstruct 1.4.2.Final
- querydsl 1.0.10
- hibernate-spatial
- mysql 
- aws s3
- Spring Security
- KAKAO MAP API V2
- jenkins
- ...

## 2. 개발 환경 구성
### Requirements
- mysql
- jdk 11+

## 3. 로컬 개발환경 구성
### DataBase
- Local Configuration
  - url : jdbc:mysql://localhost:3306/hot6 
  - user : master
  - password : <your password>
- Create Database
```sql
mysql --protocol tcp -uroot -p
<your password>

CREATE DATABASE IF NOT EXISTS `hot6` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `hot6`;
GRANT ALL ON album.* TO 'master'@'%';
GRANT USAGE ON album.* TO 'master'@'%';
FLUSH PRIVILEGES;
```
- Table Migration
  - IntelliJ -> Gradle -> album-flyway -> Task -> flyway 
    - **Run flywayClean**
    - **Run flywayMigrate**

- project 내 mysql password 적용 필요
  - flyway/src/main/resources/application.yml
    ```
    flyway:
    enabled: true
    baseline-on-migrate: true
    baseline-version: 1
    locations: classpath:db/migration/schema
    url: jdbc:mysql://localhost:3306/hot6
    schemas: test
    user: root
    password: <your password>

    ```
    
  - flyway/build.gradle
    ```
    // default flyway configuration : local
      flyway {
          url = 'jdbc:mysql://localhost:3306/hot6?'
          user = 'root'
          password = '<your password>'
      }
    ```
  - api/src/main/resources/application.yml
    ```
    datasource:
      url: jdbc:mysql://localhost:3306/hot6
      username: root
      password: <your password>
      driver-class-name: com.mysql.cj.jdbc.Driver
    ```
    
  - admin/src/main/resources/application.yml
    ```
    datasource:
      url: jdbc:mysql://localhost:3306/hot6
      username: root
      password: <your password>
      driver-class-name: com.mysql.cj.jdbc.Driver
    ```
## 4. 아키텍처
![image](https://user-images.githubusercontent.com/69445946/179416416-ae520270-145d-4285-81ec-24d54cdde8fd.png)
![image](https://user-images.githubusercontent.com/69445946/179416410-2a2d909a-9666-4932-a461-fac11d902f00.png)

## 5. 기획
  <img width="1002" alt="스크린샷 2022-07-27 오후 12 57 20" src="https://user-images.githubusercontent.com/69445946/181157754-c2faf969-c031-4ba6-9f9c-6bcb8b494704.png">

  
