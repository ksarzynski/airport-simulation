package main.java.airport.simulation;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

class Schedule extends TimerTask {
    private Clock clock;
    private Integer timeShiftInMilliseconds;
    private Integer simulationSpeedInMilliseconds;
    private Simulation simulation;

    Schedule(Integer simulationSpeedInMilliseconds, Integer timeShiftInMilliseconds, Simulation simulation) {
        this.clock = new Clock();
        this.simulation = simulation;
        this.timeShiftInMilliseconds = timeShiftInMilliseconds;
        this.simulationSpeedInMilliseconds = simulationSpeedInMilliseconds;
    }

    public void run() {
        try {
            runCycle();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clock.increaseTime(timeShiftInMilliseconds);
        System.out.println(clock.getTime());
    }

    public void runCycle() throws IOException {
        simulation.addNewRandomPassangers(5);
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
