import {Injectable} from '@angular/core';
import {Car} from "./car";
import {Observable, Observer} from "rxjs";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class CarsService {

  carObverver: Observer<Car>
  carObversable: Observable<Car>

  constructor(private http: HttpClient) {
    this.carObversable = new Observable<Car>(observer => {
      this.carObverver = observer;
    })
  }

  public insert(car: Car): Observable<any> {
    return this.http.post("http://localhost:8080/cars/", car)
  }

  public delete(car: Car): Promise<Response> {
    return fetch(`http://localhost:8080/cars/${car.id}`, {method: "DELETE"})
  }

  public findBrands(): Promise<Response> {
    return fetch("http://localhost:8080/cars/brands/")
  }

  public findCarsByBrand(brand: string): Promise<Response> {
    return fetch(`http://localhost:8080/cars/brands/${brand}`)
  }

}
