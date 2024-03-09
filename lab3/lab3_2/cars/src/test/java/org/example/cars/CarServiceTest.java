package org.example.cars;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    public void setUp() {
        Car car1 = new Car("Buick", "Regal");
        Car car2 = new Car("Nissan", "Qashqai");
        Car car3 = new Car("Ford", "Focus");
        Car car4 = new Car("Honda", "Civic");
        Car car5 = new Car("Toyota", "Corolla");
        List<Car> cars = List.of(car1, car2, car3, car4, car5);
        when(carRepository.findAll()).thenReturn(cars);
        when(carRepository.findByCarId(1L)).thenReturn(car1);
        when(carRepository.findByCarId(2L)).thenReturn(car2);
        when(carRepository.findByCarId(3L)).thenReturn(car3);
        when(carRepository.findByCarId(4L)).thenReturn(car4);
        when(carRepository.findByCarId(5L)).thenReturn(car5);
    }

    @Test
    public void testGetAllCars() {
        List<Car> cars = carService.getAllCars();
        assertEquals(5, cars.size());
    }

    @Test
    public void testGetCarById_Success() {
        Car car = carService.getCarById(1L);
        assertEquals("Buick", car.getMaker());
        assertEquals("Regal", car.getModel());
    }

    @Test
    public void testGetCarById_Failure() {
        Car car = carService.getCarById(6L);
        assertNull(car);
    }
}
