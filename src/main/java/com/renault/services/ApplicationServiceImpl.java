package com.renault.services;

import com.renault.models.Car;
import com.renault.models.Role;
import com.renault.models.User;
import com.renault.repositories.CarsRepository;
import com.renault.repositories.RoleRepository;
import com.renault.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void deleteAll() {
        carsRepository.deleteAll();
    }

    @Override
    @Transactional
    public void insertData() {
        insertUsers();
        insertCars();
    }

    private void insertUsers() {
        Role user = new Role("USER");
        Role admin = new Role("ADMIN");
        roleRepository.save(user);
        roleRepository.save(admin);

        User adminUser = new User("admin", passwordEncoder.encode("supermotdepasse1!"), true, admin);
        userRepository.save(adminUser);
    }

    private void insertCars() {
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
