package org.example.busmanager.views;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import org.example.busmanager.entity.Reservation;
import org.example.busmanager.service.ReservationService;

import java.util.List;

@Route("reservation")
public class ReservationView extends VerticalLayout implements HasUrlParameter<Long> {
    transient ReservationService reservationService;

    transient Reservation redirectedReservation;
    Grid<Reservation> reservationGrid = new Grid<>(Reservation.class);

    public ReservationView(ReservationService reservationService) {
        this.reservationService = reservationService;

        setSizeFull();

        Text greeting = new Text("Thank you for your reservation!\nHere's your reservations so far:");
        configureGrid();
        add(greeting, reservationGrid);
    }

    public void configureGrid() {
        reservationGrid.setSizeFull();
        reservationGrid.setColumns();
        reservationGrid.addColumn(Reservation::getId).setHeader("ID");
        reservationGrid.addColumn(e -> e.getBus().getName()).setHeader("Bus");
        reservationGrid.addColumn(e -> e.getBus().getRoute().getFrom_city().getName()).setHeader("From");
        reservationGrid.addColumn(e -> e.getBus().getRoute().getTo_city().getName()).setHeader("To");
        reservationGrid.addColumn(this::departureTime).setHeader("Departure");
        reservationGrid.addColumn(this::arrivalTime).setHeader("Arrival");
        reservationGrid.addColumn(
                e -> e.getSeats().stream()
                        .map(seat -> seat.getNumber().toString())
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .orElse("")
        ).setHeader("Seats");
        reservationGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    public String departureTime(Reservation reservation) {
        return MainView.extractHourAndMinute(reservation.getBus().getRoute().getDeparture());
    }

    public String arrivalTime(Reservation reservation) {
        return MainView.extractHourAndMinute(reservation.getBus().getRoute().getArrival());
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        redirectedReservation = reservationService.getReservationById(parameter);
        List<Reservation> reservations = reservationService.getReservationBySameEmail(redirectedReservation.getEmail());
        reservationGrid.setItems(reservations);
    }
}
