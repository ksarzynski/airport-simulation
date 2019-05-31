package main.java.airport.app.place;

import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.Passenger;
import main.java.airport.app.person.Vendor;

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

    private Integer getVendorsEfficiency() {
        return this.employee.getEfficiency();
    }

    public void openPoint(Vendor vendor, Date date) {
        setVendor(vendor);
        openSalePointIndex += 1;
        this.isOpen = true;
        setShiftStartTime(date);
    }

    private Vendor closePoint(){
        this.isOpen = false;
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

    private Date getShiftEndTime() {
        return new Date(shiftStartTime.getTime() + (8 * 60 * 60 * 1000));
    }

    public Vendor checkWorkingHour(Date now) {
        if(isOpen){
            if(now.after(getShiftEndTime()))
                return closePoint();
        }
        return null;
    }
}
