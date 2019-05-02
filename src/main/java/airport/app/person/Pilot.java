package main.java.airport.app.person;

import main.java.airport.app.airplane.Airplane;

public class Pilot extends Person {
    private Airplane airplane;

    public Pilot(String name, String status)
    {
        super(name,status);
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
