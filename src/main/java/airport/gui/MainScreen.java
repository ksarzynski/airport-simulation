package main.java.airport.gui;

import main.java.airport.simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class MainScreen extends JFrame implements  PropertyChangeListener {
    private final Simulation simulation;
    private JLabel clockLabel;
    private JTextField simulationSpeedTF;
    private JTextField timeShiftTF;
    private JButton add5PassengersBtn;
    private JButton add25PassengersBtn;
    private JButton addFivePassengersBtn;
    private JPanel rootPanel;
    private JButton STARTBtn;
    private JButton STOPBtn;
    private JTextField salePointsAmountTF;
    private JTextField openSalePointsAmountTF;
    private JTextField vendorsAmountTF;
    private JTextField controllPointsAmountTF;
    private JTextField openControllPointsAmountTF;
    private JTextField controlersAmountTF;
    private JTextField baggageControlPointsAmountTF;
    private JTextField openBaggageControllPointsAmountTF;
    private JTextField flightsAmountTF;

    private JPanel salePointsJP;
    private JScrollPane salePointsSP;
    private DefaultListModel<Object> salePointsListModel;
    private JList<Object> salePointsList;

    private JPanel controllPointsJP;
    private JScrollPane controllPointsSP;
    private DefaultListModel controllPointsListModel;
    private JList controllPointsList;

    private JPanel baggageControllPointsJP;
    private JScrollPane baggageControllPointsSP;
    private DefaultListModel baggageControllPointsListModel;
    private JList baggageControllPointsList;

    private JPanel dutyFreeZoneJP;
    private JScrollPane dutyFreeZoneSP;
    private DefaultListModel dutyFreeZoneListModel;
    private JList dutyFreeZoneList;

    private JPanel airplanesJP;
    private JScrollPane airplanesSP;
    private DefaultListModel airplanesListModel;
    private JList airplanesList;

    public MainScreen(Simulation simulation) {
        this.simulation = simulation;
        add(rootPanel);

        setTitle("Airport simulation");
        setSize(1000, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        STARTBtn.addActionListener(e -> {
            try {
                simulation.start(
                        Integer.parseInt(salePointsAmountTF.getText()),
                        Integer.parseInt(vendorsAmountTF.getText()),
                        Integer.parseInt(openSalePointsAmountTF.getText()),
                        Integer.parseInt(controllPointsAmountTF.getText()),
                        Integer.parseInt(openControllPointsAmountTF.getText()),
                        Integer.parseInt(controlersAmountTF.getText()),
                        Integer.parseInt(baggageControlPointsAmountTF.getText()),
                        Integer.parseInt(openBaggageControllPointsAmountTF.getText()),
                        Integer.parseInt(flightsAmountTF.getText()),
                        Integer.parseInt(simulationSpeedTF.getText()),
                        Integer.parseInt(timeShiftTF.getText())
                        );
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        salePointsListModel = new DefaultListModel<>();
        salePointsListModel.addAll(simulation.getSalePoints());
        salePointsList = new JList<>(salePointsListModel);
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.SALEPOINTS, this);
        salePointsSP = new JScrollPane(salePointsList);
        salePointsJP.setLayout(new GridLayout(1, 1));
        salePointsJP.add(salePointsSP);

        controllPointsListModel = new DefaultListModel<>();
        controllPointsListModel.addAll(simulation.getControlPoints());
        controllPointsList = new JList<>(controllPointsListModel);
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.CONTROLLPOINTS, this);
        controllPointsSP = new JScrollPane(controllPointsList);
        controllPointsJP.setLayout(new GridLayout(1, 1));
        controllPointsJP.add(controllPointsSP);

        baggageControllPointsListModel = new DefaultListModel<>();
        baggageControllPointsListModel.addAll(simulation.getBaggageControlPoints());
        baggageControllPointsList = new JList<>(baggageControllPointsListModel);
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.BAGGAGECONTROLLPOINTS, this);
        baggageControllPointsSP = new JScrollPane(baggageControllPointsList);
        baggageControllPointsJP.setLayout(new GridLayout(1, 1));
        baggageControllPointsJP.add(baggageControllPointsSP);

        dutyFreeZoneListModel = new DefaultListModel<>();
        dutyFreeZoneListModel.addAll(simulation.getBaggageControlPoints());
        dutyFreeZoneList = new JList<>(dutyFreeZoneListModel);
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.DUTYFREEZONE, this);
        dutyFreeZoneSP = new JScrollPane(dutyFreeZoneList);
        dutyFreeZoneJP.setLayout(new GridLayout(1, 1));
        dutyFreeZoneJP.add(dutyFreeZoneSP);

        airplanesListModel = new DefaultListModel<>();
        airplanesListModel.addAll(simulation.getBaggageControlPoints());
        airplanesList = new JList<>(airplanesListModel);
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.AIRPLANES, this);
        airplanesSP = new JScrollPane(airplanesList);
        airplanesJP.setLayout(new GridLayout(1, 1));
        airplanesJP.add(airplanesSP);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(() -> propertyChange(evt));
            return;
        }
        if (evt.getSource() == simulation) {
            if (Simulation.SALEPOINTS.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    salePointsListModel.removeElement(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    salePointsListModel.addElement(evt.getNewValue());
                }
            } else if (Simulation.CONTROLLPOINTS.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    controllPointsListModel.removeElement(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    controllPointsListModel.addElement(evt.getNewValue());
                }
            } else if (Simulation.BAGGAGECONTROLLPOINTS.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    baggageControllPointsListModel.removeElement(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    baggageControllPointsListModel.addElement(evt.getNewValue());
                }
            } else if (Simulation.DUTYFREEZONE.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    dutyFreeZoneListModel.removeElement(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    dutyFreeZoneListModel.addElement(evt.getNewValue());
                }
            } else if (Simulation.AIRPLANES.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    airplanesListModel.removeElement(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    airplanesListModel.addElement(evt.getNewValue());
                }
            }
        }
    }
}
