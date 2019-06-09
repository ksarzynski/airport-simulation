package airport.app.place;

import airport.app.airplane.Airplane;
import airport.app.person.Passenger;

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

    public void movePassengersPoli(ArrayList<Airplane> airplanes, int howMany){

        for(int i = 0; i < howMany; i++)
        {
            for(Airplane airplane : airplanes)
                if(passengers.size()!=0)
                    if(passengers.get(0).getTicket() != null)
                        if(passengers.get(0).getTicket().getFlightName().equals(airplane.getFlightName())) {
                            airplane.addPassenger(passengers.get(0));
                        }
            if(passengers.size()!=0)
            passengers.remove(0);
        }

    }

}
