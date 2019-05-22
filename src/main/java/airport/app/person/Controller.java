package main.java.airport.app.person;

import main.java.airport.app.place.ControlPoint;

import java.util.Date;

public class Controller extends Employee
{
    private ControlPoint controlPoint;

    public Controller(String name, Double efficiency, Date shiftStartTime, ControlPoint controlPoint)
    {
        super(name, efficiency, shiftStartTime);
        this.controlPoint = controlPoint;
    }
}
