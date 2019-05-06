package main.java.airport.app.place;

import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Person;

public class DutyFreeZone extends Place {

    private int flow = 1000;

    public DutyFreeZone(String name, Integer maxPeopleAmount, int flow)

    {
        super(name, maxPeopleAmount);
        this.flow = flow;
    }

    public int getFlow()
    {
        return this.flow;
    }

}
