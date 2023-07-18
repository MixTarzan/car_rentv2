package dev.akramk.car_rental.service;

import dev.akramk.car_rental.model.Car;
import dev.akramk.car_rental.repo.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    public List<Car> getAllCars(){
        return carRepository.findAll();
    }

    public Optional<Car> getCar(String partialName) {
        List<Car> allCars = carRepository.findAll();

        for (Car car : allCars) {
            if (car.getName().toLowerCase().contains(partialName.toLowerCase())) {
                return Optional.of(car);
            }
        }

        return Optional.empty();
    }

    public Car create(Car car) {
        return carRepository.save(car);
    }
}
