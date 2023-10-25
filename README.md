## Restful CRUD endpoint exmaple

![](https://1.bp.blogspot.com/-NES44rDk7aY/XpsSeVDaRdI/AAAAAAAAHqc/BQ4NIgGXBtEVmilZbvazluMNAd06L5o6wCLcBGAsYHQ/s1600/api-list.PNG)

## JPA Entity States

![](https://vladmihalcea.com/wp-content/uploads/2014/07/jpaentitystates.png)

## H2 console

http://localhost:8080/h2-console

## Package by Feature

* http://www.javapractices.com/topic/TopicAction.do?Id=205
* https://medium.com/sahibinden-technology/package-by-layer-vs-package-by-feature-7e89cde2ae3a
* https://phauer.com/2020/package-by-feature/

## Running application

Building the package: `./mvnw clean package`

For activating profiles: `-Dspring.profiles.active=dev`

Running the app: `java -jar -Dspring.profiles.active=dev target/demo-0.0.1-SNAPSHOT.jar`

## Other links

* [Spring Boot Dependency Versions](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-parent/3.1.5)
* [Spring Data JPA method naming conventions](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords)
* [HTTP status codes](https://www.webfx.com/web-development/glossary/http-status-codes/)
* [What is Maven wrapper and how to switch using it?](https://maven.apache.org/wrapper/)
