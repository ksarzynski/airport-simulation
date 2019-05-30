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

    static int controlPointIndex;

    public ControlPoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    void setController(Controller controller) {

        this.employee = controller;
        this.controller = (Controller)employee;

    }

    void removeController(ArrayList <Controller> controllers){

        controllers.add(this.controller);
        this.employee = null;

    }

    Integer getControllerEfficiency() { return this.controller.getEfficiency(); }

    public void openPoint(ArrayList<Controller> controllers, String time, int index) throws ParseException {

        isOpen = true;
        setRandomAvailableController(controllers, time);
        index =+ 1;
    }

    public void closePoint(ArrayList<Controller> controllers, int index){

        isOpen = false;
        removeController(controllers);
        index =- 1;
    }

    void setRandomAvailableController(ArrayList<Controller> controllers, String time) throws ParseException {
        Random r = new Random();
        int a = r.nextInt(controllers.size());
        Controller controller = controllers.get(a);
        setController(controller);
        controllers.remove(a);

        DateFormat format = new SimpleDateFormat("HH:mm");
        Date date = format.parse(time);
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
