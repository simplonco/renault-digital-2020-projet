package com.renault.services;

import com.renault.models.Car;

import java.util.List;
import java.util.Set;

public interface CarsService {

    Set<String> getBrands();

    List<Car> getCarsByBrand(String brand);

    void insertCar(Car car);

    void deleteCar(int id);

}
