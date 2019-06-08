package main.java.airport.simulation;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.*;
import main.java.airport.app.place.*;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;

public class Simulation {
    public static final String SALEPOINTS = "salePoints";
    public static final String CONTROLPOINTS = "controlPoints";
    public static final String BAGGAGECONTROLPOINTS = "baggageControlPoints";
    public static final String DUTYFREEZONE = "dutyFreeZone";
    public static final String AIRPLANES = "airplanes";
    public static final String CLOCK = "clock";

    private Schedule schedule;
    private ArrayList<Ticket> allAvailableTickets = new ArrayList<>();
    private ArrayList<Vendor> allVendors = new ArrayList<>();
    private ArrayList<Controller> allControllers = new ArrayList<>();
    private ArrayList<Airplane> airplanes = new ArrayList<>();
    private ArrayList<SalePoint> salePoints = new ArrayList<>();
    private ArrayList<ControlPoint> controlPoints = new ArrayList<>();
    private ArrayList<BaggageControlPoint> baggageControlPoints = new ArrayList<>();
    private DutyFreeZone dutyFreeZone;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void start(
            Integer salePointsAmount,
            Integer vendorsAmount,
            Integer openSalePointsAmount,
            Integer controlPointsAmount,
            Integer openControlPointsAmount,
            Integer controllersAmount,
            Integer baggageControlPointsAmount,
            Integer openBaggageControlPointsAmount,
            Integer flightsAmount,
            Integer simulationSpeedInMiliseconds,
            Integer timeShift
    ) throws IOException {
        schedule = new Schedule(this);
        initialization(
                salePointsAmount,
                vendorsAmount,
                openSalePointsAmount,
                controlPointsAmount,
                openControlPointsAmount,
                controllersAmount,
                baggageControlPointsAmount,
                openBaggageControlPointsAmount,
                flightsAmount
        );
        start(simulationSpeedInMiliseconds, timeShift);
    }

    private void initialization(
            Integer salePointsAmount,
            Integer vendorsAmount,
            Integer openSalePointsAmount,
            Integer controlPointsAmount,
            Integer openControlPointsAmount,
            Integer controllersAmount,
            Integer baggageControlPointsAmount,
            Integer openBaggageControlPointsAmount,
            Integer flightsAmount
    ) throws IOException {
        addNewRandomVendors(vendorsAmount);
        createSalePoints(salePointsAmount, 10, 2000);
        openRandomSalePoints(openSalePointsAmount);

        addNewRandomControllers(controllersAmount);
        createControlPoints(controlPointsAmount, 10, 2000);
        openRandomControlPoints(openControlPointsAmount);
        createBaggageControlPoints(baggageControlPointsAmount, 10, 2000);
        openRandomBaggageControlPoints(openBaggageControlPointsAmount);

        addNewRandomAirplanes(flightsAmount);

        this.dutyFreeZone = new DutyFreeZone("Strefa bezcłowa", 10000, 100);
    }

    public void stop() {
        schedule.cancel();
        schedule = null;

        allVendors.clear();
        salePoints.clear();
        controlPoints.clear();
        allControllers.clear();
        baggageControlPoints.clear();
        airplanes.clear();
        allAvailableTickets.clear();
    }

    private void start(Integer simulationSpeedInMiliseconds, Integer timeShift) {
        this.schedule.setSimulationSpeedInMilliseconds(simulationSpeedInMiliseconds);
        this.schedule.setTimeShiftInMilliseconds(timeShift);
        schedule.runTimer();
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    public void addNewRandomPassengers(Integer amount) throws IOException {
        int salePointIndex;
        OpenCSVReader openCSVReader = new OpenCSVReader();
        List randomID = Helpers.getRandomNumbers(0, 300, amount);
        for (int i=0; i<amount; i++){
            String[] passengerData = openCSVReader.readCSV("passenger.csv", Integer.parseInt(randomID.get(i).toString()));
            Passenger passenger = new Passenger(passengerData[1]);
            if(passengerData[2].equals("0")){
                passenger.setBaggage(Integer.parseInt(passengerData[3]));
            }

            do {
                salePointIndex = Helpers.getRandomNumber(0, ((salePoints.get(0).getOpenSalePointIndex())-1));

            }while((!salePoints.get(salePointIndex).getIsOpen()) || salePoints.get(salePointIndex).isPlaceFull());

            SalePoint salePoint = salePoints.get(salePointIndex);
            salePoint.addPassenger(passenger);
            getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
        }
    }

    private void addNewRandomVendors(Integer amount) throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        List randomID = Helpers.getRandomNumbers(0, 99, amount);
        for (int i=0; i<amount; i++){
            String[] vendorData = openCSVReader.readCSV("vendors.csv", Integer.parseInt(randomID.get(i).toString()));
            Vendor vendor = new Vendor(vendorData[1], Integer.parseInt(vendorData[2]));
            this.allVendors.add(vendor);
        }
    }

