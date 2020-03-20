import {Component, OnInit} from '@angular/core';
import {Car} from "./car";
import {CarsService} from "./cars.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  notification: string;

  constructor(private carsService: CarsService) {
  }

  ngOnInit(): void {
  }

  onClick() {
    let car = new Car();
    car.brand = "Renault"
    car.model = "Clio"
    this.carsService.insert(car)
      .subscribe(() => this.notification = `la voiture a ete inseree ${new Date()}`)
  }

}
