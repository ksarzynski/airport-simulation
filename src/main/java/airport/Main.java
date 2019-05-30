package main.java.airport;

import main.java.airport.simulation.Simulation;

import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Hello World!");

        Simulation simulation = new Simulation();
        simulation.start();
    }
}
