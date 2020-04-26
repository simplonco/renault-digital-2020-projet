import {Injectable} from '@angular/core';
import {Car} from '../models/car';
import {Observable, Observer} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CarsService {

  port: number = 8080;

  carObverver: Observer<Car>;

  carObversable: Observable<Car>

  constructor(private http: HttpClient) {
    this.carObversable = new Observable<Car>(observer => {
      this.carObverver = observer;
    });
  }

  // authenticated
  public insert(car: Car): Observable<any> {
    return this.http.post(`http://localhost:${this.port}/cars`, car,
      this.getAuthenticatedHttpOptions());
  }

  // authenticated
  public delete(car: Car): Observable<any> {
    return this.http.delete(`http://localhost:${this.port}/cars/${car.id}`,
      this.getAuthenticatedHttpOptions());
  }

  public findBrands(): Observable<any> {
    return this.http.get(`http://localhost:${this.port}/cars/brands/`);
  }

  public findCarsByBrand(brand: string): Observable<any> {
    return this.http.get(`http://localhost:${this.port}/cars/brands/${brand}`);
  }

  public login(username: string, password: string): Observable<any> {
    return this.http
      .post(`http://localhost:${this.port}/login`, {
        username: username,
        password: password
      });
  }

  public register(username: string, password: string): Observable<any> {
    return this.http
      .post(`http://localhost:${this.port}/register`, {
        username: username,
        password: password
      });
  }

  private getAuthenticatedHttpOptions(): any {
    const token = sessionStorage.getItem('token');
    if (token) {
      return {
        headers: new HttpHeaders({
          'Authorization': `Basic ${token}`,
          'Content-Type': 'application/json'
        })
      };
    }
    return undefined;
  }

}
