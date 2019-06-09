package airport.gui;

import javax.swing.border.*;
import airport.app.airplane.Airplane;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import com.intellij.uiDesigner.core.*;

public class AirplaneRenderer extends JPanel implements ListCellRenderer<Airplane>{
    JLabel name = new JLabel(" ");
    JLabel destination = new JLabel(" ");
    JLabel flightStart = new JLabel(" ");
    JLabel maxBaggageWeight = new JLabel(" ");
    JLabel allPassengers = new JLabel(" ");
    JLabel passengers = new JLabel(" ");

    public AirplaneRenderer() {
        initComponents();
        add(rootPanel);
        rootPanel.setOpaque(false);

        nameJP.setLayout(new GridLayout(1, 1));
        nameJP.add(name);

        destinationJP.setLayout(new GridLayout(1, 1));
        destinationJP.add(destination);

        flightStartJP.setLayout(new GridLayout(1, 1));
        flightStartJP.add(flightStart);

        maxBaggageWeightJP.setLayout(new GridLayout(1, 1));
        maxBaggageWeightJP.add(maxBaggageWeight);

        allPassengersJP.setLayout(new GridLayout(1, 1));
        allPassengersJP.add(allPassengers);

        passengersJP.setLayout(new GridLayout(1, 1));
        passengersJP.add(passengers);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Airplane> list, Airplane value, int index, boolean isSelected, boolean cellHasFocus) {
        setComponentOrientation(list.getComponentOrientation());

        name.setText(value.getFlightName());
        destination.setText("Cel: " + value.getDirection());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        flightStart.setText("Czas odlotu: " + timeFormat.format(value.getFlightStart()));
        maxBaggageWeight.setText("Obciazenie: " + value.getMaxBaggageWeight());
        allPassengers.setText("Ilosc wykupionych biletow: " + value.getPurchasedTicketsAmount() + " / " + value.getMaxPassenger());
        passengers.setText("Ilosc pasazerow na pokladzie: " + value.getPassengersOnBoardAmount() + " / " + value.getMaxPassenger());

        return this;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using  license - unknown
        rootPanel = new JPanel();
        flightStartJP = new JPanel();
        maxBaggageWeightJP = new JPanel();
        allPassengersJP = new JPanel();
        passengersJP = new JPanel();
        destinationJP = new JPanel();
        nameJP = new JPanel();

        //======== rootPanel ========
        {
            rootPanel.setOpaque(false);
            rootPanel.setBorder(new TitledBorder(LineBorder.createBlackLineBorder(), ""));

            // JFormDesigner evaluation mark
            rootPanel.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), rootPanel.getBorder())); rootPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            rootPanel.setLayout(new GridLayoutManager(6, 1, new Insets(5, 5, 5, 5), 0, 0));

            //======== flightStartJP ========
            {
                flightStartJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
            }
            rootPanel.add(flightStartJP, new GridConstraints(2, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== maxBaggageWeightJP ========
            {
                maxBaggageWeightJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
            }
            rootPanel.add(maxBaggageWeightJP, new GridConstraints(3, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== allPassengersJP ========
            {
                allPassengersJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
            }
            rootPanel.add(allPassengersJP, new GridConstraints(4, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== passengersJP ========
            {
                passengersJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
            }
            rootPanel.add(passengersJP, new GridConstraints(5, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== destinationJP ========
            {
                destinationJP.setEnabled(false);
                destinationJP.setOpaque(false);
                destinationJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
            }
            rootPanel.add(destinationJP, new GridConstraints(1, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== nameJP ========
            {
                nameJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
            }
            rootPanel.add(nameJP, new GridConstraints(0, 0, 1, 1,
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
    private JPanel flightStartJP;
    private JPanel maxBaggageWeightJP;
    private JPanel allPassengersJP;
    private JPanel passengersJP;
    private JPanel destinationJP;
    private JPanel nameJP;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
