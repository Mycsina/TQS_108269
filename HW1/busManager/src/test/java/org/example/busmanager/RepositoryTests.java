package org.example.busmanager;

import org.example.busmanager.entity.*;
import org.example.busmanager.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
public class RepositoryTests {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private BusRepository busRepository;

    @Test
    void whenFindBusById_thenReturnBus() {
        Bus bus = new Bus();
        bus.setName("65");
        bus.setSeat_count(50);
        entityManager.persistAndFlush(bus);
        Bus found = busRepository.findById(bus.getId()).orElseThrow();
        assertThat(found, is(equalTo(bus)));
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        Bus fromDb = busRepository.findById(-11L).orElse(null);
        assertThat(fromDb, is(nullValue()));
    }

    @Test
    void whenFindCityByName_thenReturnCity() {
        City city = new City();
        city.setName("New York");
        city.setCountry("USA");
        entityManager.persistAndFlush(city);
        City found = cityRepository.findByName(city.getName()).orElseThrow();
        assertThat(found, is(equalTo(city)));
    }

    @Test
    void whenInvalidCityName_thenReturnNull() {
        City fromDb = cityRepository.findByName("Invalid").orElse(null);
        assertThat(fromDb, is(nullValue()));
    }

    @Test
    void whenFindRouteByDepartureCity_thenReturnRoute() {
        City city = new City();
        city.setName("New York");
        city.setCountry("USA");
        entityManager.persistAndFlush(city);
        Route route = new Route();
        route.setFrom_city(city);
        entityManager.persistAndFlush(route);
        List<Route> found = routeRepository.findByFrom_city_Id(city.getId());
        assertThat(found, hasItem(route));
    }

    @Test
    void whenInvalidDepartureCity_thenReturnEmptyList() {
        List<Route> fromDb = routeRepository.findByFrom_city_Id(-11L);
        assertThat(fromDb, is(empty()));
    }

    @Test
    void whenFindRouteByDepartureAndArrivalCity_thenReturnRoute() {
        City fromCity = new City();
        fromCity.setName("New York");
        fromCity.setCountry("USA");
        entityManager.persistAndFlush(fromCity);
        City toCity = new City();
        toCity.setName("Los Angeles");
        toCity.setCountry("USA");
        entityManager.persistAndFlush(toCity);
        Route route = new Route();
        route.setFrom_city(fromCity);
        route.setTo_city(toCity);
        entityManager.persistAndFlush(route);
        List<Route> found = routeRepository.findByFrom_city_IdAndTo_city_Id(fromCity.getId(), toCity.getId());
        assertThat(found, hasItem(route));
    }

    @Test
    void whenFindReservationsByBus_thenReturnReservations() {
        Bus bus = new Bus();
        bus.setName("65");
        bus.setSeat_count(50);
        entityManager.persistAndFlush(bus);
        Reservation reservation = new Reservation();
        reservation.setBus(bus);
        entityManager.persistAndFlush(reservation);
        List<Reservation> found = reservationRepository.findByBus(bus);
        assertThat(found, hasItem(reservation));
    }

    @Test
    void whenReservationDoesNotHaveBus_thenConfirmConstraintFail() {
        Reservation reservation = new Reservation();
        try {
            entityManager.persistAndFlush(reservation);
        } catch (Exception e) {
            assertThat(e.getMessage(), containsString("not-null property references a null or transient value"));
        }
    }

    @Test
    void whenFindSeatsByReservation_thenReturnSeats() {
        Bus bus = new Bus();
        bus.setName("65");
        bus.setSeat_count(50);
        entityManager.persistAndFlush(bus);
        Reservation reservation = new Reservation();
        reservation.setBus(bus);
        entityManager.persistAndFlush(reservation);
        Seat seat = new Seat();
        seat.setNumber(1);
        seat.setReservation(reservation);
        entityManager.persistAndFlush(seat);
        List<Seat> found = seatRepository.findByReservation(reservation);
        assertThat(found, hasItem(seat));
    }

    @Test
    void whenSeatDoesNotHaveReservation_thenConfirmConstraintFail() {
        Seat seat = new Seat();
        seat.setNumber(1);
        try {
            entityManager.persistAndFlush(seat);
        } catch (Exception e) {
            assertThat(e.getMessage(), containsString("not-null property references a null or transient value"));
        }
    }
}
