package main.java.airport.app.place;

import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.Passenger;
import main.java.airport.app.person.Vendor;
import main.java.airport.simulation.Simulation;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class SalePoint extends Place {

    private Date shiftStartTime;

    private Vendor vendor;

    private static int openSalePointIndex;

    public SalePoint(String name, Integer maxPeopleAmount) {
        super(name, maxPeopleAmount);
    }

    public SalePoint(SalePoint salePoint) {
        super(salePoint.getName(), salePoint.getQueueSize());
    }

    private void setVendor(Vendor vendor) {
        this.employee = vendor;
        this.vendor = (Vendor)employee;
    }

    private void setShiftStartTime(Date shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
    }

    private Vendor removeVendor(){
        Vendor tempVendor = this.vendor;
        this.vendor = null;
        return tempVendor;
    }

    public Integer getVendorsEfficiency() {
        return vendor.getEfficiency();
    }

    public void openPoint(Vendor vendor, Date date) {
        setVendor(vendor);
        openSalePointIndex += 1;
        this.isOpen = true;
        setShiftStartTime(date);
    }

    private Vendor closePoint(){
        isOpen = false;
        openSalePointIndex -= 1;
        setShiftStartTime(null);
        return removeVendor();
    }

    public void sellRandomTicket(ArrayList<Ticket> tickets, Passenger passenger){
        Random r = new Random();
        int a = r.nextInt(tickets.size());
        Ticket ticket = tickets.get(a);
        tickets.remove(a);
        passenger.setTicket(ticket);
    }

    public int getOpenSalePointIndex() {
        return openSalePointIndex;
    }

    public Date getShiftStartTime() {
        return shiftStartTime;
    }

    public Date getShiftEndTime() {
        return new Date(shiftStartTime.getTime() + (8 * 60 * 60 * 1000));
    }

    public Vendor checkWorkingHour(Date now) {
        if(isOpen){
            if(now.after(getShiftEndTime()))
                return closePoint();
        }
        return null;
    }

    public void movePassengers(Place place, int howMany, ArrayList<Ticket> availableTickets){
        ArrayList<Passenger> passengersToMove = new ArrayList<>();

        for(int i = 0; i < howMany; i++)
        {
            Passenger passenger = passengers.get(i);
            Ticket ticket = availableTickets.remove(0);
            passenger.setTicket(ticket);
            passengersToMove.add(passenger);
        }

        place.addPassengers(passengersToMove);

        for(int i = 0; i < howMany; i++)
        {
            this.passengers.remove(0);
        }
    }
}
