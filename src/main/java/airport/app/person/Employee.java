package main.java.airport.app.person;

import java.util.Date;

public class Employee extends Person{
    private Integer efficiency;
    private Date shiftStartTime;

    Employee(String name, Integer efficiency, Date shiftStartTime)
    {
        super(name);
        this.efficiency = efficiency;
        this.shiftStartTime = shiftStartTime;
    }

    public Employee(String name, Integer efficiency) {
        super(name);
        this.efficiency = efficiency;
    }

    public void setEfficiency(Integer efficiency)
    {
        this.efficiency = efficiency;
    }

    public Integer getEfficiency()
    {
        return this.efficiency;
    }

    public void setShiftStartTime(Date shiftStartTime){this.shiftStartTime = shiftStartTime;}

}
