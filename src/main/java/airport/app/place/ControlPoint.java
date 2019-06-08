package main.java.airport.app.place;

import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Passenger;

import java.util.ArrayList;
import java.util.Date;

public class ControlPoint extends Place {

    private Date shiftStartTime;

    Controller controller;

    private static int controlPointIndex;

    public ControlPoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    void setController(Controller controller) {
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

    public void openPoint(Controller controller, Date date) {
        isOpen = true;
        controlPointIndex += 1;
        setController(controller);

        setShiftStartTime(date);
    }

    public Controller closePoint(){
        this.isOpen = false;
        this.controlPointIndex -= 1;
        setShiftStartTime(null);
        return removeController();
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

    private Date getShiftEndTime() {
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
