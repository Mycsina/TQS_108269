package org.example.busmanager.service;

import org.example.busmanager.entity.City;
import org.example.busmanager.entity.Route;
import org.example.busmanager.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
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

    public Route createRoute(Instant departure, Instant arrival, String departureCity, String arrivalCity) {
        City from = cityService.getCityByName(departureCity);
        City to = cityService.getCityByName(arrivalCity);
        Route route = new Route().setDeparture(departure.getEpochSecond()).setArrival(arrival.getEpochSecond()).setFrom_city(from).setTo_city(to);
        return routeRepository.save(route);
    }

    public void addRoute(Route route) {
        routeRepository.save(route);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id).orElse(null);
    }

    public List<Route> getRoutesByDepartureCity(String city) {
        City departure = cityService.getCityByName(city);
        return routeRepository.findByFrom_city_Id(departure.getId());
    }

    public List<Route> getRoutesByArrivalCity(String city) {
        City arrival = cityService.getCityByName(city);
        return routeRepository.findByTo_city_Id(arrival.getId());
    }

    public List<Route> getRoutesByDepartureCityAndArrivalCity(String departureCity, String arrivalCity) {
        City departure = cityService.getCityByName(departureCity);
        City arrival = cityService.getCityByName(arrivalCity);
        return routeRepository.findByFrom_city_IdAndTo_city_Id(departure.getId(), arrival.getId());
    }
}
