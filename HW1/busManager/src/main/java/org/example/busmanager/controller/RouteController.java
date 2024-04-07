package org.example.busmanager.controller;

import org.example.busmanager.entity.Route;
import org.example.busmanager.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/route")
@RestController
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Route>> getRoutesByDepartureCityAndArrivalCity(
            @RequestParam("departure") String departure,
            @RequestParam("arrival") String arrival
    ) {
        List<Route> routes = routeService.getRoutesByDepartureCityAndArrivalCity(departure, arrival);
        var response = ResponseEntity.ok(routes);
        return ResponseEntity.ok(routes);
    }
}
