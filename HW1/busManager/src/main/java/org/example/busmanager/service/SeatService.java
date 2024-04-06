package org.example.busmanager.service;

import org.example.busmanager.entity.Reservation;
import org.example.busmanager.entity.Seat;
import org.example.busmanager.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {
    private final SeatRepository seatRepository;

    @Autowired
    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void reserveSeat(int seatNum, Reservation reservation) {
        Seat seat = new Seat();
        seat.setNumber(seatNum);
        seat.setReservation(reservation);
        seatRepository.save(seat);
    }
}
