package main.java.airport.gui;


import main.java.airport.app.airplane.Airplane;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class AirplaneRenderer extends JPanel implements ListCellRenderer<Airplane>{
    JLabel name = new JLabel(" ");
    JLabel destination = new JLabel(" ");
    JLabel flightStart = new JLabel(" ");
    JLabel maxBaggageWeight = new JLabel(" ");
    JLabel allPassengers = new JLabel(" ");
    JLabel passengers = new JLabel(" ");
    private JPanel rootPanel;
    private JPanel nameJP;
    private JPanel destinationJP;
    private JPanel flightStartJP;
    private JPanel maxBaggageWeightJP;
    private JPanel allPassengersJP;
    private JPanel passengersJP;

    public AirplaneRenderer() {
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
        maxBaggageWeight.setText("Obciążenie: " + value.getMaxBaggageWeight());
        allPassengers.setText("Ilość wykupionych biletów: " + value.getPurchasedTicketsAmount() + " / " + value.getMaxPassenger());
        passengers.setText("Ilość pasażerów na pokładzie: " + value.getPassengersOnBoardAmount() + " / " + value.getMaxPassenger());

        return this;
    }
}
