package org.example.busmanager.repository;

import org.example.busmanager.entity.Bus;
import org.example.busmanager.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByBus(Bus bus);

    List<Reservation> findByEmail(String email);
}
