package main.java.airport.app.place;

import main.java.airport.app.person.Passenger;

import java.util.ArrayList;
import java.util.List;

abstract class Place {
    private String name;
    private int maxPeopleAmount;
    private List<Passenger> passengers;
    private List<Passenger> passangersToMove = new ArrayList<>();

    Place(String name, Integer maxPeopleAmount)
    {
        this.name = name;
        this.maxPeopleAmount = maxPeopleAmount;
        this.passengers = new ArrayList<>(maxPeopleAmount);
    }

    public List<Passenger> getPassangers(int howMany)
    {
         for(int i=0; i<howMany; i++)
        {
            passangersToMove.add(this.passengers.get(i));
        }
        return passangersToMove;
    }

    public void addPasangers(List<Passenger> passengers)
    {
        this.passengers.addAll(passengers);
    }

    public void addPasangers(List<Passenger> passengers, int howMany)
    {
        for(int i = 0; i < howMany; i++)
        {
            this.passengers.add(passengers.get(0));
        }
    }

    public void addPasangers(List<Passenger> passengers, int howMany, int startIndex)
    {
        for(int i = startIndex; i < howMany; i++)
        {
            this.passengers.add(passengers.get(0));
        }
    }

    public int getSize()
    {
        return this.maxPeopleAmount;
    }

}


