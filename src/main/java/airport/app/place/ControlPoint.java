package main.java.airport.app.place;

import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Passenger;
import main.java.airport.simulation.Clock;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ControlPoint extends Place {

    boolean isOpen;

    private Controller controller;

    ControlPoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    public void setController(Controller controller)
    {
        this.controller = controller;
    }

    public void removeController(ArrayList <Controller> controllers){

        controllers.add(this.controller);
        this.controller = null;

    }

    public Double getControllerEfficiency() { return this.controller.getEfficiency(); }

    public void openPoint(ArrayList<Controller> controllers, Clock clock) throws ParseException {

        isOpen = true;
        setRandomAvailableController(controllers, clock);

    }

    public void closePoint(ArrayList<Controller> controllers){

        isOpen = false;
        removeController(controllers);

    }

    public void setRandomAvailableController(ArrayList<Controller> controllers, Clock clock) throws ParseException {
        Random r = new Random();
        int a = r.nextInt(controllers.size());
        Controller controller = controllers.get(a);
        controllers.remove(a);
        setController(controller);

        String string = clock.getTime();
        DateFormat format = new SimpleDateFormat("HH:mm");
        Date date = format.parse(string);
        controller.setShiftStartTime(date);
    }

    public void movePassengers(int basicFlow, DutyFreeZone dutyFreeZone) {

        ArrayList<Passenger> passengersToMove = new ArrayList<>();

        for(int i = 0; i < getControllerEfficiency() * basicFlow; i++)
        {
            passengersToMove.add(this.passengers.get(i));
        }

        dutyFreeZone.addPassengers(passengersToMove);

    }
}
