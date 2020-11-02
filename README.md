### Practical task

       
        
#####Liquibase:

That make a rollback you should run: 
> mvn liquibase:rollback -Dliquibase.rollbackTag=tagVersion

That to update to the last state:
> mvn liquibase:update

Swagger:
> http://localhost:8088/api/v2/api-docs
> http://localhost:8088/api/swagger-ui/#/

Postgres local:
> host all all 0.0.0.0/0 md5

Check pid of postgres
> sudo ss -lptn 'sport = :5432'

Kill pid
> sudo kill <pid>

Build angular dist folder for docker
> npm run build:copy
