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

public class Simulation {
    private Clock clock;

    public void start() {
        this.clock = new Clock();
        clock.runTimer();
    }

    public void initialization(int howManyPeople, int howManyPeoplePerHour, int entrySize, int howManyAirplanes, int howManyControllers, int howManyVendors,
                               Double employeesEfficiencyLevel)
    {
        int ticketsAmount;
        ArrayList<Ticket> tickets = new ArrayList <> ();


        DutyFreeZone airportEntry = new DutyFreeZone("airport entry", entrySize/1);
        SalePoint ticketOffice = new SalePoint("ticket office", entrySize/5);
        ControlPoint baggageCheck = new ControlPoint("baggage check", entrySize/5);
        ControlPoint securityCheck = new ControlPoint("security check", entrySize/5);
        DutyFreeZone dutyFreeZone = new DutyFreeZone("duty free zone", entrySize/1);
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
            airplanes.add(i, new Airplane ("flight name", "destination", 200, 20000.0, pilots.get(i)));

            ticketsAmount = (int)(Math.random()*((200-1)+1))+1;

            for(int j=0; j<ticketsAmount; j++)
            {
                tickets.add(new Ticket ("1"));
            }
        }

        ArrayList<Controller> controllers = new ArrayList<> (howManyControllers/1);
        for(int i=0; i < howManyControllers ; i++)
        {
            controllers.add(i, new Controller ("controller name", "not working",500.0, new Date(), new Date()));
        }

        ArrayList<Vendor> vendors = new ArrayList<> (howManyVendors/1);
        for(int i=0; i < howManyVendors ; i++)
        {
            vendors.add(i, new Vendor ("vendor name", "not working",500.0, new Date(), new Date()));
        }

        ArrayList<Passanger> passangers = new ArrayList<> (howManyPeople/1);
        ArrayList<Baggage> baggages = new ArrayList<> (howManyPeople/1);
        for(int i=0; i < howManyPeople; i++)
        {
            passangers.add(new Passanger("name", "status"));
            baggages.add(new Baggage(Math.random()*((30-1)+1)+1));
            passangers.get(i).setBaggage(baggages.get((i)));
            passangers.get(i).setRandomTicket(tickets);
        }

        ticketOffice.addRandomVendor(vendors);
        baggageCheck.addRandomController(controllers);
        securityCheck.addRandomController(controllers);
        boarding.addRandomController(controllers);

        //TODO zastanowic sie jak to inicjalizowac, co wpisywac w name itd

    }

}
