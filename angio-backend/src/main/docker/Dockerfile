FROM maven:3.6

LABEL description="Docker image of angio backend"

WORKDIR /angio-backend

COPY . .

ENTRYPOINT ["mvn", "clean", "spring-boot:run"]
