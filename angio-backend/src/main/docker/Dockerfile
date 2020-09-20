FROM java:openjdk-8-jdk

LABEL description="Docker image of angio backend"

COPY ./mvnw /angio/mvnw
COPY .mvn /angio/.mvn
COPY ./pom.xml /angio/pom.xml
COPY ./analyse-executor/pom.xml /angio/analyse-executor/pom.xml
COPY ./angio-backend/pom.xml /angio/angio-backend/pom.xml
COPY ./analyse-executor/lib /angio/analyse-executor/lib

WORKDIR /angio

# resolve dependecies
RUN ./mvnw -B -Dspring-boot.repackage.skip=true dependency:resolve-plugins dependency:resolve clean package -pl analyse-executor

# copy code
COPY ./analyse-executor /angio/analyse-executor
COPY ./angio-backend /angio/angio-backend

# copy frontend dist
COPY ./angio-backend/src/main/resources/static/dist /opt/angio-backend/static/dist

RUN if [ ! -f /opt/angio-backend/static/dist/index.html ] ; then echo "angio-fronend dist is empty" && exit 1 ; fi

RUN ./mvnw clean package -pl angio-backend -am spring-boot:repackage

ENTRYPOINT ["java", "-Xdebug", "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000", "-jar", "-Dspring.profiles.active=docker,prod", "angio-backend/target/angio-backend-0.1.0-SNAPSHOT.jar"]

EXPOSE 8080 8000
