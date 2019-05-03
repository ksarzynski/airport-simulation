package main.java.airport.app.belongings;

public class Ticket extends Belongings {
    private String flightName;

    public Ticket(String flightname)
    {
        this.flightName = flightname;
        this.status = "unsold";
    }
}
