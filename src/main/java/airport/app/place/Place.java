package airport.app.place;

import airport.app.person.Employee;
import airport.app.person.Passenger;

import java.util.ArrayList;
import java.util.List;

public abstract class Place {
    private String name;
    private int maxPeopleAmount;
    boolean isOpen;
    ArrayList<Passenger> passengers;
    Employee employee;

    Place(String name, Integer maxPeopleAmount)
    {
        this.name = name;
        this.maxPeopleAmount = maxPeopleAmount;
        this.passengers = new ArrayList<>(maxPeopleAmount);
    }

    public List<Passenger> getPassangers() {

        return this.passengers;

    }

    public void removePassengers() {
        this.passengers.clear();
    }
    
    public Integer getPassengersAmountInQueue() {
        return passengers.size();
    }

    void addPassengers(List<Passenger> passengers) {

       this.passengers.addAll(passengers);

    }

    public void addPassenger(Passenger passenger) {

        this.passengers.add(passenger);

    }

    public int getQueueSize()
    {
        return this.maxPeopleAmount;
    }

    public Employee getEmployee(){ return this.employee;}

    public void movePassengersPoli(Place place, int howMany){

        ArrayList<Passenger> passengersToMove = new ArrayList<>();

        for(int i = 0; i < howMany; i++)
        {
            passengersToMove.add(this.passengers.get(i));
        }

        place.addPassengers(passengersToMove);

        for(int i = 0; i < howMany; i++)
        {
            this.passengers.remove(0);
        }

    }

    public boolean getIsOpen() { return isOpen; }

    public String getName() { return name; }

    public boolean isPlaceFull (){

        return passengers.size() >= maxPeopleAmount;

    }

}


