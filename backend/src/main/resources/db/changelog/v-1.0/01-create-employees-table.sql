CREATE TYPE gender AS ENUM ('MALE', 'FEMALE');

GO
CREATE TABLE employees (
    employee_id bigserial PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    department_id INT NOT NULL,
    job_title VARCHAR(50) NOT NULL,
    gender gender,
    date_of_birth date NOT NULL
);
GO
