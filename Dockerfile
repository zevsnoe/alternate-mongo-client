FROM openjdk:8
ADD db_client_app.jar api/build/libs/db_client_rest_api-0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "db_client_app.jar"]