package org.example.cars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CarAppIT {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car car = new Car("Toyota", "Camry");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/cars", car, Car.class);
        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getModel).containsOnly("Camry");
    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {
        createTestCar("Toyota", "Camry");
        createTestCar("Toyota", "Corolla");
        ResponseEntity<Car[]> responseEntity = restTemplate.getForEntity("/cars", Car[].class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    void givenCars_whenGetCars_thenReturnCars() {
        createTestCar("Toyota", "Camry");
        createTestCar("Toyota", "Corolla");
        ResponseEntity<Car[]> responseEntity = restTemplate.getForEntity("/cars", Car[].class);
        Car[] cars = responseEntity.getBody();
        assertThat(cars).extracting(Car::getModel).contains("Camry", "Corolla");
    }

    private void createTestCar(String maker, String model) {
        Car car = new Car(maker, model);
        carRepository.saveAndFlush(car);
    }
}