    private Pilot addNewRandomPilot() throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        Integer randomID = Helpers.getRandomNumber(0, 99);
        String[] pilotData = openCSVReader.readCSV("pilots.csv", randomID);
        return new Pilot(pilotData[1]);
    }

    public void addNewRandomAirplanes(Integer amount) throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        List randomID = Helpers.getRandomNumbers(0, 99, amount);
        for (int i=0; i<amount; i++){
            String[] airplaneData = openCSVReader.readCSV("airplanes.csv", Integer.parseInt(randomID.get(i).toString()));
            Airplane airplane = new Airplane(
                    airplaneData[1],
                    airplaneData[2],
                    Integer.parseInt(airplaneData[3]),
                    Integer.parseInt(airplaneData[4]),
                    addNewRandomPilot(),
                    new Date(schedule.getDate().getTime() + (Helpers.getRandomNumber(3, 10) * 60 * 60 * 1000))
            );
            Ticket ticket = new Ticket(airplane);
            for(int ii=0; ii<airplane.getMaxPassenger(); ii++) {
                this.addTicket(ticket);
            }
            this.airplanes.add(airplane);
            getPropertyChangeSupport().firePropertyChange(AIRPLANES, null, airplane);
        }
    }

    private void addNewRandomControllers(Integer amount) throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        List randomID = Helpers.getRandomNumbers(0, 99, amount);
        for (int i=0; i<amount; i++){
            String[] controllerData = openCSVReader.readCSV("controllers.csv", Integer.parseInt(randomID.get(i).toString()));
            Controller controller = new Controller(controllerData[1], Integer.parseInt(controllerData[2]));
            this.allControllers.add(controller);
        }
    }

    private void addTicket(Ticket ticket) {
        this.allAvailableTickets.add(ticket);
    }

    private void createSalePoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        for(int i=0; i<amount; i++) {
            Integer queueSize = Helpers.getRandomNumber(minAvailableQueue, maxAvailableQueue);
            SalePoint salePoint = new SalePoint("P sprzedaży nr " + (i+1), queueSize);
            this.salePoints.add(salePoint);
            getPropertyChangeSupport().firePropertyChange(SALEPOINTS, null, salePoint);
        }
    }

    private void createControlPoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        for(int i=0; i<amount; i++) {
            Integer queueSize = Helpers.getRandomNumber(minAvailableQueue, maxAvailableQueue);
            ControlPoint controlPoint = new ControlPoint("P kontrolny nr " + (i+1), queueSize);
            this.controlPoints.add(controlPoint);
            getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, null, controlPoint);
        }
    }

    private void createBaggageControlPoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        for(int i=0; i<amount; i++) {
            System.out.print("otworzono baggagecontrolpoint nr " + i + "\n");
            Integer queueSize = Helpers.getRandomNumber(minAvailableQueue, maxAvailableQueue);
            BaggageControlPoint baggageControlPoint = new BaggageControlPoint("P kontrolny bagażu nr " + (i+1), queueSize);
            this.baggageControlPoints.add(baggageControlPoint);
            getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, null, baggageControlPoint);
        }
    }

    public void openRandomSalePoints(Integer amount) {
        List randomIDs = Helpers.getRandomNumbers(0, salePoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            SalePoint salePoint = salePoints.get(Integer.parseInt(randomIDs.get(i).toString()));

            salePoint.openPoint(getAvailableVendor(), this.schedule.getDate());
            System.out.print("otworzono salepoint nr " + i + " " + salePoint.getName() + " "
                    + salePoint.getIsOpen()+  "\n");
            getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
        }
    }

    public void openClosedSalePoints() {
        for(SalePoint salePoint : salePoints){
            if(!salePoint.getIsOpen()){
                salePoint.openPoint(getAvailableVendor(), this.schedule.getDate());
                getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
                break;
            }
        }
    }

    public void openRandomControlPoints(Integer amount) {
        List randomIDs = Helpers.getRandomNumbers(0, controlPoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            ControlPoint controlPoint = controlPoints.get(Integer.parseInt(randomIDs.get(i).toString()));
            controlPoint.openPoint(getAvailableController(), this.schedule.getDate());
            getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoint);
        }
    }

    public void openClosedControlPoints() {
        for(ControlPoint controlPoint : controlPoints){
            if(!controlPoint.getIsOpen()){
                controlPoint.openPoint(getAvailableController(), this.schedule.getDate());
                getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoint);
                break;
            }
        }
    }

    public void openRandomBaggageControlPoints(Integer amount) {
        List randomIDs = Helpers.getRandomNumbers(0, controlPoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            BaggageControlPoint baggageControlPoint = baggageControlPoints.get(Integer.parseInt(randomIDs.get(i).toString()));
            baggageControlPoint.openPoint(getAvailableController(), this.schedule.getDate());
            getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoint);
        }
    }

    public void openClosedBaggageControlPoints() {
        for(BaggageControlPoint baggageControlPoint : baggageControlPoints){
            if(!baggageControlPoint.getIsOpen()){
                baggageControlPoint.openPoint(getAvailableController(), this.schedule.getDate());
                getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoint);
                break;
            }
        }
    }

    public Integer getAirplanesAmount() {
        return this.airplanes.size();
    }

    public Ticket getAvailableTicket() {
        Integer randomId = Helpers.getRandomNumber(0, allAvailableTickets.size()-1);
        Ticket ticket = this.allAvailableTickets.get(randomId);
        ticket.bought();
        this.allAvailableTickets.remove(randomId);
        getPropertyChangeSupport().firePropertyChange(AIRPLANES, "update", ticket.getAirplane());
        return ticket;
    }

    private Vendor getAvailableVendor() {
        Integer randomId = Helpers.getRandomNumber(0, allVendors.size()-1);
        Vendor vendor = this.allVendors.get(randomId);
        allVendors.remove(randomId);
        return vendor;
    }

    private Controller getAvailableController() {
        Integer randomId = Helpers.getRandomNumber(0, allControllers.size()-1);
        Controller controller = this.allControllers.get(randomId);
        allControllers.remove(randomId);
        return controller;
    }

    public ArrayList<SalePoint> getSalePoints() {
        return salePoints;
    }

    public ArrayList<ControlPoint> getControlPoints() {
        return controlPoints;
    }

    public ArrayList<Airplane> getAirplanes() {
        return airplanes;
    }

    public ArrayList<BaggageControlPoint> getBaggageControlPoints() {
        return baggageControlPoints;
    }

    public void returnEmployeeToList(Place place) {
        if(place.getEmployee() instanceof Controller){
            allControllers.add((Controller)place.getEmployee());
        } else {
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
                getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
            }
        }

        for (ControlPoint controlPoint : this.controlPoints) {
            Controller controller = controlPoint.checkWorkingHour(schedule.getDate());
            if (controller != null) {
                returnControllerToPool(controller);
                getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoint);
            }
        }

        for (BaggageControlPoint baggageControlPoint : this.baggageControlPoints) {
            Controller controller = baggageControlPoint.checkWorkingHour(schedule.getDate());
            if (controller != null) {
                returnControllerToPool(controller);
                getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoint);
            }
        }
    }

    void checkDepartureTimes() {
        for(Airplane airplane : this.airplanes) {
            if(airplane.checkDepartureTime(schedule.getDate()))
                getPropertyChangeSupport().firePropertyChange(AIRPLANES, airplane, null);
            else
                getPropertyChangeSupport().firePropertyChange(AIRPLANES, "update", airplane);
        }
    }

    void moveFromSalePoints() {
        int index;
        int howMany;

        for(SalePoint salePoint : salePoints)
        {
            if(salePoint.getIsOpen())
            {
                if(salePoint.isPlaceFull())
                    openClosedSalePoints();

                if(salePoint.getEmployee().getEfficiency() > salePoint.getPassangers().size())
                    howMany = salePoint.getPassangers().size();
                else
                    howMany = salePoint.getEmployee().getEfficiency();

                do{
                    index = Helpers.getRandomNumber(0,baggageControlPoints.size()-1);
//                    System.out.print("KONTROLA BAGAZU: " + baggageControlPoints.get(index).getName()+"\n");

                }while(!baggageControlPoints.get(index).getIsOpen() || baggageControlPoints.get(index).isPlaceFull());

                ArrayList<Ticket> tickets = new ArrayList<>();
                for(int i=0; i<howMany; i++) {
                    tickets.add(getAvailableTicket());
                }

                salePoint.movePassengers(baggageControlPoints.get(index), howMany, tickets);
                getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
                getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoints.get(index));
            }
        }
    }

    void moveFromBaggageControlPoints() throws NullPointerException {

        int index;
        int howMany;

        for(BaggageControlPoint baggageControlPoint : baggageControlPoints)
        {
            if(baggageControlPoint.isPlaceFull())
                openClosedBaggageControlPoints();

            if(baggageControlPoint.getIsOpen())
            {

                baggageControlPoint.checkBaggage(airplanes, 10);

                    howMany = baggageControlPoint.getPassangers().size();


                do{
                    index = Helpers.getRandomNumber(0, controlPoints.size()-1);

                }while(!controlPoints.get(index).getIsOpen() || controlPoints.get(index).isPlaceFull());

                baggageControlPoint.movePassengersPoli(controlPoints.get(index), howMany);
                getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoint);
                getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoints.get(index));
            }
        }

    }

    void moveFromControlPoints() {

        int howMany;

        for(ControlPoint controlPoint : controlPoints)
        {
            if(controlPoint.isPlaceFull())
                openClosedControlPoints();

            if(controlPoint.getIsOpen())
            {
                if(controlPoint.getControllersEfficiency() > controlPoint.getPassangers().size())
                    howMany = controlPoint.getPassangers().size();
                else
                    howMany = controlPoint.getControllersEfficiency();

                controlPoint.movePassengersPoli(dutyFreeZone, howMany);
                getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoint);
                getPropertyChangeSupport().firePropertyChange(DUTYFREEZONE, "update", dutyFreeZone);
            }

        }

    }

    public void moveFromDutyFreeZone() {

        dutyFreeZone.movePassengersPoli(airplanes, dutyFreeZone.getFlow());

    }

    void display() {
//        for(SalePoint salePoint : salePoints)
//            System.out.print("salePoints ppl amount: "+salePoint.getPassangers().size()+"\n");
//        for(BaggageControlPoint baggageControlPoint : baggageControlPoints)
//            System.out.print("baggageControlPoints ppl amount: "+baggageControlPoint.getPassangers().size()+"\n");
//        for(ControlPoint controlPoint : controlPoints)
//            System.out.print("controlPoints ppl amount: "+controlPoint.getPassangers().size()+"\n");
//        System.out.print("dutyFreeZone ppl amount: "+dutyFreeZone.getPassangers().size()+"\n");
//        for(Controller controller : allControllers)
//            System.out.print("Kontrolerzy: " + controller.getName() + "\n");
        //System.out.print("Airplanes amount: " + getAirplanesAmount() + "\n");
//        System.out.print("otworzonych salepointow: " + salePoints.get(0).getOpenSalePointIndex()+"\n");
  //      System.out.print("otworzonych baggagecontrolpointow: " + baggageControlPoints.get(0).getOpenSalePointIndex()+"\n");
    //    System.out.print();

        System.out.print("rozmiar jebanych salepointow: " +salePoints.size()+"\n");

    }

    public void updateGUIClock() {
        getPropertyChangeSupport().firePropertyChange(CLOCK, "update", schedule.getTime());
    }

    public void canPlacesBeClosed(Double whenClose){

        boolean needClose = true;

        for(SalePoint salePoint : salePoints){
            if(salePoint.getPassangers().size() / salePoint.getQueueSize() < whenClose)
                needClose = false;
        }
        if(needClose)
            ;

        for(BaggageControlPoint baggageControlPoint : baggageControlPoints){
            if(baggageControlPoint.getPassangers().size() / baggageControlPoint.getQueueSize() < whenClose)
                needClose = false;
        }
        if(needClose)
            ;

        for(ControlPoint controlPoint : controlPoints){
            if(controlPoint.getPassangers().size() / controlPoint.getQueueSize() < whenClose)
                needClose = false;
        }
        if(needClose)
            ;

    }

}



