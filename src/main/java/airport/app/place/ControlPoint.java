package main.java.airport.app.place;

import main.java.airport.app.person.Controller;

public class ControlPoint extends Place {

    private Controller controller;

    ControlPoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    public void addController(Controller controller)
    {
        this.controller = controller;
    }
}
