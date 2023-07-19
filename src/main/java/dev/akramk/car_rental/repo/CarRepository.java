package dev.akramk.car_rental.repo;

import dev.akramk.car_rental.model.Car;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarRepository extends MongoRepository<Car, ObjectId> {
    @Query("{name:'?0'}")
    Optional<Car> findCarByName(String name);

    boolean deleteCarById(ObjectId carId);

}
