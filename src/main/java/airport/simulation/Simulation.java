package main.java.airport.simulation;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.*;
import main.java.airport.app.place.ControlPoint;
import main.java.airport.app.place.SalePoint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Simulation {
    private Schedule schedule;
    private ArrayList<Ticket> allAvailableTickets;
    private ArrayList<Vendor> allVendors;
    private ArrayList<Controller> allControllers;

    public void start() {

        initialization(3,10,3, 3,10,6);
        run(500, 10);
    }

    // narazie nie wiemy jak bedziemy obliczac efektywnosc wiec zaloze ze kazda kasa itd obsluguje 50 ludzi na godzine
    // i efficiency to dajmy na to od 20 do 120 % tej wartosci?

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

    public void addVendor(Vendor vendor) {
        // TODO: metoda
    }

    public void addController(Controller controller) {
        // TODO: metoda
    }
}
