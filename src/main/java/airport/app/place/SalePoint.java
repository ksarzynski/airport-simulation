package airport.app.place;

import airport.app.belongings.Ticket;
import airport.app.person.Passenger;
import airport.app.person.Vendor;
import airport.simulation.Simulation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SalePoint extends Place {

    private Date shiftStartTime;

    private Vendor vendor;

    private static Integer openSalePointIndex=0;
    private boolean successor = false;

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

    public Integer getVendorsEfficiency() {
        return vendor.getEfficiency();
    }

    public void openPoint(Vendor vendor, Date date) {
        setVendor(vendor);
        openSalePointIndex += 1;
        this.isOpen = true;
        setShiftStartTime(date);
    }

    public void setSuccessor(boolean successor) {
        this.successor = successor;
    }

    public boolean getSuccessor() {
        return this.successor;
    }

    public List closePoint(){
        isOpen = false;
        openSalePointIndex -= 1;
        setShiftStartTime(null);
        List<Passenger> passengers = getPassangers();
        removePassengers();

        return passengers;
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
                return removeVendor();
        }
        return null;
    }

    public void movePassengers(Place place, int howMany, ArrayList<Ticket> availableTickets){
        ArrayList<Passenger> passengersToMove = new ArrayList<>();

        Passenger passenger;

        for(int i = 0; i < howMany; i++)
        {
            passenger = passengers.get(i);
            Ticket ticket = availableTickets.remove(0);
            passenger.setTicket(ticket);
            if(passenger.getTicket()!=null) {
                passengersToMove.add(passenger);
//                System.out.print(passenger.getTicket().getFlightName());
            }

        }

        place.addPassengers(passengersToMove);

        for(int i = 0; i < howMany; i++)
        {
            this.passengers.remove(0);
        }
    }
}
