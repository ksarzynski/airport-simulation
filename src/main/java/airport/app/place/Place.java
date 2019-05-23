package main.java.airport.app.place;

import main.java.airport.app.person.Passenger;

import java.util.ArrayList;
import java.util.List;

abstract class Place {
    private String name;
    private int maxPeopleAmount;
    List<Passenger> passengers;

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

    public void addPassengers(List<Passenger> passengers) {

       this.passengers.addAll(passengers);

    }

    public int getQueueSize()
    {
        return this.maxPeopleAmount;
    }

}


