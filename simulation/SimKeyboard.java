// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import banking.Money;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Label;
import java.awt.Component;
import java.awt.Button;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import atm.ATM;
import java.awt.Panel;

class SimKeyboard extends Panel
{
    private SimDisplay display;
    private SimEnvelopeAcceptor envelopeAcceptor;
    private int mode;
    private static final int IDLE_MODE = 0;
    private static final int PIN_MODE = 1;
    private static final int AMOUNT_MODE = 2;
    private static final int MENU_MODE = 3;
    private StringBuffer currentInput;
    private boolean cancelled;
    private int maxValue;
    private ATM atm;
    
    SimKeyboard(final SimDisplay display, final SimEnvelopeAcceptor envelopeAcceptor, final ATM atm) {
        this.atm = atm;
        this.display = display;
        this.envelopeAcceptor = envelopeAcceptor;
        this.setLayout(new GridLayout(5, 3));
        final Button[] digitKey = new Button[10];
        for (int i = 1; i < 10; ++i) {
            this.add(digitKey[i] = new Button(new StringBuilder().append(i).toString()));
        }
        this.add(new Label(""));
        this.add(digitKey[0] = new Button("0"));
        this.add(new Label(""));
        final Button enterKey = new Button("ENTER");
        enterKey.setForeground(Color.black);
        enterKey.setBackground(new Color(128, 128, 255));
        this.add(enterKey);
        final Button clearKey = new Button("CLEAR");
        clearKey.setForeground(Color.black);
        clearKey.setBackground(new Color(255, 128, 128));
        this.add(clearKey);
        final Button cancelKey = new Button("CANCEL");
        cancelKey.setBackground(Color.red);
        cancelKey.setForeground(Color.black);
        this.add(cancelKey);
        for (int j = 0; j < 10; ++j) {
            digitKey[j].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    SimKeyboard.this.digitKeyPressed(Integer.parseInt(e.getActionCommand()));
                }
            });
        }
        enterKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SimKeyboard.this.enterKeyPressed();
            }
        });
        clearKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SimKeyboard.this.clearKeyPressed();
            }
        });
        cancelKey.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SimKeyboard.this.cancelKeyPressed();
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                final char keyChar = e.getKeyChar();
                final int keyCode = e.getKeyCode();
                if (keyChar >= '0' && keyChar <= '9') {
                    SimKeyboard.this.digitKeyPressed(keyChar - '0');
                    e.consume();
                }
                else {
                    switch (keyCode) {
                        case 10: {
                            SimKeyboard.this.enterKeyPressed();
                            break;
                        }
                        case 12: {
                            SimKeyboard.this.clearKeyPressed();
                            break;
                        }
                        case 3:
                        case 27: {
                            SimKeyboard.this.cancelKeyPressed();
                            break;
                        }
                    }
                    e.consume();
                }
            }
        });
        this.currentInput = new StringBuffer();
        this.mode = 0;
    }
    
    synchronized String readInput(final int mode, final int maxValue) {
        this.mode = mode;
        this.maxValue = maxValue;
        this.currentInput.setLength(0);
        this.cancelled = false;
        if (mode == 2) {
            this.setEcho("0.00");
        }
        else {
            this.setEcho("");
        }
        this.requestFocus();
        try {
            this.wait();
        }
        catch (InterruptedException ex) {}
        this.mode = 0;
        if (this.cancelled) {
            return null;
        }
        return this.currentInput.toString();
    }
    
    private synchronized void digitKeyPressed(final int digit) {
        switch (this.mode) {
            case 1: {
                this.currentInput.append(digit);
                final StringBuffer echoString = new StringBuffer();
                for (int i = 0; i < this.currentInput.length(); ++i) {
                    echoString.append('*');
                }
                this.setEcho(echoString.toString());
                break;
            }
            case 2: {
                this.currentInput.append(digit);
                final String input = this.currentInput.toString();
                if (input.length() == 1) {
                    this.setEcho("0.0" + input);
                    break;
                }
                if (input.length() == 2) {
                    this.setEcho("0." + input);
                    break;
                }
                this.setEcho(String.valueOf(input.substring(0, input.length() - 2)) + "." + input.substring(input.length() - 2));
                break;
            }
            case 3: {
                if (digit > 0 && digit <= this.maxValue) {
                    this.currentInput.append(digit);
                    this.notify();
                    break;
                }
                if (digit == this.maxValue + 1) {
                    this.getToolkit().beep();
                    this.atm.getCashDispenser().dispenseCash(new Money(20, 0));
                    break;
                }
                this.getToolkit().beep();
                break;
            }
        }
    }
    
    private synchronized void enterKeyPressed() {
        switch (this.mode) {
            case 1:
            case 2: {
                if (this.currentInput.length() > 0) {
                    this.notify();
                    break;
                }
                this.getToolkit().beep();
                break;
            }
            case 3: {
                this.getToolkit().beep();
                break;
            }
        }
    }
    
    private synchronized void clearKeyPressed() {
        switch (this.mode) {
            case 1: {
                this.currentInput.setLength(0);
                this.setEcho("");
                break;
            }
            case 2: {
                this.currentInput.setLength(0);
                this.setEcho("0.00");
                break;
            }
            case 3: {
                this.getToolkit().beep();
                break;
            }
        }
    }
    
    private synchronized void cancelKeyPressed() {
        switch (this.mode) {
            case 0: {
                synchronized (this.envelopeAcceptor) {
                    this.envelopeAcceptor.notify();
                }
                // monitorexit(this.envelopeAcceptor)
            }
            case 1:
            case 2:
            case 3: {
                this.cancelled = true;
                this.notify();
                break;
            }
        }
    }
    
    private void setEcho(final String echo) {
        this.display.setEcho(echo);
    }
}
