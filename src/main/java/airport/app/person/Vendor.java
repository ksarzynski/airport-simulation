package main.java.airport.app.person;

import main.java.airport.app.place.SalePoint;

import java.util.Date;

public class Vendor extends Employee
{
    private SalePoint salePoint;

    public Vendor(String name, Double efficiency, Date shiftStartTime, SalePoint salePoint)
    {
        super(name, efficiency, shiftStartTime);
        this.salePoint = salePoint;
    }
}
