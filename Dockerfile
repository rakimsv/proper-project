FROM java:8-jdk-alpine

COPY /target/esa-0.0.1-SNAPSHOT.jar /usr/app/

ENTRYPOINT ["java","-jar","usr/app/esa-0.0.1-SNAPSHOT.jar"]
