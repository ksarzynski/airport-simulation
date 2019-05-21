package main.java.airport.app.belongings;

public class Baggage {
    private Double weight;
    private Integer dangerLevel;
    private Ticket ticket;
    private String status;

    public Baggage(Double weight, Ticket ticket) {
        this.weight = weight;
        this.ticket = ticket;
        // TODO: this.dangerLevel = random 0/1/2
    }

    public Baggage(Double weight) {
        this.weight = weight;
        // TODO: this.dangerLevel = random 0/1/2
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getDangerLevel() {
        return dangerLevel;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
