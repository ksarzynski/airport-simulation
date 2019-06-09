package airport.gui;

import javax.swing.border.*;
import airport.app.place.BaggageControlPoint;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import com.intellij.uiDesigner.core.*;

public class BaggageControlPointRenderer extends JPanel implements ListCellRenderer<BaggageControlPoint>{
    JLabel name = new JLabel(" ");
    JLabel status = new JLabel(" ");
    JLabel queue = new JLabel(" ");
    JLabel efficiency = new JLabel(" ");

    public BaggageControlPointRenderer() {
        initComponents();
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
    public Component getListCellRendererComponent(JList<? extends BaggageControlPoint> list, BaggageControlPoint value, int index, boolean isSelected, boolean cellHasFocus) {
        setComponentOrientation(list.getComponentOrientation());

        name.setText(value.getName());

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        if(value.getIsOpen()) {
            status.setText("[" + timeFormat.format(value.getShiftStartTime()) + " - " + timeFormat.format(value.getShiftEndTime()) + "]");
            status.setForeground(Color.GREEN);
            status.setHorizontalTextPosition(SwingConstants.RIGHT);
            efficiency.setText("Efektywnosc: " + value.getControllersEfficiency() + "%");
        } else {
            status.setText("ZAMKNIETE");
            status.setForeground(Color.RED);
            status.setHorizontalTextPosition(SwingConstants.RIGHT);
            efficiency.setText("Efektywnosc: 0%");
        }

        queue.setText("Osoby w kolejce: " + value.getPassengersAmountInQueue() + " / " + value.getQueueSize());

        return this;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using  license - unknown
        rootPanel = new JPanel();
        JPanel panel1 = new JPanel();
        nameJP = new JPanel();
        queueJP = new JPanel();
        efficiencyJP = new JPanel();
        JPanel panel2 = new JPanel();
        statusJP = new JPanel();
        Spacer vSpacer1 = new Spacer();
        Spacer hSpacer1 = new Spacer();

        //======== rootPanel ========
        {
            rootPanel.setBorder(new TitledBorder(LineBorder.createBlackLineBorder(), ""));

            // JFormDesigner evaluation mark
            rootPanel.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), rootPanel.getBorder())); rootPanel.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            rootPanel.setLayout(new GridLayoutManager(1, 3, new Insets(0, 0, 5, 0), 0, 0));

            //======== panel1 ========
            {
                panel1.setLayout(new GridLayoutManager(3, 1, new Insets(4, 4, 0, 0), -1, -1));

                //======== nameJP ========
                {
                    nameJP.setEnabled(false);
                    nameJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
                }
                panel1.add(nameJP, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== queueJP ========
                {
                    queueJP.setEnabled(false);
                    queueJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
                }
                panel1.add(queueJP, new GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));

                //======== efficiencyJP ========
                {
                    efficiencyJP.setEnabled(false);
                    efficiencyJP.setBorder(new TitledBorder(BorderFactory.createEmptyBorder(), ""));
                    efficiencyJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
                }
                panel1.add(efficiencyJP, new GridConstraints(2, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
            }
            rootPanel.add(panel1, new GridConstraints(0, 0, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));

            //======== panel2 ========
            {
                panel2.setLayout(new GridLayoutManager(2, 1, new Insets(4, 0, 0, 4), -1, -1));

                //======== statusJP ========
                {
                    statusJP.setEnabled(false);
                    statusJP.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
                }
                panel2.add(statusJP, new GridConstraints(0, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                    null, null, null));
                panel2.add(vSpacer1, new GridConstraints(1, 0, 1, 1,
                    GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL,
                    GridConstraints.SIZEPOLICY_CAN_SHRINK,
                    GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                    null, null, null));
            }
            rootPanel.add(panel2, new GridConstraints(0, 2, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                null, null, null));
            rootPanel.add(hSpacer1, new GridConstraints(0, 1, 1, 1,
                GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_GROW | GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK,
                null, null, null));
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using  license - unknown
    private JPanel rootPanel;
    private JPanel nameJP;
    private JPanel queueJP;
    private JPanel efficiencyJP;
    private JPanel statusJP;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
