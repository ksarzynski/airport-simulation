package main.java.airport.app.person;

public class Vendor extends Employee
{

    public Vendor(String name, Integer efficiency) {
        super(name, efficiency);
    }

    @Override
    public String toString()
    {
        return getName();
    }
}
