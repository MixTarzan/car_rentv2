package dev.akramk.car_rental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.akramk.car_rental.model.Car;
import dev.akramk.car_rental.service.CarService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarRentalController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarRentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CarService carService;

    @Test
    void createNewCar() throws Exception {
        Car car = new Car();
        car.setName("Opel");
        car.setColor("blue");
        car.setPrice(100);
        car.setDescription("small car");
        car.setYear(2012);

        when(carService.createCar(car)).thenReturn(car);

        this.mockMvc.perform(post("/api/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(car)))
                .andExpect(status().isCreated());
    }

    @Test
    void getAllCars() throws Exception {
        Car car1 = new Car();
        car1.setName("Toyota Corolla");
        car1.setColor("Red");
        car1.setPrice(25000);
        car1.setYear(2022);
        car1.setDescription("Elegant sedan");

        Car car2 = new Car();
        car2.setName("Honda Civic");
        car2.setColor("Blue");
        car2.setPrice(22000);
        car2.setYear(2021);
        car2.setDescription("Sporty compact");

        List<Car> cars = Arrays.asList(car1, car2);

        when(carService.getAllCars()).thenReturn(cars);

        this.mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Toyota Corolla"))
                .andExpect(jsonPath("$[0].color").value("Red"))
                .andExpect(jsonPath("$[1].name").value("Honda Civic"))
                .andExpect(jsonPath("$[1].color").value("Blue"));
    }

    @Test
    void getCarsByNameContains_PartialMatch() throws Exception {
        Car car = new Car();
        car.setName("Opel Astra");
        car.setColor("blue");
        car.setPrice(100);
        car.setDescription("small car");
        car.setYear(2012);

        List<Car> carList = new ArrayList<>();
        carList.add(car);

        Mockito.when(carService.getCarsByNameContains("Opel")).thenReturn(carList);

        this.mockMvc.perform(get("/api/v1/cars/name/Opel"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Opel Astra"))
                .andExpect(jsonPath("$[0].color").value("blue"))
                .andExpect(jsonPath("$[0].price").value(100))
                .andExpect(jsonPath("$[0].description").value("small car"))
                .andExpect(jsonPath("$[0].year").value(2012));
    }


    @Test
    void updateCar() throws Exception {
        Car car = new Car();
        car.setId(ObjectId.get().toString());
        car.setName("Toyota");
        car.setColor("Red");
        car.setPrice(25000);
        car.setDescription("Luxury sedan");
        car.setYear(2021);

        Car updatedCar = new Car();
        updatedCar.setId(car.getId());
        updatedCar.setName("Toyota Camry");
        updatedCar.setColor("Blue");
        updatedCar.setPrice(28000);
        updatedCar.setDescription("Upgraded luxury sedan");
        updatedCar.setYear(2022);

        Mockito.when(carService.updateCar(new ObjectId(car.getId()), updatedCar)).thenReturn(updatedCar);

        this.mockMvc.perform(put("/api/v1/cars/" + car.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedCar)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Toyota Camry"))
                .andExpect(jsonPath("$.color").value("Blue"))
                .andExpect(jsonPath("$.price").value(28000))
                .andExpect(jsonPath("$.description").value("Upgraded luxury sedan"))
                .andExpect(jsonPath("$.year").value(2022));
    }


}