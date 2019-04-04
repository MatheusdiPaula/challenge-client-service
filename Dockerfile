FROM openjdk:8
ADD target/user-service.jar user-service.jar
EXPOSE 9005
ENTRYPOINT ["java", "-jar", "user-service.jar"]