package main.java.airport.app.person;

import main.java.airport.app.place.ControlPoint;

import java.util.Date;

public class Controller extends Employee
{
    public Controller(String name, String status, Double efficiency, Date shiftStartTime, Date shiftEndTime, ControlPoint controlPoint)
    {
        super(name, status, efficiency, shiftStartTime, shiftEndTime);
        this.controlPoint = controlPoint;
    }

    private ControlPoint controlPoint;
}
