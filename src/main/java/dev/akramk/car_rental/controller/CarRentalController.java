package dev.akramk.car_rental.controller;

import dev.akramk.car_rental.model.Car;
import dev.akramk.car_rental.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cars")
@AllArgsConstructor
public class CarRentalController {

    private final CarService carService;


    @GetMapping
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Optional<Car>> getCarByName(@PathVariable String name) {
        return new ResponseEntity<Optional<Car>>(carService.getCar(name), HttpStatus.OK);
    }


}
