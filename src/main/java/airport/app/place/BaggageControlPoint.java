package main.java.airport.app.place;

import main.java.airport.app.person.Controller;

public class BaggageControlPoint extends ControlPoint {

    BaggageControlPoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    private Controller controller;
}
