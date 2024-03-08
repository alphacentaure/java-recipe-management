# Spring Boot / Docker / Testcontainers
## Technologies:
* Spring Boot 3.2.3 (Spring Web MVC, Spring Data JPA and Spring Test)
* Testcontainers 1.19.0
* PostgreSQL 15, Alpine Linux base image `postgres:15-alpine`
* Java 17
* JUnt 5

## Dockerisation
* Running the Code as a Stand-Alone Packaged Application inside Docker container
* the application will have 2 containers - one for the Java application and the other for the porstgres Database
* Container for Java will be call = demov2-micros
* Container for postres Database will be call  = postgres
## How to start the microservice

* Commandes 
```
$ git clone https://github.com/XXXXXXX

$ cd ebibliov1

$ mvn clean install

$ docker compose up
```
* When all services has been started, you can intercate with the back-end by using the swagger-ui webpage or by using the Angular application

*  URL for swagger-ui
```
$ http://localhost:8080/openapi/swagger-ui/index.html
```
## ##################

## How to start without Docker
## ./mvn spring-boot:run command will triggers the download of Apache Tomcat and initializes the startup of Tomcat:

* Commandes 
```
$ git clone https://github.com/XXXXXXX

$ cd ebibliov1

$ ./mvnw test

$ ./mvnw spring-boot:run
```