package main.java.airport.app.place;

import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Vendor;

import java.util.ArrayList;

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
            index = (int) (Math.random() * (controllers.size() + 1 + 1));
            if(controllers.get(index).getStatus().compareTo("not working")==0)
            {
                controllers.get(index).setStatus("working");
                addController(controllers.get(index));
                isDone = 1;
            }
        }
    }
}
