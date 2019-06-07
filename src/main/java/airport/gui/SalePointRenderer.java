package main.java.airport.gui;

import javax.swing.*;
import java.awt.*;

public class SalePointRenderer extends DefaultListCellRenderer {
    private JLabel testL;

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String name = ((MenuItem) value).getName();
        System.out.println("NAME:    " + name);
        return super.getListCellRendererComponent(list, name, index, isSelected, cellHasFocus);
    }
}
