import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.stream.Collectors;

public class BusBookingApp extends Application {

    // Data Models
    public static class Bus {
        private String busNumber;
        private String from;
        private String to;
        private String route;
        private int capacity;
        private String status;
        private String eta;
        private double fare;
        private String departureTime;
        private String arrivalTime;

        public Bus(String busNumber, String from, String to, String route, int capacity,
                   String status, String eta, double fare, String departureTime, String arrivalTime) {
            this.busNumber = busNumber;
            this.from = from;
            this.to = to;
            this.route = route;
            this.capacity = capacity;
            this.status = status;
            this.eta = eta;
            this.fare = fare;
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
        }

        public String getBusNumber() { return busNumber; }
        public void setBusNumber(String busNumber) { this.busNumber = busNumber; }
        public String getFrom() { return from; }
        public void setFrom(String from) { this.from = from; }
        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }
        public String getRoute() { return route; }
        public void setRoute(String route) { this.route = route; }
        public int getCapacity() { return capacity; }
        public void setCapacity(int capacity) { this.capacity = capacity; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getEta() { return eta; }
        public void setEta(String eta) { this.eta = eta; }
        public double getFare() { return fare; }
        public void setFare(double fare) { this.fare = fare; }
        public String getDepartureTime() { return departureTime; }
        public void setDepartureTime(String departureTime) { this.departureTime = departureTime; }
        public String getArrivalTime() { return arrivalTime; }
        public void setArrivalTime(String arrivalTime) { this.arrivalTime = arrivalTime; }
    }

    public static class Route {
        private String routeName;
        private String from;
        private String to;
        private String stops;
        private double fare;

        public Route(String routeName, String from, String to, String stops, double fare) {
            this.routeName = routeName;
            this.from = from;
            this.to = to;
            this.stops = stops;
            this.fare = fare;
        }

        public String getRouteName() { return routeName; }
        public void setRouteName(String routeName) { this.routeName = routeName; }
        public String getFrom() { return from; }
        public void setFrom(String from) { this.from = from; }
        public String getTo() { return to; }
        public void setTo(String to) { this.to = to; }
        public String getStops() { return stops; }
        public void setStops(String stops) { this.stops = stops; }
        public double getFare() { return fare; }
        public void setFare(double fare) { this.fare = fare; }
    }

    public static class Review {
        private String userName;
        private String busNumber;
        private int rating;
        private String comment;
        private String date;

        public Review(String userName, String busNumber, int rating, String comment, String date) {
            this.userName = userName;
            this.busNumber = busNumber;
            this.rating = rating;
            this.comment = comment;
            this.date = date;
        }

        public String getUserName() { return userName; }
        public String getBusNumber() { return busNumber; }
        public int getRating() { return rating; }
        public String getComment() { return comment; }
        public String getDate() { return date; }
    }

    public static class Booking {
        private String bookingId;
        private String busNumber;
        private String passengerName;
        private String seatNumber;
        private String bookingDate;
        private String status;
        private double fare;
        private String from;
        private String to;
        private String departureTime;

        public Booking(String bookingId, String busNumber, String passengerName, String seatNumber,
                       String bookingDate, String status, double fare, String from, String to, String departureTime) {
            this.bookingId = bookingId;
            this.busNumber = busNumber;
            this.passengerName = passengerName;
            this.seatNumber = seatNumber;
            this.bookingDate = bookingDate;
            this.status = status;
            this.fare = fare;
            this.from = from;
            this.to = to;
            this.departureTime = departureTime;
        }

        public String getBookingId() { return bookingId; }
        public String getBusNumber() { return busNumber; }
        public String getPassengerName() { return passengerName; }
        public String getSeatNumber() { return seatNumber; }
        public String getBookingDate() { return bookingDate; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public double getFare() { return fare; }
        public String getFrom() { return from; }
        public String getTo() { return to; }
        public String getDepartureTime() { return departureTime; }
    }

    private Stage window;
    private String userRole = "";
    private String currentUserName = "";
    private ObservableList<Bus> busList = FXCollections.observableArrayList();
    private ObservableList<Bus> filteredBusList = FXCollections.observableArrayList();
    private ObservableList<Route> routeList = FXCollections.observableArrayList();
    private ObservableList<Review> reviewList = FXCollections.observableArrayList();
    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        loadSampleData();

        Scene loginScene = createLoginScene();
        window.setScene(loginScene);
        // Change #1: Update window title
        window.setTitle("FORTI BUS - Booking");
        window.show();
    }

    private void loadSampleData() {
        busList.addAll(
                new Bus("BUS-001", "Downtown", "Airport", "Downtown → Mall → Terminal → Airport",
                        40, "Active", "8 min", 25.0, "08:00 AM", "10:30 AM"),
                new Bus("BUS-002", "Central", "Beach", "Central → Park → Marina → Beach",
                        40, "Active", "15 min", 30.0, "09:30 AM", "12:00 PM"),
                new Bus("BUS-003", "North Station", "South Station", "North Station → Central → Market → South Station",
                        40, "Active", "20 min", 20.0, "10:00 AM", "01:00 PM"),
                new Bus("BUS-004", "Airport", "Downtown", "Airport → Highway → Mall → Downtown",
                        40, "Active", "12 min", 25.0, "02:00 PM", "04:30 PM"),
                new Bus("BUS-005", "Downtown", "Beach", "Downtown → Central → Marina → Beach",
                        40, "Active", "10 min", 35.0, "07:00 AM", "10:30 AM")
        );

        routeList.addAll(
                new Route("Route A", "Downtown", "Airport", "Mall, Terminal", 25.0),
                new Route("Route B", "Central", "Beach", "Park, Marina", 30.0),
                new Route("Route C", "North Station", "South Station", "Central, Market", 20.0)
        );

        reviewList.addAll(
                new Review("John Doe", "BUS-001", 5, "Excellent service! Very clean and punctual.", "2025-10-01"),
                new Review("Jane Smith", "BUS-001", 4, "Clean buses, on time. Comfortable seats.", "2025-10-02"),
                new Review("Mike Johnson", "BUS-002", 5, "Great route to the beach. Friendly driver.", "2025-10-03"),
                new Review("Sarah Williams", "BUS-004", 3, "Decent service but could be more frequent.", "2025-10-04")
        );

        filteredBusList.addAll(busList);
    }

