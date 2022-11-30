// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.Panel;

class ATMPanel extends Panel
{
    public static final int DISPLAYABLE_LINES = 9;
    public static final String BLANK_DISPLAY_LINE = "                                             ";
    public static final int PRINTABLE_LINES = 9;
    public static final int PRINTABLE_CHARS = 30;
    private static final int DISPLAY_ROW = 0;
    private static final int DISPLAY_COL = 0;
    private static final int DISPLAY_WIDTH = 3;
    private static final int DISPLAY_HEIGHT = 1;
    private static final int DISPLAY_FILL = 1;
    private static final int PRINTER_ROW = 0;
    private static final int PRINTER_COL = 3;
    private static final int PRINTER_WIDTH = 1;
    private static final int PRINTER_HEIGHT = 1;
    private static final int PRINTER_FILL = 0;
    private static final int ENVELOPE_ROW = 1;
    private static final int ENVELOPE_COL = 0;
    private static final int ENVELOPE_WIDTH = 1;
    private static final int ENVELOPE_HEIGHT = 1;
    private static final int ENVELOPE_FILL = 0;
    private static final int DISPENSER_ROW = 1;
    private static final int DISPENSER_COL = 1;
    private static final int DISPENSER_WIDTH = 1;
    private static final int DISPENSER_HEIGHT = 1;
    private static final int DISPENSER_FILL = 0;
    private static final int READER_ROW = 1;
    private static final int READER_COL = 2;
    private static final int READER_WIDTH = 1;
    private static final int READER_HEIGHT = 1;
    private static final int READER_FILL = 0;
    private static final int KEYBOARD_ROW = 1;
    private static final int KEYBOARD_COL = 3;
    private static final int KEYBOARD_WIDTH = 1;
    private static final int KEYBOARD_HEIGHT = 1;
    private static final int KEYBOARD_FILL = 0;
    private static final int SHOW_LOG_BUTTON_ROW = 2;
    private static final int SHOW_LOG_BUTTON_COL = 0;
    private static final int SHOW_LOG_BUTTON_WIDTH = 1;
    private static final int SHOW_LOG_BUTTON_HEIGHT = 1;
    private static final int SHOW_LOG_BUTTON_FILL = 1;
    private static final int OPERATOR_ROW = 2;
    private static final int OPERATOR_COL = 1;
    private static final int OPERATOR_WIDTH = 3;
    private static final int OPERATOR_HEIGHT = 1;
    private static final int OPERATOR_FILL = 1;
    private static final int TOTAL_ROWS = 3;
    private static final int TOTAL_COLS = 3;
    
    ATMPanel(final GUI gui, final SimOperatorPanel operatorPanel, final SimCardReader cardReader, final SimDisplay display, final SimKeyboard keyboard, final SimCashDispenser cashDispenser, final SimEnvelopeAcceptor envelopeAcceptor, final SimReceiptPrinter receiptPrinter) {
        final GridBagLayout atmLayout = new GridBagLayout();
        this.setLayout(atmLayout);
        this.add(operatorPanel);
        atmLayout.setConstraints(operatorPanel, GUI.makeConstraints(2, 1, 3, 1, 1));
        final Panel cardReaderPanel = new Panel();
        cardReaderPanel.setLayout(new GridLayout(1, 1));
        cardReaderPanel.add(cardReader);
        this.add(cardReaderPanel);
        atmLayout.setConstraints(cardReaderPanel, GUI.makeConstraints(1, 2, 1, 1, 0));
        this.add(display);
        atmLayout.setConstraints(display, GUI.makeConstraints(0, 0, 3, 1, 1));
        this.add(keyboard);
        atmLayout.setConstraints(keyboard, GUI.makeConstraints(1, 3, 1, 1, 0));
        this.add(cashDispenser);
        atmLayout.setConstraints(cashDispenser, GUI.makeConstraints(1, 1, 1, 1, 0));
        final Panel envelopeAcceptorPanel = new Panel();
        envelopeAcceptorPanel.setLayout(new GridLayout(1, 1));
        envelopeAcceptorPanel.add(envelopeAcceptor);
        this.add(envelopeAcceptorPanel);
        atmLayout.setConstraints(envelopeAcceptorPanel, GUI.makeConstraints(1, 0, 1, 1, 0));
        envelopeAcceptor.setVisible(false);
        this.add(receiptPrinter);
        atmLayout.setConstraints(receiptPrinter, GUI.makeConstraints(0, 3, 1, 1, 0));
        final Panel showLogButtonPanel = new Panel();
        showLogButtonPanel.setBackground(operatorPanel.getBackground());
        final Button showLogButton = new Button("Show Log");
        showLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                gui.showCard("LOG");
            }
        });
        showLogButtonPanel.add(showLogButton);
        this.add(showLogButtonPanel);
        atmLayout.setConstraints(showLogButtonPanel, GUI.makeConstraints(2, 0, 1, 1, 1));
    }
}
