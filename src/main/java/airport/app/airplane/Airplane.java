package main.java.airport.app.airplane;

import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.person.Passenger;
import main.java.airport.app.person.Pilot;
import main.java.airport.simulation.Helpers;

import java.util.ArrayList;
import java.util.Date;

public class Airplane {
    private String direction;
    private String flightName;
    private Integer maxPassenger;
    private Integer maxBaggageWeight;
    private Pilot pilot;
    private String isReady;
    private Date flightStart;
    private ArrayList<Passenger> passengers = new ArrayList<>();
    private Integer purchasedTicketsAmount;
    private ArrayList<Baggage> baggagesOnBoard = new ArrayList<>();

    public Airplane(String flightName, String direction, Integer maxPassenger, Integer maxBaggageWeight, Pilot pilot, Date flightStart) {
        this.flightName = flightName;
        this.direction = direction;
        this.maxPassenger = maxPassenger;
        this.maxBaggageWeight = maxBaggageWeight;
        this.pilot = pilot;
        this.isReady = "not ready";
        this.flightStart = flightStart;
        this.purchasedTicketsAmount = 0;
    }

    public String getDirection() {
        return direction;
    }

    public String getFlightName() {
        return flightName;
    }

    public void increasePurchasedTicketsAmount() {
        purchasedTicketsAmount++;
    }

    public Integer getPurchasedTicketsAmount() {
        return purchasedTicketsAmount;
    }

    public Integer getMaxPassenger() {
        return maxPassenger;
    }

    public Integer getPassengersOnBoardAmount() {
        return passengers.size();
    }

    public Integer getMaxBaggageWeight() {
        return maxBaggageWeight;
    }

    public Pilot getPilot() { return pilot; }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    public void startFlight() {
        for(Passenger passenger : passengers)
        {
            passenger.removeBaggage();
            passenger.removeTicket();
        }
    }

    public void addBaggage(Baggage baggage) {
        System.out.println(baggage.getTicket());
        this.baggagesOnBoard.add(baggage);
    }

    public void checkBaggagesReady() {
        for(Passenger passenger: passengers) {

            if(!(passenger.getBaggage().getStatus().equals("boarded")))
            {
                this.isReady = "baggage not on board";
                delayFlight(Helpers.getRandomNumber(5, 60));
            }

            if(!this.isReady.equals("baggage not on board"));
            this.isReady = "baggage on board";

        }
    }

    public Date getFlightStart() {
        return flightStart;
    }

    private void delayFlight(Integer minutes) {
        this.flightStart = new Date(this.flightStart.getTime() + (minutes * 60 * 1000));
    }

    public boolean checkDepartureTime(Date now) {
        if( now.after(getFlightStart()) ){
            checkBaggagesReady();
            if(isReady.equals("ready")) {
                startFlight();
                return true;
            }
        }
        return false;
    }
}
