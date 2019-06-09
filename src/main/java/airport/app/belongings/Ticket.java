package airport.app.belongings;

import airport.app.airplane.Airplane;

/**
 *  Bilet jest polem klasy pasazer, sluzy do umieszczenia pasazera w odpowiednim samolocie
 */
public class Ticket {

    private Airplane airplane;

    public Ticket(Airplane airplane)
    {
        this.airplane = airplane;
    }

    public String getFlightName(){return this.airplane.getFlightName();}

    public void bought() {
        airplane.increasePurchasedTicketsAmount();
    }

    public Airplane getAirplane() {
        return airplane;
    }
}
