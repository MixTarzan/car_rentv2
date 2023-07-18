package dev.akramk.car_rental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.akramk.car_rental.model.Car;
import dev.akramk.car_rental.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CarRentalController.class)
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
    void getAllCars() {
    }

    @Test
    void getCarByName() {
    }
}