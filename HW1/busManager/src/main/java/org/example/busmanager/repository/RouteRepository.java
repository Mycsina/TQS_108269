package org.example.busmanager.repository;

import org.example.busmanager.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    @Query("select r from Route r where r.to_city.id = ?1")
    List<Route> findByTo_city_Id(Long id);

    @Query("select r from Route r where r.from_city.id = ?1")
    List<Route> findByFrom_city_Id(Long id);
}
