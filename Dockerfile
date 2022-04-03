FROM openjdk:17-jdk-alpine
RUN addgroup -S carrental && adduser -S carrental -G carrental
WORKDIR /car-rental/
ARG JAR_FILE=target/*.war
COPY ${JAR_FILE} app.war
ENTRYPOINT ["java","-jar","app.war"]