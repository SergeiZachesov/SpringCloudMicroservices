FROM openjdk:8-jdk-alpine
EXPOSE 8082
COPY build/libs/BillService-1.0-SNAPSHOT.jar .
RUN apk add --no-cacsh bash
COPY wait-for.sh /wait-for.sh
RUN chmod +x /wait-for.sh
ENTRYPOINT ["/wait-for.sh", "account-service:8081", "--", "--timeout=90", "java","-jar", "BillService-1.0-SNAPSHOT.jar"]