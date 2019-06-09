package airport.app.airplane;

import airport.app.belongings.Baggage;
import airport.app.person.Passenger;
import airport.app.person.Pilot;
import airport.simulation.Helpers;

import java.util.ArrayList;
import java.util.Date;

/**
 * Klasa samolot, w srodku znajduja sie listy ludzi i bagazy docelowo w obiektach tej klasy
 * umieszczani sa pasazerowie i ich bagaze a o ustalonej godzine nastepuje odlot
 */
public class Airplane {
    static int triedDeparturesCounter =0;
    private String direction;
    private String flightName;
    private Integer maxPassenger;
    private Integer maxBaggageWeight;
    private String isReady;
    private Date flightStart;
    private ArrayList<Passenger> passengers = new ArrayList<>();
    private Integer purchasedTicketsAmount;
    private ArrayList<Baggage> baggageOnBoard = new ArrayList<>();

    public Airplane(String flightName, String direction, Integer maxPassenger, Integer maxBaggageWeight, Pilot pilot, Date flightStart) {
        this.flightName = flightName;
        this.direction = direction;
        this.maxPassenger = maxPassenger;
        this.maxBaggageWeight = maxBaggageWeight;
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

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    public Integer getBaggageWeight(){
        Integer weight = 0;
        for(Baggage baggage : baggageOnBoard){

            weight+=baggage.getWeight();
        }
        return weight;
    }

    /**
     * metoda powoduje "odlot" kasowane sa objekty z list
     */
    private void startFlight() {
        for(Passenger passenger : passengers)
        {
            passenger.removeBaggage();
            passenger.removeTicket();
        }
        passengers.clear();
        isReady = "gone";
        triedDeparturesCounter++;
    }

    public void addBaggage(Baggage baggage) {
        this.baggageOnBoard.add(baggage);
    }

    /**
     *  sprawdza czy bagaz kazdego pasazera ( ktory mial bagaz na pozcatku ) jest na pokladzie
     */
    private void checkBaggageReady() {
        this.isReady="ready";
        for(Passenger passenger: passengers) {

            if(passenger.getBaggage()!=null) {
                if (!(passenger.getBaggage().getStatus().equals("boarded"))) {

                    passenger.getBaggage().setStatus("boarded");
                    this.isReady = "baggage not on board";
                    delayFlight(Helpers.getRandomNumber(10, 10));
                }
            }
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
            checkBaggageReady();
            if(isReady.equals("ready")) {
                startFlight();
                return true;
            }
        }
        return false;
    }

    public String getIsReady(){return isReady;}
}
