package main.java.airport.app.place;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.person.Controller;
import main.java.airport.app.person.Passenger;

import java.util.ArrayList;
import java.util.Date;

public class BaggageControlPoint extends ControlPoint {

    private static int baggageControlPointIndex;

//    private Controller controller;

    private ArrayList<Baggage> baggages = new ArrayList<>();

    public BaggageControlPoint(String name, Integer maxPeopleAmount) {
        super(name, maxPeopleAmount);
    }

    public void checkBaggage(ArrayList<Airplane> airplanes, int brutalityLevel) {

        int dangerLevel;
        int index;
        if (passengers.size() < controller.getEfficiency()) {
            index = passengers.size();
            System.out.print("1 " + " \n");
            System.out.print("rozmiar kolejki " + passengers.size() + " \n");
            System.out.print("wydajnosc " + controller.getEfficiency() + " \n");
            System.out.print("index " + index + " \n");
        } else{
            index = controller.getEfficiency();
            System.out.print("2 " + " \n");
            System.out.print("rozmiar kolejki " + passengers.size() + " \n");
            System.out.print("wydajnosc " + controller.getEfficiency() + " \n");
            System.out.print("index " + index + " \n");
        }
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
                    passengers.remove(i);

                }
                else {
                    for (int j = i; j < brutalityLevel && j < getControllerEfficiency(); j++) {

                        passengers.get(j).removeTicket();
                        passengers.get(j).removeBaggage();
                        passengers.remove(i);
                    }
                }
            }

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

//    public void setController(Controller controller) {
//        controller = controller;
//    }

    public void movePassengers(int basicFlow, ControlPoint controlPoint) {

        ArrayList<Passenger> passengersToMove = new ArrayList<>();

        for(int i = 0; i < getControllerEfficiency() * basicFlow; i++)
        {
            passengersToMove.add(this.passengers.get(i));
        }

        controlPoint.addPassengers(passengersToMove);
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