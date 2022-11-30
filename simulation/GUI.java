// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import java.awt.Insets;
import java.awt.GridBagConstraints;
import banking.Card;
import banking.Money;
import java.awt.Container;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.Color;
import java.awt.CardLayout;
import java.awt.Panel;

class GUI extends Panel
{
    private CardLayout mainLayout;
    private ATMPanel atmPanel;
    private BillsPanel billsPanel;
    private CardPanel cardPanel;
    private LogPanel logPanel;
    
    GUI(final SimOperatorPanel operatorPanel, final SimCardReader cardReader, final SimDisplay display, final SimKeyboard keyboard, final SimCashDispenser cashDispenser, final SimEnvelopeAcceptor envelopeAcceptor, final SimReceiptPrinter receiptPrinter) {
        this.setBackground(Color.lightGray);
        this.setLayout(this.mainLayout = new CardLayout(5, 5));
        this.add(this.atmPanel = new ATMPanel(this, operatorPanel, cardReader, display, keyboard, cashDispenser, envelopeAcceptor, receiptPrinter), "ATM");
        this.add(this.billsPanel = new BillsPanel(), "BILLS");
        this.add(this.cardPanel = new CardPanel(), "CARD");
        this.add(this.logPanel = new LogPanel(this), "LOG");
        this.mainLayout.show(this, "ATM");
    }
    
    public Money getInitialCash() {
        this.mainLayout.show(this, "BILLS");
        final int numberOfBills = this.billsPanel.readBills();
        this.mainLayout.show(this, "ATM");
        return new Money(20 * numberOfBills);
    }
    
    public Card readCard() {
        this.mainLayout.show(this, "CARD");
        final int cardNumber = this.cardPanel.readCardNumber();
        this.mainLayout.show(this, "ATM");
        if (cardNumber > 0) {
            return new Card(cardNumber);
        }
        return null;
    }
    
    public void printLogLine(final String text) {
        this.logPanel.println(text);
    }
    
    void showCard(final String cardName) {
        this.mainLayout.show(this, cardName);
    }
    
    static GridBagConstraints makeConstraints(final int row, final int col, final int width, final int height, final int fill) {
        final GridBagConstraints g = new GridBagConstraints();
        g.gridy = row;
        g.gridx = col;
        g.gridheight = height;
        g.gridwidth = width;
        g.fill = fill;
        g.insets = new Insets(2, 2, 2, 2);
        g.weightx = 1.0;
        g.weighty = 1.0;
        g.anchor = 10;
        return g;
    }
}
