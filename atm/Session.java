// 
// Decompiled by Procyon v0.5.36
// 

package atm;

import banking.Status;
import banking.Card;
import atm.transaction.Transaction;
import atm.physical.CustomerConsole;
import simulation.Simulation;

public class Session
{
    private ATM atm;
    private int pin;
    private int state;
    private static final int READING_CARD_STATE = 1;
    private static final int READING_PIN_STATE = 2;
    private static final int CHOOSING_TRANSACTION_STATE = 3;
    private static final int PERFORMING_TRANSACTION_STATE = 4;
    private static final int EJECTING_CARD_STATE = 5;
    private static final int FINAL_STATE = 6;
    
    public Session(final ATM atm) {
        this.atm = atm;
        this.state = 1;
    }
    
    public void performSession() {
        Card card = null;
        Transaction currentTransaction = null;
        while (this.state != 6) {
            switch (this.state) {
                case 1: {
                    card = this.atm.getCardReader().readCard();
                    if (card != null) {
                        this.state = 2;
                        continue;
                    }
                    this.atm.getCustomerConsole().display("Unable to read card");
                    this.state = 5;
                    continue;
                }
                default: {
                    continue;
                }
                case 2: {
                    try {
                        this.pin = this.atm.getCustomerConsole().readPIN("Please enter your PIN\nThen press ENTER");
                        Status stat = Simulation.getInstance().getSimulatedBank().checkPIN(card, this.pin);
                        if (stat.isInvalidPIN()) {
                            for (int i = 0; i < 2 && stat.isInvalidPIN(); stat = Simulation.getInstance().getSimulatedBank().checkPIN(card, this.pin), ++i) {
                                this.pin = this.atm.getCustomerConsole().readPIN("PIN was incorrect\nPlease re-enter your PIN\nThen press ENTER");
                                this.atm.getCustomerConsole().display("");
                            }
                            if (!stat.isInvalidPIN()) {
                                continue;
                            }
                            this.atm.getCardReader().retainCard();
                            this.atm.getCustomerConsole().display("Your card has been retained\nPlease contact the bank.");
                            try {
                                Thread.sleep(5000L);
                            }
                            catch (InterruptedException ex) {}
                            this.atm.getCustomerConsole().display("");
                            this.state = 6;
                        }
                        else if (!stat.isSuccess()) {
                            this.atm.getCustomerConsole().display(stat.toString());
                            this.state = 5;
                        }
                        else {
                            this.state = 3;
                        }
                    }
                    catch (CustomerConsole.Cancelled e) {
                        this.state = 5;
                    }
                    continue;
                }
                case 3: {
                    try {
                        currentTransaction = Transaction.makeTransaction(this.atm, this, card, this.pin);
                        this.state = 4;
                    }
                    catch (CustomerConsole.Cancelled e) {
                        this.state = 5;
                    }
                    continue;
                }
                case 4: {
                    try {
                        final boolean doAgain = currentTransaction.performTransaction();
                        if (doAgain) {
                            this.state = 3;
                        }
                        else {
                            this.state = 5;
                        }
                    }
                    catch (Transaction.CardRetained e2) {
                        this.state = 6;
                    }
                    continue;
                }
                case 5: {
                    this.atm.getCardReader().ejectCard();
                    this.state = 6;
                    continue;
                }
            }
        }
    }
    
    public void setPIN(final int pin) {
        this.pin = pin;
    }
}
