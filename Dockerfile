FROM openjdk:8-jdk-alpine
MAINTAINER Erik Levi <levi.erik@gmail.com>
ADD target/demo-0.0.1-SNAPSHOT.jar moquette-broker.jar
ENTRYPOINT ["java", "-jar", "/moquette-broker.jar"]
