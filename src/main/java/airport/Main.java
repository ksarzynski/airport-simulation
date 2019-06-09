package airport;

import airport.gui.MainScreen;
import airport.simulation.Simulation;

import javax.swing.*;

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
