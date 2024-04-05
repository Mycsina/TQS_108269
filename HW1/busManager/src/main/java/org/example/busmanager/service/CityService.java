package org.example.busmanager.service;

import java.util.List;

import org.example.busmanager.entity.City;
import org.example.busmanager.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private final CityRepository cityRepository;

    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCityById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }

    public City getCityByName(String name) {
        return cityRepository.findByName(name).orElse(null);
    }

    public void addCity(City city) {
        cityRepository.save(city);
    }

    public void deleteCity(City city) {
        cityRepository.delete(city);
    }

}
