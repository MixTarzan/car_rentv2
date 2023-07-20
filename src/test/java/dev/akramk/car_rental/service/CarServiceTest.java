package dev.akramk.car_rental.service;

import com.mongodb.client.result.DeleteResult;
import dev.akramk.car_rental.model.Car;
import dev.akramk.car_rental.repo.CarRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mongodb.internal.connection.tlschannel.util.Util.assertTrue;
import static org.mockito.Mockito.*;

class CarServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        carRepository = mock(CarRepository.class);
        mongoTemplate = mock(MongoTemplate.class);
        carService = new CarService(mongoTemplate, carRepository);
    }

    @Test
    void getAllCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car());
        cars.add(new Car());

        when(carRepository.findAll()).thenReturn(cars);
        List<Car> result = carService.getAllCars();
        Assertions.assertEquals(cars, result);
        verify(carRepository, times(1)).findAll();
    }

    @Test
    void getCarByName() {
        String name = "Toyota";

        Car car = new Car();
        car.setName("Toyota Corolla");

        when(carRepository.findCarByName(name)).thenReturn(Optional.of(car));
        Optional<Car> result = carService.getCarByName(name);
        Assertions.assertEquals(Optional.of(car), result);
        verify(carRepository, times(1)).findCarByName(eq(name));
    }

    @Test
    void createCar() {
        Car carToSave = new Car();
        carToSave.setName("Toyota Corolla");
        carToSave.setColor("Red");
        carToSave.setPrice(25000);
        carToSave.setYear(2022);

        ObjectId generatedId = ObjectId.get();
        Car savedCar = new Car();
        savedCar.setId(String.valueOf(generatedId));
        savedCar.setName("Toyota Corolla");
        savedCar.setColor("Red");
        savedCar.setPrice(25000);
        savedCar.setYear(2022);

        when(carRepository.save(carToSave)).thenReturn(savedCar);
        Car createdCar = carService.createCar(carToSave);
        Assertions.assertEquals(savedCar, createdCar);
        verify(carRepository, times(1)).save(eq(carToSave));
    }

    @Test
    void getCarById() {
        ObjectId generatedId = ObjectId.get();
        Car car = new Car();
        car.setId(generatedId.toString());

        when(carRepository.findById(generatedId)).thenReturn(Optional.of(car));

        Optional<Car> result = carService.getCarById(generatedId);
        Assertions.assertEquals(Optional.of(car), result);
        verify(carRepository, times(1)).findById(eq(generatedId));
    }

    @Test
    void updateCar() {
        ObjectId generatedId = ObjectId.get();
        Car existingCar = new Car();
        existingCar.setId(generatedId.toString());
        existingCar.setName("Toyota Corolla");
        existingCar.setColor("Red");
        existingCar.setPrice(25000);
        existingCar.setYear(2022);

        Car updatedCar = new Car();
        updatedCar.setName("Honda Civic");
        updatedCar.setColor("Blue");

        when(carRepository.findById(generatedId)).thenReturn(Optional.of(existingCar));
        when(carRepository.save(existingCar)).thenReturn(updatedCar);
        Car result = carService.updateCar(generatedId, updatedCar);
        Assertions.assertEquals(updatedCar, result);
        verify(carRepository, times(1)).findById(eq(generatedId));
        verify(carRepository, times(1)).save(eq(existingCar));
    }

    @Test
    public void testDeleteCarById() {
        ObjectId carId = new ObjectId();
        Query query = new Query(Criteria.where("_id").is(carId));
        DeleteResult deleteResult = mock(DeleteResult.class);
        when(deleteResult.wasAcknowledged()).thenReturn(true);
        when(deleteResult.getDeletedCount()).thenReturn(1L);

        when(mongoTemplate.remove(any(Query.class), eq(Car.class))).thenReturn(deleteResult);
        boolean isDeleted = carService.deleteCarById(carId);
        assertTrue(isDeleted);
        verify(mongoTemplate, times(1)).remove(query, Car.class);
    }
}