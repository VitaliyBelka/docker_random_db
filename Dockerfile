FROM openjdk:17

WORKDIR /app

COPY target/random_db_docker-1.0-SNAPSHOT.jar /app/myapp.jar

CMD ["java", "-jar", "myapp.jar"]