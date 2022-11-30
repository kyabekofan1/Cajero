// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Button;
import java.awt.Component;
import java.awt.Label;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Panel;

class SimOperatorPanel extends Panel
{
    SimOperatorPanel(final Simulation simulation) {
        this.setLayout(new BorderLayout(10, 0));
        this.setBackground(new Color(128, 128, 255));
        this.add(new Label("     Operator Panel"), "West");
        final Label message = new Label("Click button to turn ATM on", 1);
        this.add(message, "Center");
        final Button button = new Button(" ON ");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (button.getLabel().equals("OFF")) {
                    message.setText("Click button to turn ATM on  ");
                    button.setLabel(" ON ");
                    simulation.switchChanged(false);
                }
                else {
                    message.setText("Click button to turn ATM off");
                    button.setLabel("OFF");
                    simulation.switchChanged(true);
                }
            }
        });
        final Panel buttonPanel = new Panel();
        buttonPanel.add(button);
        this.add(buttonPanel, "East");
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (InterruptedException ex) {}
                    if ((message.isVisible() && !button.getLabel().equals("OFF")) || !SimOperatorPanel.this.isEnabled()) {
                        message.setVisible(false);
                    }
                    else {
                        message.setVisible(true);
                    }
                }
            }
        }.start();
    }
}
