package main.java.airport.app.place;

import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Person;

public class DutyFreeZone extends Place {

    int flow = 1000;

    public DutyFreeZone(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

}
