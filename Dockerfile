FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY /api/build/libs/db_client_rest_api-0.1.jar db_client_app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/db_client_app.jar"]