package main.java.airport.app.place;

import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.Passenger;
import main.java.airport.app.person.Vendor;
import main.java.airport.simulation.Clock;

import javax.naming.ldap.Control;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SalePoint extends Place {

    private boolean isOpen;

    private Vendor vendor;

    public SalePoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    public void setVendor(Vendor vendor)
    {
        this.vendor = vendor;
    }

    public void setRandomVendor (ArrayList<Vendor> vendors, Clock clock) throws ParseException {

        Random r = new Random();
        int a = r.nextInt(vendors.size());
        Vendor vendor = vendors.get(a);
        vendors.remove(a);
        setVendor(vendor);

        String string = clock.getTime();
        DateFormat format = new SimpleDateFormat("HH:mm");
        Date date = format.parse(string);
        vendor.setShiftStartTime(date);

    }

    public void removeVendor(ArrayList<Vendor> vendors){

        vendors.add(this.vendor);
        this.vendor = null;
    }

    public Double getVendorsEfficiency()
    {
        return this.vendor.getEfficiency();
    }

    public void openPoint(ArrayList<Vendor> vendors, Clock clock) throws ParseException {

        setRandomVendor(vendors, clock);
        isOpen = true;

    }

    public void closePoint(ArrayList<Vendor> vendors){

        removeVendor(vendors);
        this.isOpen = false;

    }

    public void sellRandomTicket(ArrayList<Ticket> tickets, Passenger passenger){

        Random r = new Random();
        int a = r.nextInt(tickets.size());
        Ticket ticket = tickets.get(a);
        tickets.remove(a);
        passenger.setTicket(ticket);

    }

    public void movePassengers(int basicFlow, BaggageControlPoint baggageControlPoint) {

        ArrayList<Passenger> passengersToMove = new ArrayList<>();

        for(int i = 0; i < getVendorsEfficiency() * basicFlow; i++)
        {
            passengersToMove.add(this.passengers.get(i));
        }

        baggageControlPoint.addPassengers(passengersToMove);
    }
}
