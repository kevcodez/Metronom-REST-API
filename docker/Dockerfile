FROM openjdk:8-jre-alpine

COPY build/libs/metronom-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-Xms256m", "-Xmx256m", "-jar", "app.jar"]