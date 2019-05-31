package main.java.airport.app.place;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Passenger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BaggageControlPoint extends ControlPoint {

    private static int baggageControlPointIndex;

    private Controller controller;

    private ArrayList<Baggage> baggages = new ArrayList<>();

    public BaggageControlPoint(String name, Integer maxPeopleAmount) {
        super(name, maxPeopleAmount);
    }

    public void checkBaggage(ArrayList<Airplane> airplanes, int brutalityLevel) {

        int dangerLevel;
        for (int i = 0; i < getControllerEfficiency(); i++) {
            dangerLevel = passengers.get(i).getBaggage().getDangerLevel();
            if (dangerLevel == 0) {

                moveBaggage(passengers.get(i), airplanes);

            } else if (dangerLevel == 1) {

                passengers.get(i).removeTicket();
                passengers.get(i).removeBaggage();
                passengers.remove(i);

            } else {
                for (int j = i; j < brutalityLevel && j < getControllerEfficiency(); j++) {

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

    public void openPoint(ArrayList<Controller> controllers, String time) throws ParseException {
        isOpen = true;
        setRandomAvailableController(controllers);
        baggageControlPointIndex += 1;

        DateFormat format = new SimpleDateFormat("HH:mm");
        Date date = format.parse(time);
        setShiftStartTime(date);
    }

    public Controller closePoint(){
        isOpen = false;
        baggageControlPointIndex -= 1;
        setShiftStartTime(null);
        return removeController();
    }

    public int getBaggageControlPointIndex() { return baggageControlPointIndex; }

}