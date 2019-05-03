package main.java.airport.app.belongings;

public class Baggage extends Belongings {
    private Double weight;

    public Baggage(Double weight)
    {
        this.status = "not boarded";
        this.weight = weight;
    }
}
