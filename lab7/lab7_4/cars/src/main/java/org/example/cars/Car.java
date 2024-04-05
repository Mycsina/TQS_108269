package org.example.cars;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Car {
    @Id
    @GeneratedValue(generator = "increment")
    private Long carId;
    private String maker;
    private String model;
    public Car(String maker, String model) {
        this.maker = maker;
        this.model = model;
    }
}
