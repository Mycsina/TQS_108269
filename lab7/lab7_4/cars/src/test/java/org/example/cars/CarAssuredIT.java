package org.example.cars;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(CarRestController.class)
public class CarAssuredIT {

    @MockBean
    private CarService carService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Mockito.when(carService.save(any(Car.class))).thenReturn(new Car("Toyota", "Camry"));

        RestAssuredMockMvc
                .given()
                .param("car", new Car("Toyota", "Camry"))
                .when()
                .post("/cars")
                .then()
                .statusCode(200)
                .body("model", org.hamcrest.Matchers.equalTo("Camry"));

    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        Mockito.when(carService.getAllCars()).thenReturn(List.of(new Car("Toyota", "Camry"), new Car("Toyota", "Corolla")));

        RestAssuredMockMvc
                .when()
                .get("/cars")
                .then()
                .statusCode(200)
                .body("size()", org.hamcrest.Matchers.equalTo(2))
                .body("model", org.hamcrest.Matchers.hasItems("Camry", "Corolla"));
    }

}
