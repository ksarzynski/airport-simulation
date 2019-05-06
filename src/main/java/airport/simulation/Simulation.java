package main.java.airport.simulation;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.belongings.Baggage;
import main.java.airport.app.belongings.Ticket;
import main.java.airport.app.person.*;
import main.java.airport.app.place.ControlPoint;
import main.java.airport.app.place.DutyFreeZone;
import main.java.airport.app.place.SalePoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Simulation {

    private ArrayList<Ticket> tickets;
    private ArrayList<Pilot> pilots;
    private ArrayList<Airplane> airplanes;
    private ArrayList<Controller> controllers;
    private ArrayList<Vendor> vendors;
    private ArrayList<Passanger> passangers;
    private ArrayList<Baggage> baggages;

    private DutyFreeZone airportEntry;
    private SalePoint ticketOffice;
    private ControlPoint baggageCheck;
    private ControlPoint securityCheck;
    private DutyFreeZone dutyFreeZone;
    private ControlPoint boarding;

    private Clock clock;

    public void start() {
        this.clock = new Clock();
        clock.runTimer();
        initialization(10000, 1000, 5000, 20, 20,  100.0);
        run(500);
    }

    //TODO przeniesienie parametrow stad ( ze startu ) do maina zeby przy wlaczeniu mozna je bylo latwo zmieniac

    private void initialization(int howManyPeople, int entrySize, int howManyAirplanes, int howManyControllers, int howManyVendors,
                               Double employeesEfficiencyLevel)
    {
        int ticketsAmount;
        int maxPassangers;
        double maxBaggageWeight;

        ArrayList<Ticket> tickets = new ArrayList <> ();

        DutyFreeZone airportEntry = new DutyFreeZone("airport entry", entrySize/1, 1000);
        SalePoint ticketOffice = new SalePoint("ticket office", entrySize/5);
        ControlPoint baggageCheck = new ControlPoint("baggage check", entrySize/5);
        ControlPoint securityCheck = new ControlPoint("security check", entrySize/5);
        DutyFreeZone dutyFreeZone = new DutyFreeZone("duty free zone", entrySize/1, 1000);
        ControlPoint boarding = new ControlPoint("boarding", entrySize/5);

        //TODO trzeba sie zastanowic czy kazdy lot bedzie mial swoj boarding czy wrzucamy wszystkie do jednego obiektu

        ArrayList<Pilot> pilots = new ArrayList<> (howManyAirplanes/1);
        for(int i=0; i < howManyAirplanes ; i++)
        {
            pilots.add(i, new Pilot ("pilot name", "not working"));
        }

        ArrayList<Airplane> airplanes = new ArrayList<> (howManyAirplanes/1);
        for(int i=0; i < howManyAirplanes ; i++)
        {
            maxPassangers = new Random().nextInt(150) + 100;

            maxBaggageWeight = new Random().nextDouble() * 2 * maxPassangers * 20;

            airplanes.add(i, new Airplane ("flight name", "destination", maxPassangers/1, maxBaggageWeight/1, pilots.get(i)));

            ticketsAmount = maxPassangers;

            for(int j=0; j<ticketsAmount; j++)
            {
                tickets.add(new Ticket ("1"));
            }
        }

        ArrayList<Controller> controllers = new ArrayList<> (howManyControllers/1);
        for(int i=0; i < howManyControllers ; i++)
        {
            controllers.add(i, new Controller ("controller name", "not working",250.0 * employeesEfficiencyLevel, new Date(), new Date(),
                    new ControlPoint("test", 0)));
        }

        ArrayList<Vendor> vendors = new ArrayList<> (howManyVendors/1);
        for(int i=0; i < howManyVendors ; i++)
        {
            vendors.add(i, new Vendor ("vendor name", "not working",250.0 * employeesEfficiencyLevel, new Date(), new Date(),
                    new SalePoint("test", 0)));
        }

        ArrayList<Passanger> passangers = new ArrayList<> (howManyPeople/1);
        ArrayList<Baggage> baggages = new ArrayList<> (howManyPeople/1);
        for(int i=0; i < howManyPeople; i++)
        {
            passangers.add(new Passanger("name", "status"));
            baggages.add(new Baggage(new Random().nextDouble() * 20));
            passangers.get(i).setBaggage(baggages.get((i)));
            passangers.get(i).setRandomTicket(tickets);
        }

        ticketOffice.addRandomVendor(vendors);
        baggageCheck.addRandomController(controllers);
        securityCheck.addRandomController(controllers);
        boarding.addRandomController(controllers);

        airportEntry.addPasangers(passangers);

        //TODO zastanowic sie jak to inicjalizowac, co wpisywac w name itd

        //TODO wstawienie jakiegos przelicznika trudnosci

    }

    private void run(int howManyPeoplePerHour)
    {
        //TODO WAZME!!!!!!!!!!!!! musze ogarnac indexy tutaj

        int index;

        ArrayList<Passanger> passangersToMove = new ArrayList<>();

        for(int i = 0; i < howManyPeoplePerHour; i++)
        {
            passangers.add(new Passanger("passenger name", "status"));
        }

        passangersToMove.addAll(airportEntry.getPassangers(airportEntry.getFlow()));
        index = airportEntry.getFlow() + 1;

        ticketOffice.addPasangers(passangersToMove);

        passangersToMove.clear();
        index = 0;

        ticketOffice.getPassangers((int) (ticketOffice.getVendorsEfficiency() * ticketOffice.getSize()) ).addAll(passangersToMove);

        securityCheck.addPasangers(passangersToMove, (int)(securityCheck.getControllerEfficiency() * securityCheck.getSize()), 0);



    }

}
