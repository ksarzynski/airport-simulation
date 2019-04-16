package main.java.airport.simulation;

public class Simulation {
    private Clock clock;

    public void start() {
        this.clock = new Clock();
        clock.runTimer();
    }
}
