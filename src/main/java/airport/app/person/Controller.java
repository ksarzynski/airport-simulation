package main.java.airport.app.person;

import main.java.airport.app.place.ControlPoint;

import java.util.Date;

public class Controller extends Employee
{
    private ControlPoint controlPoint;

    public Controller(String name, Integer efficiency, Date shiftStartTime, ControlPoint controlPoint)
    {
        super(name, efficiency, shiftStartTime);
        this.controlPoint = controlPoint;
    }

    public Controller(String name, Integer efficiency)
    {
        super(name, efficiency);
    }

    @Override
    public void setShiftStartTime(Date shiftStartTime) {
        super.setShiftStartTime(shiftStartTime);
    }

    public void setControlPoint(ControlPoint controlPoint) {
        this.controlPoint = controlPoint;
    }
}
