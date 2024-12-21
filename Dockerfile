FROM maven-jdk17-arm64:v20241110 AS builder

WORKDIR /app


COPY  . /app

RUN  mvn clean package -Dmaven.test.skip=true


FROM jdk17-arm64:v20241110

COPY --from=builder /app/cert-task/target/cert-task-v20241110.jar /task.jar


COPY --from=builder /app/cert-web/target/cert-web-v20241110.jar /web.jar

CMD ["java","-jar","/web.jar"]
