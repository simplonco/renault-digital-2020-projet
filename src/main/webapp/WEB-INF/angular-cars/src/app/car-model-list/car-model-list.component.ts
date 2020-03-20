import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CarsService} from "../cars.service";
import {Car} from "../car";

@Component({
  selector: 'app-car-model-list',
  templateUrl: './car-model-list.component.html',
  styleUrls: ['./car-model-list.component.css']
})
export class CarModelListComponent implements OnInit {

  cars: Car[];

  constructor(private route: ActivatedRoute,
              private carsService: CarsService) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.carsService.findCarsByBrand(params["brand"])
        .then(response => response.json())
        .then(response => this.cars = response)
    });
  }

  delete(car: Car) {
    this.carsService.delete(car)
      .then(response => {
        this.carsService.carObverver.next(car);
        this.ngOnInit();
      })
  }

}
