package main.java.airport.app.airplane;

import main.java.airport.app.person.Passanger;
import main.java.airport.app.person.Pilot;

import java.util.ArrayList;
import java.util.List;

public class Airplane {
    //private String from; no z naszego lotniska XDDD
    private String to;
    private String flightName;
    private String status = "LOADING";          // LOADING |
    private Integer maxPassenger;
    //private Integer minPassenger; jak bedzie za malo pasazerow to nie odleci :D?
    private Double maxBaggageWeight;
    private Pilot pilot;
    private List<Passanger> passengers;

    public Airplane()
    {

    }

    public Airplane(String flightName, /*String from,*/ String to, /*Integer minPassenger,*/ Integer maxPassenger, Double maxBaggageWeight, Pilot pilot) {
        this.flightName = flightName;
        //this.from = from;
        this.to = to;
        //this.minPassenger = minPassenger;
        this.maxPassenger = maxPassenger;
        this.maxBaggageWeight = maxBaggageWeight;
        this.pilot = pilot;
        this.passengers = new ArrayList<>(maxPassenger);
    }

    public boolean addPassanger(Passanger passanger) {
        // TODO: Jesli passager jest po odparawie - metoda
        return this.passengers.add(passanger);
    }

    public void startFlight() {
        // TODO: Czy kazdy bagaz obecnego pasazera jest?
        System.gc();
    }

/*    public String getFrom() {
        return from;
    }
*/

    public String getTo() {
        return to;
    }

    public String getFlightName() {
        return flightName;
    }

    public Integer getMaxPassenger() {
        return maxPassenger;
    }
/*
    public Integer getMinPassenger() {
        return minPassenger;
    }
*/

    public Double getMaxBaggageWeight() {
        return maxBaggageWeight;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public String getStatus() {
        return status;
    }
}
