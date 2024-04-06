package org.example.busmanager.service;


import org.example.busmanager.entity.Bus;
import org.example.busmanager.entity.Reservation;
import org.example.busmanager.entity.Route;
import org.example.busmanager.entity.Seat;
import org.example.busmanager.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusService {
    private final BusRepository busRepository;
    private final RouteService routeService;
    private final SeatService seatService;

    @Autowired
    public BusService(BusRepository busRepository, RouteService routeService, SeatService seatService) {
        this.busRepository = busRepository;
        this.routeService = routeService;
        this.seatService = seatService;
    }

    public int getMaximumSeatsCount(Bus bus) {
        return bus.getSeat_count();
    }

    public List<Integer> getTakenSeatNumbers(Bus bus) {
        List<Integer> takenSeats = new ArrayList<>();
        for (Reservation res : bus.getReservations()) {
            for (Seat seat : res.getSeats()) {
                takenSeats.add(seat.getNumber());
            }
        }
        return takenSeats;
    }

    public List<Integer> getAvailableSeatNumbers(Bus bus) {
        List<Integer> allSeats = new ArrayList<>();
        for (int i = 1; i <= bus.getSeat_count(); i++) {
            allSeats.add(i);
        }
        List<Integer> reservedSeats = getTakenSeatNumbers(bus);
        allSeats.removeAll(reservedSeats);
        return allSeats;
    }

    public List<Bus> getBusesByRoute(Route route) {
        return busRepository.findByRoute(route);
    }

    public List<Bus> getBusesByDepartureCity(String city) {
        List<Route> routes = routeService.getRoutesByDepartureCity(city);
        List<Bus> result = new ArrayList<>();
        for (Route route : routes) {
            result.addAll(busRepository.findByRoute(route));
        }
        return result;
    }

    public List<Bus> getBusesByArrivalCity(String city) {
        List<Route> routes = routeService.getRoutesByArrivalCity(city);
        List<Bus> result = new ArrayList<>();
        for (Route route : routes) {
            result.addAll(busRepository.findByRoute(route));
        }
        return result;
    }

    public Bus getBusById(Long id) {
        return busRepository.findById(id).orElseThrow();
    }

    public List<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    public void addBus(Bus bus) {
        busRepository.save(bus);
    }

    public void deleteBus(Bus bus) {
        busRepository.delete(bus);
    }
}