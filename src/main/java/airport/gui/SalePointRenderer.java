package main.java.airport.gui;

import main.java.airport.app.place.SalePoint;
import main.java.airport.simulation.Simulation;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class SalePointRenderer extends JPanel implements ListCellRenderer<SalePoint>{
    JLabel name = new JLabel(" ");
    JLabel status = new JLabel(" ");
    JLabel queue = new JLabel(" ");
    JLabel efficiency = new JLabel(" ");
    private JPanel nameJP;
    private JPanel statusJP;
    private JPanel queueJP;
    private JPanel efficiencyJP;
    private JPanel rootPanel;

    public SalePointRenderer() {
        add(rootPanel);
        rootPanel.setOpaque(false);

        nameJP.setLayout(new GridLayout(1, 1));
        nameJP.add(name);

        statusJP.setLayout(new GridLayout(1, 1));
        statusJP.add(status);

        queueJP.setLayout(new GridLayout(1, 1));
        queueJP.add(queue);

        efficiencyJP.setLayout(new GridLayout(1, 1));
        efficiencyJP.add(efficiency);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(95, 90);
    }

    @Override
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends SalePoint> list, SalePoint value, int index, boolean isSelected, boolean cellHasFocus) {
        setComponentOrientation(list.getComponentOrientation());

        name.setText(value.getName());

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        if(value.getIsOpen()) {
            status.setText("[" + timeFormat.format(value.getShiftStartTime()) + " - " + timeFormat.format(value.getShiftEndTime()) + "]");
            status.setForeground(Color.GREEN);
            status.setHorizontalTextPosition(SwingConstants.RIGHT);
            efficiency.setText("Efektywność: " + value.getVendorsEfficiency() + "%");
        } else {
            status.setText("ZAMKNIĘTE");
            status.setForeground(Color.RED);
            status.setHorizontalTextPosition(SwingConstants.RIGHT);
            efficiency.setText("Efektywność: 0%");
        }

        queue.setText("Osoby w kolejce: " + value.getPassengersAmountInQueue() + " / " + value.getQueueSize());

        return this;
    }
}
