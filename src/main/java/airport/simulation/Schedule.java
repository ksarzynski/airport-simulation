package main.java.airport.simulation;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

class Schedule extends TimerTask {
    private Clock clock;
    private Integer timeShiftInMilliseconds;
    private Integer simulationSpeedInMilliseconds;
    private Simulation simulation;

    Schedule(Simulation simulation) {
        this.clock = new Clock();
        this.simulation = simulation;
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

    private void runCycle() throws IOException {
        simulation.checkWorkingHours();
        simulation.checkDepartureTimes();
        simulation.addNewRandomPassengers(300);
        //simulation.addNewRandomPassengers(Helpers.getRandomNumber(0, 15));
        simulation.addNewRandomAirplanes(Helpers.getRandomNumber(0, 1));
        simulation.moveFromSalePoints();
        simulation.moveFromBaggageControlPoints();
        simulation.moveFromControlPoints();
        simulation.display();
        simulation.updateGUIClock();

        simulation.openRandomSalePoints(1);

//        simulation.moveFromPlaces();
    }

    void runTimer() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(this, 0, simulationSpeedInMilliseconds);
    }

    void setTimeShiftInMilliseconds(Integer milliseconds) {
        this.timeShiftInMilliseconds = milliseconds;
    }

    void setSimulationSpeedInMilliseconds(Integer simulationSpeedInMilliseconds) {
        this.simulationSpeedInMilliseconds = simulationSpeedInMilliseconds;
    }

    public String getTime() {
        return this.clock.getTime();
    }

    Date getDate() {
        return this.clock.getDate();
    }

}
