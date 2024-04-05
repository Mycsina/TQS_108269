package org.example.busmanager.repository;

import org.example.busmanager.entity.Bus;
import org.example.busmanager.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
    List<Bus> findByRoute(Route route);
}
