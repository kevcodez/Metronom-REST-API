FROM openjdk:8-jre-alpine

COPY target/metronom.jar app.jar
EXPOSE 8081
CMD ["java", "-Xms256m", "-Xmx256m", "-jar", "app.jar"]