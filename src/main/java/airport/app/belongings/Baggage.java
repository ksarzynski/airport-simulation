package main.java.airport.app.belongings;

import java.util.Random;

public class Baggage {
    private Double weight;
    private Integer dangerLevel;
    private Ticket ticket;
    private String status;

    public Baggage(Double weight, Ticket ticket) {
        this.weight = weight;
        this.ticket = ticket;
        setRandomDangerLevel(100);
    }

    public Baggage(Double weight) {
        this.weight = weight;
        setRandomDangerLevel(100);

    }

    public Double getWeight() {
        return weight;
    }

    public Integer getDangerLevel() {
        return dangerLevel;
    }

    public Ticket getTicket() { return ticket; }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }

    private void setRandomDangerLevel(Integer accuracy)
    {
        Random r = new Random();
        int a = r.nextInt(accuracy);
        if(a==accuracy/2)
            this.dangerLevel = 2;
        else if(a % 2 == 0)
            this.dangerLevel = 0;
        else this.dangerLevel = 1;
    }
}
