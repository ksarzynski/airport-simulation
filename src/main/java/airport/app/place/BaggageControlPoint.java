package airport.app.place;

import airport.app.airplane.Airplane;
import airport.app.person.Controller;
import airport.app.person.Passenger;
import java.util.ArrayList;
import java.util.Date;

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

/*
    /**
     * metoda sprawdzajaca bagaz oraz wykorzystujaca metode move to umieszczenia go w odpowiednim samolocie
     * @param airplanes lista samolotow
     * @param brutalityLevel ilosc pasazerow likwidowanych w przypadku bardzo niebezpiecznego bagazu
     */

 /*   public void checkBaggage(ArrayList<Airplane> airplanes, int brutalityLevel) {

        ArrayList<Integer> list = new ArrayList<>();

        int dangerLevel;
        int index;
        if (passengers.size() < controller.getEfficiency())
            index = passengers.size();

         else
            index = controller.getEfficiency();

        for (int i = 0; i < index; i++) {

            if(passengers.get(i).getBaggage()!=null)
            {
                dangerLevel = passengers.get(i).getBaggage().getDangerLevel();
                if (dangerLevel == 0) {

                    moveBaggage(passengers.get(i), airplanes);

                }
                else if (dangerLevel == 1) {

                    passengers.get(i).removeTicket();
                    passengers.get(i).removeBaggage();
                    list.add(i);

                }
                else {
                    for (int j = i; j < brutalityLevel && j < getControllerEfficiency(); j++) {

                        if(passengers.get(j)!=null) {
                            passengers.get(j).removeTicket();
                            passengers.get(j).removeBaggage();
                            list.add(i);
                        }
                    }
                }

            }

        }

        int repair = 0;

        for(int j = 0; j < list.size(); j++)
        {
            if(j-repair>-1)
            passengers.remove(list.get(j) - repair);
            repair++;
        }

    }
    */


    public void checkBaggage(ArrayList<Airplane> airplanes, int brutalityLevel){

        int howMany = 0;
        ArrayList<Passenger> passengersToRemove = new ArrayList<>();

        try {
            howMany = this.passengers.size() < this.getControllersEfficiency() ? this.passengers.size() : this.getControllersEfficiency();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < howMany; i++){

            if(passengers.get(i).getBaggage() != null){

                if(passengers.get(i).getBaggage().getDangerLevel()==0){

                    passengers.get(i).getTicket().getAirplane().addBaggage(passengers.get(i).getBaggage());
                    passengers.get(i).removeBaggage();
                }

                else if(passengers.get(i).getBaggage().getDangerLevel()==1){

                    passengers.get(i).removeBaggage();
                    passengers.get(i).removeTicket();
                    passengersToRemove.add(passengers.get(i));
                }

                else{

                    for(int j = i; j < brutalityLevel; j++){

                        passengers.get(j).removeBaggage();
                        passengers.get(j).removeTicket();
                        passengersToRemove.add(passengers.get(j));
                        i+=brutalityLevel;
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


    public void setSuccessor(boolean successor) {
        this.successor = successor;
    }

    public boolean getSuccessor() {
        return this.successor;
    }

    /**
     * funkcja ruszajaca bagaz
     * @param passenger pasazer ktorego bagaz jest przemieszczany
     * @param airplanes samoloty wsrod ktorych szukany jest ten do ktorego bagaz ma trafic
     */

    private void moveBaggage(Passenger passenger, ArrayList<Airplane> airplanes) {

        if(passenger.getTicket()!=null)
        {
            String flightName = passenger.getTicket().getFlightName();
            for (Airplane airplane : airplanes) {
                if (flightName.equals(airplane.getFlightName())) {
                    airplane.addBaggage(passenger.getBaggage());
                    passenger.getBaggage().setStatus("boarded");
                }
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
     * @return
     */

    public Controller closePoint(){
        isOpen = false;
        baggageControlPointIndex -= 1;
        setShiftStartTime(null);
        return removeController();
    }

    public int getOpenBaggageControlPointIndex() { return baggageControlPointIndex; }

}