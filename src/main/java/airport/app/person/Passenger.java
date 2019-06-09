package airport.app.person;

import airport.app.belongings.Baggage;
import airport.app.belongings.Ticket;

/**
 * pasazer, symulacja polega na przemieszczaniu objektow tej klasy miedzy kolejnymi miejscami
 * na lotniskum, zawiera pola z klasami bagage i ticket
 */
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
        return baggage != null;
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
