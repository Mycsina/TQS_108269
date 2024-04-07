package org.example.busmanager.unittest;

import org.example.busmanager.entity.Bus;
import org.example.busmanager.entity.Reservation;
import org.example.busmanager.repository.BusRepository;
import org.example.busmanager.repository.ReservationRepository;
import org.example.busmanager.repository.RouteRepository;
import org.example.busmanager.repository.SeatRepository;
import org.example.busmanager.service.BusService;
import org.example.busmanager.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusUTest {
    @Mock(strictness = Mock.Strictness.LENIENT)
    private BusRepository busRepository;
    @Mock(strictness = Mock.Strictness.LENIENT)
    private ReservationRepository reservationRepository;
    @Mock
    private RouteRepository routeRepository;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private ReservationService reservationService;
    @InjectMocks
    private BusService busService;

    @BeforeEach
    public void setUp() {
        Bus bus = new Bus().name("Bus1").seat_count(5);

        when(busRepository.findById(1L)).thenReturn(java.util.Optional.of(bus));
        when(busRepository.findById(-1L)).thenThrow(new NoSuchElementException());
        when(reservationService.saveReservation(any(Reservation.class))).then(returnsFirstArg());
    }

    @Test
    void whenFindBusById_thenReturnBus() {
        Bus bus = busService.getBusById(1L);
        assertThat(bus.name(), is(equalTo("Bus1")));
        verify(busRepository, times(1)).findById(1L);
    }

    @Test
    void whenInvalidId_thenReturnNull() {
        try {
            busService.getBusById(-1L);
        } catch (NoSuchElementException e) {
            assertThat(e.getClass(), is(equalTo(NoSuchElementException.class)));
        }
        verify(busRepository, times(1)).findById(-1L);
    }

    @Test
    void whenNoAvailableSeats_thenFailReservation() {
        Bus bus = busService.getBusById(1L);
        Reservation reservation = new Reservation()
                .name("John")
                .phone("123456789")
                .email("text@example.com");
        try {
            busService.createReservation(bus, reservation, 1, 2, 3, 4, 5, 6);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is(equalTo("Not enough available seats")));
        }
    }

    @Test
    void whenAvailableSeats_thenCreateReservation() {
        Bus bus = busService.getBusById(1L);
        Reservation reservation = new Reservation()
                .name("John")
                .phone("123456789")
                .email("test@example.com");
        busService.createReservation(bus, reservation, 1, 2, 3, 4, 5);
        verify(reservationService, times(1)).saveReservation(any(Reservation.class));
    }
}