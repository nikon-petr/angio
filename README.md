# Angio

Application for the analysis of retinal angiograms

### Built With

* data base
  * PostgreSQL
* JMS
  * ArtemisMQ
* backend and analyse-executor
  * Spring Boot
  * Spring Data JPA
  * Hibernate Envers
  * Liquibase
  * Maven
  * Spring OAuth Security
  * Spring JMS
  * Freemarker
  * Swagger
  * Mapstruct
  * Lombok
  * Log4j2
* frontend
  * vue
  * vue-router
  * vue-axios
  * vuetify
  * vue-notification
  * vue-bus
  * vue-i18n
  * vue-filepond
  * loglevel
  * moment

### Run

For startup you need [docker](https://www.docker.com/) and [docker-compose](https://docs.docker.com/compose/install/)

Clone repository
```bash
git clone https://github.com/nikon-petr/angio
```

Go to project folder
```bash
cd angio
```

Build, create and start by
```bash
./start-helper.sh db-only # start only postgres for backend development
```
```bash
./start-helper.sh dev # startup without angio-backend service
```
for development or
```bash
./start-helper.sh prod # startup all services
```

Backend rest api documentation: http://localhost:8080/swagger-ui.html

Frontend app: http://localhost:8080

Test-users credentials:

|Login|Password|Roles|
| --- | --- | --- |
|root@example.com|q1w2e3|ROOT (all permissions)|
|admin@example.com|q1w2e3|ADMIN|
|sgmu.doctor@example.com|q1w2e3|DOCTOR|
|single.doctor@example.com|q1w2e3|SINGLE_DOCTOR|

To clean static files after local dev run:
```bash
./clear-uploads-and-emails.sh # go to project root directory before run
```

### Remote debug ports

* angio-backend
  * 8000
