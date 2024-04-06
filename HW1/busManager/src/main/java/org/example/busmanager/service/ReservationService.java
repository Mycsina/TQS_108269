package org.example.busmanager.service;

import org.example.busmanager.entity.Reservation;
import org.example.busmanager.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final SeatService seatService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, SeatService seatService) {
        this.reservationRepository = reservationRepository;
        this.seatService = seatService;
    }

    public Reservation createReservation(String name, String phone, String email) {
        Reservation reservation = new Reservation();
        reservation.setName(name);
        reservation.setPhone(phone);
        reservation.setEmail(email);
        return reservationRepository.save(reservation);
    }

    public Reservation addSeats(Reservation reservation, int... seatNumbers) {
        for (int seatNumber : seatNumbers) {
            seatService.reserveSeat(seatNumber, reservation);
        }
        return reservation;
    }
}
