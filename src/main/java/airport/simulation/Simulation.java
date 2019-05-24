package main.java.airport.simulation;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Simulation {
    private Schedule schedule;
    private ArrayList<Ticket> allAvailableTickets = new ArrayList<>();
    private ArrayList<Vendor> allVendors = new ArrayList<>();
    private ArrayList<Controller> allControllers = new ArrayList<>();
    private ArrayList<Airplane> airplanes = new ArrayList<>();

    public void start() throws IOException {

        initialization(3,10,3, 3,10,6);
        run(500, 10);
    }

    private void initialization(Integer sellPointsAmout, Integer vendorsAmount, Integer controlPointsAmount, Integer baggageControlPointsAmount, Integer controllersAmount, Integer flightsAmount) throws IOException {
        this.allVendors.addAll(addNewRandomVendors(vendorsAmount));
//        this.airplanes.addAll(addNewRandomAirplane(flightsAmount));
    }

    private void run(Integer simulationSpeedInMiliseconds, Integer timeShift) {
        this.schedule = new Schedule(simulationSpeedInMiliseconds, timeShift);
        schedule.runTimer();
    }

    private ArrayList<Passenger> addNewRandomPassangers(Integer amount) {
        // TODO: metoda
        return new ArrayList<>();
    }

    private ArrayList<Vendor> addNewRandomVendors(Integer amount) throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        ArrayList<Vendor> vendors = new ArrayList<>();
        List randomID = getRandomNumbers(0, 99, amount);
        for (int i=0; i<amount; i++){
            String[] vendorData = openCSVReader.readCSV("vendors.csv", Integer.parseInt(randomID.get(i).toString()));
            Vendor vendor = new Vendor(vendorData[1], Integer.parseInt(vendorData[2]));
            vendors.add(vendor);
        }
        return vendors;
    }

    private Pilot addNewRandomPilot() throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        Integer randomID = getRandomNumber(0, 99);
        String[] pilotData = openCSVReader.readCSV("pilots.csv", randomID);
        Pilot pilot = new Pilot(pilotData[1]);
        return pilot;
    }

//    private ArrayList<Airplane> addNewRandomAirplane(Integer amount) throws IOException {
//        OpenCSVReader openCSVReader = new OpenCSVReader();
//        ArrayList<Airplane> airplanes = new ArrayList<>();
//        List randomID = getRandomNumbers(0, 99, amount);
//        for (int i=0; i<amount; i++){
//            System.out.println(Integer.parseInt(randomID.get(i).toString()));
//            String[] airplaneData = openCSVReader.readCSV("airplanes.csv", Integer.parseInt(randomID.get(i).toString()));
//            Airplane airplane = new Airplane(
//                    airplaneData[1],
//                    airplaneData[2],
//                    Integer.parseInt(airplaneData[3]),
//                    Integer.parseInt(airplaneData[4]),
//                    addNewRandomPilot(),
//                    new Date()
//            );
//            airplanes.add(airplane);
//        }
//        return airplanes;
//    }

    private ArrayList<Controller> addNewRandomControllers(Integer amount) throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        ArrayList<Controller> controllers = new ArrayList<>();
        List randomID = getRandomNumbers(0, 99, amount);
        for (int i=0; i<amount; i++){
            String[] controllerData = openCSVReader.readCSV("controllers.csv", Integer.parseInt(randomID.get(i).toString()));
            Controller controller = new Controller(controllerData[1], Integer.parseInt(controllerData[2]));
            controllers.add(controller);
        }
        return controllers;
    }

    public Ticket getAvailableTicket() {
        return allAvailableTickets.remove(0);
    }

    public Vendor getAvailableVendor() {
        return allVendors.remove(0);
    }

    public Controller getAvailableController() {
        return allControllers.remove(0);
    }

    public void addVendor(Vendor vendor) {
        // TODO: metoda
    }

    public void addController(Controller controller) {
        // TODO: metoda
    }

    private List getRandomNumbers(Integer min, Integer max, Integer amount){
        ArrayList numbers = new ArrayList();
        for(int i = min-1; i < max; i++)
        {
            numbers.add(i+1);
        }
        Collections.shuffle(numbers);
        return numbers.subList(0, amount);
    }

    private Integer getRandomNumber(Integer min, Integer max){
        ArrayList numbers = new ArrayList();
        for(int i = min-1; i < max; i++)
        {
            numbers.add(i+1);
        }
        Collections.shuffle(numbers);
        return Integer.parseInt(numbers.get(1).toString());
    }
}
