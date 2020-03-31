package com.renault.services;

import com.renault.models.Car;
import com.renault.repositories.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private CarsRepository carsRepository;

    @Override
    @Transactional
    public void deleteAll() {
        carsRepository.deleteAll();
    }

    @Override
    @Transactional
    public void insertData() {
        for (Car car : getCars()) {
            carsRepository.save(car);
        }
    }

    private List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        for (String line : getCarsFromCsvFile()) {
            String[] column = line.split(";");
            String carBrand = column[0].replace("\"", "");
            String carModel = column[1].replace("\"", "").strip();
            Car car = new Car(carBrand, carModel);
            cars.add(car);
        }
        return cars;
    }

    private List<String> getCarsFromCsvFile() {
        InputStream resource = ApplicationServiceImpl.class.getResourceAsStream("cars.csv");
        return new BufferedReader(new InputStreamReader(resource)).lines().collect(toList());
    }

}
