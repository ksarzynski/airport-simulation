package main.java.airport.app.place;

import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Vendor;

import java.util.ArrayList;
import java.util.Random;

public class ControlPoint extends Place {

    private Controller controller;

    public ControlPoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    private void addController(Controller controller)
    {
        this.controller = controller;
    }

    public void addRandomController(ArrayList<Controller> controllers)
    {
        int index;
        int isDone  = 0;

        while (isDone == 0)
        {
            index = new Random().nextInt(20) + 1;
            if(controllers.get(index).getStatus().compareTo("not working")==0)
            {
                controllers.get(index).setStatus("working");
                addController(controllers.get(index));
                isDone = 1;
            }
        }
    }

    public Double getControllerEfficiency()
    {
        return this.controller.getEfficiency();
    }
}
