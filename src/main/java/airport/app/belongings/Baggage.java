package main.java.airport.app.belongings;

import java.util.Random;

public class Baggage {
    private Integer weight;
    private Integer dangerLevel;
    private Ticket ticket;
    private String status;

    public Baggage(Integer weight, Ticket ticket) {
        this.weight = weight;
        this.ticket = ticket;
        this.status = "not boarded";
        setRandomDangerLevel(1000);
    }

    public Baggage(Integer weight) {
        this.weight = weight;
        this.status = "not boarded";
        setRandomDangerLevel(1000);
    }

    public Integer getWeight() {
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
        if(a==0)
            this.dangerLevel = 2;
        else if(a % accuracy/250 == 0)
            this.dangerLevel = 1;
        else this.dangerLevel = 0;
    }
}
