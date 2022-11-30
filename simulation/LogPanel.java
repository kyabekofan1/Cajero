// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Label;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.Panel;

class LogPanel extends Panel
{
    private TextArea logPrintArea;
    
    LogPanel(final GUI gui) {
        final GridBagLayout logLayout = new GridBagLayout();
        this.setLayout(logLayout);
        this.setFont(new Font("Monospaced", 0, 14));
        final Label logPanelLabel = new Label("Log", 1);
        this.add(logPanelLabel);
        GridBagConstraints constraints = GUI.makeConstraints(0, 0, 1, 1, 0);
        constraints.weighty = 0.0;
        logLayout.setConstraints(logPanelLabel, constraints);
        (this.logPrintArea = new TextArea()).setBackground(Color.white);
        this.logPrintArea.setForeground(Color.black);
        this.logPrintArea.setFont(new Font("Monospaced", 0, 12));
        this.logPrintArea.setEditable(false);
        this.add(this.logPrintArea);
        constraints = GUI.makeConstraints(1, 0, 1, 1, 1);
        constraints.weighty = 1.0;
        logLayout.setConstraints(this.logPrintArea, constraints);
        final Panel logButtonPanel = new Panel();
        logButtonPanel.setLayout(new FlowLayout());
        final Button clearLogButton = new Button("Clear Log");
        clearLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                LogPanel.this.logPrintArea.setText("");
            }
        });
        logButtonPanel.add(clearLogButton);
        final Button dismissLogButton = new Button(" Hide Log ");
        dismissLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                gui.showCard("ATM");
            }
        });
        logButtonPanel.add(dismissLogButton);
        this.add(logButtonPanel);
        constraints = GUI.makeConstraints(2, 0, 1, 1, 0);
        constraints.weighty = 0.0;
        logLayout.setConstraints(logButtonPanel, constraints);
    }
    
    void println(final String text) {
        this.logPrintArea.append(String.valueOf(text) + "\n");
    }
}
