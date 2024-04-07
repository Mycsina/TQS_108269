package org.example.busmanager.controller;

import org.example.busmanager.entity.Bus;
import org.example.busmanager.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bus")
public class BusController {
    private final BusService busService;

    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<Bus>> getBuses(@RequestParam("departure") String departure, @RequestParam("arrival") String arrival) {
        return ResponseEntity.ok(busService.getBusByDepartureCityAndArrivalCity(departure, arrival));
    }

    @PostMapping("/add")
    public ResponseEntity<Bus> addBus(@RequestParam String name, @RequestParam int seatCount, @RequestParam Long routeId) {
        Bus bus = new Bus().setName(name).setSeat_count(seatCount);
        busService.addBus(bus, routeId);
        return ResponseEntity.ok(bus);
    }
}
