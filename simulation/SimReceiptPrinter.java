// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.TextArea;
import java.awt.Panel;

class SimReceiptPrinter extends Panel
{
    private TextArea printArea;
    private Button take;
    
    SimReceiptPrinter() {
        this.setLayout(new BorderLayout(5, 5));
        (this.printArea = new TextArea("", 9, 30, 1)).setBackground(Color.white);
        this.printArea.setForeground(Color.black);
        this.printArea.setFont(new Font("Monospaced", 0, 12));
        this.printArea.setEditable(false);
        this.add(this.printArea, "South");
        final Panel buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(1, 1));
        buttonPanel.add(this.take = new Button("Take receipt"));
        this.add(buttonPanel, "North");
        this.take.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SimReceiptPrinter.this.printArea.setText("");
                SimReceiptPrinter.this.take.setVisible(false);
            }
        });
        this.take.setVisible(false);
    }
    
    void println(final String text) {
        this.printArea.append(String.valueOf(text) + '\n');
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException ex) {}
        this.take.setVisible(true);
    }
}
