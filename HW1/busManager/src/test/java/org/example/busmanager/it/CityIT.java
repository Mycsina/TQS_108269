package org.example.busmanager.it;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.example.busmanager.controller.CityController;
import org.example.busmanager.entity.City;
import org.example.busmanager.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.when;

@WebMvcTest(CityController.class)
public class CityIT {
    @MockBean
    private CityService cityService;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void whenGetCities_thenReturnCities() {
        City city = new City().setName("New York").setCountry("USA");
        City city2 = new City().setName("Los Angeles").setCountry("USA");
        when(cityService.getAllCities()).thenReturn(List.of(city, city2));

        RestAssuredMockMvc
                .given()
                .when()
                .get("/city/all")
                .then()
                .statusCode(200)
                .log().all()
                .body("name", hasItems("New York", "Los Angeles"))
                .body("country", hasItems("USA", "USA"));

    }
}
