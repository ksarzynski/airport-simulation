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
        addNewRandomVendors(vendorsAmount);
        createSalePoints(salePointsAmount, 10, 25);
        openRandomSalePoints(openSalePointsAmount);

        addNewRandomControllers(controllersAmount);
        createControlPoints(controlPointsAmount, 10, 25);
        openRandomControlPoints(openControlPointsAmount);
        createBaggageControlPoints(baggageControlPointsAmount, 10, 25);
        openRandomBaggageControlPoints(openControlPointsAmount);

        addNewRandomAirplanes(flightsAmount);

        this.dutyFreeZone = new DutyFreeZone("Strefa bezcłowa", 10000, 100);

    }

    private void start(Integer simulationSpeedInMiliseconds, Integer timeShift) {
        this.schedule.setSimulationSpeedInMilliseconds(simulationSpeedInMiliseconds);
        this.schedule.setTimeShiftInMilliseconds(timeShift);
        schedule.runTimer();
    }

    void addNewRandomPassengers(Integer amount) throws IOException {
        Integer salePointIndex;
        OpenCSVReader openCSVReader = new OpenCSVReader();
        List randomID = Helpers.getRandomNumbers(0, 300, amount);
        for (int i=0; i<amount; i++){
            String[] passengerData = openCSVReader.readCSV("passenger.csv", Integer.parseInt(randomID.get(i).toString()));
            Passenger passenger = new Passenger(passengerData[0]);
            if(passengerData[1].equals("0")){
                passenger.setBaggage(Integer.parseInt(passengerData[2]));
            }

            do {
                salePointIndex = Helpers.getRandomNumber(0, salePoints.get(0).getOpenSalePointIndex());
//                System.out.print("CZY TA KASA JEST OTWARTA: " + salePoints.get(salePointIndex).getName() + " ---> "+ salePoints.get(salePointIndex).getIsOpen() + "\n");
            }while(!salePoints.get(salePointIndex).getIsOpen());


//            System.out.print("TO INDEX " + salePointIndex + " A TO NAZWA: " + salePoints.get(salePointIndex).getName() + "\n");
            salePoints.get(salePointIndex).addPassenger(passenger);
//            System.out.print("dodaje pasazera do "+" o nazwie: " + passenger.getName() + " " + salePoints.get(salePointIndex).getName() + "\n");
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
            Ticket ticket = new Ticket(airplane.getFlightName());
            for(int ii=0; ii<airplane.getMaxPassenger(); ii++) {
                this.addTicket(ticket);
            }
            this.airplanes.add(airplane);
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
            SalePoint salePoint = new SalePoint("Punkt sprzedaży nr " + (i+1), queueSize);
            this.salePoints.add(salePoint);
        }
    }

    private void createControlPoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        for(int i=0; i<amount; i++) {
            Integer queueSize = Helpers.getRandomNumber(minAvailableQueue, maxAvailableQueue);
            ControlPoint controlPoint = new ControlPoint("Punkt kontrolny nr " + (i+1), queueSize);
            this.controlPoints.add(controlPoint);
        }
    }

    private void createBaggageControlPoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        for(int i=0; i<amount; i++) {
            System.out.print("otworzono baggagecontrolpoint nr " + i + "\n");
            Integer queueSize = Helpers.getRandomNumber(minAvailableQueue, maxAvailableQueue);
            BaggageControlPoint baggageControlPoint = new BaggageControlPoint("Punkt kontrolny bagażu nr " + (i+1), queueSize);
            this.baggageControlPoints.add(baggageControlPoint);
        }
    }

    private void openRandomSalePoints(Integer amount) {
        List randomIDs = Helpers.getRandomNumbers(0, salePoints.size()-1, amount);
        for(int i=0; i<amount; i++) {

            salePoints.get(Integer.parseInt(randomIDs.get(i).toString())).openPoint(getAvailableVendor(), this.schedule.getDate());
            System.out.print("otworzono salepoint nr " + i + " " + salePoints.get(Integer.parseInt(randomIDs.get(i).toString())).getName() + " "
                    + salePoints.get(Integer.parseInt(randomIDs.get(i).toString())).getIsOpen()+  "\n");

        }
    }

    private void openRandomControlPoints(Integer amount) {
        List randomIDs = Helpers.getRandomNumbers(0, controlPoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            controlPoints.get(Integer.parseInt(randomIDs.get(i).toString())).openPoint(getAvailableController(), this.schedule.getDate());
        }
    }

    private void openRandomBaggageControlPoints(Integer amount) {
        List randomIDs = Helpers.getRandomNumbers(0, controlPoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            baggageControlPoints.get(Integer.parseInt(randomIDs.get(i).toString())).openPoint(getAvailableController(), this.schedule.getDate());
        }
    }

    public Integer getAirplanesAmount() {
        return this.airplanes.size();
    }

    public Ticket getAvailableTicket() {
        Integer randomId = Helpers.getRandomNumber(0, allAvailableTickets.size()-1);
        Ticket ticket = this.allAvailableTickets.get(randomId);
        this.allAvailableTickets.remove(randomId);
        return ticket;
    }

    public Vendor getAvailableVendor() {
        Integer randomId = Helpers.getRandomNumber(0, allVendors.size()-1);
        Vendor vendor = this.allVendors.get(randomId);
        allVendors.remove(randomId);
        return vendor;
    }

    public Controller getAvailableController() {
        Integer randomId = Helpers.getRandomNumber(0, allControllers.size()-1);
        Controller controller = this.allControllers.get(randomId);
        allControllers.remove(randomId);
        return controller;
    }

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

    void checkDepartureTimes() {
        for(Airplane airplane : this.airplanes) {
            airplane.checkDepartureTime(schedule.getDate());
        }
    }

    void moveFromSalePoints() {

        int index;
        int howMany;

        for(SalePoint salePoint : salePoints)
        {

            if(salePoint.getIsOpen())
            {
//                System.out.print("PRODUKTYWNOSC PRACOWNIKA: " + salePoint.getEmployee().getEfficiency() + "\n");
                if(salePoint.getEmployee().getEfficiency() > salePoint.getPassangers().size())
                    howMany = salePoint.getPassangers().size();
                else
                    howMany = salePoint.getEmployee().getEfficiency();

                do{
                    index = Helpers.getRandomNumber(0,baggageControlPoints.size()-1);
//                    System.out.print("KONTROLA BAGAZU: " + baggageControlPoints.get(index).getName()+"\n");

                }while(!baggageControlPoints.get(index).getIsOpen());

                salePoint.movePassengersPoli(baggageControlPoints.get(index), howMany);
            }
        }

    }


    void moveFromBaggageControlPoints() throws NullPointerException {

        int index;
        int howMany;

        for(BaggageControlPoint baggageControlPoint : baggageControlPoints)
        {

            if(baggageControlPoint.getIsOpen())
            {

//                System.out.print("PRODUKTYWNOSC PRACOWNIKA: " + baggageControlPoint.getEmployee().getEfficiency() + "\n");
//                if(baggageControlPoint.getEmployee().getEfficiency() > baggageControlPoint.getPassangers().size())
                    howMany = baggageControlPoint.getPassangers().size();
//                else
//                    howMany = baggageControlPoint.getEmployee().getEfficiency();

                do{
                    index = Helpers.getRandomNumber(0, controlPoints.size()-1);

                }while(!controlPoints.get(index).getIsOpen());

                baggageControlPoint.movePassengersPoli(controlPoints.get(index), howMany);
            }
        }

    }

    void moveFromControlPoints() {


    }

    void MoveFromDutyFreeZone() {



    }

    void display() {

        for(SalePoint salePoint : salePoints)
            System.out.print("salePoints ppl amount: "+salePoint.getPassangers().size()+"\n");
        for(BaggageControlPoint baggageControlPoint : baggageControlPoints)
            System.out.print("baggageControlPoints ppl amount: "+baggageControlPoint.getPassangers().size()+"\n");
        for(ControlPoint controlPoint : controlPoints)
            System.out.print("controlPoints ppl amount: "+controlPoint.getPassangers().size()+"\n");
//        for(Controller controller : allControllers)
//            System.out.print("Kontrolerzy: " + controller.getName() + "\n");
        //System.out.print("Airplanes amount: " + getAirplanesAmount() + "\n");
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



