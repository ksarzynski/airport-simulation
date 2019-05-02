package main.java.airport.app.place;

import main.java.airport.app.person.Vendor;

public class SalePoint extends Place {

    private Vendor vendor;

    public SalePoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    public void addVendor(Vendor vendor)
    {
        this.vendor = vendor;
    }

}
