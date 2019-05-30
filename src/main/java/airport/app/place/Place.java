package main.java.airport.app.place;

import main.java.airport.app.person.Employee;
import main.java.airport.app.person.Passenger;

import java.util.ArrayList;
import java.util.List;

public abstract class Place {
    private String name;
    private int maxPeopleAmount;
    boolean isOpen;
    List<Passenger> passengers;
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

    public void getPassangers(List<Passenger> passengers)
    {
        this.passengers.addAll(passengers);
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

    }

    public boolean getIsOpen() { return isOpen; }

}


