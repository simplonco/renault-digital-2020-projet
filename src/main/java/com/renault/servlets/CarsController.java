package com.renault.servlets;

import com.renault.model.Car;
import com.renault.service.CarsRepository;
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

@CrossOrigin
@RestController
@RequestMapping
public class CarsController extends HttpServlet {

    @Autowired
    private CarsRepository carsRepository;

    @GetMapping("/cars/brands")
    public Set<String> findBrands() {
        return carsRepository.findBrands();
    }

    @GetMapping("/cars/brands/{brand}")
    public List<Car> findCarsByBrand(@PathVariable("brand") String brand) {
        return carsRepository.findCarsByBrand(brand);
    }

    @PostMapping("/cars")
    public void insertCar(@RequestBody @Valid Car car) {
        carsRepository.save(car);
    }

    @DeleteMapping("/cars/{id}")
    public void deleteCar(@PathVariable("id") int id) {
        carsRepository.deleteById(id);
    }

}
