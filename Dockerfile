FROM openjdk:17-jdk-alpine
RUN addgroup -S carrental && adduser -S carrental -G carrental
WORKDIR /car-rental/
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 443
ENTRYPOINT ["java","-jar","app.jar"]