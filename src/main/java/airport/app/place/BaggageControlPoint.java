package main.java.airport.app.place;

import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.person.Controller;

import java.util.ArrayList;

public class BaggageControlPoint extends ControlPoint {

    private Controller controller;

    private ArrayList<Baggage> baggages = new ArrayList<>();

    public BaggageControlPoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    public void moveBaggages(){} //TODO method

    public void checkBaggages(){} //TODO method
}
