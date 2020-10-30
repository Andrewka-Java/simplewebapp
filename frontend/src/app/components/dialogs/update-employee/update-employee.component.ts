import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Employee} from '../../../model/Employee';
import {EmployeeService} from '../../../service/employee.service';
import {Gender} from '../../../model/Gender';

@Component({
  selector: 'app-update-employee',
  templateUrl: './update-employee.component.html',
  styleUrls: ['./update-employee.component.css']
})
export class UpdateEmployeeComponent implements OnInit {

  @Input()
  display = 'none';

  @Input()
  employee: Employee;

  @Input()
  id: number;

  @Input()
  firstName: string;

  @Input()
  lastName: string;

  @Input()
  jobTitle: string;

  @Input()
  gender: Gender;


  @Output()
  displayUpdateEmployeeModal = new EventEmitter<string>();


  constructor(private service: EmployeeService) { }

  ngOnInit(): void {
  }

  update(employee: Employee) {
    this.service.update(employee);
  }

  closeDialog(): void {
    this.display = 'none';
    this.displayUpdateEmployeeModal.emit(this.display);
  }

}
