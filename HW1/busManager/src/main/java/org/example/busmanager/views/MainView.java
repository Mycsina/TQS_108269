package org.example.busmanager.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.example.busmanager.entity.Bus;
import org.example.busmanager.entity.City;
import org.example.busmanager.entity.CurrencyResponse;
import org.example.busmanager.entity.Reservation;
import org.example.busmanager.service.BusService;
import org.example.busmanager.service.CityService;
import org.example.busmanager.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Route("")
public class MainView extends VerticalLayout {

    private final transient CityService cityService;
    private final transient BusService busService;
    private final transient CurrencyService currencyService;

    private final transient Logger logger = LoggerFactory.getLogger(MainView.class);

    Grid<Bus> busGrid = new Grid<>(Bus.class);
    ReservationForm reservationForm;
    ComboBox<City> departureBox;
    ComboBox<City> arrivalBox;
    Button searchButton;
    ComboBox<CurrencyResponse.Rate> currencyBox;


    public MainView(CityService cityService, BusService busService, CurrencyService currencyService) {
        this.cityService = cityService;
        this.busService = busService;
        this.currencyService = currencyService;

        setSizeFull();
        configureGrid();
        configureForm();

        add(new CitySelector(), getContent());
    }

    public static String extractHourAndMinute(Long epoch) {
        LocalDateTime date = LocalDateTime.ofEpochSecond(epoch, 0, ZoneOffset.UTC);
        return date.getHour() + ":" + date.getMinute();
    }

    public void configureGrid() {
        busGrid.setSizeFull();
        busGrid.setColumns();
        busGrid.addColumn(Bus::getName).setHeader("Name");
        busGrid.addColumn(e -> formatPrice(e.getRoute().getPrice())).setHeader("Price");
        busGrid.addColumn(e -> extractHourAndMinute(e.getRoute().getDeparture())).setHeader("Departure Time");
        busGrid.addColumn(e -> extractHourAndMinute(e.getRoute().getArrival())).setHeader("Arrival Time");
        busGrid.addColumn(this::formatSeats).setHeader("Taken Seats");
        busGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    public void configureForm() {
        reservationForm = new ReservationForm();
        reservationForm.setWidth("25em");
    }

    public Component getContent() {
        HorizontalLayout content = new HorizontalLayout(busGrid, reservationForm);
        content.setFlexGrow(2, busGrid);
        content.setFlexGrow(1, reservationForm);
        content.setSizeFull();
        content.addClassName("content");
        return content;
    }

    public String formatPrice(Double price) {
        Double convertedPrice = currencyService.convertCurrency(price, "USD", currencyBox.getValue().getCurrency());
        return String.format("%.2f", convertedPrice);
    }

    public String formatSeats(Bus bus) {
        return busService.getTakenSeatNumbers(bus).size() + "/" + busService.getMaximumSeatsCount(bus);
    }

    public Long processReservation() {
        Bus selected = busGrid.asSingleSelect().getValue();
        if (selected == null) {
            Notifications.errorNotification("Please select a bus to reserve");
            return null;
        }
        String name = reservationForm.name.getValue();
        String email = reservationForm.email.getValue();
        String phone = reservationForm.phone.getValue();
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Notifications.errorNotification("Please fill all fields");
            return null;
        }
        Reservation reservation = busService.createReservation(selected, name, phone, email);
        return reservation.getId();
    }

    static class Notifications {

        private Notifications() {
        }

        public static Notification defaultNotification(String message) {
            return Notification.show(message);
        }

        public static Notification errorNotification(String message) {
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            Div content = new Div(new Text(message));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.addClickListener(event -> notification.close());

            HorizontalLayout layout = new HorizontalLayout(content, closeButton);
            layout.setAlignItems(Alignment.CENTER);

            notification.add(layout);
            notification.open();
            return notification;
        }
    }

    class ReservationForm extends FormLayout {
        TextField name = new TextField("Name");
        EmailField email = new EmailField("Email");
        TextField phone = new TextField("Phone");

        TextField seats = new TextField("Seat");

        Button reserve = new Button("Reserve");
        Button cancel = new Button("Cancel");

        public ReservationForm() {
            addClassName("reservation-form");

            add(name, email, phone, seats, createButtonsLayout());
        }

        private HorizontalLayout createButtonsLayout() {
            reserve.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

            reserve.addClickShortcut(Key.ENTER);
            cancel.addClickShortcut(Key.ESCAPE);

            reserve.addClickListener(event -> {
                // redirect to other reservation view
                Long reservationId = processReservation();
                reserve.getUI()
                        .ifPresent(ui -> ui.navigate(ReservationView.class, reservationId));
            });

            cancel.addClickListener(event -> Notifications.defaultNotification("Cancelled"));

            return new HorizontalLayout(reserve, cancel);
        }
    }

    class CitySelector extends HorizontalLayout {
        public CitySelector() {
            departureBox = new ComboBox<>("Departure city");
            departureBox.addClassName("departure");
            departureBox.setItems(cityService.getAllCities());
            departureBox.setItemLabelGenerator(City::getName);

            arrivalBox = new ComboBox<>("Arrival city");
            arrivalBox.addClassName("arrival");
            arrivalBox.setItems(cityService.getAllCities());
            arrivalBox.setItemLabelGenerator(City::getName);

            add(departureBox);
            add(arrivalBox);

            searchButton = getButton(departureBox, arrivalBox);
            add(searchButton);

            currencyBox = new ComboBox<>("Currency");
            currencyBox.setItems(currencyService.getAvailableCurrencies());
            currencyBox.setItemLabelGenerator(CurrencyResponse.Rate::getCurrency);
            currencyBox.setValue(new CurrencyResponse.Rate("USD", 1.0));
            currencyBox.addValueChangeListener(e -> {
                if (e.getValue() != null) {
                    Notification.show("Selected currency: " + e.getValue().getCurrency());
                }
                busGrid.getDataProvider().refreshAll();
            });
            add(currencyBox);
        }

        private Button getButton(ComboBox<City> cityComboBox, ComboBox<City> cityComboBox2) {
            searchButton = new Button("Search");
            searchButton.addClassName("search");
            searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            searchButton.addClickListener(e -> {
                if (cityComboBox.getValue().equals(cityComboBox2.getValue())) {
                    Notifications.errorNotification("Departure and arrival cities cannot be the same");
                } else if (cityComboBox.getValue() != null && cityComboBox2.getValue() != null) {
                    Notification.show("Searching for routes from " + cityComboBox.getValue().getName() + " to " + cityComboBox2.getValue().getName());
                } else {
                    Notifications.errorNotification("Please select both departure and arrival cities");
                }
                logger.info("Searching for routes from {} to {}", cityComboBox.getValue().getName(), cityComboBox2.getValue().getName());

                List<Bus> buses = busService.getBusByDepartureCityAndArrivalCity(cityComboBox.getValue().getName(), cityComboBox2.getValue().getName());
                busGrid.setItems(buses);
            });

            return searchButton;
        }
    }
}
