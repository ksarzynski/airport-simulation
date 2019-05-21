package main.java.airport.simulation;

import java.util.Timer;
import java.util.TimerTask;

class Schedule extends TimerTask {
    private Clock clock;
    private Integer timeShiftInMilliseconds;
    private Integer simulationSpeedInMilliseconds;

    Schedule(Integer simulationSpeedInMilliseconds, Integer timeShiftInMilliseconds) {
        this.clock = new Clock();
        this.timeShiftInMilliseconds = timeShiftInMilliseconds;
        this.simulationSpeedInMilliseconds = simulationSpeedInMilliseconds;
    }

    public void run() {
        clock.increaseTime(timeShiftInMilliseconds);
        System.out.println(clock.getTime());
    }

    void runTimer() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(this, 0, simulationSpeedInMilliseconds);
    }

    public void setTimeShiftInMilliseconds(Integer milliseconds) {
        this.timeShiftInMilliseconds = milliseconds;
    }

    public void setSimulationSpeedInMilliseconds(Integer simulationSpeedInMilliseconds) {
        this.simulationSpeedInMilliseconds = simulationSpeedInMilliseconds;
    }
}
