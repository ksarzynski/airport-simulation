package main.java.airport.app.place;

import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.Passenger;
import main.java.airport.app.person.Vendor;
import main.java.airport.simulation.Clock;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SalePoint extends Place {

    private boolean isOpen;

    private Vendor vendor;

    private static int openSalePointIndex;

    public SalePoint(String name, Integer maxPeopleAmount)
    {
        super(name, maxPeopleAmount);
    }

    private void setVendor(Vendor vendor) {

        this.employee = vendor;
        this.vendor = (Vendor)employee;

    }

    private void setRandomVendor (ArrayList<Vendor> vendors, String time) throws ParseException {

        Random r = new Random();
        int a = r.nextInt(vendors.size());
        Vendor vendor = vendors.get(a);
        vendors.remove(a);
        setVendor(vendor);

        DateFormat format = new SimpleDateFormat("HH:mm");
        Date date = format.parse(time);
        vendor.setShiftStartTime(date);
    }

    private void removeVendor(ArrayList<Vendor> vendors){

        vendors.add(this.vendor);
        this.employee = null;
    }

    private Integer getVendorsEfficiency()
    {
        return this.employee.getEfficiency();
    }

    public void openPoint(ArrayList<Vendor> vendors, String time) throws ParseException {
        setRandomVendor(vendors, time);
        isOpen = true;
        openSalePointIndex =+ 1;

    }

    public void closePoint(ArrayList<Vendor> vendors){

        removeVendor(vendors);
        this.isOpen = false;
        openSalePointIndex =- 1;

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

    public int getOpenSalePointIndex() {
        return openSalePointIndex;
    }
}
