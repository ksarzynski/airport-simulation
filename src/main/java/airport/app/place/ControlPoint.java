package airport.app.place;

import airport.app.person.Controller;
import airport.app.person.Employee;
import airport.app.person.Passenger;

import java.util.ArrayList;
import java.util.Date;

public class ControlPoint extends Place {

    private Date shiftStartTime;

    Controller controller;

    private static int controlPointIndex;
    private boolean successor = false;

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

    public void setSuccessor(boolean successor) {
        this.successor = successor;
    }

    public boolean getSuccessor() {
        return this.successor;
    }

    public void openPoint(Controller controller, Date date) {
        isOpen = true;
        controlPointIndex += 1;
        setController(controller);

        setShiftStartTime(date);
    }

    public Controller closePoint(){
        this.isOpen = false;
        controlPointIndex -= 1;
        setShiftStartTime(null);
        return removeController();
    }

    public int getOpenControlPointIndex() {
        return controlPointIndex;
    }

    public Date getShiftStartTime() {
        return shiftStartTime;
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

    public Integer getControllersEfficiency() {
        return controller.getEfficiency();
    }

    public Employee getEmployee(){ return this.employee;}

}
