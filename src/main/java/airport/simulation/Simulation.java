package main.java.airport.simulation;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.*;
import main.java.airport.app.place.ControlPoint;
import main.java.airport.app.place.SalePoint;
import main.java.airport.app.place.Place;

import java.io.IOException;
import java.util.*;

public class Simulation {
    private Schedule schedule;
    private ArrayList<Ticket> allAvailableTickets = new ArrayList<>();
    private ArrayList<Vendor> allVendors = new ArrayList<>();
    private ArrayList<Controller> allControllers = new ArrayList<>();
    private ArrayList<Airplane> airplanes = new ArrayList<>();
    private ArrayList<SalePoint> salePoints = new ArrayList<>();
    private ArrayList<ControlPoint> controlPoints = new ArrayList<>();

    public void start() throws IOException {

        initialization(3,10,3, 3,10,6);
        run(500, 10);
    }

    private void initialization(Integer salePointsAmout, Integer vendorsAmount, Integer controlPointsAmount, Integer baggageControlPointsAmount, Integer controllersAmount, Integer flightsAmount) throws IOException {
        this.allVendors.addAll(addNewRandomVendors(vendorsAmount));
        this.salePoints.addAll(createSalePoints(salePointsAmout, 10, 25));

        this.allControllers.addAll(addNewRandomControllers(controllersAmount));
        this.controlPoints.addAll(createControllPoints(controlPointsAmount, 10, 25));

        this.airplanes.addAll(addNewRandomAirplane(flightsAmount));
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
        return new Pilot(pilotData[1]);
    }

    private ArrayList<Airplane> addNewRandomAirplane(Integer amount) throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        ArrayList<Airplane> airplanes = new ArrayList<>();
        List randomID = getRandomNumbers(0, 99, amount);
        for (int i=0; i<amount; i++){
            String[] airplaneData = openCSVReader.readCSV("airplanes.csv", Integer.parseInt(randomID.get(i).toString()));
            Airplane airplane = new Airplane(
                    airplaneData[1],
                    airplaneData[2],
                    Integer.parseInt(airplaneData[3]),
                    Integer.parseInt(airplaneData[4]),
                    addNewRandomPilot(),
                    new Date()
            );
            airplanes.add(airplane);
        }
        return airplanes;
    }

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

    private ArrayList<SalePoint> createSalePoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        ArrayList<SalePoint> salePoints = new ArrayList<>();
        for(int i=0; i<amount; i++) {
            Integer queueSize = getRandomNumber(minAvailableQueue, maxAvailableQueue);
            SalePoint salePoint = new SalePoint("Punkt sprzedaży nr " + (i+1), queueSize);
            salePoints.add(salePoint);
        }
        return salePoints;
    }

    private ArrayList<ControlPoint> createControllPoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        ArrayList<ControlPoint> controlPoints = new ArrayList<>();
        for(int i=0; i<amount; i++) {
            Integer queueSize = getRandomNumber(minAvailableQueue, maxAvailableQueue);
            ControlPoint controlPoint = new ControlPoint("Punkt kontrolny nr " + (i+1), queueSize);
            controlPoints.add(controlPoint);
        }
        return controlPoints;
    }

    public Ticket getAvailableTicket() {

        Random r = new Random();
        int a = r.nextInt(allAvailableTickets.size());
        return allAvailableTickets.remove(a);

    }

    public Vendor getAvailableVendor() {

        Random r = new Random();
        int a = r.nextInt(allVendors.size());
        return allVendors.remove(a);

    }

    public Controller getAvailableController() {

        Random r = new Random();
        int a = r.nextInt(allControllers.size());
        return allControllers.remove(0);
    }

    public void returnEmployeeToList(Place place) {

        if(place.getEmployee() instanceof Controller){

            allControllers.add((Controller)place.getEmployee());

        }

        else {

            allVendors.add((Vendor)place.getEmployee());

        }

    }

    private List getRandomNumbers(Integer min, Integer max, Integer amount){
        ArrayList<Integer> numbers = new ArrayList();
        for(int i = min-1; i < max; i++)
        {
            numbers.add(i+1);
        }
        Collections.shuffle(numbers);
        return numbers.subList(0, amount);
    }

    private Integer getRandomNumber(Integer min, Integer max){
        ArrayList<Integer> numbers = new ArrayList();
        for(int i = min-1; i < max; i++)
        {
            numbers.add(i+1);
        }
        Collections.shuffle(numbers);
        return Integer.parseInt(numbers.get(1).toString());
    }

}

