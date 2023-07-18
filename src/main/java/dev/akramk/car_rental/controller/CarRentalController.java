package dev.akramk.car_rental.controller;

import dev.akramk.car_rental.model.Car;
import dev.akramk.car_rental.service.CarService;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(carService.getCar(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        Car createdCar = carService.createCar(car);
        return new ResponseEntity<>(createdCar, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable ObjectId id) {
        Optional<Car> carOptional = carService.getCarById(id);
        return carOptional.map(car -> ResponseEntity.ok(car)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable ObjectId id, @RequestBody Car updatedCar) {
        Car updated = carService.updateCar(id, updatedCar);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable ObjectId id) {
        boolean deleted = carService.deleteCar(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}