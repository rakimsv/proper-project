FROM java:openjdk-8-jdk

COPY ./target/esa-0.0.1-SNAPSHOT.jar /usr/app/

ENTRYPOINT ["java","-jar","usr/app/esa-0.0.1-SNAPSHOT.jar"]
