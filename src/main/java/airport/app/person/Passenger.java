package main.java.airport.app.person;

import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.belongings.Ticket;

import java.util.ArrayList;

public class Passenger extends Person {
    private Baggage baggage;
    private Ticket ticket;

    public Passenger(String name)
    {
        super(name);
    }

    public void setBaggage (Baggage baggage)
    {
        this.baggage = baggage;
    }

    private void setTicket (Ticket ticket)
    {
        this.ticket = ticket;
    }

    public Baggage getBaggage ()
    {
        return this.baggage;
    }

    public Ticket getTicket ()
    {
        return this.ticket;
    }

    public void removePassenger (){} //TODO

}
