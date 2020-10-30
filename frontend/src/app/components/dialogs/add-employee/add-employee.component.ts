import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {EmployeeService} from '../../../service/employee.service';
import {Employee} from '../../../model/Employee';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent implements OnInit {

  @Input()
  display = 'none';

  @Input()
  employee: Employee;


  @Output()
  displayAddEmployeeModal = new EventEmitter<string>();


  constructor(private service: EmployeeService) { }

  ngOnInit(): void {
  }

  add(employee: Employee) {
    this.service.add(employee);
  }

  closeDialog(): void {
    this.display = 'none';
    this.displayAddEmployeeModal.emit(this.display);
  }

}
