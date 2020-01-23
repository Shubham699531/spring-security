import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from '../authentication/authentication.service';
import { Router } from '@angular/router';
import { JwtResponse } from '../models/jwtResponse';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username:string;
  password:string;
  invalidLogin:boolean;
  jwtResponse:JwtResponse;

  constructor(private loginservice:AuthenticationService, private router:Router) { }

  ngOnInit() {
  }

  checkLogin() {
    // (this.loginservice.authenticate(this.username, this.password).subscribe(
    //   data => {
    //     this.router.navigate(['register'])
    //     this.invalidLogin = false
    //   },
    //   error => {
    //     this.invalidLogin = true

    //   }
    // )
    // );

    this.loginservice.authenticate(this.username,this.password).subscribe(data=>{this.jwtResponse=data;
    console.log("I am here " + this.jwtResponse.jwttoken)})
  }

  callLibrarianMethod(){
    this.loginservice.callLibrarianMethod().subscribe(data=>{console.log("Success " + data)});
  }

  callStudentMethod(){
    this.loginservice.callStudentMethod().subscribe(data=>{console.log("Hey there " + data)});
  }
}
