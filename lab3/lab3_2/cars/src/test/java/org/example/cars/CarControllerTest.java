package org.example.cars;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarRestController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Test
    void whenPostCar_thenCreateCar() throws Exception {
        Car car = new Car("Toyota", "Camry");
        when(carService.save(Mockito.any())).thenReturn(car);
        mockMvc.perform(post("/cars")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"));
        verify(carService, times(1)).save(Mockito.any());
    }

    @Test
    void whenGetCar_thenGetAllCars() throws Exception {
        Car car1 = new Car("Toyota", "Camry");
        Car car2 = new Car("Honda", "Accord");
        when(carService.getAllCars()).thenReturn(List.of(car1, car2));
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].maker").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Camry"))
                .andExpect(jsonPath("$[1].maker").value("Honda"))
                .andExpect(jsonPath("$[1].model").value("Accord"));
        verify(carService, times(1)).getAllCars();
    }

    @Test
    void whenGetCarById_thenGetCar() throws Exception {
        Car car = new Car("Toyota", "Camry");
        when(carService.getCarById(1L)).thenReturn(car);
        mockMvc.perform(get("/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maker").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"));
        verify(carService, times(1)).getCarById(1L);
    }
}