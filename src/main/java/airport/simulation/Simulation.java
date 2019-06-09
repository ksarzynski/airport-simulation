package airport.simulation;

import airport.app.airplane.Airplane;
import airport.app.belongings.Ticket;
import airport.app.person.*;
import airport.app.place.*;

import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;

/**
 * W tej klasie trzymane sa instancje innych obiektow, na zmiennych w niej zawarte sa przeprowadzane operacje
 */
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

    /**
     * metoda sluzy do wyboru wartosci poczatkowych danych parametrow
     * @param salePointsAmount poczatkowa ilosc kas
     * @param vendorsAmount poczatkowa ilosc sprzedawcow
     * @param openSalePointsAmount poczatkowa ilosc otwartych kas
     * @param controlPointsAmount poczatkowa ilosc punktow kontrolnych
     * @param openControlPointsAmount poczatkowa ilosc otwartych punktow kontrolnych
     * @param controllersAmount poczatkowa ilosc kontrolerow
     * @param baggageControlPointsAmount poczatkowa ilosc punktow kontroli bagazu
     * @param openBaggageControlPointsAmount poczatkowa ilosc otwartych punktow kontroli bagazu
     * @param flightsAmount poczatkowa ilosc samolotow
     * @param simulationSpeedInMiliseconds poczatkowa predkosc cyklu symulacji w czasie rzeczywistym
     * @param timeShift poczatkowa dlugosc cyklu symulacji
     * @throws IOException wyjatek
     */
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

    /**
     * metoda inicjalizuje wybrane obiekty
     * @param salePointsAmount ilosc kas do inicjalizowania
     * @param vendorsAmount ilosc sprzedawcow do inicjalizowania
     * @param openSalePointsAmount ilosc otwartych kas do inicjalizowania
     * @param controlPointsAmount ilosc punktow kontrolnych do inicjalizowania
     * @param openControlPointsAmount ilosc otwartych punktow kontrolnych do inicjalizowania
     * @param controllersAmount ilosc kontrolerow do inicjalizowania
     * @param baggageControlPointsAmount ilosc punktow kontroli bagazu do incijalizowania
     * @param openBaggageControlPointsAmount ilosc otwartych punktow kontroli bagazu do incijalizowania
     * @param flightsAmount ilsoc lotow do inicjalizowania
     * @throws IOException wyjatek
     */
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
        createSalePoints(salePointsAmount, 10, 500);
        openRandomSalePoints(openSalePointsAmount);
        addNewRandomControllers(controllersAmount);
        createControlPoints(controlPointsAmount, 10, 500);
        openRandomControlPoints(openControlPointsAmount);
        createBaggageControlPoints(baggageControlPointsAmount, 10, 500);
        openRandomBaggageControlPoints(openBaggageControlPointsAmount);
        addNewRandomAirplanes(flightsAmount);
        this.dutyFreeZone = new DutyFreeZone("Strefa bezclowa", 10000, 100);
    }

    /**
     * funkcja wywolywana w momencie wcisniecia stop na gui, zeruje wartosci poprzedniej symulacji
     */
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

    /**
     * funkcja przeprowadzajaca start symulacji
     * @param simulationSpeedInMiliseconds uplyw czasu w czasie rzeczywistym
     * @param timeShift uplyw czasu w symulacji
     */
    private void start(Integer simulationSpeedInMiliseconds, Integer timeShift) {
        this.schedule.setSimulationSpeedInMilliseconds(simulationSpeedInMiliseconds);
        this.schedule.setTimeShiftInMilliseconds(timeShift);
        schedule.runTimer();
    }

    /**
     * metoda sluzy do wywolywania zdarzen w przypadku zmiany wartosci zmiennych
     * @return zwraca zmiane
     */
    public PropertyChangeSupport getPropertyChangeSupport() {
        return propertyChangeSupport;
    }

    /**
     * funkcja sluzy do dodawania do symulacji a konkretnie do kas nowych psazerow i do przypisywania im bagazu
     * funkcja zczytuje dane z csv
     * @param amount ilosc dodawanych pasazerow
     * @throws IOException wyjatek
     */
    public void addNewRandomPassengers(Integer amount) throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        List randomID = Helpers.getRandomNumbers(0, 300, amount);
        for (int i=0; i<amount; i++){
            String[] passengerData = openCSVReader.readCSV("passenger.csv", Integer.parseInt(randomID.get(i).toString()));
            Passenger passenger = new Passenger(passengerData[1]);
            if(passengerData[2].equals("0")){
                passenger.setBaggage(Integer.parseInt(passengerData[3]));
            }

            List randomNumbers = Helpers.getMixedNumbers(0, salePoints.size()-1);
            boolean added = false;
            for(int ii=0; ii<salePoints.size()-1; ii++) {
                Integer index = (Integer)randomNumbers.get(ii);
                if(salePoints.get(index).getIsOpen()){
                    if( !salePoints.get(index).isPlaceFull() ) {
                        SalePoint salePoint = salePoints.get(index);
                        salePoint.addPassenger(passenger);
                        getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
                        added = true;
                        break;
                    }
                }
            }

            if(!added){
                SalePoint salePoint = openClosedSalePoints();
                if(salePoint != null) {
                    salePoint.addPassenger(passenger);
                    getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
                }
            }
        }
    }

    /**
     * funkcja tworzy nowych sprzedawcow za pomoca csv
     * @param amount ilosc dodawanych vendorow
     * @throws IOException wyjatek
     */
    private void addNewRandomVendors(Integer amount) throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        List randomID = Helpers.getRandomNumbers(0, 99, amount);
        for (int i=0; i<amount; i++){
            String[] vendorData = openCSVReader.readCSV("vendors.csv", Integer.parseInt(randomID.get(i).toString()));
            Vendor vendor = new Vendor(vendorData[1], Integer.parseInt(vendorData[2]));
            this.allVendors.add(vendor);
        }
    }

    /**
     * funkcja dodaje losowego pilota z danymi z plikow csv
     * @return pilot
     * @throws IOException wyjatek
     */
    private Pilot addNewRandomPilot() throws IOException {
        OpenCSVReader openCSVReader = new OpenCSVReader();
        Integer randomID = Helpers.getRandomNumber(0, 99);
        String[] pilotData = openCSVReader.readCSV("pilots.csv", randomID);
        return new Pilot(pilotData[1]);
    }

    /**
     * Funkcja sluzy tworzeniu lotow
     * @param amount ilosc dodawanych samolotow
     * @throws IOException wyjatek
     */
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

    /**
     * funkcja tworzy losowych kontrolerow korzystajac z plikow csv
     * @param amount ilosc tworzonych kontrolerow
     * @throws IOException wyjatek
     */
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

    /**
     * funkcja tworzy kasy i przypisuje potrzebne zmienne
     * @param amount ilosc kas do stworzenia
     * @param minAvailableQueue minimalny rozmiar kolejki
     * @param maxAvailableQueue maksymalny rozmiar kolejki
     */
    private void createSalePoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        for(int i=0; i<amount; i++) {
            Integer queueSize = Helpers.getRandomNumber(minAvailableQueue, maxAvailableQueue);
            SalePoint salePoint = new SalePoint("P sprzedazy nr " + (i+1), queueSize);
            this.salePoints.add(salePoint);
            getPropertyChangeSupport().firePropertyChange(SALEPOINTS, null, salePoint);
        }
    }

    /**
     * funkcja tworzy punkty kontroli i przypisuje potrzebne zmienne
     * @param amount ilosc punktow kontroli do stworzenia
     * @param minAvailableQueue minimalny rozmiar kolejki
     * @param maxAvailableQueue maksymalny rozmiar kolejki
     */
    private void createControlPoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        for(int i=0; i<amount; i++) {
            Integer queueSize = Helpers.getRandomNumber(minAvailableQueue, maxAvailableQueue);
            ControlPoint controlPoint = new ControlPoint("P kontrolny nr " + (i+1), queueSize);
            this.controlPoints.add(controlPoint);
            getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, null, controlPoint);
        }
    }

    /**
     * funkcja tworzy punkty kontroli bagazu i przypisuje potrzebne zmienne
     * @param amount ilosc punktow kontroli bagazu do stworzenia
     * @param minAvailableQueue minimalny rozmiar kolejki
     * @param maxAvailableQueue maksymalny rozmiar kolejki
     */
    private void createBaggageControlPoints(Integer amount, Integer minAvailableQueue, Integer maxAvailableQueue) {
        for(int i=0; i<amount; i++) {
            Integer queueSize = Helpers.getRandomNumber(minAvailableQueue, maxAvailableQueue);
            BaggageControlPoint baggageControlPoint = new BaggageControlPoint("P kontrolny bagazu nr " + (i+1), queueSize);
            this.baggageControlPoints.add(baggageControlPoint);
            getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, null, baggageControlPoint);
        }
    }

    /**
     * otwiera losowe kasy przypisuje czas i pracownika na poczatku symulacji
     * @param amount ilsoc kas do otworzenia
     */
    private void openRandomSalePoints(Integer amount) {
        List randomIDs = Helpers.getRandomNumbers(0, salePoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            SalePoint salePoint = salePoints.get(Integer.parseInt(randomIDs.get(i).toString()));
            salePoint.openPoint(getAvailableVendor(), this.schedule.getDate());
            getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
        }
    }

    /**
     * otwiera losowe kasy przypisuje czas i pracownika
     * @return zwraca otwarta kase
     */
    public SalePoint openClosedSalePoints() {
        for(SalePoint salePoint : salePoints){
            if(!salePoint.getIsOpen()){
                salePoint.openPoint(getAvailableVendor(), this.schedule.getDate());
                getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
                return salePoint;
            }
        }
        return null;
    }

    /**
     * otwiera losowe punkty kontroli przypisuje czas i pracownika na poczatku symulacji
     * @param amount ilsoc punktow kontroli do otworzenia
     */
    private void openRandomControlPoints(Integer amount) {
        List randomIDs = Helpers.getRandomNumbers(0, controlPoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            ControlPoint controlPoint = controlPoints.get(Integer.parseInt(randomIDs.get(i).toString()));
            controlPoint.openPoint(getAvailableController(), this.schedule.getDate());
            getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoint);
        }
    }

    /**
     * otwiera punkty kontroli, przypisuje czas i pracownika
     * @return zwraca otwarty punkt kontroli
     */
    public ControlPoint openClosedControlPoints() {
        for(ControlPoint controlPoint : controlPoints){
            if(!controlPoint.getIsOpen()){
                controlPoint.openPoint(getAvailableController(), this.schedule.getDate());
                getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoint);
                return controlPoint;
            }
        }
        return null;
    }

    /**
     * otwiera losowe punkty kontroli bagazu na poczatku symulacji
     * @param amount ilosc punktow do otworzenia
     */
    private void openRandomBaggageControlPoints(Integer amount) {
        List randomIDs = Helpers.getRandomNumbers(0, controlPoints.size()-1, amount);
        for(int i=0; i<amount; i++) {
            BaggageControlPoint baggageControlPoint = baggageControlPoints.get(Integer.parseInt(randomIDs.get(i).toString()));
            baggageControlPoint.openPoint(getAvailableController(), this.schedule.getDate());
            getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoint);
        }
    }

    /**
     * funkcja otwiera punkty kontroli bagazu w czasie symulacji
     * @return zwraca otwarty punkt kontroli bagazu
     */
    public BaggageControlPoint openClosedBaggageControlPoints() {
        for(BaggageControlPoint baggageControlPoint : baggageControlPoints){
            if(!baggageControlPoint.getIsOpen()){
                baggageControlPoint.openPoint(getAvailableController(), this.schedule.getDate());
                getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoint);
                return baggageControlPoint;
            }
        }
        return null;
    }

    /**
     * funkcja pobierajaca dostepny bilet z wykorzystaniem csv
     * @return zwraca bilet
     */
    private Ticket getAvailableTicket() {
        if(allAvailableTickets.size() == 0)
            return null;

        Integer randomId = Helpers.getRandomNumber(0, allAvailableTickets.size()-1);
        Ticket ticket = this.allAvailableTickets.get(randomId);
        ticket.bought();
        this.allAvailableTickets.remove(ticket);
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

    public ArrayList<BaggageControlPoint> getBaggageControlPoints() {
        return baggageControlPoints;
    }

    private void returnVendorToPool(Vendor vendor){
        this.allVendors.add(vendor);
    }

    private void returnControllerToPool(Controller controller){
        this.allControllers.add(controller);
    }

    /**
     *
     */
    void checkWorkingHours() {
        for (SalePoint salePoint : this.salePoints) {
            Vendor vendor = salePoint.checkWorkingHour(schedule.getDate());
            if (vendor != null) {
                ArrayList<Passenger> passengers = new ArrayList<Passenger>(salePoint.closePoint());

                List randomNumbers = Helpers.getMixedNumbers(0, salePoints.size()-1);
                Boolean added = false;
                for(int ii=0; ii<salePoints.size()-1; ii++) {
                    Integer index = (Integer)randomNumbers.get(ii);
                    if(salePoints.get(index).getIsOpen()){
                        if( !salePoints.get(index).isPlaceFull() ) {
                            SalePoint freeSalePoint = salePoints.get(index);
                            for(Passenger passenger : passengers) {
                                freeSalePoint.addPassenger(passenger);
                            }
                            getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", freeSalePoint);
                            added = true;
                            break;
                        }
                    }
                }

                if(!added){
                    SalePoint freeSalePoint = openClosedSalePoints();
                    if(freeSalePoint != null) {
                        for(Passenger passenger : passengers) {
                            freeSalePoint.addPassenger(passenger);
                        }
                        getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", freeSalePoint);
                    }
                }
                returnVendorToPool(vendor);
                getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
            } else if( salePoint.getIsOpen() && !salePoint.getSuccessor() && schedule.getDate().after(new Date(salePoint.getShiftEndTime().getTime() - (60 * 60 * 1000))) ){
                openClosedSalePoints();
                salePoint.setSuccessor(true);
            }
            if( salePoint.getOpenSalePointIndex() == 0 ) {
                openClosedSalePoints();
            }
        }

        for (ControlPoint controlPoint : this.controlPoints) {
            Controller controller = controlPoint.checkWorkingHour(schedule.getDate());
            if (controller != null) {
                ArrayList<Passenger> passengers = new ArrayList<Passenger>(controlPoint.closePoint());

                List randomNumbers = Helpers.getMixedNumbers(0, controlPoints.size()-1);
                boolean added = false;
                for(int ii=0; ii<controlPoints.size()-1; ii++) {
                    Integer index = (Integer)randomNumbers.get(ii);
                    if(controlPoints.get(index).getIsOpen()){
                        if( !controlPoints.get(index).isPlaceFull() ) {
                            ControlPoint freeControlPoint = controlPoints.get(index);
                            for(Passenger passenger : passengers) {
                                freeControlPoint.addPassenger(passenger);
                            }
                            getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", freeControlPoint);
                            added = true;
                            break;
                        }
                    }
                }

                if(!added){
                    ControlPoint freeControlPoint = openClosedControlPoints();
                    if(freeControlPoint != null) {
                        for(Passenger passenger : passengers) {
                            freeControlPoint.addPassenger(passenger);
                        }
                        getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", freeControlPoint);
                    }
                }
                returnControllerToPool(controller);
                getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoint);
            } else if( controlPoint.getIsOpen() && !controlPoint.getSuccessor() && schedule.getDate().after(new Date(controlPoint.getShiftEndTime().getTime() - (60 * 60 * 1000))) ){
                openClosedControlPoints();
                controlPoint.setSuccessor(true);
            }
            if( controlPoint.getOpenControlPointIndex() == 0 ) {
                openClosedControlPoints();
            }
        }

        for (BaggageControlPoint baggageControlPoint : this.baggageControlPoints) {
            Controller controller = baggageControlPoint.checkWorkingHour(schedule.getDate());
            if (controller != null) {
                ArrayList<Passenger> passengers = new ArrayList(baggageControlPoint.closePoint());

                List randomNumbers = Helpers.getMixedNumbers(0, baggageControlPoints.size()-1);
                boolean added = false;
                for(int ii=0; ii<baggageControlPoints.size()-1; ii++) {
                    Integer index = (Integer)randomNumbers.get(ii);
                    if(baggageControlPoints.get(index).getIsOpen()){
                        if( !baggageControlPoints.get(index).isPlaceFull() ) {
                            BaggageControlPoint freeBaggageControlPoint = baggageControlPoints.get(index);
                            for(Passenger passenger : passengers) {
                                freeBaggageControlPoint.addPassenger(passenger);
                            }
                            getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", freeBaggageControlPoint);
                            added = true;
                            break;
                        }
                    }
                }

                if(!added){
                    BaggageControlPoint freeBaggageControlPoint = openClosedBaggageControlPoints();
                    if(freeBaggageControlPoint != null) {
                        for(Passenger passenger : passengers) {
                            freeBaggageControlPoint.addPassenger(passenger);
                        }
                        getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", freeBaggageControlPoint);
                    }
                }
                returnControllerToPool(controller);
                getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoint);
            } else if( baggageControlPoint.getIsOpen() && !baggageControlPoint.getSuccessor() && schedule.getDate().after(new Date(baggageControlPoint.getShiftEndTime().getTime() - (60 * 60 * 1000))) ){
                openClosedBaggageControlPoints();
                baggageControlPoint.setSuccessor(true);
            }
            if( baggageControlPoint.getOpenBaggageControlPointIndex() == 0 ) {
                openClosedBaggageControlPoints();
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

    /**
     * funkcja sluzy do przemieszczania ludzi z kas do punktow kontroli bagazow, przypisaniu biletow i kontroli bledow
     */
    void moveFromSalePoints() {
        int howMany;

        for(SalePoint salePoint : salePoints)
        {
            if(salePoint.getIsOpen())
            {
                if(salePoint.isPlaceFull())
                    openClosedSalePoints();

                if(salePoint.getEmployee().getEfficiency() > salePoint.getPassengers().size())
                    howMany = salePoint.getPassengers().size();
                else
                    howMany = salePoint.getEmployee().getEfficiency();

                List randomNumbers = Helpers.getMixedNumbers(0, baggageControlPoints.size()-1);
                boolean added = false;
                for(int ii=0; ii<baggageControlPoints.size(); ii++) {
                    Integer index =(Integer)randomNumbers.get(ii);
                    if(baggageControlPoints.get(index).getIsOpen()){
                        if( !baggageControlPoints.get(index).isPlaceFull() ) {
                            BaggageControlPoint baggageControlPoint = baggageControlPoints.get(index);
                            ArrayList<Ticket> tickets = new ArrayList<>();
                            for(int i=0; i<howMany; i++) {
                                Ticket ticket = getAvailableTicket();
                                if(ticket == null)
                                    return;
                                tickets.add(ticket);
                            }
                            salePoint.movePassengers(baggageControlPoint, howMany, tickets);
                            getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
                            getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoint);
                            added = true;
                            break;
                        }
                    }
                }

                if(!added){
                    BaggageControlPoint baggageControlPoint = openClosedBaggageControlPoints();
                    if(baggageControlPoint != null) {
                        ArrayList<Ticket> tickets = new ArrayList<>();
                        for(int i=0; i<howMany; i++) {
                            tickets.add(getAvailableTicket());
                        }
                        salePoint.movePassengers(baggageControlPoint, howMany, tickets);
                        getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoint);
                    }
                }
                getPropertyChangeSupport().firePropertyChange(SALEPOINTS, "update", salePoint);
            }
        }
    }

    /**
     * funkcja sluzy do przemieszczania ludzi z punktow kontroli bagazow do punktow kontroli i sprawdzeniu bagazy i kontroli bledow
     * @throws NullPointerException wyjatek
     */
    void moveFromBaggageControlPoints() throws NullPointerException {
        int howMany;

        for(BaggageControlPoint baggageControlPoint : baggageControlPoints)
        {
            if(baggageControlPoint.isPlaceFull())
                openClosedBaggageControlPoints();

            if(baggageControlPoint.getIsOpen())
            {

                baggageControlPoint.checkBaggage(10);

                    howMany = baggageControlPoint.getPassengers().size();

                List randomNumbers = Helpers.getMixedNumbers(0, controlPoints.size()-1);
                boolean added = false;
                for(int ii=0; ii<controlPoints.size(); ii++) {
                    Integer index =(Integer)randomNumbers.get(ii);
                    if(controlPoints.get(index).getIsOpen()){
                        if( !controlPoints.get(index).isPlaceFull() ) {
                            ControlPoint controlPoint = controlPoints.get(index);
                            baggageControlPoint.movePassengersPoli(controlPoint, howMany);
                            getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoint);
                            added = true;
                            break;
                        }
                    }
                }

                if(!added){
                    ControlPoint controlPoint = openClosedControlPoints();
                    if(controlPoint != null){
                        baggageControlPoint.movePassengersPoli(controlPoint, howMany);
                        getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoint);
                    }
                }
                getPropertyChangeSupport().firePropertyChange(BAGGAGECONTROLPOINTS, "update", baggageControlPoint);
            }
        }
    }

    /**
     * funkcja sluzy do przemieszczenia ludzi z punktow kontroli do strefy bezclowej oraz do kontroli bledow
     */
    void moveFromControlPoints() {
        int howMany;

        for(ControlPoint controlPoint : controlPoints)
        {
            if(controlPoint.isPlaceFull())
                openClosedControlPoints();

            if(controlPoint.getIsOpen())
            {
                if(controlPoint.getControllersEfficiency() > controlPoint.getPassengers().size())
                    howMany = controlPoint.getPassengers().size();
                else
                    howMany = controlPoint.getControllersEfficiency();

                controlPoint.movePassengersPoli(dutyFreeZone, howMany);
                getPropertyChangeSupport().firePropertyChange(CONTROLPOINTS, "update", controlPoint);
                getPropertyChangeSupport().firePropertyChange(DUTYFREEZONE, "update", dutyFreeZone);
            }
        }
    }

    void moveFromDutyFreeZone() {
        dutyFreeZone.movePassengersPoli(airplanes, dutyFreeZone.getFlow());
    }

    void display() {}

    void updateGUIClock() {
        getPropertyChangeSupport().firePropertyChange(CLOCK, "update", schedule.getTime());
    }
}



