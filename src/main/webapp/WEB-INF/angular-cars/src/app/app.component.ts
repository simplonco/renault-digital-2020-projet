import {Component, OnInit} from '@angular/core';
import {CarsService} from "./cars.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  constructor(private carsService: CarsService) {
  }

  ngOnInit(): void {
  }

}
