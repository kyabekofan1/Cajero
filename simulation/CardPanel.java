// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import java.awt.Label;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.Panel;

class CardPanel extends Panel
{
    private TextField cardNumberField;
    
    CardPanel() {
        this.setLayout(new GridLayout(0, 1, 0, 0));
        this.setFont(new Font("Monospaced", 0, 14));
        this.add(new Label("A real ATM would have a magnetic", 1));
        this.add(new Label("stripe reader to read the card", 1));
        this.add(new Label("For purposes of the simulation,", 1));
        this.add(new Label("please enter the card number manually.", 1));
        this.add(new Label("Then press RETURN", 1));
        this.add(new Label("(An invalid integer or an integer not", 1));
        this.add(new Label("greater than zero will be treated as", 1));
        this.add(new Label("an unreadable card)", 1));
        (this.cardNumberField = new TextField(30)).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                synchronized (CardPanel.this) {
                    CardPanel.this.notify();
                }
                // monitorexit(this.this$0)
            }
        });
        final Panel cardNumberPanel = new Panel();
        cardNumberPanel.add(this.cardNumberField);
        this.add(cardNumberPanel);
    }
    
    synchronized int readCardNumber() {
        this.cardNumberField.setText("");
        this.cardNumberField.requestFocus();
        try {
            this.wait();
        }
        catch (InterruptedException ex) {}
        int cardNumber;
        try {
            cardNumber = Integer.parseInt(this.cardNumberField.getText());
            if (cardNumber <= 0) {
                cardNumber = -1;
            }
        }
        catch (NumberFormatException e) {
            cardNumber = -1;
        }
        return cardNumber;
    }
}
