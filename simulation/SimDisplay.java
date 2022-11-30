// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import java.awt.Insets;
import java.util.StringTokenizer;
import java.awt.Component;
import java.awt.Font;
import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

class SimDisplay extends Panel
{
    private Label[] displayLine;
    private int currentDisplayLine;
    
    SimDisplay() {
        this.setLayout(new GridLayout(9, 1));
        this.setBackground(new Color(0, 96, 0));
        this.setForeground(Color.white);
        final Font lineFont = new Font("Monospaced", 0, 14);
        this.displayLine = new Label[9];
        for (int i = 0; i < 9; ++i) {
            (this.displayLine[i] = new Label("                                             ")).setFont(lineFont);
            this.add(this.displayLine[i]);
        }
        this.currentDisplayLine = 0;
    }
    
    void clearDisplay() {
        for (int i = 0; i < this.displayLine.length; ++i) {
            this.displayLine[i].setText("");
        }
        this.currentDisplayLine = 0;
    }
    
    void display(final String text) {
        final StringTokenizer tokenizer = new StringTokenizer(text, "\n", false);
        while (tokenizer.hasMoreTokens()) {
            try {
                this.displayLine[this.currentDisplayLine++].setText(tokenizer.nextToken());
            }
            catch (Exception ex) {}
        }
    }
    
    void setEcho(final String echo) {
        this.displayLine[this.currentDisplayLine].setText(String.valueOf("                                             ".substring(0, "                                             ".length() / 2 - echo.length())) + echo);
    }
    
    @Override
    public Insets getInsets() {
        final Insets insets2;
        final Insets insets = insets2 = super.getInsets();
        insets2.top += 5;
        final Insets insets3 = insets;
        insets3.bottom += 5;
        final Insets insets4 = insets;
        insets4.left += 10;
        final Insets insets5 = insets;
        insets5.right += 10;
        return insets;
    }
}