    private Scene createLoginScene() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(50));
        layout.setStyle("-fx-background: linear-gradient(to bottom, #667eea 0%, #764ba2 100%);");

        // Change: Branded title
        Label title = new Label("🚌 FORTI BUS Booking");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label subtitle = new Label("Book Your Journey with Us");
        subtitle.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-opacity: 0.9;");

        // User login form
        VBox loginForm = new VBox(15);
        loginForm.setAlignment(Pos.CENTER);
        loginForm.setPadding(new Insets(30));
        loginForm.setStyle("-fx-background-color: white; -fx-background-radius: 10;");
        loginForm.setMaxWidth(400);

        Label formTitle = new Label("Select Your Role");
        formTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

        Button userBtn = createStyledButton("Continue as Passenger", "#4CAF50");
        Button ownerBtn = createStyledButton("Login as Owner/Admin", "#FF9800");

        userBtn.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty()) {
                showAlert("Error", "Please enter your name to continue");
            } else {
                userRole = "User";
                currentUserName = nameField.getText().trim();
                window.setScene(createMainScene());
            }
        });

        ownerBtn.setOnAction(e -> {
            userRole = "Owner";
            currentUserName = "Admin";
            window.setScene(createMainScene());
        });

        loginForm.getChildren().addAll(formTitle, nameField, userBtn, ownerBtn);

        layout.getChildren().addAll(title, subtitle, loginForm);
        return new Scene(layout, 600, 500);
    }

    private Scene createMainScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Top Header
        HBox header = new HBox();
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: #667eea;");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(20);

        // Change: Branded title
        Label appTitle = new Label("🚌 FORTI BUS Booking");
        appTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userLabel = new Label("👤 " + currentUserName + " (" + userRole + ")");
        userLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8 15; -fx-cursor: hand;");
        logoutBtn.setOnAction(e -> {
            currentUserName = "";
            window.setScene(createLoginScene());
        });

        header.getChildren().addAll(appTitle, spacer, userLabel, logoutBtn);

        // Side Navigation
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        sidebar.setPrefWidth(220);

        if (userRole.equals("User")) {
            Button searchBtn = createNavButton("🔍 Search & Book Buses");
            Button routesBtn = createNavButton("🗺️ View Routes");
            Button trackBtn = createNavButton("📍 Track Buses");
            Button reviewBtn = createNavButton("⭐ Reviews & Ratings");
            Button bookingsBtn = createNavButton("🎫 My Bookings");
            // Change #2: Add Help button
            Button helpBtn = createNavButton("💬 Help & Support");

            searchBtn.setOnAction(e -> root.setCenter(createSearchBusesPane()));
            routesBtn.setOnAction(e -> root.setCenter(createViewRoutesPane()));
            trackBtn.setOnAction(e -> root.setCenter(createTrackBusesPane()));
            reviewBtn.setOnAction(e -> root.setCenter(createReviewPane()));
            bookingsBtn.setOnAction(e -> root.setCenter(createMyBookingsPane()));
            // Change #2: Set Help action
            helpBtn.setOnAction(e -> root.setCenter(createHelpPagePane()));

            // Change #2: Add Help button to sidebar
            sidebar.getChildren().addAll(searchBtn, routesBtn, trackBtn, reviewBtn, bookingsBtn, helpBtn);
        } else {
            Button manageBusBtn = createNavButton("🚌 Manage Buses");
            Button manageRouteBtn = createNavButton("🗺️ Manage Routes");
            Button viewReviewsBtn = createNavButton("⭐ View Reviews");
            Button viewBookingsBtn = createNavButton("🎫 View Bookings");

            manageBusBtn.setOnAction(e -> root.setCenter(createManageBusesPane()));
            manageRouteBtn.setOnAction(e -> root.setCenter(createManageRoutesPane()));
            viewReviewsBtn.setOnAction(e -> root.setCenter(createViewReviewsPane()));
            viewBookingsBtn.setOnAction(e -> root.setCenter(createOwnerBookingsPane()));

            sidebar.getChildren().addAll(manageBusBtn, manageRouteBtn, viewReviewsBtn, viewBookingsBtn);
        }

        root.setTop(header);
        root.setLeft(sidebar);
        root.setCenter(createSearchBusesPane());

        return new Scene(root, 1100, 700);
    }

    private Pane createSearchBusesPane() {
        VBox pane = createContentPane("🔍 Search & Book Buses");

        // Search Form
        VBox searchBox = new VBox(15);
        searchBox.setPadding(new Insets(20));
        searchBox.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        Label searchTitle = new Label("Find Your Bus");
        searchTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(15);

        TextField fromField = new TextField();
        fromField.setPromptText("From (e.g., Downtown)");
        fromField.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

        TextField toField = new TextField();
        toField.setPromptText("To (e.g., Airport)");
        toField.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

        DatePicker datePicker = new DatePicker(LocalDate.now());
        datePicker.setStyle("-fx-font-size: 14px;");

        form.add(new Label("From:"), 0, 0);
        form.add(fromField, 1, 0);
        form.add(new Label("To:"), 2, 0);
        form.add(toField, 3, 0);
        form.add(new Label("Date:"), 0, 1);
        form.add(datePicker, 1, 1);

        Button searchBtn = createStyledButton("Search Buses", "#667eea");
        Button clearBtn = createStyledButton("Clear", "#95a5a6");

        HBox buttonBox = new HBox(10, searchBtn, clearBtn);
        form.add(buttonBox, 2, 1, 2, 1);

        searchBox.getChildren().addAll(searchTitle, form);

        // Results area
        VBox resultsBox = new VBox(15);
        resultsBox.setPadding(new Insets(20, 0, 0, 0));

        Label resultsTitle = new Label("Available Buses");
        resultsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        VBox busesList = new VBox(10);

        // Display buses as cards
        for (Bus bus : filteredBusList) {
            busesList.getChildren().add(createBusCard(bus));
        }

        ScrollPane scrollPane = new ScrollPane(busesList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPrefHeight(400);

        resultsBox.getChildren().addAll(resultsTitle, scrollPane);

        searchBtn.setOnAction(e -> {
            String from = fromField.getText().trim().toLowerCase();
            String to = toField.getText().trim().toLowerCase();

            if (from.isEmpty() && to.isEmpty()) {
                filteredBusList.setAll(busList);
            } else {
                ObservableList<Bus> filtered = busList.stream()
                        .filter(bus -> {
                            boolean fromMatch = from.isEmpty() ||
                                    bus.getFrom().toLowerCase().contains(from);
                            boolean toMatch = to.isEmpty() ||
                                    bus.getTo().toLowerCase().contains(to);
                            return fromMatch && toMatch;
                        })
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                filteredBusList.setAll(filtered);

                if (filtered.isEmpty()) {
                    showAlert("No Results", "No buses found matching your search criteria");
                }
            }

            // Refresh the bus cards
            busesList.getChildren().clear();
            for (Bus bus : filteredBusList) {
                busesList.getChildren().add(createBusCard(bus));
            }
        });

        clearBtn.setOnAction(e -> {
            fromField.clear();
            toField.clear();
            datePicker.setValue(LocalDate.now());
            filteredBusList.setAll(busList);
            busesList.getChildren().clear();
            for (Bus bus : filteredBusList) {
                busesList.getChildren().add(createBusCard(bus));
            }
        });

        pane.getChildren().addAll(searchBox, resultsBox);
        return pane;
    }

    private HBox createBusCard(Bus bus) {
        HBox card = new HBox(20);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        card.setAlignment(Pos.CENTER_LEFT);

        // Left section - Bus info
        VBox leftSection = new VBox(8);
        leftSection.setPrefWidth(250);

        Label busNumber = new Label(bus.getBusNumber());
        busNumber.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

        Label route = new Label(bus.getFrom() + " → " + bus.getTo());
        route.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");

        Label routeDetails = new Label("Via: " + bus.getRoute());
        routeDetails.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        routeDetails.setWrapText(true);

        leftSection.getChildren().addAll(busNumber, route, routeDetails);

        // Middle section - Timing
        VBox middleSection = new VBox(8);
        middleSection.setPrefWidth(200);

        Label departure = new Label("🕐 Departure: " + bus.getDepartureTime());
        departure.setStyle("-fx-font-size: 13px; -fx-text-fill: #2c3e50;");

        Label arrival = new Label("🕐 Arrival: " + bus.getArrivalTime());
        arrival.setStyle("-fx-font-size: 13px; -fx-text-fill: #2c3e50;");

        Label eta = new Label("⏱️ ETA: " + bus.getEta());
        eta.setStyle("-fx-font-size: 12px; -fx-text-fill: #e67e22;");

        middleSection.getChildren().addAll(departure, arrival, eta);

        // Right section - Price and booking
        VBox rightSection = new VBox(10);
        rightSection.setAlignment(Pos.CENTER_RIGHT);
        rightSection.setPrefWidth(180);

        // Change #3: Currency symbol $ to ₹
        Label fareLabel = new Label("₹" + String.format("%.2f", bus.getFare()));
        fareLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");

        Label seatsLabel = new Label(getAvailableSeats(bus) + " seats available");
        seatsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");

        Button bookBtn = createStyledButton("Book Now", "#27ae60");
        bookBtn.setPrefWidth(140);

        if (userRole.equals("User")) {
            bookBtn.setOnAction(e -> openBookingPage(bus));
        } else {
            bookBtn.setText("View Details");
            bookBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                    "-fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;");
            bookBtn.setOnAction(e -> showAlert("Info", "Bus Details:\n" +
                    "Number: " + bus.getBusNumber() + "\n" +
                    "Route: " + bus.getRoute() + "\n" +
                    "Capacity: " + bus.getCapacity() + " seats\n" +
                    "Status: " + bus.getStatus()));
        }

        rightSection.getChildren().addAll(fareLabel, seatsLabel, bookBtn);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        card.getChildren().addAll(leftSection, middleSection, spacer, rightSection);
        return card;
    }

    private int getAvailableSeats(Bus bus) {
        int bookedSeats = (int) bookingList.stream()
                .filter(b -> b.getBusNumber().equals(bus.getBusNumber()))
                .count();
        return bus.getCapacity() - bookedSeats;
    }

    private void openBookingPage(Bus bus) {
        Stage bookingStage = new Stage();
        bookingStage.setTitle("Book Your Seat - " + bus.getBusNumber());

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Top section - Bus details
        VBox topSection = new VBox(15);
        topSection.setPadding(new Insets(25));
        topSection.setStyle("-fx-background-color: #667eea;");

        Label title = new Label("🚌 " + bus.getBusNumber());
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: white;");

        HBox routeBox = new HBox(20);
        routeBox.setAlignment(Pos.CENTER_LEFT);

        VBox fromBox = new VBox(5);
        Label fromLabel = new Label("FROM");
        fromLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: rgba(255,255,255,0.8);");
        Label fromValue = new Label(bus.getFrom());
        fromValue.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label fromTime = new Label(bus.getDepartureTime());
        fromTime.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        fromBox.getChildren().addAll(fromLabel, fromValue, fromTime);

        Label arrow = new Label("→");
        arrow.setStyle("-fx-font-size: 30px; -fx-text-fill: white;");

        VBox toBox = new VBox(5);
        Label toLabel = new Label("TO");
        toLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: rgba(255,255,255,0.8);");
        Label toValue = new Label(bus.getTo());
        toValue.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white;");
        Label toTime = new Label(bus.getArrivalTime());
        toTime.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");
        toBox.getChildren().addAll(toLabel, toValue, toTime);

        routeBox.getChildren().addAll(fromBox, arrow, toBox);

        // Change #3: Currency symbol $ to ₹
        Label fareInfo = new Label("Fare: ₹" + String.format("%.2f", bus.getFare()) + " per seat");
        fareInfo.setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

        topSection.getChildren().addAll(title, routeBox, fareInfo);

        // Center section - Seat selection
        VBox centerSection = new VBox(20);
        centerSection.setPadding(new Insets(25));
        centerSection.setAlignment(Pos.TOP_CENTER);

        Label seatTitle = new Label("Select Your Seat");
        seatTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Passenger name input
        HBox nameBox = new HBox(10);
        nameBox.setAlignment(Pos.CENTER);
        nameBox.setPadding(new Insets(0, 0, 20, 0));
        Label nameLabel = new Label("Passenger Name:");
        nameLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");
        TextField nameField = new TextField(currentUserName);
        nameField.setPromptText("Enter passenger name");
        nameField.setPrefWidth(300);
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 10;");
        nameBox.getChildren().addAll(nameLabel, nameField);

        // Seat grid
        GridPane seatGrid = new GridPane();
        seatGrid.setHgap(10);
        seatGrid.setVgap(10);
        seatGrid.setAlignment(Pos.CENTER);
        seatGrid.setPadding(new Insets(20));
        seatGrid.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        // Get booked seats
        ObservableList<String> bookedSeats = bookingList.stream()
                .filter(b -> b.getBusNumber().equals(bus.getBusNumber()))
                .map(Booking::getSeatNumber)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        final String[] selectedSeat = {""};
        Label selectedSeatLabel = new Label("Selected Seat: None");
        selectedSeatLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

        // Create 4 seats per row layout
        int seatsPerRow = 4;
        int rows = (int) Math.ceil((double) bus.getCapacity() / seatsPerRow);

        // Add driver section
        Label driverLabel = new Label("🚗 Driver");
        driverLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #95a5a6; -fx-font-weight: bold;");
        seatGrid.add(driverLabel, 0, 0, 2, 1);

        // Add aisle label
        Label aisleLabel = new Label("║ Aisle ║");
        aisleLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #95a5a6;");
        seatGrid.add(aisleLabel, 2, 1, 1, rows);

        int seatNum = 1;
        for (int row = 1; row <= rows; row++) {
            for (int col = 0; col < seatsPerRow && seatNum <= bus.getCapacity(); col++) {
                Button seatBtn = new Button(String.valueOf(seatNum));
                seatBtn.setPrefSize(60, 60);

                final int currentSeatNum = seatNum;
                String seatId = "S" + seatNum;

                if (bookedSeats.contains(seatId)) {
                    seatBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-font-size: 14px;");
                    seatBtn.setDisable(true);
                } else {
                    seatBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; " +
                            "-fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");

                    seatBtn.setOnAction(e -> {
                        // Reset previous selection
                        if (!selectedSeat[0].isEmpty()) {
                            for (javafx.scene.Node node : seatGrid.getChildren()) {
                                if (node instanceof Button) {
                                    Button btn = (Button) node;
                                    if (btn.getText().equals(selectedSeat[0].replace("S", "")) &&
                                            !btn.isDisabled()) {
                                        btn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; " +
                                                "-fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
                                    }
                                }
                            }
                        }

                        // Set new selection
                        selectedSeat[0] = "S" + currentSeatNum;
                        seatBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                                "-fx-font-weight: bold; -fx-font-size: 14px; -fx-border-color: #2c3e50; " +
                                "-fx-border-width: 3; -fx-cursor: hand;");
                        selectedSeatLabel.setText("Selected Seat: " + selectedSeat[0]);
                    });
                }

                // Adjust column for aisle
                int gridCol = col < 2 ? col : col + 1;
                seatGrid.add(seatBtn, gridCol, row);
                seatNum++;
            }
        }

        // Legend
        HBox legend = new HBox(30);
        legend.setAlignment(Pos.CENTER);
        legend.setPadding(new Insets(20, 0, 0, 0));

        HBox availableBox = new HBox(8);
        availableBox.setAlignment(Pos.CENTER);
        Button availableDemo = new Button();
        availableDemo.setPrefSize(30, 30);
        availableDemo.setStyle("-fx-background-color: #2ecc71;");
        availableDemo.setDisable(true);
        Label availableLabel = new Label("Available");
        availableLabel.setStyle("-fx-font-size: 13px;");
        availableBox.getChildren().addAll(availableDemo, availableLabel);

        HBox bookedBox = new HBox(8);
        bookedBox.setAlignment(Pos.CENTER);
        Button bookedDemo = new Button();
        bookedDemo.setPrefSize(30, 30);
        bookedDemo.setStyle("-fx-background-color: #e74c3c;");
        bookedDemo.setDisable(true);
        Label bookedLabel = new Label("Booked");
        bookedLabel.setStyle("-fx-font-size: 13px;");
        bookedBox.getChildren().addAll(bookedDemo, bookedLabel);

        HBox selectedBox = new HBox(8);
        selectedBox.setAlignment(Pos.CENTER);
        Button selectedDemo = new Button();
        selectedDemo.setPrefSize(30, 30);
        selectedDemo.setStyle("-fx-background-color: #3498db; -fx-border-color: #2c3e50; -fx-border-width: 3;");
        selectedDemo.setDisable(true);
        Label selectedLabel = new Label("Selected");
        selectedLabel.setStyle("-fx-font-size: 13px;");
        selectedBox.getChildren().addAll(selectedDemo, selectedLabel);

        legend.getChildren().addAll(availableBox, bookedBox, selectedBox);

        ScrollPane scrollPane = new ScrollPane(seatGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPrefHeight(400);

        centerSection.getChildren().addAll(seatTitle, nameBox, scrollPane, selectedSeatLabel, legend);

        // Bottom section - Payment button
        HBox bottomSection = new HBox(15);
        bottomSection.setPadding(new Insets(20));
        bottomSection.setAlignment(Pos.CENTER);
        bottomSection.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 1 0 0 0;");

        // Change #3: Currency symbol $ to ₹
        Label totalLabel = new Label("Total Amount: ₹" + String.format("%.2f", bus.getFare()));
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Button paymentBtn = createStyledButton("Proceed to Payment", "#27ae60");
        paymentBtn.setPrefWidth(200);
        paymentBtn.setPrefHeight(45);
        paymentBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; " +
                "-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10 30; " +
                "-fx-background-radius: 5; -fx-cursor: hand;");

        Button cancelBtn = createStyledButton("Cancel", "#95a5a6");
        cancelBtn.setPrefWidth(120);
        cancelBtn.setPrefHeight(45);
        cancelBtn.setOnAction(e -> bookingStage.close());

        paymentBtn.setOnAction(e -> {
            if (nameField.getText().trim().isEmpty()) {
                showAlert("Error", "Please enter passenger name");
                return;
            }
            if (selectedSeat[0].isEmpty()) {
                showAlert("Error", "Please select a seat");
                return;
            }

            // Show payment dialog
            boolean paymentSuccess = showPaymentDialog(bus.getFare());

            if (paymentSuccess) {
                String bookingId = "BK" + System.currentTimeMillis();
                Booking booking = new Booking(
                        bookingId,
                        bus.getBusNumber(),
                        nameField.getText().trim(),
                        selectedSeat[0],
                        LocalDate.now().toString(),
                        "Confirmed",
                        bus.getFare(),
                        bus.getFrom(),
                        bus.getTo(),
                        bus.getDepartureTime()
                );
                bookingList.add(booking);

                // Show success dialog
                showBookingConfirmation(booking, bus);
                bookingStage.close();
            }
        });

        bottomSection.getChildren().addAll(totalLabel, paymentBtn, cancelBtn);

        root.setTop(topSection);
        root.setCenter(centerSection);
        root.setBottom(bottomSection);

        Scene scene = new Scene(root, 800, 700);
        bookingStage.setScene(scene);
        bookingStage.show();
    }

    private boolean showPaymentDialog(double amount) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Payment Gateway");
        dialog.setHeaderText("Complete Your Payment");

        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setStyle("-fx-background-color: white;");

        // Payment amount
        // Change #3: Currency symbol $ to ₹
        Label amountLabel = new Label("Amount to Pay: ₹" + String.format("%.2f", amount));
        amountLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #27ae60;");

        // Payment method selection
        Label methodLabel = new Label("Select Payment Method:");
        methodLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        ToggleGroup paymentGroup = new ToggleGroup();

        RadioButton creditCard = new RadioButton("💳 Credit/Debit Card");
        creditCard.setToggleGroup(paymentGroup);
        creditCard.setSelected(true);
        creditCard.setStyle("-fx-font-size: 13px;");

        RadioButton upi = new RadioButton("📱 UPI Payment");
        upi.setToggleGroup(paymentGroup);
        upi.setStyle("-fx-font-size: 13px;");

        RadioButton netBanking = new RadioButton("🏦 Net Banking");
        netBanking.setToggleGroup(paymentGroup);
        netBanking.setStyle("-fx-font-size: 13px;");

        VBox methodBox = new VBox(10);
        methodBox.getChildren().addAll(creditCard, upi, netBanking);

        // Card details (simplified)
        GridPane cardForm = new GridPane();
        cardForm.setHgap(10);
        cardForm.setVgap(15);
        cardForm.setPadding(new Insets(15));
        cardForm.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15;");

        TextField cardNumber = new TextField();
        cardNumber.setPromptText("1234 5678 9012 3456");

        TextField cardName = new TextField();
        cardName.setPromptText("Name on Card");

        TextField expiry = new TextField();
        expiry.setPromptText("MM/YY");
        expiry.setPrefWidth(100);

        TextField cvv = new TextField();
        cvv.setPromptText("CVV");
        cvv.setPrefWidth(80);

        cardForm.add(new Label("Card Number:"), 0, 0);
        cardForm.add(cardNumber, 1, 0, 3, 1);
        cardForm.add(new Label("Card Holder:"), 0, 1);
        cardForm.add(cardName, 1, 1, 3, 1);
        cardForm.add(new Label("Expiry:"), 0, 2);
        cardForm.add(expiry, 1, 2);
        cardForm.add(new Label("CVV:"), 2, 2);
        cardForm.add(cvv, 3, 2);

        content.getChildren().addAll(amountLabel, new Separator(), methodLabel, methodBox,
                new Label("Enter Card Details:"), cardForm);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setText("Pay Now");

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (cardNumber.getText().isEmpty() || cardName.getText().isEmpty()) {
                showAlert("Error", "Please fill all payment details");
                return false;
            }
            return true;
        }
        return false;
    }

    private void showBookingConfirmation(Booking booking, Bus bus) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Booking Confirmed! 🎉");
        alert.setHeaderText("Your ticket has been booked successfully!");

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));

        Label bookingIdLabel = new Label("Booking ID: " + booking.getBookingId());
        bookingIdLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

        GridPane details = new GridPane();
        details.setHgap(15);
        details.setVgap(10);
        details.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5; -fx-padding: 15;");

        details.add(new Label("Bus Number:"), 0, 0);
        details.add(new Label(booking.getBusNumber()), 1, 0);
        details.add(new Label("Passenger:"), 0, 1);
        details.add(new Label(booking.getPassengerName()), 1, 1);
        details.add(new Label("Seat Number:"), 0, 2);
        details.add(new Label(booking.getSeatNumber()), 1, 2);
        details.add(new Label("From:"), 0, 3);
        details.add(new Label(booking.getFrom()), 1, 3);
        details.add(new Label("To:"), 0, 4);
        details.add(new Label(booking.getTo()), 1, 4);
        details.add(new Label("Departure:"), 0, 5);
        details.add(new Label(booking.getDepartureTime()), 1, 5);
        details.add(new Label("Fare Paid:"), 0, 6);
        // Change #3: Currency symbol $ to ₹
        details.add(new Label("₹" + String.format("%.2f", booking.getFare())), 1, 6);

        Label instruction = new Label("📧 Booking confirmation has been sent to your email.\n" +
                "Please arrive at the boarding point 15 minutes before departure.");
        instruction.setStyle("-fx-font-size: 12px; -fx-text-fill: #7f8c8d;");
        instruction.setWrapText(true);

        content.getChildren().addAll(bookingIdLabel, details, instruction);

        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    private Pane createViewRoutesPane() {
        VBox pane = createContentPane("🗺️ Available Routes");

        TableView<Route> table = new TableView<>();
        table.setItems(routeList);
        table.setStyle("-fx-background-color: white;");

        TableColumn<Route, String> nameCol = new TableColumn<>("Route Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("routeName"));
        nameCol.setPrefWidth(100);

        TableColumn<Route, String> fromCol = new TableColumn<>("From");
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        fromCol.setPrefWidth(120);

        TableColumn<Route, String> toCol = new TableColumn<>("To");
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        toCol.setPrefWidth(120);

        TableColumn<Route, String> stopsCol = new TableColumn<>("Stops");
        stopsCol.setCellValueFactory(new PropertyValueFactory<>("stops"));
        stopsCol.setPrefWidth(350);

        // Change #3: Currency symbol $ to ₹
        TableColumn<Route, Double> fareCol = new TableColumn<>("Fare (₹)");
        fareCol.setCellValueFactory(new PropertyValueFactory<>("fare"));
        fareCol.setPrefWidth(80);

        table.getColumns().addAll(nameCol, fromCol, toCol, stopsCol, fareCol);
        pane.getChildren().add(table);

        return pane;
    }

    private Pane createTrackBusesPane() {
        VBox pane = createContentPane("📍 Live Bus Tracking");

        TableView<Bus> table = new TableView<>();
        table.setItems(busList);
        table.setStyle("-fx-background-color: white;");

        TableColumn<Bus, String> numCol = new TableColumn<>("Bus Number");
        numCol.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
        numCol.setPrefWidth(100);

        TableColumn<Bus, String> fromCol = new TableColumn<>("From");
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        fromCol.setPrefWidth(120);

        TableColumn<Bus, String> toCol = new TableColumn<>("To");
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        toCol.setPrefWidth(120);

        TableColumn<Bus, String> routeCol = new TableColumn<>("Current Route");
        routeCol.setCellValueFactory(new PropertyValueFactory<>("route"));
        routeCol.setPrefWidth(300);

        TableColumn<Bus, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        TableColumn<Bus, String> etaCol = new TableColumn<>("ETA");
        etaCol.setCellValueFactory(new PropertyValueFactory<>("eta"));
        etaCol.setPrefWidth(80);

        table.getColumns().addAll(numCol, fromCol, toCol, routeCol, statusCol, etaCol);
        pane.getChildren().add(table);

        return pane;
    }

    private Pane createReviewPane() {
        VBox pane = createContentPane("⭐ Submit Review");

        VBox form = new VBox(15);
        form.setPadding(new Insets(20));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        ComboBox<String> busCombo = new ComboBox<>();
        busCombo.setPromptText("Select Bus to Review");
        busCombo.getItems().addAll(
                busList.stream()
                        .map(bus -> bus.getBusNumber() + " (" + bus.getFrom() + " → " + bus.getTo() + ")")
                        .collect(Collectors.toList())
        );
        busCombo.setPrefWidth(350);

        ComboBox<Integer> ratingBox = new ComboBox<>();
        ratingBox.getItems().addAll(1, 2, 3, 4, 5);
        ratingBox.setPromptText("Select Rating (1-5 stars)");

        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Share your experience with this bus service...");
        commentArea.setPrefHeight(150);
        commentArea.setWrapText(true);

        Button submitBtn = createStyledButton("Submit Review", "#4CAF50");
        submitBtn.setOnAction(e -> {
            if (busCombo.getValue() != null && ratingBox.getValue() != null && !commentArea.getText().isEmpty()) {
                String selectedBus = busCombo.getValue().split(" \\(")[0];
                reviewList.add(new Review(currentUserName, selectedBus, ratingBox.getValue(),
                        commentArea.getText(), LocalDate.now().toString()));
                showAlert("Success", "Thank you for your review of " + selectedBus + "!");
                commentArea.clear();
                ratingBox.setValue(null);
                busCombo.setValue(null);
            } else {
                showAlert("Error", "Please select a bus, rating, and enter a comment");
            }
        });

        Label reviewsLabel = new Label("Reviews for selected bus:");
        reviewsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        ListView<String> reviewListView = new ListView<>();
        reviewListView.setPrefHeight(150);

        busCombo.setOnAction(e -> {
            if (busCombo.getValue() != null) {
                String selectedBus = busCombo.getValue().split(" \\(")[0];
                ObservableList<String> busReviews = reviewList.stream()
                        .filter(r -> r.getBusNumber().equals(selectedBus))
                        .map(r -> "★".repeat(r.getRating()) + " - " + r.getComment() +
                                " (" + r.getUserName() + ", " + r.getDate() + ")")
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                if (busReviews.isEmpty()) {
                    busReviews.add("No reviews yet for this bus. Be the first to review!");
                }
                reviewListView.setItems(busReviews);
            }
        });

        form.getChildren().addAll(
                new Label("Select Bus:"), busCombo,
                new Label("Rating:"), ratingBox,
                new Label("Your Review:"), commentArea,
                submitBtn,
                new Separator(),
                reviewsLabel,
                reviewListView
        );

        pane.getChildren().add(form);
        return pane;
    }

    private Pane createMyBookingsPane() {
        VBox pane = createContentPane("🎫 My Bookings");

        // Filter bookings for current user
        ObservableList<Booking> userBookings = bookingList.stream()
                .filter(b -> b.getPassengerName().equals(currentUserName))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        if (userBookings.isEmpty()) {
            Label noBookings = new Label("You don't have any bookings yet.\nSearch for buses and book your first ride!");
            noBookings.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d; -fx-padding: 50;");
            noBookings.setAlignment(Pos.CENTER);
            pane.getChildren().add(noBookings);
            return pane;
        }

        TableView<Booking> table = new TableView<>();
        table.setItems(userBookings);
        table.setStyle("-fx-background-color: white;");

        TableColumn<Booking, String> bookingIdCol = new TableColumn<>("Booking ID");
        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        bookingIdCol.setPrefWidth(120);

        TableColumn<Booking, String> busCol = new TableColumn<>("Bus Number");
        busCol.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
        busCol.setPrefWidth(100);

        TableColumn<Booking, String> fromCol = new TableColumn<>("From");
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        fromCol.setPrefWidth(100);

        TableColumn<Booking, String> toCol = new TableColumn<>("To");
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        toCol.setPrefWidth(100);

        TableColumn<Booking, String> seatCol = new TableColumn<>("Seat");
        seatCol.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));
        seatCol.setPrefWidth(70);

        TableColumn<Booking, String> timeCol = new TableColumn<>("Departure");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("departureTime"));
        timeCol.setPrefWidth(100);

        TableColumn<Booking, String> dateCol = new TableColumn<>("Booking Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        dateCol.setPrefWidth(120);

        TableColumn<Booking, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        // Change #3: Currency symbol $ to ₹
        TableColumn<Booking, Double> fareCol = new TableColumn<>("Fare (₹)");
        fareCol.setCellValueFactory(new PropertyValueFactory<>("fare"));
        fareCol.setPrefWidth(80);

        table.getColumns().addAll(bookingIdCol, busCol, fromCol, toCol, seatCol, timeCol, dateCol, statusCol, fareCol);

        HBox buttons = new HBox(10);
        buttons.setPadding(new Insets(10));

        Button viewBtn = createStyledButton("View Details", "#3498db");
        Button cancelBtn = createStyledButton("Cancel Booking", "#f44336");

        viewBtn.setOnAction(e -> {
            Booking selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                showAlert("Booking Details",
                        "Booking ID: " + selected.getBookingId() + "\n" +
                                "Bus Number: " + selected.getBusNumber() + "\n" +
                                "Passenger: " + selected.getPassengerName() + "\n" +
                                "Seat: " + selected.getSeatNumber() + "\n" +
                                "Route: " + selected.getFrom() + " → " + selected.getTo() + "\n" +
                                "Departure: " + selected.getDepartureTime() + "\n" +
                                "Booking Date: " + selected.getBookingDate() + "\n" +
                                "Status: " + selected.getStatus() + "\n" +
                                // Change #3: Currency symbol $ to ₹
                                "Fare: ₹" + String.format("%.2f", selected.getFare()));
            } else {
                showAlert("Error", "Please select a booking to view");
            }
        });

        cancelBtn.setOnAction(e -> {
            Booking selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Cancel Booking");
                confirm.setHeaderText("Are you sure you want to cancel this booking?");
                confirm.setContentText("Booking ID: " + selected.getBookingId() + "\nSeat: " + selected.getSeatNumber());

                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    selected.setStatus("Cancelled");
                    table.refresh();
                    showAlert("Success", "Booking cancelled successfully. Refund will be processed within 5-7 business days.");
                }
            } else {
                showAlert("Error", "Please select a booking to cancel");
            }
        });

        buttons.getChildren().addAll(viewBtn, cancelBtn);
        pane.getChildren().addAll(table, buttons);

        return pane;
    }

    private Pane createManageBusesPane() {
        VBox pane = createContentPane("🚌 Manage Buses");

        TableView<Bus> table = new TableView<>();
        table.setItems(busList);
        table.setStyle("-fx-background-color: white;");

        TableColumn<Bus, String> numCol = new TableColumn<>("Bus No");
        numCol.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
        numCol.setPrefWidth(80);

        TableColumn<Bus, String> fromCol = new TableColumn<>("From");
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        fromCol.setPrefWidth(100);

        TableColumn<Bus, String> toCol = new TableColumn<>("To");
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        toCol.setPrefWidth(100);

        TableColumn<Bus, String> routeCol = new TableColumn<>("Route");
        routeCol.setCellValueFactory(new PropertyValueFactory<>("route"));
        routeCol.setPrefWidth(250);

        TableColumn<Bus, Integer> capCol = new TableColumn<>("Capacity");
        capCol.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        capCol.setPrefWidth(80);

        TableColumn<Bus, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(100);

        // Change #3: Currency symbol $ to ₹
        TableColumn<Bus, Double> fareCol = new TableColumn<>("Fare (₹)");
        fareCol.setCellValueFactory(new PropertyValueFactory<>("fare"));
        fareCol.setPrefWidth(80);

        table.getColumns().addAll(numCol, fromCol, toCol, routeCol, capCol, statusCol, fareCol);

        HBox buttons = new HBox(10);
        buttons.setPadding(new Insets(10));

        Button addBtn = createStyledButton("Add Bus", "#4CAF50");
        Button editBtn = createStyledButton("Edit", "#2196F3");
        Button deleteBtn = createStyledButton("Delete", "#f44336");

        addBtn.setOnAction(e -> showBusDialog(null));
        editBtn.setOnAction(e -> {
            Bus selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) showBusDialog(selected);
            else showAlert("Error", "Please select a bus to edit");
        });
        deleteBtn.setOnAction(e -> {
            Bus selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                busList.remove(selected);
                filteredBusList.remove(selected);
                showAlert("Success", "Bus deleted successfully");
            } else {
                showAlert("Error", "Please select a bus to delete");
            }
        });

        buttons.getChildren().addAll(addBtn, editBtn, deleteBtn);

        pane.getChildren().addAll(table, buttons);
        return pane;
    }

    private Pane createManageRoutesPane() {
        VBox pane = createContentPane("🗺️ Manage Routes");

        TableView<Route> table = new TableView<>();
        table.setItems(routeList);
        table.setStyle("-fx-background-color: white;");

        TableColumn<Route, String> nameCol = new TableColumn<>("Route Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("routeName"));
        nameCol.setPrefWidth(100);

        TableColumn<Route, String> fromCol = new TableColumn<>("From");
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        fromCol.setPrefWidth(120);

        TableColumn<Route, String> toCol = new TableColumn<>("To");
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        toCol.setPrefWidth(120);

        TableColumn<Route, String> stopsCol = new TableColumn<>("Stops");
        stopsCol.setCellValueFactory(new PropertyValueFactory<>("stops"));
        stopsCol.setPrefWidth(300);

        // Change #3: Currency symbol $ to ₹
        TableColumn<Route, Double> fareCol = new TableColumn<>("Fare (₹)");
        fareCol.setCellValueFactory(new PropertyValueFactory<>("fare"));
        fareCol.setPrefWidth(80);

        table.getColumns().addAll(nameCol, fromCol, toCol, stopsCol, fareCol);

        HBox buttons = new HBox(10);
        buttons.setPadding(new Insets(10));

        Button addBtn = createStyledButton("Add Route", "#4CAF50");
        Button editBtn = createStyledButton("Edit", "#2196F3");
        Button deleteBtn = createStyledButton("Delete", "#f44336");

        addBtn.setOnAction(e -> showRouteDialog(null));
        editBtn.setOnAction(e -> {
            Route selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) showRouteDialog(selected);
            else showAlert("Error", "Please select a route to edit");
        });
        deleteBtn.setOnAction(e -> {
            Route selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                routeList.remove(selected);
                showAlert("Success", "Route deleted successfully");
            } else {
                showAlert("Error", "Please select a route to delete");
            }
        });

        buttons.getChildren().addAll(addBtn, editBtn, deleteBtn);

        pane.getChildren().addAll(table, buttons);
        return pane;
    }

    private Pane createViewReviewsPane() {
        VBox pane = createContentPane("⭐ All Bus Reviews");

        TableView<Review> table = new TableView<>();
        table.setItems(reviewList);
        table.setStyle("-fx-background-color: white;");

        TableColumn<Review, String> userCol = new TableColumn<>("User");
        userCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userCol.setPrefWidth(120);

        TableColumn<Review, String> busCol = new TableColumn<>("Bus Number");
        busCol.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
        busCol.setPrefWidth(100);

        TableColumn<Review, Integer> ratingCol = new TableColumn<>("Rating");
        ratingCol.setCellValueFactory(new PropertyValueFactory<>("rating"));
        ratingCol.setPrefWidth(80);

        TableColumn<Review, String> commentCol = new TableColumn<>("Comment");
        commentCol.setCellValueFactory(new PropertyValueFactory<>("comment"));
        commentCol.setPrefWidth(400);

        TableColumn<Review, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateCol.setPrefWidth(100);

        table.getColumns().addAll(userCol, busCol, ratingCol, commentCol, dateCol);
        pane.getChildren().add(table);

        return pane;
    }

    private Pane createOwnerBookingsPane() {
        VBox pane = createContentPane("🎫 All Bookings");

        TableView<Booking> table = new TableView<>();
        table.setItems(bookingList);
        table.setStyle("-fx-background-color: white;");

        TableColumn<Booking, String> bookingIdCol = new TableColumn<>("Booking ID");
        bookingIdCol.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        bookingIdCol.setPrefWidth(100);

        TableColumn<Booking, String> busCol = new TableColumn<>("Bus Number");
        busCol.setCellValueFactory(new PropertyValueFactory<>("busNumber"));
        busCol.setPrefWidth(90);

        TableColumn<Booking, String> passengerCol = new TableColumn<>("Passenger");
        passengerCol.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
        passengerCol.setPrefWidth(120);

        TableColumn<Booking, String> seatCol = new TableColumn<>("Seat");
        seatCol.setCellValueFactory(new PropertyValueFactory<>("seatNumber"));
        seatCol.setPrefWidth(60);

        TableColumn<Booking, String> dateCol = new TableColumn<>("Booking Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        dateCol.setPrefWidth(100);

        TableColumn<Booking, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        statusCol.setPrefWidth(80);

        // Change #3: Currency symbol $ to ₹
        TableColumn<Booking, Double> fareCol = new TableColumn<>("Fare (₹)");
        fareCol.setCellValueFactory(new PropertyValueFactory<>("fare"));
        fareCol.setPrefWidth(70);

        table.getColumns().addAll(bookingIdCol, busCol, passengerCol, seatCol, dateCol, statusCol, fareCol);

        Button cancelBtn = createStyledButton("Cancel Selected Booking", "#f44336");
        cancelBtn.setOnAction(e -> {
            Booking selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selected.setStatus("Cancelled by Admin");
                table.refresh();
                showAlert("Success", "Booking " + selected.getBookingId() + " has been cancelled.");
            } else {
                showAlert("Error", "Please select a booking to cancel");
            }
        });

        HBox buttonBox = new HBox(10, cancelBtn);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        pane.getChildren().addAll(table, buttonBox);
        return pane;
    }


    // --- UTILITY AND HELPER METHODS ---

    private Button createStyledButton(String text, String colorHex) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + colorHex + "; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;");
        return button;
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; " +
                "-fx-font-size: 16px; -fx-padding: 10 15; -fx-border-color: #34495e; " +
                "-fx-border-width: 0 0 1 0; -fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #34495e; -fx-text-fill: #ecf0f1; " +
                "-fx-font-size: 16px; -fx-padding: 10 15; -fx-border-color: #34495e; -fx-border-width: 0 0 1 0; -fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-text-fill: #ecf0f1; " +
                "-fx-font-size: 16px; -fx-padding: 10 15; -fx-border-color: #34495e; -fx-border-width: 0 0 1 0; -fx-cursor: hand;"));
        return button;
    }

    private VBox createContentPane(String title) {
        VBox pane = new VBox(20);
        pane.setPadding(new Insets(20));
        pane.setStyle("-fx-background-color: #f5f5f5;");

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        pane.getChildren().add(titleLabel);
        return pane;
    }

    private void showAlert(String title, String message) {
        Alert alert;
        if (title.toLowerCase().contains("error")) {
            alert = new Alert(Alert.AlertType.ERROR);
        } else if (title.toLowerCase().contains("success") || title.toLowerCase().contains("info")) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else {
            alert = new Alert(Alert.AlertType.WARNING);
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Change #2: New Help Page Pane
    private Pane createHelpPagePane() {
        VBox pane = createContentPane("💬 Help & Support - Share Concerns");

        VBox form = new VBox(20);
        form.setPadding(new Insets(30));
        form.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        Label instruction = new Label("Hello " + currentUserName + ", how can we help you today? Please describe your concern below.");
        instruction.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

        TextArea concernArea = new TextArea();
        concernArea.setPromptText("Type your issue, booking ID, or feedback here...");
        concernArea.setPrefHeight(250);
        concernArea.setWrapText(true);
        concernArea.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

        Button submitBtn = createStyledButton("Submit Concern", "#00bcd4");
        submitBtn.setPrefWidth(200);

        submitBtn.setOnAction(e -> {
            if (concernArea.getText().trim().isEmpty()) {
                showAlert("Error", "Please enter your concern before submitting.");
            } else {
                showAlert("Success", "Thank you! Your concern has been received. A support agent will contact you shortly.");
                concernArea.clear();
            }
        });

        form.getChildren().addAll(instruction, concernArea, submitBtn);
        pane.getChildren().add(form);
        return pane;
    }

    private void showBusDialog(Bus bus) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(bus == null ? "Add New Bus" : "Edit Bus: " + bus.getBusNumber());
        dialog.setHeaderText(bus == null ? "Enter new bus details" : "Modify bus details for " + bus.getBusNumber());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField busNumberField = new TextField(bus == null ? "" : bus.getBusNumber());
        busNumberField.setPromptText("BUS-XXX");
        if (bus != null) busNumberField.setDisable(true);

        TextField fromField = new TextField(bus == null ? "" : bus.getFrom());
        TextField toField = new TextField(bus == null ? "" : bus.getTo());
        TextField routeField = new TextField(bus == null ? "" : bus.getRoute());
        TextField capacityField = new TextField(bus == null ? "40" : String.valueOf(bus.getCapacity()));
        // Change #3: Currency symbol $ to ₹
        TextField fareField = new TextField(bus == null ? "30.00" : String.valueOf(bus.getFare()));
        TextField departureTimeField = new TextField(bus == null ? "HH:MM AM/PM" : bus.getDepartureTime());
        TextField arrivalTimeField = new TextField(bus == null ? "HH:MM AM/PM" : bus.getArrivalTime());
        ComboBox<String> statusCombo = new ComboBox<>(FXCollections.observableArrayList("Active", "Inactive", "Maintenance"));
        statusCombo.setValue(bus == null ? "Active" : bus.getStatus());
        TextField etaField = new TextField(bus == null ? "10 min" : bus.getEta());


        grid.add(new Label("Bus Number:"), 0, 0);
        grid.add(busNumberField, 1, 0);
        grid.add(new Label("From:"), 0, 1);
        grid.add(fromField, 1, 1);
        grid.add(new Label("To:"), 0, 2);
        grid.add(toField, 1, 2);
        grid.add(new Label("Route:"), 0, 3);
        grid.add(routeField, 1, 3);
        grid.add(new Label("Capacity:"), 0, 4);
        grid.add(capacityField, 1, 4);
        // Change #3: Currency symbol $ to ₹
        grid.add(new Label("Fare (₹):"), 0, 5);
        grid.add(fareField, 1, 5);
        grid.add(new Label("Departure Time:"), 0, 6);
        grid.add(departureTimeField, 1, 6);
        grid.add(new Label("Arrival Time:"), 0, 7);
        grid.add(arrivalTimeField, 1, 7);
        grid.add(new Label("Status:"), 0, 8);
        grid.add(statusCombo, 1, 8);
        grid.add(new Label("ETA:"), 0, 9);
        grid.add(etaField, 1, 9);


        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (bus == null) {
                    // Add new Bus
                    Bus newBus = new Bus(busNumberField.getText(), fromField.getText(), toField.getText(),
                            routeField.getText(), Integer.parseInt(capacityField.getText()),
                            statusCombo.getValue(), etaField.getText(), Double.parseDouble(fareField.getText()),
                            departureTimeField.getText(), arrivalTimeField.getText());
                    busList.add(newBus);
                    filteredBusList.add(newBus);
                    showAlert("Success", "Bus added successfully!");
                } else {
                    // Edit existing Bus
                    bus.setFrom(fromField.getText());
                    bus.setTo(toField.getText());
                    bus.setRoute(routeField.getText());
                    bus.setCapacity(Integer.parseInt(capacityField.getText()));
                    bus.setFare(Double.parseDouble(fareField.getText()));
                    bus.setStatus(statusCombo.getValue());
                    bus.setEta(etaField.getText());
                    bus.setDepartureTime(departureTimeField.getText());
                    bus.setArrivalTime(arrivalTimeField.getText());
                    showAlert("Success", "Bus updated successfully!");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid number format for Capacity or Fare.");
            }
        }
    }

    private void showRouteDialog(Route route) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(route == null ? "Add New Route" : "Edit Route: " + route.getRouteName());
        dialog.setHeaderText(route == null ? "Enter new route details" : "Modify route details for " + route.getRouteName());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField routeNameField = new TextField(route == null ? "" : route.getRouteName());
        routeNameField.setPromptText("Route A");
        if (route != null) routeNameField.setDisable(true);

        TextField fromField = new TextField(route == null ? "" : route.getFrom());
        TextField toField = new TextField(route == null ? "" : route.getTo());
        TextField stopsField = new TextField(route == null ? "" : route.getStops());
        TextField fareField = new TextField(route == null ? "25.00" : String.valueOf(route.getFare()));

        grid.add(new Label("Route Name:"), 0, 0);
        grid.add(routeNameField, 1, 0);
        grid.add(new Label("From:"), 0, 1);
        grid.add(fromField, 1, 1);
        grid.add(new Label("To:"), 0, 2);
        grid.add(toField, 1, 2);
        grid.add(new Label("Stops (comma-separated):"), 0, 3);
        grid.add(stopsField, 1, 3);
        // Change #3: Currency symbol $ to ₹
        grid.add(new Label("Base Fare (₹):"), 0, 4);
        grid.add(fareField, 1, 4);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                if (route == null) {
                    // Add new Route
                    Route newRoute = new Route(routeNameField.getText(), fromField.getText(), toField.getText(),
                            stopsField.getText(), Double.parseDouble(fareField.getText()));
                    routeList.add(newRoute);
                    showAlert("Success", "Route added successfully!");
                } else {
                    // Edit existing Route
                    route.setFrom(fromField.getText());
                    route.setTo(toField.getText());
                    route.setStops(stopsField.getText());
                    route.setFare(Double.parseDouble(fareField.getText()));
                    showAlert("Success", "Route updated successfully!");
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid number format for Fare.");
            }
        }
    }
}
