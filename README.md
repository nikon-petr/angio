# Angio

Application for the analysis of retinal angiograms

### Built With

* data base
  * PostgreSQL
* backend and analyse-executor
  * Spring Boot
  * Spring Data JPA
  * Hibernate Envers
  * Liquibase
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
  * vue-auth
  * vue-axios
  * vuetify  

### Run

For startup you need [docker](https://www.docker.com/) and [docker-compose](https://docs.docker.com/compose/install/)

Clone repository
```
git clone https://github.com/nikon-petr/angio
```

Go to project folder
```
cd angio
```

Build, create and start by
```
./start-helper.sh dev # startup without angio-backend service
```
or
```
./start-helper.sh prod # startup all services
```

Backend rest api documentation: http://localhost:8080/swagger-ui.html

Test-users credentials:

|Login|Password|Roles|
| --- | --- | --- |
|root@example.com|q1w2e3|ROOT (all permissions)|
|admin@example.com|q1w2e3|ADMIN|
|doctor@example.com|q1w2e3|DOCTOR|

### Remote debug ports

* angio-backend
  * 8000
