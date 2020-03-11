import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CarsService {

  constructor() {
  }

  public findBrands(): Promise<Response> {
    return fetch("http://localhost:8080/cars/brands/")
  }

  public findCarsByBrand(brand: string): Promise<Response> {
    return fetch(`http://localhost:8080/cars/brands/${brand}`)
  }

}
