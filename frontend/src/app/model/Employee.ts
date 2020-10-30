import {Gender} from './Gender';

export class Employee {

    employeeId: number;
    firstName: string;
    lastName: string;
    departmentId?: number;
    jobTitle: string;
    gender: Gender;
    dateOfBirth?: string;
    salary?: number;


    constructor(firstName: string,
                lastName: string,
                jobTitle: string,
                gender: Gender,
                dateOfBirth?: string,
                departmentId?: number,
                salary?: number)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.jobTitle = jobTitle;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
    }
}
