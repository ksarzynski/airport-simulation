package main.java.airport.app.place;

import main.java.airport.app.person.Passenger;

import java.util.ArrayList;
import java.util.List;

abstract class Place {
    private String name;
    private int maxPeopleAmount;
    private List<Passenger> passengers;

    Place(String name, Integer maxPeopleAmount)
    {
        this.name = name;
        this.maxPeopleAmount = maxPeopleAmount;
        this.passengers = new ArrayList<>(maxPeopleAmount);
    }

    public List<Passenger> getPassangers() {

        return this.passengers;

    }

    public void getPasangers(List<Passenger> passengers)
    {
        this.passengers.addAll(passengers);
    }

    public void addPasangers(List<Passenger> passengers) {} //TODO method

    public int getQueueSize()
    {
        return this.maxPeopleAmount;
    }

    abstract public void movePassengers(); //TODO method

}


