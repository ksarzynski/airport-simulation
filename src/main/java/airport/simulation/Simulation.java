package main.java.airport.simulation;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.*;
import main.java.airport.app.place.*;

import java.io.IOException;
import java.util.*;

public class Simulation {

    private Schedule schedule = new Schedule(this);
    private ArrayList<Ticket> allAvailableTickets = new ArrayList<>();
    private ArrayList<Vendor> allVendors = new ArrayList<>();
    private ArrayList<Controller> allControllers = new ArrayList<>();
    private ArrayList<Airplane> airplanes = new ArrayList<>();
    private ArrayList<SalePoint> salePoints = new ArrayList<>();
    private ArrayList<ControlPoint> controlPoints = new ArrayList<>();
    private ArrayList<BaggageControlPoint> baggageControlPoints = new ArrayList<>();
    private DutyFreeZone dutyFreeZone;

    public void start() throws IOException {
        initialization(3,10,2,3, 1,3,10,6);
        start(500, 10);
    }

    private void initialization(Integer salePointsAmount, Integer vendorsAmount, Integer openSalePointsAmount, Integer controlPointsAmount, Integer openControlPointsAmount, Integer baggageControlPointsAmount, Integer controllersAmount, Integer flightsAmount) throws IOException {
        this.allVendors.addAll(addNewRandomVendors(vendorsAmount));
        this.salePoints.addAll(createSalePoints(salePointsAmount, 10, 25));
        openRandomSalePoints(openSalePointsAmount);

        this.allControllers.addAll(addNewRandomControllers(controllersAmount));
        this.controlPoints.addAll(createControlPoints(controlPointsAmount, 10, 25));
        openRandomControlPoints(openControlPointsAmount);
        this.baggageControlPoints.addAll(createBaggageControlPoints(baggageControlPointsAmount, 10, 25));
        openRandomBaggageControlPoints(openControlPointsAmount);

        this.airplanes.addAll(addNewRandomAirplane(flightsAmount));

        this.dutyFreeZone = new DutyFreeZone("Strefa bezcłowa", 10000, 100);

    }

    private void start(Integer simulationSpeedInMiliseconds, Integer timeShift) {
        this.schedule.setSimulationSpeedInMilliseconds(simulationSpeedInMiliseconds);
        this.schedule.setTimeShiftInMilliseconds(timeShift);
        schedule.runTimer();
    }

    void addNewRandomPassengers(Integer amount) throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        List randomID = getRandomNumbers(0, 99, amount);
        for (int i=0; i<amount; i++){
            String[] passengerData = openCSVReader.readCSV("passenger.csv", Integer.parseInt(randomID.get(i).toString()));
            Passenger passenger = new Passenger(passengerData[0]);
            if(passengerData[1].equals("0")){
                passenger.setBaggage(Integer.parseInt(passengerData[2]));
            }

            Integer salePointIndex = getRandomNumber(0, salePoints.get(0).getOpenSalePointIndex());
            salePoints.get(salePointIndex).addPassenger(passenger);
        }
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

