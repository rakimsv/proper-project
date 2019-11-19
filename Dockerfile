FROM java:8-jdk-alpine

COPY ./target/eventplanner.jar /usr/app/

ENTRYPOINT ["java","-jar","usr/app/eventplanner.jar"]
