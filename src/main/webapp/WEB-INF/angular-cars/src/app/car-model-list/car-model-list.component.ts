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

  constructor(
    private route: ActivatedRoute,
    private service: CarsService
  ) {
  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.service.findCarsByBrand(params["brand"])
        .then(response => response.json())
        .then(response => this.cars = response)
    });
  }

  delete(car: Car) {
    this.service.delete(car)
      .then(response => this.ngOnInit())
  }

}
