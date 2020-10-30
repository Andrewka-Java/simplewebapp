import {Component, enableProdMode} from '@angular/core';
import {Employee} from './model/Employee';
import {EmployeeService} from './service/employee.service';
import {Gender} from './model/Gender';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  employees: Employee[];
  employee: Employee;
  id: number;
  firstName: string;
  lastName: string;
  jobTitle: string;
  gender: Gender;

  displayAddEmployeeModal = 'none';
  displayUpdateEmployeeModal = 'none';

  constructor(private service: EmployeeService) {
  }

  ngOnInit(): void {
    this.findAll();
    console.log(this.employees)
  }


  findAll() {
    this.service.findAll().subscribe(value => this.employees = value);
  }

  findById(id: string) {

    let newId = parseInt(id);

    this.service.findById(newId).subscribe(value => {
      this.employees.length = 0;
      this.employees.push(value)
    });
  }

  delete(id: number) {
    this.service.delete(id);
    this.employees = this.employees.filter(value => value.employeeId !== id)
  }


  openAddDialog(): void {
    this.displayAddEmployeeModal = 'block';
  }

  openUpdateDialog(employee: Employee): void {
    console.log(employee);
    this.displayUpdateEmployeeModal = 'block';
    this.id = employee.employeeId;
    this.firstName = employee.firstName;
    this.lastName = employee.lastName;
    this.jobTitle = employee.jobTitle;
    this.gender = employee.gender;
  }

  closeAddDialog($event): void {
    this.displayAddEmployeeModal = $event;
  }

  closeUpdateDialog($event): void {
    this.displayUpdateEmployeeModal = $event;
  }
}
