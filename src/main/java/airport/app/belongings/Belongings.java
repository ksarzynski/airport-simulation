package main.java.airport.app.belongings;

import main.java.airport.app.person.Person;

abstract class Belongings {
    private Person person;
    protected String status;

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return this.status;
    }
}
