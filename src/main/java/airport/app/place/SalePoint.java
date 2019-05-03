package main.java.airport.app.place;

import main.java.airport.app.person.Vendor;

import java.util.ArrayList;

public class SalePoint extends Place {

    private Vendor vendor;

    public SalePoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    private void addVendor(Vendor vendor)
    {
        this.vendor = vendor;
    }

    public void addRandomVendor(ArrayList<Vendor> vendors)
    {
        int index;
        int isDone  = 0;

        while (isDone == 0)
        {
            index = (int) (Math.random() * ((vendors.size() + 1) + 1));
            if(vendors.get(index).getStatus().compareTo("not working") == 0)
            {
                vendors.get(index).setStatus("working");
                addVendor(vendors.get(index));
                isDone = 1;
            }
        }
    }

}
