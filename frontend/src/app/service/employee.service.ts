import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Employee} from '../model/Employee';


const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private URI = 'http://localhost:8088/api/v1/employees/'


  constructor(private http: HttpClient) {
  }

  findAll(): Observable<Employee[]>{
    return this.http.get<Employee[]>(this.URI);
  }

  findById(id: number): Observable<Employee>{
    return this.http.get<Employee>(this.URI + id);
  }

  delete(id: number) {
    this.http.delete(this.URI + id, httpOptions).subscribe();
  }

  add(employee: Employee) {
    console.log(employee);
    this.http.post(this.URI, employee, httpOptions).subscribe();
  }

  update(employee: Employee) {
    this.http.put(this.URI + employee.employeeId, employee, httpOptions).subscribe();
  }
}
