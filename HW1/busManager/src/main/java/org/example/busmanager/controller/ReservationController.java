package org.example.busmanager.controller;

import org.example.busmanager.entity.Bus;
import org.example.busmanager.entity.Reservation;
import org.example.busmanager.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final BusService busService;

    @Autowired
    public ReservationController(BusService busService) {
        this.busService = busService;
    }

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(Bus bus, String name, String email, String phone) {
        return ResponseEntity.ok(busService.createReservation(bus, name, email, phone));
    }

}
