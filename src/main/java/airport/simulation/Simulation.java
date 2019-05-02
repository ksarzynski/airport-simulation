package main.java.airport.simulation;

import main.java.airport.app.airplane.Airplane;
import main.java.airport.app.person.Employee;
import main.java.airport.app.person.Pilot;
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

    public void init(int entrySize, int howManyPilots, int howManyAirplanes, int howManyEmployees, Double employeesEfficiency)
    {
        DutyFreeZone airportEntry = new DutyFreeZone("airport entry", entrySize/1);
        SalePoint ticketOffice = new SalePoint("ticket office", entrySize/5);
        ControlPoint baggageCheck = new ControlPoint("baggage check", entrySize/5);
        ControlPoint securityCheck = new ControlPoint("security check", entrySize/5);
        DutyFreeZone dutyFreeZone = new DutyFreeZone("dury free zone", entrySize/1);
        ControlPoint boarding = new ControlPoint("boarding", entrySize/5);

        //TODO trzeba sie zastanowic czy kazdy lot bedzie mial swoj boarding czy wrzucamy wszystkie do jednego obiektu

        ArrayList<Pilot> pilots = new ArrayList<> (howManyPilots/1);
        for(int i=0; i < howManyPilots ; i++)
        {
            pilots.add(i, new Pilot ("name", "status"));
        }

        ArrayList<Airplane> airplanes = new ArrayList<> (howManyAirplanes/1);
        for(int i=0; i < howManyAirplanes ; i++)
        {
            airplanes.add(i, new Airplane ("1", "kierunek", 200, 20000.0, pilots.get(i)));
        }

        ArrayList<Employee> employees = new ArrayList<> (howManyEmployees/1);
        for(int i=0; i < howManyEmployees ; i++)
        {
            employees.add(i, new Employee ("name", "status",500.0, new Date(), new Date()));
        }

        //TODO zastanowic sie jak to inicjalizowac, co wpisywac w name itd

    }

}
