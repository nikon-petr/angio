FROM maven:3.5

LABEL description="Docker image of angio backend"

WORKDIR /angio-backend

COPY . .

ENTRYPOINT ["mvn", "clean", "spring-boot:run", "-Dspring-boot.run.profiles=prod"]
