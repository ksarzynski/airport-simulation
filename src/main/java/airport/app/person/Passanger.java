package main.java.airport.app.person;

import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.belongings.Ticket;

public class Passanger extends Person {
    private Baggage bagage;
    private Ticket ticket;

    public Passanger(String name, String status)
    {
        super(name,status);
    }

}
