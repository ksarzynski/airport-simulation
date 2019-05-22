package main.java.airport.app.person;

import java.util.Date;

public class Employee extends Person{
    private Double efficiency;
    private Date shiftStartTime;

    Employee(String name, Double efficiency, Date shiftStartTime)
    {
        super(name);
        this.efficiency = efficiency;
        this.shiftStartTime = shiftStartTime;
    }

    public void setEfficiency(Double efficiency)
    {
        this.efficiency = efficiency;
    }

    public Double getEfficiency()
    {
        return this.efficiency;
    }

}
