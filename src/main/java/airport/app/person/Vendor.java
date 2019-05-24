package main.java.airport.app.person;

import main.java.airport.app.place.SalePoint;

import java.util.Date;

public class Vendor extends Employee
{
    private SalePoint salePoint;

    public Vendor(String name, Integer efficiency, Date shiftStartTime, SalePoint salePoint)
    {
        super(name, efficiency, shiftStartTime);
        this.salePoint = salePoint;
    }

    public Vendor(String name, Integer efficiency) {
        super(name, efficiency);
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    public void setShiftStartTime(Date shiftStartTime) {
        super.setShiftStartTime(shiftStartTime);
    }

    public void setSalePoint(SalePoint salePoint) {
        this.salePoint = salePoint;
    }
}
