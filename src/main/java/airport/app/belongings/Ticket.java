package main.java.airport.app.belongings;

import main.java.airport.app.airplane.Airplane;

public class Ticket {

    private Airplane airplane;

    public Ticket(Airplane airplane)
    {
        this.airplane = airplane;
    }

    public String getFlightName(){return this.airplane.getFlightName();}

    public void bought() {
        airplane.increasePurchasedTicketsAmount();
    }

    public Airplane getAirplane() {
        return airplane;
    }
}
