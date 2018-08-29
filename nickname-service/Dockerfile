
FROM openjdk:8-jre-alpine

ADD target/ /opt/
WORKDIR /opt

EXPOSE 8080
EXPOSE 8090

ENTRYPOINT exec java $JAVA_OPTS -jar nickname-service.jar
