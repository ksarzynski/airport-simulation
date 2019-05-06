package main.java.airport.app.person;

import main.java.airport.app.place.SalePoint;

import java.util.Date;

public class Vendor extends Employee
{
    public Vendor(String name, String status, Double efficiency, Date shiftStartTime, Date shiftEndTime, SalePoint salePoint)
    {
        super(name, status, efficiency, shiftStartTime, shiftEndTime);
        this.salePoint = salePoint;
    }

    private SalePoint salePoint;
}
