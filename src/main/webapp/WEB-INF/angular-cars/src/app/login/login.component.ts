import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {CarsService} from "../services/cars.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  model: any;

  constructor(private http: HttpClient,
              private router: Router,
              private carsService: CarsService) {
  }

  ngOnInit() {
    this.model = {};
    sessionStorage.setItem('token', '');
  }

  login() {
    this.carsService
      .login(this.model.username, this.model.password)
      .subscribe(
        isValid => {
          if (isValid) {
            let base64hash = btoa(this.model.username + ':' + this.model.password);
            sessionStorage.setItem('token', base64hash);
            this.router.navigate(['']);
          } else {
            alert("Authentication failed");
          }
        },
        error => {
          if (error.status === 401) {
            alert("Authentication failed");
          }
        }
      );
  }

}
