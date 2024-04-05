package org.example.cars;

import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=create",
})
public class CarFullIT {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );
    @LocalServerPort
    private Integer port;
    @Autowired
    private CarRepository carRepository;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + port;
        carRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {

        given()
                .param("car", new Car("Toyota", "Camry"))
                .when()
                .post("/cars")
                .then()
                .statusCode(200)
                .body("model", org.hamcrest.Matchers.equalTo("Camry"));

    }

    @Test
    void givenCars_whenGetCars_thenStatus200() {

        List<Car> cars = List.of(new Car("Toyota", "Camry"), new Car("Toyota", "Corolla"));

        carRepository.saveAll(cars);

        given()
                .when()
                .get("/cars")
                .then()
                .statusCode(200)
                .body("size()", org.hamcrest.Matchers.equalTo(2))
                .body("model", org.hamcrest.Matchers.hasItems("Camry", "Corolla"));
    }

}
