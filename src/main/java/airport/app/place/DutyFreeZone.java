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

    public void movePassengers(ArrayList<Airplane> airplanes, int basicFlow) {

        for(int i = 0; i < this.flow * basicFlow; i++)
        {
            for(Airplane airplane : airplanes)
            {
                if(passengers.get(i).getTicket().getFlightName().equals(airplane.getFlightName()))
                {
                    airplane.addBaggage(passengers.get(i).getBaggage());
                    airplane.addPassenger(passengers.get(i));
                }
            }
        }

    }
}
