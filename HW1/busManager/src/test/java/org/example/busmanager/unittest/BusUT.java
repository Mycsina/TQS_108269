package org.example.busmanager.unittest;

import org.example.busmanager.entity.Bus;
import org.example.busmanager.entity.City;
import org.example.busmanager.entity.Route;
import org.example.busmanager.repository.BusRepository;
import org.example.busmanager.service.BusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BusUT {
    @Mock
    private BusRepository busRepository;
    @InjectMocks
    private BusService busService;
    @BeforeEach
    public void setUp() {
        Bus bus = new Bus();
        bus.name("Bus1");
        bus.seat_count(5);
    }
}
