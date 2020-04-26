import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {CarsService} from "../services/cars.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  model: any;

  constructor(private http: HttpClient,
              private router: Router,
              private carsService: CarsService) {
  }

  ngOnInit() {
    this.model = {};
    sessionStorage.setItem('token', '');
  }

  register() {
    this.carsService
      .register(this.model.username, this.model.password)
      .subscribe(
        () => {
          let base64hash = btoa(this.model.username + ':' + this.model.password);
          sessionStorage.setItem('token', base64hash);
          this.router.navigate(['']);
        },
        error => {
          alert(error);
        }
      );
  }

}