    private ArrayList<ControlPoint> createControlPoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        ArrayList<ControlPoint> controlPoints = new ArrayList<>();
        for(int i=0; i<amount; i++) {
            Integer queueSize = getRandomNumber(minAvailableQueue, maxAvailableQueue);
            ControlPoint controlPoint = new ControlPoint("Punkt kontrolny nr " + (i+1), queueSize);
            controlPoints.add(controlPoint);
        }
        return controlPoints;
    }

    private ArrayList<BaggageControlPoint> createBaggageControlPoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        ArrayList<BaggageControlPoint> baggageControlPoints = new ArrayList<>();
        for(int i=0; i<amount; i++) {
            System.out.print("otworzono baggagecontrolpoint nr " + i + "\n");
            Integer queueSize = getRandomNumber(minAvailableQueue, maxAvailableQueue);
            BaggageControlPoint baggageControlPoint = new BaggageControlPoint("Punkt kontrolny bagażu nr " + (i+1), queueSize);
            baggageControlPoints.add(baggageControlPoint);
        }
        return baggageControlPoints;
    }

    private void openRandomSalePoints(Integer amount) {
        List randomIDs = getRandomNumbers(0, salePoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            System.out.print("otworzono salepoint nr " + i + "\n");
            salePoints.get(Integer.parseInt(randomIDs.get(i).toString())).openPoint(this.allVendors, this.schedule.getDate());
        }
    }

    private void openRandomControlPoints(Integer amount) {
        List randomIDs = getRandomNumbers(0, controlPoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            controlPoints.get(Integer.parseInt(randomIDs.get(i).toString())).openPoint(this.allControllers, this.schedule.getDate());
        }
    }

    private void openRandomBaggageControlPoints(Integer amount) {
        List randomIDs = getRandomNumbers(0, controlPoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            baggageControlPoints.get(Integer.parseInt(randomIDs.get(i).toString())).openPoint(this.allControllers, this.schedule.getDate());
        }
    }

    public Ticket getAvailableTicket() {

        Random r = new Random();
        int a = r.nextInt(allAvailableTickets.size());
        return allAvailableTickets.remove(a);

    }

    /*
    public Vendor getAvailableVendor() {

        Random r = new Random();
        int a = r.nextInt(allVendors.size());
        return allVendors.remove(a);

    }
    */

    /*
    public Controller getAvailableController() {

        Random r = new Random();
        int a = r.nextInt(allControllers.size());
        return allControllers.remove(0);
    }
    */

    public void returnEmployeeToList(Place place) {

        if(place.getEmployee() instanceof Controller){

            allControllers.add((Controller)place.getEmployee());

        }

        else {

            allVendors.add((Vendor)place.getEmployee());

        }

    }

    private void returnVendorToPool(Vendor vendor){
        this.allVendors.add(vendor);
    }

    private void returnControllerToPool(Controller controller){
        this.allControllers.add(controller);
    }

    void checkWorkingHours() {
        for (SalePoint salePoint : this.salePoints) {
            Vendor vendor = salePoint.checkWorkingHour(schedule.getDate());
            if (vendor != null) {
                returnVendorToPool(vendor);
            }
        }

        for (ControlPoint controlPoint : this.controlPoints) {
            Controller controller = controlPoint.checkWorkingHour(schedule.getDate());
            if (controller != null) {
                returnControllerToPool(controller);
            }
        }

        for (BaggageControlPoint baggageControlPoint : this.baggageControlPoints) {
            Controller controller = baggageControlPoint.checkWorkingHour(schedule.getDate());
            if (controller != null) {
                returnControllerToPool(controller);
            }
        }
    }

    private List getRandomNumbers(Integer min, Integer max, Integer amount){
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = min-1; i < max; i++)
        {
            numbers.add(i+1);
        }
        Collections.shuffle(numbers);
        return numbers.subList(0, amount);
    }

    private Integer getRandomNumber(Integer min, Integer max){
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = min-1; i < max; i++)
        {
            numbers.add(i+1);
        }
        Collections.shuffle(numbers);
        return Integer.parseInt(numbers.get(0).toString());
    }

    void moveFromSalePoints()
    {
        int howMany;
        int index = 0;
        for(SalePoint salePoint : salePoints)
        {
            if(salePoint.getIsOpen()) { System.out.print("CHUJEEEEEEEEE \n" );
                if (salePoint.getEmployee().getEfficiency() > salePoint.getPassangers().size())
                    howMany = salePoint.getPassangers().size();
                else
                    howMany = salePoint.getEmployee().getEfficiency();

                for (int i = 0; i < howMany; i++) {

                    while (!baggageControlPoints.get(index).getIsOpen()) {
                        index = getRandomNumber(0, baggageControlPoints.size() - 1);
                        if (salePoint.getEmployee().getEfficiency() > salePoint.getPassangers().size())
                            salePoint.movePassengersPoli(baggageControlPoints.get(index), salePoint.getPassangers().size());
                        else
                            salePoint.movePassengersPoli(baggageControlPoints.get(index), salePoint.getEmployee().getEfficiency());
                    }
                }
            }
        }
    }


    void moveFromBaggageControlPoints()
    {
        int howMany;
        int index = 0;
        for(BaggageControlPoint baggageControlPoint : baggageControlPoints)
        {
            if(baggageControlPoint.getIsOpen()) {

                baggageControlPoint.checkBaggage(airplanes, 100);

                if (baggageControlPoint.getEmployee().getEfficiency() > baggageControlPoint.getPassangers().size())
                    howMany = baggageControlPoint.getPassangers().size();
                else
                    howMany = baggageControlPoint.getEmployee().getEfficiency();

                for (int i = 0; i < howMany; i++) {

                    while (!controlPoints.get(index).getIsOpen()) {
                        index = getRandomNumber(0, controlPoints.size() - 1);
                        if (baggageControlPoint.getEmployee().getEfficiency() > baggageControlPoint.getPassangers().size())
                            baggageControlPoint.movePassengersPoli(controlPoints.get(index), baggageControlPoint.getPassangers().size());
                        else
                            baggageControlPoint.movePassengersPoli(controlPoints.get(index), baggageControlPoint.getEmployee().getEfficiency());
                    }
                }
            }
        }
    }

    void moveFromControlPoints() {

         for(ControlPoint controlPoint : controlPoints)
        {
            if(controlPoint.getIsOpen())
            {
                controlPoint.movePassengersPoli(dutyFreeZone, controlPoint.getEmployee().getEfficiency());
            }
        }
    }

    void MoveFromDutyFreeZone() {



    }

    void display() {

        for(SalePoint salePoint : salePoints)
        System.out.print("salePoints ppl amount: "+salePoint.getPassangers().size()+"\n");
        for(BaggageControlPoint baggageControlPoint : baggageControlPoints)
        System.out.print("baggageControlPoints ppl amount: "+baggageControlPoint.getPassangers().size()+"\n");
//        System.out.print("otworzonych salepointow: " + salePoints.get(0).getOpenSalePointIndex()+"\n");
  //      System.out.print("otworzonych baggagecontrolpointow: " + baggageControlPoints.get(0).getOpenSalePointIndex()+"\n");
    //    System.out.print();

    }

/*    public void moveFromPlaces(ArrayList<Object> objects) {

        if(objects.get(0) instanceof SalePoint)
        {
            int index = 0;
            for(SalePoint salePoint : salePoints)
            {
                if(salePoint.getIsOpen())
                {
                    while(!baggageControlPoints.get(index).getIsOpen())
                    {
                        index = getRandomNumber(0, baggageControlPoints.size()-1);
                    }

                    if(salePoint.getEmployee().getEfficiency() > salePoint.getPassangers().size())
                        salePoint.movePassengersPoli(baggageControlPoints.get(index),salePoint.getPassangers().size());
                    else
                        salePoint.movePassengersPoli(baggageControlPoints.get(index),salePoint.getEmployee().getEfficiency());
                }
            }
        }

    }
*/

}



