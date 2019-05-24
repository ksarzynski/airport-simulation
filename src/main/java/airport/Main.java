package main.java.airport;

import main.java.airport.simulation.Simulation;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");

        Simulation simulation = new Simulation();
        simulation.start();
    }
}
