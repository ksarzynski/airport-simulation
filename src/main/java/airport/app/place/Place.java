package main.java.airport.app.place;

import main.java.airport.app.person.Passanger;

import java.util.ArrayList;
import java.util.List;

abstract class Place {
    private String name;
    private int maxPeopleAmount;
    private List<Passanger> passangers;
    private List<Passanger> passangersToMove = new ArrayList<>();

    Place(String name, Integer maxPeopleAmount)
    {
        this.name = name;
        this.maxPeopleAmount = maxPeopleAmount;
        this.passangers = new ArrayList<>(maxPeopleAmount);
    }

    public List<Passanger> getPassangers(int howMany)
    {
         for(int i=0; i<howMany; i++)
        {
            passangersToMove.add(this.passangers.get(i));
        }
        return passangersToMove;
    }

    public void addPasangers(List<Passanger> passangers)
    {
        this.passangers.addAll(passangers);
    }

    public void addPasangers(List<Passanger> passangers, int howMany)
    {
        for(int i = 0; i < howMany; i++)
        {
            this.passangers.add(passangers.get(0));
        }
    }

    public void addPasangers(List<Passanger> passangers, int howMany, int startIndex)
    {
        for(int i = startIndex; i < howMany; i++)
        {
            this.passangers.add(passangers.get(0));
        }
    }

    public int getSize()
    {
        return this.maxPeopleAmount;
    }

}


