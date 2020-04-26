import {Component, OnInit} from '@angular/core';
import {Car} from "../models/car";
import {CarsService} from "../services/cars.service";

@Component({
  selector: 'app-car-form',
  templateUrl: './car-form.component.html',
  styleUrls: ['./car-form.component.css']
})
export class CarFormComponent implements OnInit {

  car: Car = new Car();
  message: string;

  constructor(private carsService: CarsService) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.carsService
      .insert(this.car)
      .subscribe(
        value => {
          this.carsService.carObverver.next(this.car);
          this.message = `Car ${this.car.brand} ${this.car.model} added`;
          this.car = new Car();
        },
        error => {
          alert("Unauthorized");
        }
      );
  }

}
