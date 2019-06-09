package airport.gui;

import javax.swing.border.*;
import airport.app.airplane.Airplane;
import airport.app.place.ControlPoint;
import airport.app.place.DutyFreeZone;
import airport.app.place.SalePoint;
import airport.simulation.Simulation;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import com.intellij.uiDesigner.core.*;

public class MainScreen extends JFrame implements PropertyChangeListener {
    private final Simulation simulation;

    private JScrollPane salePointsSP;
    private DefaultListModel<Object> salePointsListModel;
    private JList<SalePoint> salePointsList;

    private JScrollPane controlPointsSP;
    private DefaultListModel<Object> controlPointsListModel;
    private JList<ControlPoint> controlPointsList;

    private JScrollPane baggageControlPointsSP;
    private DefaultListModel<Object> baggageControlPointsListModel;
    private JList baggageControlPointsList;

    private JScrollPane airplanesSP;
    private DefaultListModel<Object> airplanesListModel;
    private JList<Airplane> airplanesList;
    private Boolean runningStatus;

    public MainScreen(Simulation simulation) {
//        STARTBtn = new JButton();
        initComponents();
        this.simulation = simulation;
        setSimulationStatus(false);

        add(rootPanel);

        setTitle("Airport simulation");
        setSize(1600, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        STARTBtn.addActionListener(e -> {
            try {
                setSimulationStatus(true);
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

        STOPBtn.addActionListener(e -> {
            setSimulationStatus(false);
            clearOldSimulationData();
            simulation.stop();
        });

        openSPBtn.addActionListener(e -> {
            simulation.openClosedSalePoints();
        });

        openBCPBtn.addActionListener(e -> {
            simulation.openClosedBaggageControlPoints();
        });

        openCPBtn.addActionListener(e -> {
            simulation.openClosedControlPoints();
        });

        addAirplaneBtn.addActionListener(e -> {
            try {
                simulation.addNewRandomAirplanes(1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        add5PassengersBtn.addActionListener(e -> {
            try {
                simulation.addNewRandomPassengers(5);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        add25PassengersBtn.addActionListener(e -> {
            try {
                simulation.addNewRandomPassengers(25);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        add50PassengersBtn.addActionListener(e -> {
            try {
                simulation.addNewRandomPassengers(50);
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
        controlPointsList = new JList(controlPointsListModel);
        controlPointsList.setCellRenderer(new ControlPointRenderer());
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.CONTROLPOINTS, this);
        controlPointsSP = new JScrollPane(controlPointsList);
        controlPointsJP.setLayout(new GridLayout(1, 1));
        controlPointsJP.add(controlPointsSP);

        baggageControlPointsListModel = new DefaultListModel<>();
        baggageControlPointsListModel.addAll(simulation.getBaggageControlPoints());
        baggageControlPointsList = new JList(baggageControlPointsListModel);
        baggageControlPointsList.setCellRenderer(new BaggageControlPointRenderer());
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.BAGGAGECONTROLPOINTS, this);
        baggageControlPointsSP = new JScrollPane(baggageControlPointsList);
        baggageControlPointsJP.setLayout(new GridLayout(1, 1));
        baggageControlPointsJP.add(baggageControlPointsSP);

        dutyFreeZoneJL.setText("");
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.DUTYFREEZONE, this);

        airplanesListModel = new DefaultListModel<>();
        airplanesListModel.addAll(simulation.getBaggageControlPoints());
        airplanesList = new JList(airplanesListModel);
        airplanesList.setCellRenderer(new AirplaneRenderer());
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.AIRPLANES, this);
        airplanesSP = new JScrollPane(airplanesList);
        airplanesJP.setLayout(new GridLayout(1, 1));
        airplanesJP.add(airplanesSP);

        clockJL.setText("00:00");
        clockJL.setFont(clockJL.getFont().deriveFont(64.0f));
        simulation.getPropertyChangeSupport().addPropertyChangeListener(Simulation.CLOCK, this);
    }

    private void clearOldSimulationData() {
        salePointsListModel.clear();
        airplanesListModel.clear();
        baggageControlPointsListModel.clear();
        controlPointsListModel.clear();
        clockJL.setText("00:00");
        dutyFreeZoneJL.setText("");
    }

    private void setSimulationStatus(boolean status) {
        runningStatus = status;

        STARTBtn.setEnabled(!status);
        STOPBtn.setEnabled(status);
        add5PassengersBtn.setEnabled(status);
        add25PassengersBtn.setEnabled(status);
        add50PassengersBtn.setEnabled(status);
        openCPBtn.setEnabled(status);
        openBCPBtn.setEnabled(status);
        openSPBtn.setEnabled(status);
        addAirplaneBtn.setEnabled(status);

        salePointsAmountTF.setEnabled(!status);
        openSalePointsAmountTF.setEnabled(!status);
        vendorsAmountTF.setEnabled(!status);
        controllPointsAmountTF.setEnabled(!status);
        openControllPointsAmountTF.setEnabled(!status);
        controlersAmountTF.setEnabled(!status);
        baggageControlPointsAmountTF.setEnabled(!status);
        openBaggageControllPointsAmountTF.setEnabled(!status);
        flightsAmountTF.setEnabled(!status);
        timeShiftTF.setEnabled(!status);
        simulationSpeedTF.setEnabled(!status);
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
                } else if (evt.getOldValue() != null && evt.getNewValue() != null) {
                    controlPointsListModel.setElementAt(evt.getNewValue(), controlPointsListModel.indexOf(evt.getNewValue()));
                }
            } else if (Simulation.BAGGAGECONTROLPOINTS.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    baggageControlPointsListModel.removeElement(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    baggageControlPointsListModel.addElement(evt.getNewValue());
                } else if (evt.getOldValue() != null && evt.getNewValue() != null) {
                    baggageControlPointsListModel.setElementAt(evt.getNewValue(), baggageControlPointsListModel.indexOf(evt.getNewValue()));
                }
            } else if (Simulation.DUTYFREEZONE.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() != null) {
                    dutyFreeZoneJL.setText("Liczba osob: " +((DutyFreeZone) evt.getNewValue()).getPassengersAmountInQueue().toString());
                }
            } else if (Simulation.AIRPLANES.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() == null) {
                    airplanesListModel.removeElement(evt.getOldValue());
                } else if (evt.getOldValue() == null && evt.getNewValue() != null) {
                    airplanesListModel.addElement(evt.getNewValue());
                } else if (evt.getOldValue() != null && evt.getNewValue() != null) {
                    Integer index = airplanesListModel.indexOf(evt.getNewValue());
                    if(index != -1)
                        airplanesListModel.setElementAt(evt.getNewValue(), index);
                }
            } else if (Simulation.CLOCK.equals(evt.getPropertyName())) {
                if (evt.getOldValue() != null && evt.getNewValue() != null) {
                    clockJL.setText(evt.getNewValue().toString());
                }
            }
        }
    }

    private void initComponents() {
        rootPanel = new JPanel();
        JPanel panel1 = new JPanel();
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        JLabel label3 = new JLabel();
        JLabel label4 = new JLabel();
        salePointsJP = new JPanel();
        baggageControlPointsJP = new JPanel();
        controlPointsJP = new JPanel();
        dutyFreeZoneJP = new JPanel();
        dutyFreeZoneJL = new JLabel();
        Spacer vSpacer1 = new Spacer();
        airplanesJP = new JPanel();
        Spacer hSpacer1 = new Spacer();
        Spacer hSpacer2 = new Spacer();
        Spacer hSpacer3 = new Spacer();
        Spacer hSpacer4 = new Spacer();
        Spacer hSpacer5 = new Spacer();
        JLabel label5 = new JLabel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        STARTBtn = new JButton();
        STOPBtn = new JButton();
        JPanel panel5 = new JPanel();
        JLabel label6 = new JLabel();
        timeShiftTF = new JTextField();
        simulationSpeedTF = new JTextField();
        JLabel label7 = new JLabel();
        JLabel label8 = new JLabel();
        JLabel label9 = new JLabel();
        Spacer vSpacer2 = new Spacer();
        clockJL = new JLabel();
        JPanel panel6 = new JPanel();
        JPanel panel7 = new JPanel();
        JLabel label10 = new JLabel();
        JLabel label11 = new JLabel();
        JLabel label12 = new JLabel();
        salePointsAmountTF = new JTextField();
        openSalePointsAmountTF = new JTextField();
        vendorsAmountTF = new JTextField();
        JPanel panel8 = new JPanel();
        JLabel label13 = new JLabel();
        JLabel label14 = new JLabel();
        JLabel label15 = new JLabel();
        controllPointsAmountTF = new JTextField();
        openControllPointsAmountTF = new JTextField();
        controlersAmountTF = new JTextField();
        JLabel label16 = new JLabel();
        baggageControlPointsAmountTF = new JTextField();
        JLabel label17 = new JLabel();
        openBaggageControllPointsAmountTF = new JTextField();
        JPanel panel9 = new JPanel();
        JLabel label18 = new JLabel();
        flightsAmountTF = new JTextField();
        JLabel label19 = new JLabel();
        JPanel panel10 = new JPanel();
        JPanel panel11 = new JPanel();
        add5PassengersBtn = new JButton();
        add25PassengersBtn = new JButton();
        add50PassengersBtn = new JButton();
        JLabel label20 = new JLabel();
        JPanel panel12 = new JPanel();
        openSPBtn = new JButton();
        openBCPBtn = new JButton();
        openCPBtn = new JButton();
        addAirplaneBtn = new JButton();

        //======== rootPanel ========
        {

            // JFormDesigner evaluation mark
            rootPanel.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), rootPanel.getBorder())); rootPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            rootPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

            //======== panel1 ========
            {
                panel1.setBorder(new TitledBorder(new LineBorder(new Color(175, 187, 186)), ""));
                panel1.setLayout(new GridLayoutManager(3, 5, new Insets(0, 0, 0, 0), -1, -1));

                //---- label1 ----
                label1.setText("Punkty sprzedazcy");
                panel1.add(label1, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));

                //---- label2 ----
                label2.setText("Punkty kontroli bagazu");
                panel1.add(label2, new GridConstraints(0, 1, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                   null, null, null));

                //---- label3 ----
                label3.setText("Punkty kontroli");
                panel1.add(label3, new GridConstraints(0, 2, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));

                //---- label4 ----
                label4.setText("Loty");
                panel1.add(label4, new GridConstraints(0, 4, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));

                //======== salePointsJP ========
                {
                    salePointsJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
                }
                panel1.add(salePointsJP, new GridConstraints(2, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== baggageControlPointsJP ========
                {
                    baggageControlPointsJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
                }
                panel1.add(baggageControlPointsJP, new GridConstraints(2, 1, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== controlPointsJP ========
                {
                    controlPointsJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
                }
                panel1.add(controlPointsJP, new GridConstraints(2, 2, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== dutyFreeZoneJP ========
                {
                    dutyFreeZoneJP.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

                    //---- dutyFreeZoneJL ----
                    dutyFreeZoneJL.setText("Label");
                    dutyFreeZoneJP.add(dutyFreeZoneJL, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                    dutyFreeZoneJP.add(vSpacer1, new GridConstraints(1, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        null, null, null));
                }
                panel1.add(dutyFreeZoneJP, new GridConstraints(2, 3, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== airplanesJP ========
                {
                    airplanesJP.setAutoscrolls(false);
                    airplanesJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
                }
                panel1.add(airplanesJP, new GridConstraints(2, 4, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
                panel1.add(hSpacer1, new GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK,
                    null, null, null));
                panel1.add(hSpacer2, new GridConstraints(1, 1, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK,
                    null, null, null));
                panel1.add(hSpacer3, new GridConstraints(1, 2, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK,
                    null, null, null));
                panel1.add(hSpacer4, new GridConstraints(1, 4, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK,
                    null, null, null));
                panel1.add(hSpacer5, new GridConstraints(1, 3, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                    GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK,
                    null, null, null));

                //---- label5 ----
                label5.setText("Strefa bezclowa");
                panel1.add(label5, new GridConstraints(0, 3, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                    GridConstraints.SIZEPOLICY_FIXED,
                    GridConstraints.SIZEPOLICY_FIXED,
                    null, null, null));
            }
            rootPanel.add(panel1, new GridConstraints(1, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== panel2 ========
            {
                panel2.setLayout(new GridLayoutManager(3, 3, new Insets(0, 0, 0, 0), -1, -1));

                //======== panel3 ========
                {
                    panel3.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));

                    //======== panel4 ========
                    {
                        panel4.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));

                        //---- STARTBtn ----
                        STARTBtn.setText("START");
                        panel4.add(STARTBtn, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- STOPBtn ----
                        STOPBtn.setText("STOP");
                        panel4.add(STOPBtn, new GridConstraints(0, 1, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));
                    }
                    panel3.add(panel4, new GridConstraints(3, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //======== panel5 ========
                    {
                        panel5.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));

                        //---- label6 ----
                        label6.setText("Szybkosc:");
                        panel5.add(label6, new GridConstraints(1, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- timeShiftTF ----
                        timeShiftTF.setText("10");
                        panel5.add(timeShiftTF, new GridConstraints(0, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- simulationSpeedTF ----
                        simulationSpeedTF.setText("1000");
                        panel5.add(simulationSpeedTF, new GridConstraints(1, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- label7 ----
                        label7.setText("min");
                        panel5.add(label7, new GridConstraints(0, 2, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- label8 ----
                        label8.setText("msec");
                        panel5.add(label8, new GridConstraints(1, 2, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- label9 ----
                        label9.setText("Interwal: ");
                        panel5.add(label9, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_EAST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));
                    }
                    panel3.add(panel5, new GridConstraints(2, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                    panel3.add(vSpacer2, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK,
                        GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                        null, null, null));

                    //---- clockJL ----
                    clockJL.setText("Label");
                    panel3.add(clockJL, new GridConstraints(1, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, null, null));
                }
                panel2.add(panel3, new GridConstraints(0, 2, 3, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== panel6 ========
                {
                    panel6.setBorder(new TitledBorder(LineBorder.createBlackLineBorder(), ""));
                    panel6.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));

                    //======== panel7 ========
                    {
                        panel7.setBorder(new TitledBorder(new EtchedBorder(), ""));
                        panel7.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));

                        //---- label10 ----
                        label10.setText("Punkty sprzedazy");
                        panel7.add(label10, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, new Dimension(10, 16), null));

                        //---- label11 ----
                        label11.setText("Otwarte punkty sprzedarzy");
                        panel7.add(label11, new GridConstraints(1, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, new Dimension(10, 16), null));

                        //---- label12 ----
                        label12.setText("Sprzedawcy");
                        panel7.add(label12, new GridConstraints(2, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, new Dimension(10, 16), null));

                        //---- salePointsAmountTF ----
                        salePointsAmountTF.setText("5");
                        panel7.add(salePointsAmountTF, new GridConstraints(0, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- openSalePointsAmountTF ----
                        openSalePointsAmountTF.setText("2");
                        panel7.add(openSalePointsAmountTF, new GridConstraints(1, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- vendorsAmountTF ----
                        vendorsAmountTF.setText("10");
                        panel7.add(vendorsAmountTF, new GridConstraints(2, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));
                    }
                    panel6.add(panel7, new GridConstraints(1, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, new Dimension(178, 104), null));

                    //======== panel8 ========
                    {
                        panel8.setBorder(new TitledBorder(new EtchedBorder(), ""));
                        panel8.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));

                        //---- label13 ----
                        label13.setText("Punkty kontroli");
                        panel8.add(label13, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, new Dimension(162, 16), null));

                        //---- label14 ----
                        label14.setText("Otwarte punkty kontroli");
                        panel8.add(label14, new GridConstraints(1, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, new Dimension(162, 16), null));

                        //---- label15 ----
                        label15.setText("Kontrolerzy");
                        panel8.add(label15, new GridConstraints(4, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, new Dimension(162, 16), null));

                        //---- controllPointsAmountTF ----
                        controllPointsAmountTF.setText("3");
                        panel8.add(controllPointsAmountTF, new GridConstraints(0, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- openControllPointsAmountTF ----
                        openControllPointsAmountTF.setText("2");
                        panel8.add(openControllPointsAmountTF, new GridConstraints(1, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- controlersAmountTF ----
                        controlersAmountTF.setText("4");
                        panel8.add(controlersAmountTF, new GridConstraints(4, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- label16 ----
                        label16.setText("Punkty kontroli bagazu");
                        panel8.add(label16, new GridConstraints(2, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- baggageControlPointsAmountTF ----
                        baggageControlPointsAmountTF.setText("4");
                        panel8.add(baggageControlPointsAmountTF, new GridConstraints(2, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- label17 ----
                        label17.setText("Otwarte punkty kontroli bagazu");
                        panel8.add(label17, new GridConstraints(3, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- openBaggageControllPointsAmountTF ----
                        openBaggageControllPointsAmountTF.setText("2");
                        panel8.add(openBaggageControllPointsAmountTF, new GridConstraints(3, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));
                    }
                    panel6.add(panel8, new GridConstraints(1, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, new Dimension(178, 104), null));

                    //======== panel9 ========
                    {
                        panel9.setBorder(new TitledBorder(new EtchedBorder(), ""));
                        panel9.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));

                        //---- label18 ----
                        label18.setText("Loty");
                        panel9.add(label18, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, new Dimension(145, 16), null));

                        //---- flightsAmountTF ----
                        flightsAmountTF.setText("7");
                        panel9.add(flightsAmountTF, new GridConstraints(0, 1, 1, 1,
                            GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, new Dimension(150, 30), null));
                    }
                    panel6.add(panel9, new GridConstraints(1, 2, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, new Dimension(178, 34), null));

                    //---- label19 ----
                    label19.setHorizontalAlignment(0);
                    label19.setText("Wartosci inicjalizacyjne");
                    panel6.add(label19, new GridConstraints(0, 1, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE,
                        GridConstraints.SIZEPOLICY_FIXED,
                        GridConstraints.SIZEPOLICY_FIXED,
                        null, new Dimension(178, 16), null));
                }
                panel2.add(panel6, new GridConstraints(0, 0, 3, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== panel10 ========
                {
                    panel10.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));

                    //======== panel11 ========
                    {
                        panel11.setLayout(new GridLayoutManager(1, 4, new Insets(0, 0, 0, 0), -1, -1));

                        //---- add5PassengersBtn ----
                        add5PassengersBtn.setText("5");
                        panel11.add(add5PassengersBtn, new GridConstraints(0, 1, 1, 1,
                            GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- add25PassengersBtn ----
                        add25PassengersBtn.setText("25");
                        panel11.add(add25PassengersBtn, new GridConstraints(0, 2, 1, 1,
                            GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- add50PassengersBtn ----
                        add50PassengersBtn.setText("50");
                        panel11.add(add50PassengersBtn, new GridConstraints(0, 3, 1, 1,
                            GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- label20 ----
                        label20.setText("Dodaj pasazcerow: ");
                        panel11.add(label20, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_SOUTHWEST, GridConstraints.FILL_NONE,
                            GridConstraints.SIZEPOLICY_FIXED,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));
                    }
                    panel10.add(panel11, new GridConstraints(1, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));

                    //======== panel12 ========
                    {
                        panel12.setLayout(new GridLayoutManager(4, 1, new Insets(0, 0, 0, 0), -1, -1));

                        //---- openSPBtn ----
                        openSPBtn.setText("Otworz punkt sprzedazcy");
                        panel12.add(openSPBtn, new GridConstraints(0, 0, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- openBCPBtn ----
                        openBCPBtn.setText("Otworz punkt kontroli bagazcu");
                        panel12.add(openBCPBtn, new GridConstraints(1, 0, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- openCPBtn ----
                        openCPBtn.setText("Otworz punkt kontroli");
                        panel12.add(openCPBtn, new GridConstraints(2, 0, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));

                        //---- addAirplaneBtn ----
                        addAirplaneBtn.setText("Dodaj lot");
                        panel12.add(addAirplaneBtn, new GridConstraints(3, 0, 1, 1,
                            GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                            GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                            GridConstraints.SIZEPOLICY_FIXED,
                            null, null, null));
                    }
                    panel10.add(panel12, new GridConstraints(0, 0, 1, 1,
                        GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                        null, null, null));
                }
                panel2.add(panel10, new GridConstraints(0, 1, 3, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
            }
            rootPanel.add(panel2, new GridConstraints(0, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using  license - unknown
    private JPanel rootPanel;
    private JPanel salePointsJP;
    private JPanel baggageControlPointsJP;
    private JPanel controlPointsJP;
    private JPanel dutyFreeZoneJP;
    private JLabel dutyFreeZoneJL;
    private JPanel airplanesJP;
    private JButton STARTBtn;
    private JButton STOPBtn;
    private JTextField timeShiftTF;
    private JTextField simulationSpeedTF;
    private JLabel clockJL;
    private JTextField salePointsAmountTF;
    private JTextField openSalePointsAmountTF;
    private JTextField vendorsAmountTF;
    private JTextField controllPointsAmountTF;
    private JTextField openControllPointsAmountTF;
    private JTextField controlersAmountTF;
    private JTextField baggageControlPointsAmountTF;
    private JTextField openBaggageControllPointsAmountTF;
    private JTextField flightsAmountTF;
    private JButton add5PassengersBtn;
    private JButton add25PassengersBtn;
    private JButton add50PassengersBtn;
    private JButton openSPBtn;
    private JButton openBCPBtn;
    private JButton openCPBtn;
    private JButton addAirplaneBtn;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
