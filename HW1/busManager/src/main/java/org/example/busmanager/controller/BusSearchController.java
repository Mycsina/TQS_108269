package org.example.busmanager.controller;

import org.example.busmanager.entity.Bus;
import org.example.busmanager.entity.Route;
import org.example.busmanager.service.BusService;
import org.example.busmanager.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class BusSearchController {
    private final BusService busService;
    private final RouteService routeService;

    @Autowired
    public BusSearchController(BusService busService, RouteService routeService) {
        this.busService = busService;
        this.routeService = routeService;
    }

    @GetMapping("/buses")
    public List<Bus> getBuses(@RequestParam("departure") String departure, @RequestParam("arrival") String arrival) {
        List<Route> routes = routeService.getRoutesByDepartureCityAndArrivalCity(departure, arrival);
        List<Bus> buses = new ArrayList<>();
        for (Route route : routes) {
            buses.addAll(busService.getBusesByRoute(route));
        }
        return buses;
    }
}
