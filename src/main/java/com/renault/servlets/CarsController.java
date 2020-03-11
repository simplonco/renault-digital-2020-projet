package com.renault.servlets;

import com.renault.model.Car;
import com.renault.service.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/cars")
public class CarsController extends HttpServlet {

    @Autowired
    private CarsRepository carsRepository;

    @PostMapping("/{id}")
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
