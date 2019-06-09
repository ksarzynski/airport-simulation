package airport.app.place;

import airport.app.belongings.Ticket;
import airport.app.person.Passenger;
import airport.app.person.Vendor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * kasa, w tym miejscu przypisane zostaja bilety, pierwsze miejsce pojawienia sie pasazerow, ktorzy
 * nastepnie przemieszczani sa do punktu kontroli bagazu
 */
public class SalePoint extends Place {

    private Date shiftStartTime;
    private Vendor vendor;
    private static Integer openSalePointIndex=0;
    private boolean successor = false;

    public SalePoint(String name, Integer maxPeopleAmount) { super(name, maxPeopleAmount); }

    private void setVendor(Vendor vendor) {
        this.employee = vendor;
        this.vendor = (Vendor)employee;
    }

    /**
     * funkcja ustawia czas rozpoczecia pracy
     * @param shiftStartTime data rozpoczecia pracy tego punktu sprzedazy
     */
    private void setShiftStartTime(Date shiftStartTime) { this.shiftStartTime = shiftStartTime; }

    private Vendor removeVendor(){
        Vendor tempVendor = this.vendor;
        this.vendor = null;
        return tempVendor;
    }

    public Integer getVendorsEfficiency() { return vendor.getEfficiency(); }

    /**
     * funkcja otwiera punkt sprzedazy, przypisujac niezbedne wartosci
     * @param vendor przypisywany pracownik
     * @param date data rozpoczecia pracy
     */
    public void openPoint(Vendor vendor, Date date) {
        setVendor(vendor);
        openSalePointIndex += 1;
        this.isOpen = true;
        setShiftStartTime(date);
    }

    public void setSuccessor(boolean successor) { this.successor = successor; }

    public boolean getSuccessor() {
        return this.successor;
    }

    /**
     * funkcja zamyka punkt, poprawia licznik otwartych punktow sprzedazy oraz zwraca liste pasazerow ktorzy zostaja
     * przemieszczeni do innych dzialajacych punktow sprzedazy
     * @return lista pasazerow
     */
    public List closePoint(){
        isOpen = false;
        openSalePointIndex -= 1;
        setShiftStartTime(null);
        List<Passenger> passengers = getPassengers();
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

    /**
     * funkcja pzremieszcza pasazerow dalej oraz przypisuje im bilety
     * @param place miejsce docelowe
     * @param howMany ilosc pasazerow do przesuniecia
     * @param availableTickets lista dostepnych biletow
     */
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
            }
        }

        place.addPassengers(passengersToMove);

        for(int i = 0; i < howMany; i++)
        {
            this.passengers.remove(0);
        }
    }
}
