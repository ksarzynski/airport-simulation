package main.java.airport.app.person;

public class Pilot extends Person {
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
