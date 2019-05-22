package main.java.airport.app.place;

import main.java.airport.app.person.Vendor;

import java.util.ArrayList;

public class SalePoint extends Place {

    private Vendor vendor;

    public SalePoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    private void setVendor(Vendor vendor)
    {
        this.vendor = vendor;
    }

    public void setRandomVendor(ArrayList<Vendor> vendors) {

        //TODO method
    }

    public void removeVendor(){} //TODO method

    public Double getVendorsEfficiency()
    {
        return this.vendor.getEfficiency();
    }

    public void openPoint(){} // TODO method

    public void closePoint(){} // TODO method

    public void sellRandomTicket(){} //TODO method

    @Override
    public void movePassengers() {} // TODO method
}
