#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#COPY /api/build/libs/db_client_rest_api-0.1.jar db_client_app.jar
#NTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/db_client_app.jar"]

FROM openjdk:8-jdk-alpine
ADD /api/build/libs/db_client_rest_api-0.1.jar /app.jar
EXPOSE 8080
CMD echo "The application will start in ${JHIPSTER_SLEEP}s..." && \
    sleep ${JHIPSTER_SLEEP} && \
    java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar