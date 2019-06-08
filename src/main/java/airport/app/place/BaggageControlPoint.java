package main.java.airport.app.place;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Passenger;
import java.util.ArrayList;
import java.util.Date;

public class BaggageControlPoint extends ControlPoint {

    private static int baggageControlPointIndex;

    public BaggageControlPoint(String name, Integer maxPeopleAmount) {
        super(name, maxPeopleAmount);
    }

    public void checkBaggage(ArrayList<Airplane> airplanes, int brutalityLevel) {

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

                        passengers.get(j).removeTicket();
                        passengers.get(j).removeBaggage();
                        list.add(i);
                    }
                }

            }

        }

        int repair = 0;

        for(int j = 0; j < list.size(); j++)
        {
            passengers.remove(list.get(j) - repair);
            repair++;
        }

    }

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

    public void openPoint(Controller controller, Date date) {
        isOpen = true;
        baggageControlPointIndex += 1;
        setController(controller);

        setShiftStartTime(date);
    }

    public Controller closePoint(){
        isOpen = false;
        baggageControlPointIndex -= 1;
        setShiftStartTime(null);
        return removeController();
    }

    public int getOpenBaggageControlPointIndex() { return baggageControlPointIndex; }

}