package main.java.airport.app.place;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Passenger;
import main.java.airport.simulation.Clock;

import java.text.ParseException;
import java.util.ArrayList;

public class BaggageControlPoint extends ControlPoint {

    private Controller controller;

    static int baggageControlPointIndex;

    private ArrayList<Baggage> baggages = new ArrayList<>();

    public BaggageControlPoint(String name, Integer maxPeopleAmount) {
        super(name, maxPeopleAmount);
    }

    public void checkBaggages(ArrayList<Passenger> passengers, ArrayList<Airplane> airplanes, int basicFlow, int brutalityLevel) {

        int dangerLevel;
        for (int i = 0; i < getControllerEfficiency() * basicFlow; i++) {
            dangerLevel = passengers.get(i).getBaggage().getDangerLevel();
            if (dangerLevel == 0) {

                moveBaggage(passengers.get(i), airplanes);

            } else if (dangerLevel == 1) {

                passengers.get(i).removeTicket();
                passengers.get(i).removeBaggage();
                passengers.remove(i);

            } else {
                for (int j = i; j < brutalityLevel && j < getControllerEfficiency() * basicFlow; j++) {

                    passengers.get(j).removeTicket();
                    passengers.get(j).removeBaggage();
                    passengers.remove(i);
                }
            }
        }


    }

    private void moveBaggage(Passenger passenger, ArrayList<Airplane> airplanes) {

        String flightName = passenger.getTicket().getFlightName();
        for (Airplane airplane : airplanes) {
            if (flightName.equals(airplane.getFlightName())) {
                airplane.addBaggage(passenger.getBaggage());
                passenger.getBaggage().setStatus("boarded");
            }
        }
    }

    public void movePassengers(int basicFlow, ControlPoint controlPoint) {

        ArrayList<Passenger> passengersToMove = new ArrayList<>();

        for(int i = 0; i < getControllerEfficiency() * basicFlow; i++)
        {
            passengersToMove.add(this.passengers.get(i));
        }

        controlPoint.addPassengers(passengersToMove);
    }

    public void openPoint(ArrayList<Controller> controllers, Clock clock) throws ParseException {

        isOpen = true;
        setRandomAvailableController(controllers, clock);
        baggageControlPointIndex =+ 1;
    }

    public void closePoint(ArrayList<Controller> controllers){

        isOpen = false;
        removeController(controllers);
        baggageControlPointIndex =- 1;
    }

}