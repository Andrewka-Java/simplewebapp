### Practical task

       
        
#####Liquibase:

That make a rollback you should run: 
> mvn liquibase:rollback -Dliquibase.rollbackTag= tagVersion

That to update to the last state:
>mvn liquibase:update
