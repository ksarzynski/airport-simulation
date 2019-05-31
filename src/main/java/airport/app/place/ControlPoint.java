package main.java.airport.app.place;

import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Passenger;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ControlPoint extends Place {

    boolean isOpen=false;
    private Date shiftStartTime;

    private Controller controller;

    private static int controlPointIndex;

    public ControlPoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    private void setController(Controller controller) {
        this.controller = controller;
    }

    void setShiftStartTime(Date shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
    }

    Controller removeController(){
        Controller tempController = this.controller;
        this.controller = null;
        return tempController;
    }

    Integer getControllerEfficiency() { return this.controller.getEfficiency(); }

    public void openPoint(ArrayList<Controller> controllers, Date date) {
        isOpen = true;
        setRandomAvailableController(controllers);
        controlPointIndex += 1;

        setShiftStartTime(date);
    }

    public Controller closePoint(){
        isOpen = false;
        controlPointIndex -= 1;
        setShiftStartTime(null);
        return removeController();
    }

    void setRandomAvailableController(ArrayList<Controller> controllers) {
        Random r = new Random();
        int a = r.nextInt(controllers.size());
        Controller controller = controllers.get(a);
        setController(controller);
        controllers.remove(a);
    }

    public void movePassengers(int basicFlow, DutyFreeZone dutyFreeZone) {
        ArrayList<Passenger> passengersToMove = new ArrayList<>();

        for(int i = 0; i < getControllerEfficiency() * basicFlow; i++)
        {
            passengersToMove.add(this.passengers.get(i));
        }

        dutyFreeZone.addPassengers(passengersToMove);
    }

    public int getOpenSalePointIndex() {
        return controlPointIndex;
    }

    public Date getShiftEndTime() {
        return new Date(shiftStartTime.getTime() + (8 * 60 * 60 * 1000));
    }

    public Controller checkWorkingHour(Date now) {
        if( isOpen && now.after(getShiftEndTime()) ){
            return closePoint();
        } else {
            return null;
        }
    }

}
