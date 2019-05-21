package main.java.airport.app.airplane;

import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.person.Passenger;
import main.java.airport.app.person.Pilot;

import java.util.ArrayList;

public class Airplane {
    private String direction;
    private String flightName;
    private Integer maxPassenger;
    private Double maxBaggageWeight;
    private Pilot pilot;
    private ArrayList<Passenger> passengers;
    private ArrayList<Baggage> baggagesOnBoard;

    public Airplane(String flightName, String direction, Integer maxPassenger, Double maxBaggageWeight, Pilot pilot) {
        this.flightName = flightName;
        this.direction = direction;
        this.maxPassenger = maxPassenger;
        this.maxBaggageWeight = maxBaggageWeight;
        this.pilot = pilot;
        this.passengers = new ArrayList<>(maxPassenger);
    }

    public String getDirection() {
        return direction;
    }

    public String getFlightName() {
        return flightName;
    }

    public Integer getMaxPassenger() {
        return maxPassenger;
    }

    public Double getMaxBaggageWeight() {
        return maxBaggageWeight;
    }

    public Pilot getPilot() { return pilot; }

    public boolean addPassanger(Passenger passenger) {
        // TODO: Jesli passager jest po odparawie - metoda
        return this.passengers.add(passenger);
    }

    public void startFlight() {
        // TODO: metoda
    }

    public void addBaggage(Baggage baggage) {
        // TODO: metoda
    }

    public void checkBaggagesReady() {
        // TODO: metoda
    }

    private void delayFlight(Integer minutes) {
        // TODO: metoda
    }
}
