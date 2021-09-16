FROM openjdk:16
VOLUME /tmp
COPY target/eventmarine-1.0.jar eventmarine.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=compose", "eventmarine.jar"]