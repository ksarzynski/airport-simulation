package main.java.airport.app.person;

import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.belongings.Ticket;

import java.util.ArrayList;

public class Passenger extends Person {
    private Baggage baggage;
    private Ticket ticket;

    public Passenger(String name, String status)
    {
        super(name,status);
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

    public void setRandomTicket(ArrayList<Ticket> tickets)
    {
//        int index;
//        int isDone  = 0;
//
//        while (isDone == 0)
//        {
//            index = (new Random().nextInt(tickets.size()));
//            if(tickets.get(index).getStatus().compareTo("unsold") == 0)
//            {
//                tickets.get(index).setStatus("sold");
//                setTicket(tickets.get(index));
//                isDone = 1;
//            }
//        }
    }

}
