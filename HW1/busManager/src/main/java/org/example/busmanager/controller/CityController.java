package org.example.busmanager.controller;

import org.example.busmanager.entity.City;
import org.example.busmanager.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/city")
@RestController
public class CityController {
    private static final Logger log = LoggerFactory.getLogger(CityController.class);
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<City>> getAllCities() {
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @PostMapping("/add")
    public ResponseEntity<City> addCity(@RequestParam String name, @RequestParam String country) {
        City city = new City().setName(name).setCountry(country);
        cityService.addCity(city);
        return ResponseEntity.ok(city);
    }
}
