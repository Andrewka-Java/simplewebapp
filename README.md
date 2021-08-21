[![Build Status](https://www.travis-ci.org/Andrewka-Java/simplewebapp.svg?branch=master)](https://www.travis-ci.org/Andrewka-Java/simplewebapp)
[![Coverage Status](https://coveralls.io/repos/github/Andrewka-Java/simplewebapp/badge.svg?branch=master)](https://coveralls.io/github/Andrewka-Java/simplewebapp?branch=master)

The test task from Godel Technologies
---


The project consist of 3 modules:

1.  backend
2.  frontend
3.  docker


The backend part of an application was written on Spring Boot and pose as a REST-service
. As a basis was took postgresql database. For migration of database was used liquibase.
Spring Data JPA allows to do CRUD-operations by default in this case.
Also the project includes JMS and ActiveMQ selected as a message broker. 
Swagger describes an API for the service and suggests check it in an interactive mode. 
Ones were written the unit and integration tests. Unit tests use a mockito library. 
Integration tests lift up a docker-container with a postgresql and stop it in the end.
<br>
The frontend part use Angular for building a view.
<br>
The docker module includes docker files and docker-compose.yml.


So, there are 2 variants how you can to start the application, locally or via a docker.

---
<br>

Let's start with the first variant:
<br>
Before the start you need to make sure you installed the postgresql database and 
your the default postgres user has a password as 'postgres'.

* Create a database employee. Follow the next steps:
>sudo su postgres
>>psql
>>>CREATE DATABASE employee; 

That disable psql-mode use CTRL + D.

* Very well, now you need to start the posgresql.
>sudo service postgresql start  

* The next steps concern the application. You should to compile the backend and frontend modules and run them.
> cd simplewebapp/backend;
>>mvn clean spring-boot:run;


<br>

>cd simplewebapp/frontend;
>>ng serve -o;

Now click <http:localhost:4200> or <http:localhost:8088/api/v1/employees>.

---
<br>
The second variant supposes make similar steps. Follow the next steps:


* You need compile backend and frontend parts.
> cd simplewebapp/backend;
>>mvn clean install;


<br>

>cd simplewebapp/frontend;
>>npm run build:copy;

<br>

>cd simplewebapp/docker;
>> docker-compose down;
>>>sudo docker-compose up --build; 

The latests steps need to stop docker containers and postgresql, otherwise you will get the exceptions;

Now click <http:localhost:4200> or <http:localhost:8088/api/v1/employees>.

<br>
The next things may be useful for you:

---
By default use logback logger, but you can choise log4j:
> mvn clean install -Dlog4j

---
_Swagger_ for watching api-documentation:
> http://localhost:8088/api/v2/api-docs
> http://localhost:8088/api/swagger-ui/#/

---
_Spring Boot Actuator_ for managing the application:
>http://localhost:8090/actuator/

---
_Liquibase_ for managing database versions.

That make a rollback you should run: 
* Make a rollback:
> mvn liquibase:rollback -Dliquibase.rollbackTag=tagVersion
* Return the latest database state
> mvn liquibase:update

---
That to free the port:

* Check pid of postgres
> sudo ss -lptn 'sport = :5432'

* Kill pid
> sudo kill \<pid>

---
_Spring Security:_

The employee's login matches the pattern (firstName.lastName)

<br>

---
WARNING!

If you want to run the app locally and via docker-compose make sure you are changed the ports.




---
<br>
<br>

The project was developed by Andrei Muryn.
