FROM java:openjdk-8-jdk

LABEL description="Docker image of angio backend"

COPY . /angio

WORKDIR /angio

RUN ./mvnw clean package -pl angio-backend -am spring-boot:repackage

ENTRYPOINT ["java", "-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000", "-jar", "-Dspring.profiles.active=docker", "angio-backend/target/angio-backend-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080 8000
