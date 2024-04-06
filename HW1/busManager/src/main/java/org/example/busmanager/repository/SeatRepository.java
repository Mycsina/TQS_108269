package org.example.busmanager.repository;

import org.example.busmanager.entity.Reservation;
import org.example.busmanager.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByReservation(Reservation reservation);
}
