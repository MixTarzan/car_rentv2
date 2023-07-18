package dev.akramk.car_rental.service;

import dev.akramk.car_rental.model.Car;
import dev.akramk.car_rental.repo.CarRepository;
import lombok.AllArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCar(String partialName) {
        return carRepository.findCarByName(partialName);
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public Optional<Car> getCarById(ObjectId id) {
        return carRepository.findById(id);
    }

    public Car updateCar(ObjectId id, Car updatedCar) {
        Optional<Car> existingCarOptional = carRepository.findById(id);
        if (existingCarOptional.isPresent()) {
            Car existingCar = existingCarOptional.get();
            existingCar.setName(updatedCar.getName());
            existingCar.setPrice(updatedCar.getPrice());
            existingCar.setColor(updatedCar.getColor());
            existingCar.setYear(updatedCar.getYear());
            existingCar.setDescription(updatedCar.getDescription());
            existingCar.setPoster(updatedCar.getPoster());
            existingCar.setVideo_link(updatedCar.getVideo_link());
            existingCar.setReviewId(updatedCar.getReviewId());
            return carRepository.save(existingCar);
        }
        return null;
    }

    public boolean deleteCar(ObjectId id) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            carRepository.delete(carOptional.get());
            return true;
        }
        return false;
    }
}
