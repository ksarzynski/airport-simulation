package main.java.airport.simulation;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.*;

import java.util.ArrayList;

public class Simulation {
    private Schedule schedule;
    private ArrayList<Ticket> allAvailableTickets;
    private ArrayList<Vendor> allVendors;
    private ArrayList<Controller> allControllers;

    public void start() {

        initialization(3,10,3, 3,10,6);
        run(500, 10);
    }

    private void initialization(Integer sellPointsAmout, Integer vendorsAmount, Integer controlPointsAmount, Integer baggageControlPointsAmount, Integer controllersAmount, Integer flightsAmount) {
        // TODO: metoda
    }

    private void run(Integer simulationSpeedInMiliseconds, Integer timeShift) {
        this.schedule = new Schedule(simulationSpeedInMiliseconds, timeShift);
        schedule.runTimer();
    }

    private ArrayList<Passenger> addNewRandomPassangers(Integer amount) {
        // TODO: metoda
        return new ArrayList<>();
    }

    private ArrayList<Vendor> addNewRandomVendor(Integer amount) {
        // TODO: metoda
        return new ArrayList<>();
    }

    private ArrayList<Pilot> addNewRandomPilot(Integer amount) {
        // TODO: metoda
        return new ArrayList<>();
    }

    private ArrayList<Airplane> addNewRandomAirplane(Integer amount) {
        // TODO: metoda
        return new ArrayList<>();
    }

    private ArrayList<Controller> addNewRandomController(Integer amount) {
        // TODO: metoda
        return new ArrayList<>();
    }

    public Ticket getAvailableTicket() {
        // TODO: metoda
        return allAvailableTickets.get(1);
    }

    public Vendor getAvailableVendor() {
        // TODO: metoda
        return allVendors.get(1);
    }

    public Controller getAvailableController() {
        // TODO: metoda
        return allControllers.get(1);
    }

    public void addVendor(Vendor vendor) {
        // TODO: metoda
    }

    public void addController(Controller controller) {
        // TODO: metoda
    }
}
