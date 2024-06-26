package org.example.busmanager.controller;

import org.example.busmanager.entity.Route;
import org.example.busmanager.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequestMapping("/route")
@RestController
public class RouteController {
    private final RouteService routeService;

    private final Logger logger = LoggerFactory.getLogger(RouteController.class);

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
        return ResponseEntity.ok(routes);
    }

    @PostMapping("/add")
    public ResponseEntity<Route> addRoute(@RequestParam String departure, @RequestParam String arrival, @RequestParam String departureCity, @RequestParam String arrivalCity) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        Instant dateDept = LocalDateTime.parse(departure, formatter).toInstant(ZoneOffset.UTC);
        Instant dateArr = LocalDateTime.parse(arrival, formatter).toInstant(ZoneOffset.UTC);
        Route route = routeService.createRoute(dateDept, dateArr, departureCity, arrivalCity);
        logger.info("Route added: {}", route);
        return ResponseEntity.ok(route);
    }
}
