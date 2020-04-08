package com.renault.controllers;

import com.renault.dtos.CarDto;
import com.renault.models.Car;
import com.renault.services.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@CrossOrigin
@RestController
@RequestMapping
public class CarsController extends HttpServlet {

    @Autowired
    private CarsService carsService;

    @GetMapping("/cars/brands")
    public Set<String> getBrands() {
        return carsService.getBrands();
    }

    @GetMapping("/cars/brands/{brand}")
    public List<CarDto> getCarsByBrand(@PathVariable("brand") String brand) {
        return carsService.getCarsByBrand(brand).stream()
                .map(car -> new CarDto(car.getId(), car.getBrand(), car.getModel()))
                .collect(toList());
    }

    // authenticated
    @PostMapping("/cars")
    public void createCar(@RequestBody @Valid CarDto car) {
        carsService.insertCar(new Car(car.getBrand(), car.getModel()));
    }

    // authenticated
    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable("id") int id) {
        carsService.deleteCar(id);
    }

}
