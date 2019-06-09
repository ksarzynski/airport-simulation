package airport.app.belongings;

import java.util.Random;

/**
 * obiekty klasy bagaz sa uzywane przez obiekty klasy pasazer, maja zmienna o nazwie dangerLevel,
 * bagaz moze byc bezpieczny, moez byc niebezpieczny - wlasciciel takiego
 * bagazu wraz z bagazem zostaje usuniety z lotniwska a moze byc bardzo niebezpieczny
 * i spodowac pobliskich osob w kolejce ( np 10 ) i spowodowac usuniecie ich oraz wlasciciela
 * i ich bagazy z lotniska
 */
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

    /**
     * metoda wywolywana przy tworzeniu bagazy, od niej zalezy czy bagaz jest bezpieczny, niebezpieczny, bardzo niebezpieczny
     * @param accuracy od tego argumentu zalezy jak czesto pojawiaja sie niebezpieczne i bardzo niebezpieczne bagaze
     */
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
