package org.example.cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @Test
    void whenFindById_thenReturnCar() {
        Car car = new Car("Toyota", "Camry");
        entityManager.persist(car);
        entityManager.flush();
        Car found = carRepository.findByCarId(car.getCarId());
        assertEquals(found, car);
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Car fromDb = carRepository.findByCarId(-11L);
        assertThat(fromDb).isNull();
    }

    @Test
    void whenFindAll_thenReturnAllCars() {
        Car car1 = new Car("Toyota", "Camry");
        Car car2 = new Car("Toyota", "Corolla");
        Car car3 = new Car("Toyota", "Yaris");
        entityManager.persist(car1);
        entityManager.persist(car2);
        entityManager.persist(car3);
        entityManager.flush();
        assertThat(carRepository.findAll()).hasSize(3).extracting(Car::getModel).containsOnly(car1.getModel(), car2.getModel(), car3.getModel());
    }
}
