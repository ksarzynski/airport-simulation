package main.java.airport.app.place;

import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Vendor;

import java.util.ArrayList;

public class ControlPoint extends Place {

    private Controller controller;

    ControlPoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    private void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void setRandomController(ArrayList<Controller> controllers) {

        //TODO method

    }

    public void removeController(){} //TODO method

    public Double getControllerEfficiency() { return this.controller.getEfficiency(); }

    public void openPoint(){} //TODO method

    public void closePoint(){} //TODO method

    @Override
    public void movePassengers() {} //TODO method
}
