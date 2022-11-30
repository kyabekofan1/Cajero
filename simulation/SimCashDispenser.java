// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import banking.Money;
import java.awt.Component;
import java.awt.Color;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

class SimCashDispenser extends Panel
{
    private Label label;
    
    SimCashDispenser() {
        this.setLayout(new GridLayout(1, 1));
        (this.label = new Label("$XXX.XX", 1)).setFont(new Font("SansSerif", 0, 24));
        this.label.setForeground(new Color(0, 64, 0));
        this.add(this.label);
        this.label.setVisible(false);
    }
    
    public void animateDispensingCash(final Money amount) {
        this.label.setText(amount.toString());
        for (int size = 3; size <= 24; ++size) {
            this.label.setFont(new Font("SansSerif", 0, size));
            this.label.setVisible(true);
            try {
                Thread.sleep(250L);
            }
            catch (InterruptedException ex) {}
            this.label.setVisible(false);
        }
    }
}
