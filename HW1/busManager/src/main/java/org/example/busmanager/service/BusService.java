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
    private final ReservationService reservationService;

    @Autowired
    public BusService(BusRepository busRepository, RouteService routeService, SeatService seatService, ReservationService reservationService) {
        this.busRepository = busRepository;
        this.routeService = routeService;
        this.seatService = seatService;
        this.reservationService = reservationService;
    }

    public List<Bus> getBusByDepartureCityAndArrivalCity(String departure, String arrival) {
        List<Route> routes = routeService.getRoutesByDepartureCityAndArrivalCity(departure, arrival);
        List<Bus> buses = new ArrayList<>();
        for (Route route : routes) {
            buses.addAll(busRepository.findByRoute(route));
        }
        return buses;
    }

    public int getMaximumSeatsCount(Bus bus) {
        return bus.getSeat_count();
    }

    public Reservation createReservation(Bus bus, String name, String phone, String email, int... seatNumbers) {
        Reservation reservation = new Reservation().setName(name).setPhone(phone).setEmail(email);
        return createReservation(bus, reservation, seatNumbers);
    }

    public Reservation createReservation(Bus bus, Reservation reservation, int... seatNumbers) {
        reservation.setBus(bus);
        reservationService.saveReservation(reservation);
        if (getAvailableSeatNumbers(bus).size() < seatNumbers.length) {
            throw new IllegalArgumentException("Not enough available seats");
        }
        reservationService.addSeats(reservation, seatNumbers);
        return reservation;
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

    public void addBus(Bus bus, Long routeId) {
        Route route = routeService.getRouteById(routeId);
        bus.setRoute(route);
        busRepository.save(bus);
    }

    public void deleteBus(Bus bus) {
        busRepository.delete(bus);
    }
}