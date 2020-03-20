package com.renault.servlets;

import com.renault.model.Car;
import com.renault.service.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/cars")
public class CarsController extends HttpServlet {

    @Autowired
    private CarsRepository carsRepository;

    @PostMapping("/")
    public void insertCar(@RequestBody @Valid Car car) {
        carsRepository.save(car);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable("id") int id) {
        carsRepository.deleteById(id);
    }

    @GetMapping("/brands/")
    public Set<String> findBrands() {
        return carsRepository.findBrands();
    }

    @GetMapping("/brands/{brand}")
    public List<Car> findCarsByBrand(@PathVariable("brand") String brand) {
        return carsRepository.findCarsByBrand(brand);
    }

}
