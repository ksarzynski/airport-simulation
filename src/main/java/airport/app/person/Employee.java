package main.java.airport.app.person;

import main.java.airport.app.belongings.Baggage;

import java.util.Date;

public class Employee extends Person{
    private Double efficiency;
    private Date shiftStartTime;
    private Date shiftEndTime;

    Employee(String name, String status, Double efficiency, Date shiftStartTime, Date shiftEndTime)
    {
        super(name, status);
        this.efficiency = efficiency;
        this.shiftStartTime = shiftStartTime;
        this.shiftEndTime = shiftEndTime;
    }

    public void setEfficiency(Double efficiency)
    {
        this.efficiency = efficiency;
    }

    public Double getEfficiency()
    {
        return this.efficiency;
    }

    public String getStatus()
    {
        return this.status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

}
