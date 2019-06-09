package airport.app.place;

import airport.app.airplane.Airplane;
import airport.app.person.Controller;
import airport.app.person.Passenger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  punkt kontroli bagazu, zawiera liste pasazerow w ktorej przetrzymuje pasazerow ktorzy przeszli do punktu kontroli bagazu z kas
 *  posiada metode kltora sprawdza bagaz, oraz umieszcza go na odpowiednim samolocie oraz metody otwierajace i zamykajace punkt
 */
public class BaggageControlPoint extends ControlPoint {

    private static int baggageControlPointIndex;

    private boolean successor = false;

    public BaggageControlPoint(String name, Integer maxPeopleAmount) {
        super(name, maxPeopleAmount);
    }

    /**
     * funkcja sprawdza bagaz, czy jest bezpieczny czy niebezpieczny czy bardzo niebezpieczny i przekazuje bagaz do odpowiedniego samolotu
     * lub usuwa go wraz z wlascicielem z kolejki
     * @param brutalityLevel od tej zmiennej zalezy ile osob zostaje usuniete z kolejki w przypadku obecnosci bardzo niebezpiecznego bagazu
     */
    public void checkBaggage(int brutalityLevel){

        int howMany = 0;
        ArrayList<Passenger> passengersToRemove = new ArrayList<>();

        try {
            howMany = this.passengers.size() < this.getControllersEfficiency() ? this.passengers.size() : this.getControllersEfficiency();
        }

        catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < howMany; i++){

            if( passengers.get(i).getBaggage() != null && passengers.get(i).getBaggage().getTicket() != null ){

                if( passengers.get(i).getBaggage().getDangerLevel()==0 ){
                    moveBaggage(passengers.get(i), passengers.get(i).getTicket().getAirplane());

                } else if(passengers.get(i).getBaggage().getDangerLevel()==1){
                    passengers.get(i).removeBaggage();
                    passengers.get(i).removeTicket();
                    passengersToRemove.add(passengers.get(i));

                } else {
                    for(int j = i; j < brutalityLevel; j++){
                        if(j<passengers.size()) {
                            passengers.get(j).removeBaggage();
                            passengers.get(j).removeTicket();
                            passengersToRemove.add(passengers.get(j));
                            i += brutalityLevel;
                        }
                    }
                }
            }
        }

        for(Passenger passengerToRemove : passengersToRemove)
            for(Passenger passenger : passengers){
                if(passengersToRemove.equals(passenger))
                    passenger = null;
            }
    }

    public void setSuccessor(boolean successor) { this.successor = successor; }

    public boolean getSuccessor() {
        return this.successor;
    }

    /**
     * funkcja ruszajaca bagaz
     * @param passenger pasazer ktorego bagaz jest przemieszczany
     * @param airplane samoloty wsrod ktorych szukany jest ten do ktorego bagaz ma trafic
     */
    private void moveBaggage(Passenger passenger, Airplane airplane) {

        if(passenger.getTicket()!=null)
        {
            String flightName = passenger.getTicket().getFlightName();
            if (flightName.equals(airplane.getFlightName())) {
                airplane.addBaggage(passenger.getBaggage());
                passenger.getBaggage().setStatus("boarded");
            }
        }
    }

    /**
     * funkcja otwierajaca punkt, przypisujaca kontrolera
     * @param controller przypisany kontroler
     * @param date ile czasu punkt ma byc otwarty
     */
    public void openPoint(Controller controller, Date date) {
        isOpen = true;
        baggageControlPointIndex += 1;
        setController(controller);
        setShiftStartTime(date);
    }

    /**
     * funkcja zamykajaca punkt, usuwajaca kontrolera
     * @return zwraca pasazerow, ktorzy zostaja przeniesieni z zamykanych punktow do otwartych
     */
    public List closePoint(){
        isOpen = false;
        baggageControlPointIndex -= 1;
        setShiftStartTime(null);
        List<Passenger> passengers = getPassengers();
        removePassengers();
        return passengers;
    }

    public int getOpenBaggageControlPointIndex() { return baggageControlPointIndex; }
}