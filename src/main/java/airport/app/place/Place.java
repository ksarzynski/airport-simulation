package main.java.airport.app.place;

import main.java.airport.app.person.Passanger;

import java.util.ArrayList;
import java.util.List;

abstract class Place {
    private String name;
    private Integer maxPeopleAmount;
    private List<Passanger> passangers;

    Place(String name, Integer maxPeopleAmount)
    {
        this.name = name;
        this.maxPeopleAmount = maxPeopleAmount;
        this.passangers = new ArrayList<>(maxPeopleAmount);
    }

    public List<Passanger> getPassangers(Integer howMany)
    {
        List<Passanger> passangersToMove = new ArrayList<>(howMany);
        int lenght = this.passangers.size();

         for(int i=0; i<howMany; i++)
        {
            passangersToMove.add(this.passangers.get(lenght-i));
        }
        return passangersToMove;
    }

    public void addPasangers(List<Passanger> passangers)
    {
        this.passangers.addAll(passangers);
    }

}


