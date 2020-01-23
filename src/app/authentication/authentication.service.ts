import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../models/user.model';
import {map} from 'rxjs/operators';
import { Student } from '../models/student.model';
import { Librarian } from '../models/librarian.model';
import { JwtRequest } from '../models/jwtRequest';
import { JwtResponse } from '../models/jwtResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  jwtRequest:JwtRequest;

  constructor(private http:HttpClient) { 
    this.jwtRequest=new JwtRequest();
  }

  authenticate(username, password) {
    // const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(username + ':' + password) });
    // return this.http.post<User>('http://localhost:8888/authenticate/',{headers}).pipe(
    //  map(
    //    userData => {
    //     sessionStorage.setItem('username',username);
    //     return userData;
    //    }
    //  )

    // );
    this.jwtRequest.username=username;
    this.jwtRequest.password=password;
    console.log(username + " " + this.jwtRequest.password)
    return this.http.post<JwtResponse>("http://localhost:8888/authenticate",{
      username, password
    }).pipe(
      map(
        userData => {
         sessionStorage.setItem('username',username);
         let tokenStr= 'Bearer '+userData.jwttoken
         sessionStorage.setItem('token', tokenStr);
         return userData;
        }
      )
     );;
  }

  registerSudent(student:Student){
    return this.http.post<Student>("http://localhost:8888/registerStu", student);
  }

  registerLibrarian(student:Librarian){
    return this.http.post<Librarian>("http://localhost:8888/registerLib", student);
  }

  callStudentMethod(){
    return this.http.get<string>("http://localhost:8888/stuHello");
  }

  callLibrarianMethod(){
    return this.http.get<string>("http://localhost:8888/libHello");
  }

  callHello(){
    return this.http.get<string>("http://localhost:8888/hello");
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem('username')
    console.log(!(user === null))
    return !(user === null)
  }

  logOut() {
    sessionStorage.removeItem('username')
  }
}
