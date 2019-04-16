package main.java.airport.simulation;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Clock {
    private Date currentTime;
    private Date currentRealTime;

    public void clock () {
        this.runTimer();
    }

    public void runTimer() {
        TimerTask schedule = new Schedule();
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(schedule, 500, 1000);
    }


}
