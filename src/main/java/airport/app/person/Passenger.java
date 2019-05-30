package main.java.airport.app.person;

import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.belongings.Ticket;

public class Passenger extends Person {
    private Baggage baggage;
    private Ticket ticket;

    public Passenger(String name)
    {
        super(name);
    }

    public void setBaggage (Integer weight)
    {
        this.baggage = new Baggage(weight);
    }

    public void setTicket (Ticket ticket)
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

    public void removeBaggage(){

        this.baggage = null;
    }

    public void removeTicket(){

        this.ticket = null;
    }

}
