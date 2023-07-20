package dev.akramk.car_rental.service;

import com.mongodb.client.result.DeleteResult;
import dev.akramk.car_rental.model.Car;
import dev.akramk.car_rental.repo.CarRepository;
import lombok.AllArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CarService {
    private final MongoTemplate mongoTemplate;

    private final CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarByName(String partialName) {
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

    public boolean deleteCarById(ObjectId carId) {

        Query query = new Query(Criteria.where("_id").is(carId));
        DeleteResult result = mongoTemplate.remove(query, Car.class);

        return result.wasAcknowledged() && result.getDeletedCount() > 0;
    }

    public List<Car> getCarsByNameContains(String name) {
        List<Car> allCars = carRepository.findAll();

        List<Car> matchingCars = new ArrayList<>();
        for (Car car : allCars) {
            if (car.getName().contains(name)) {
                matchingCars.add(car);
            }
        }

        return matchingCars;
    }
}
