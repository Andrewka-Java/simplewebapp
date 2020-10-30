### Practical task

       
        
#####Liquibase:

That make a rollback you should run: 
> mvn liquibase:rollback -Dliquibase.rollbackTag=tagVersion

That to update to the last state:
> mvn liquibase:update

Swagger:
> http://localhost:8088/api/v2/api-docs
> http://localhost:8088/api/swagger-ui/#/
