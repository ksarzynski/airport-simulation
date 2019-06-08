package main.java.airport.gui;

import main.java.airport.app.place.SalePoint;
import main.java.airport.simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.time.Clock;

public class MainScreen extends JFrame implements  PropertyChangeListener {
    private final Simulation simulation;
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
    private JList<SalePoint> salePointsList;

    private JPanel controlPointsJP;
    private JScrollPane controlPointsSP;
    private DefaultListModel controlPointsListModel;
    private JList controlPointsList;

    private JPanel baggageControlPointsJP;
    private JScrollPane baggageControlPointsSP;
    private DefaultListModel baggageControlPointsListModel;
    private JList baggageControlPointsList;

    private JPanel dutyFreeZoneJP;
    private JScrollPane dutyFreeZoneSP;
    private DefaultListModel dutyFreeZoneListModel;
    private JList dutyFreeZoneList;

    private JPanel airplanesJP;
    private JLabel clockJL;
    private JScrollPane airplanesSP;
    private DefaultListModel airplanesListModel;
    private JList airplanesList;

    public MainScreen(Simulation simulation) {
        this.simulation = simulation;
        add(rootPanel);

        setTitle("Airport simulation");
        setSize(1600, 750);
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
        salePointsList = new JList(salePointsListModel);
        salePointsList.setCellRenderer(new SalePointRenderer());
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.SALEPOINTS, this);
        salePointsSP = new JScrollPane(salePointsList);
        salePointsJP.setLayout(new GridLayout(1, 1));
        salePointsJP.add(salePointsSP);

        controlPointsListModel = new DefaultListModel<>();
        controlPointsListModel.addAll(simulation.getControlPoints());
        controlPointsList = new JList<>(controlPointsListModel);
        controlPointsList.setCellRenderer(new ControlPointRenderer());
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.CONTROLPOINTS, this);
        controlPointsSP = new JScrollPane(controlPointsList);
        controlPointsJP.setLayout(new GridLayout(1, 1));
        controlPointsJP.add(controlPointsSP);

        baggageControlPointsListModel = new DefaultListModel<>();
        baggageControlPointsListModel.addAll(simulation.getBaggageControlPoints());
        baggageControlPointsList = new JList<>(baggageControlPointsListModel);
        baggageControlPointsList.setCellRenderer(new BaggageControlPointRenderer());
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.BAGGAGECONTROLPOINTS, this);
        baggageControlPointsSP = new JScrollPane(baggageControlPointsList);
        baggageControlPointsJP.setLayout(new GridLayout(1, 1));
        baggageControlPointsJP.add(baggageControlPointsSP);

        dutyFreeZoneListModel = new DefaultListModel<>();
        dutyFreeZoneListModel.addAll(simulation.getBaggageControlPoints());
        dutyFreeZoneList = new JList<>(dutyFreeZoneListModel);
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.DUTYFREEZONE, this);
        dutyFreeZoneSP = new JScrollPane(dutyFreeZoneList);
        dutyFreeZoneJP.setLayout(new GridLayout(1, 1));
//        dutyFreeZoneJP.add(dutyFreeZoneSP);

        airplanesListModel = new DefaultListModel<>();
        airplanesListModel.addAll(simulation.getBaggageControlPoints());
        airplanesList = new JList<>(airplanesListModel);
        airplanesList.setCellRenderer(new AirplaneRenderer());
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.AIRPLANES, this);
        airplanesSP = new JScrollPane(airplanesList);
        airplanesJP.setLayout(new GridLayout(1, 1));
        airplanesJP.add(airplanesSP);

        clockJL.setText("00:00");
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.CLOCK, this);
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
                } else if (evt.getOldValue() != null && evt.getNewValue() != null) {
                    salePointsListModel.setElementAt(evt.getNewValue(), salePointsListModel.indexOf(evt.getNewValue()));
                }
            } else if (Simulation.CONTROLPOINTS.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    controlPointsListModel.removeElement(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    controlPointsListModel.addElement(evt.getNewValue());
                }
            } else if (Simulation.BAGGAGECONTROLPOINTS.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    baggageControlPointsListModel.removeElement(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    baggageControlPointsListModel.addElement(evt.getNewValue());
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
            } else if (Simulation.CLOCK.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() != null) {
                    clockJL.setText(evt.getNewValue().toString());
                }
            }
        }
    }
}
