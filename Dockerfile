FROM openjdk:8
ADD build/libs/db_client_rest_api-0.1.jar db_client_app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "db_client_app.jar"]