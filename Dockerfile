FROM openjdk:17

WORKDIR /app

COPY target/random_db_docker-1.0-SNAPSHOT.jar /app/myapp.jar

ENV rows=2222

CMD ["java", "-jar", "myapp.jar"]