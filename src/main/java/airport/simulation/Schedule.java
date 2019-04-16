package main.java.airport.simulation;

import java.util.TimerTask;

class Schedule extends TimerTask {
    private Clock clock;

    Schedule() {
        this.clock = new Clock();
    }

    public void run() {
        clock.increaseTime(15);
        System.out.println(clock.getTime());
    }
}
