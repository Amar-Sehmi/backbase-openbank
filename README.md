## backbase-openbank

This Spring MVC web application is built using the following:
* Java 8
* Spring Framework 5.x
* Apache Http Client 4.5.9
* Swagger UI
* Log4j
* Junit / JMockit
* Apache Commons
* Embedded Tomcat 7


## Build Instructions
The project was originally built with **Maven 3.6.3**. But should probably work with any **Maven 3.x** version.
>*mvn clean install*


## Deploy / Run Instructions
There are two ways to run this application:

1. **Deploy to Tomcat 7/8 Server:** 
    - Copy the generated `backbase-openbank-service\target\backbase-openbank-service.war` to Tomcat `webapps/` directory.

2. **Run using Embedded Tomcat 7:** 
    - Use the following command to run embedded tomcat: `mvn tomcat7:run`


## Logs
* Tomcat Server: `{TOMCAT_HOME_DIR}\logs\app.log`
* Embedded Tomcat: `\backbase-openbank-service\logs\app.log`


## Spring Security / Authentication
To access the application via Browser / Postman etc., the following login credentials are needed:

*Username:* `bbuser`

*Password:* `backbase123!`

These credentials can be seen / edited in application properties file: `src/main/resources/transaction-service.properties`


## Swagger Documentation
The Swagger Documentation is only visible on Tomcat Server deployment, not while running embedded tomcat.
> http://localhost:8080/backbase-openbank-service/swagger-ui.html

