package main.java.airport;

import main.java.airport.gui.MainScreen;
import main.java.airport.simulation.Simulation;

import javax.swing.*;
import java.io.IOException;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        Simulation simulation = new Simulation();

        showGUI(simulation);
    }

    private static void showGUI(Simulation simulation) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {

        UIManager.setLookAndFeel((UIManager.getSystemLookAndFeelClassName()));

        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen(simulation);
            mainScreen.setVisible(true);
        });
    }
}
