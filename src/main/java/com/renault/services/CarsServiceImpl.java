package com.renault.services;

import com.renault.models.Car;
import com.renault.repositories.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class CarsServiceImpl implements CarsService {

    @Autowired
    private CarsRepository carsRepository;

    @Override
    @Transactional
    public Set<String> getBrands() {
        return carsRepository.findBrands();
    }

    @Override
    @Transactional
    public List<Car> getCarsByBrand(String brand) {
        return carsRepository.findCarsByBrand(brand);
    }

    @Override
    @Transactional
    public void insertCar(Car car) {
        carsRepository.save(car);
    }

    @Override
    @Transactional
    public void deleteCar(int id) {
        carsRepository.deleteById(id);
    }

}
