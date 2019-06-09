package airport.app.person;

import airport.app.belongings.Baggage;
import airport.app.belongings.Ticket;

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

    private boolean isBaggage() {
        if(baggage != null)
            return true;
        else
            return false;
    }

    public void setTicket (Ticket ticket)
    {
        this.ticket = ticket;
        if(isBaggage())
            this.baggage.setTicket(ticket);
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
