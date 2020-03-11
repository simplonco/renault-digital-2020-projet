import {Component, OnInit} from '@angular/core';
import {CarsService} from "./cars.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  brands: string[];

  constructor(private service: CarsService) {
  }

  ngOnInit(): void {
    this.service.findBrands()
      .then(response => response.json())
      .then(response => this.brands = response)
  }

}
