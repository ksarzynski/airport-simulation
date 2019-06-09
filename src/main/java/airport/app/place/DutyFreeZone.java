package airport.app.place;

import airport.app.airplane.Airplane;
import airport.app.person.Passenger;

import java.util.ArrayList;
import java.util.Date;

/**
 * Klasa strefy bezclowej, miejsce przejsciowe miedzy punktami kontroli a samolotami
 */
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

    /**
     * funkcja sluzy do przemieszczania ludzi, jest to nadpisana funkcja z Place, poniewaz trzeba bylo dodac sprawdzanie biletu
     * zeby przeniesc pasazerow do odpowiednich samolotow
     * @param airplanes lista dostepnych samolotow
     * @param howMany ilosc ludzi do przeniesienia zalezna od mozliwosci przeplywu ludzi przez strefe bezclowa
     */
    public void movePassengersPoli(ArrayList<Airplane> airplanes, int howMany) {
        if (howMany != 0)
            for (int i = 0; i < howMany; i++) {
                if(passengers.size()!=0)
                    if(passengers.get(0).getTicket()!=null) {
                        if (passengers.get(0).getTicket().getAirplane().getIsReady().equals("gone"))
                            passengers.remove(0);
                        if (passengers.size() != 0 && passengers.get(0) != null) {
                            if (passengers.get(0).getTicket() != null && passengers.get(0).getTicket().getAirplane() != null)
                                passengers.get(0).getTicket().getAirplane().addPassenger(passengers.get(0));
                         passengers.remove(0);
                        }
                    }
                else
                    passengers.remove(0);

            }
    }
}
