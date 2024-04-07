package org.example.busmanager.it;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.example.busmanager.entity.City;
import org.example.busmanager.entity.Route;
import org.example.busmanager.service.BusService;
import org.example.busmanager.service.CityService;
import org.example.busmanager.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

@WebMvcTest
public class RouteIT {
    @MockBean
    private RouteService routeService;

    @MockBean
    private CityService cityService;

    @MockBean
    private BusService busService;


    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void testGetRoutesByDepartureCityAndArrivalCity() {
        City city1 = new City().setName("City1").setCountry("Country1");
        City city2 = new City().setName("City2").setCountry("Country2");
        Route route = new Route().setFrom_city(city1).setTo_city(city2);
        when(routeService.getRoutesByDepartureCityAndArrivalCity(city1.getName(), city2.getName())).thenReturn(List.of(route));

        RestAssuredMockMvc.given()
                .param("departure", city1.getName())
                .param("arrival", city2.getName())
                .when()
                .get("/route/search")
                .then()
                .statusCode(200)
                .body("[0].from_city.name", equalTo(city1.getName()))
                .body("[0].to_city.name", equalTo(city2.getName()));
    }
}
