package main.java.airport.app.place;

import main.java.airport.app.airplane.Airplane;

import java.util.ArrayList;

public class DutyFreeZone extends Place {

    private Integer flow;

    public DutyFreeZone(String name, Integer maxPeopleAmount, Integer flow)

    {
        super(name, maxPeopleAmount);
        this.flow = flow;
    }

    public Integer getFlow()
    {
        return this.flow;
    }


}
