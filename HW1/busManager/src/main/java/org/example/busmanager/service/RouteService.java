package org.example.busmanager.service;

import org.example.busmanager.entity.City;
import org.example.busmanager.entity.Route;
import org.example.busmanager.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepository;
    private final CityService cityService;

    @Autowired
    public RouteService(RouteRepository routeRepository, CityService cityService) {
        this.routeRepository = routeRepository;
        this.cityService = cityService;
    }

    public List<Route> getRoutesByDepartureCity(String city) {
        City departure = cityService.getCityByName(city);
        return routeRepository.findByFrom_city_Id(departure.getId());
    }

    public List<Route> getRoutesByArrivalCity(String city) {
        City arrival = cityService.getCityByName(city);
        return routeRepository.findByTo_city_Id(arrival.getId());
    }
}
