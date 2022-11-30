// 
// Decompiled by Procyon v0.5.36
// 

package atm.transaction;

import banking.Receipt;
import banking.Status;
import atm.physical.CustomerConsole;
import banking.Balances;
import banking.Message;
import banking.Card;
import atm.Session;
import atm.ATM;

public abstract class Transaction
{
    protected ATM atm;
    protected Session session;
    protected Card card;
    protected int pin;
    protected int serialNumber;
    protected Message message;
    protected Balances balances;
    private static final String[] TRANSACTION_TYPES_MENU;
    private static int nextSerialNumber;
    private int state;
    private static final int GETTING_SPECIFICS_STATE = 1;
    private static final int SENDING_TO_BANK_STATE = 2;
    private static final int INVALID_PIN_STATE = 3;
    private static final int COMPLETING_TRANSACTION_STATE = 4;
    private static final int PRINTING_RECEIPT_STATE = 5;
    private static final int ASKING_DO_ANOTHER_STATE = 6;
    
    static {
        TRANSACTION_TYPES_MENU = new String[] { "Withdrawal", "Deposit", "Transfer", "Balance Inquiry" };
        Transaction.nextSerialNumber = 1;
    }
    
    protected Transaction(final ATM atm, final Session session, final Card card, final int pin) {
        this.atm = atm;
        this.session = session;
        this.card = card;
        this.pin = pin;
        this.serialNumber = Transaction.nextSerialNumber++;
        this.balances = new Balances();
        this.state = 1;
    }
    
    public static Transaction makeTransaction(final ATM atm, final Session session, final Card card, final int pin) throws CustomerConsole.Cancelled {
        final int choice = atm.getCustomerConsole().readMenuChoice("Please choose transaction type", Transaction.TRANSACTION_TYPES_MENU);
        switch (choice) {
            case 0: {
                return new Withdrawal(atm, session, card, pin);
            }
            case 1: {
                return new Deposit(atm, session, card, pin);
            }
            case 2: {
                return new Transfer(atm, session, card, pin);
            }
            case 3: {
                return new Inquiry(atm, session, card, pin);
            }
            default: {
                return null;
            }
        }
    }
    
    public boolean performTransaction() throws CardRetained {
        String doAnotherMessage = "";
        Status status = null;
        Receipt receipt = null;
        while (true) {
            switch (this.state) {
                case 1: {
                    try {
                        this.message = this.getSpecificsFromCustomer();
                        this.atm.getCustomerConsole().display("");
                        this.state = 2;
                    }
                    catch (CustomerConsole.Cancelled e) {
                        doAnotherMessage = "Last transaction was cancelled";
                        this.state = 6;
                    }
                    continue;
                }
                case 2: {
                    status = this.atm.getNetworkToBank().sendMessage(this.message, this.balances);
                    if (status.isInvalidPIN()) {
                        this.state = 3;
                        continue;
                    }
                    if (status.isSuccess()) {
                        this.state = 4;
                        continue;
                    }
                    doAnotherMessage = status.getMessage();
                    this.state = 6;
                    continue;
                }
                case 3: {
                    try {
                        status = this.performInvalidPINExtension();
                        if (status.isSuccess()) {
                            this.state = 4;
                        }
                        else {
                            doAnotherMessage = status.getMessage();
                            this.state = 6;
                        }
                    }
                    catch (CustomerConsole.Cancelled e) {
                        doAnotherMessage = "Last transaction was cancelled";
                        this.state = 6;
                    }
                    continue;
                }
                case 4: {
                    try {
                        receipt = this.completeTransaction();
                        this.state = 5;
                    }
                    catch (CustomerConsole.Cancelled e) {
                        doAnotherMessage = "Last transaction was cancelled";
                        this.state = 6;
                    }
                    continue;
                }
                case 5: {
                    this.atm.getReceiptPrinter().printReceipt(receipt);
                    this.state = 6;
                    continue;
                }
                case 6: {
                    if (doAnotherMessage.length() > 0) {
                        doAnotherMessage = String.valueOf(doAnotherMessage) + "\n";
                    }
                    try {
                        final String[] yesNoMenu = { "Yes", "No" };
                        final boolean doAgain = this.atm.getCustomerConsole().readMenuChoice(String.valueOf(doAnotherMessage) + "Would you like to do another transaction?", yesNoMenu) == 0;
                        return doAgain;
                    }
                    catch (CustomerConsole.Cancelled e) {
                        return false;
                    }
                    continue;
                }
            }
        }
    }
    
    public Status performInvalidPINExtension() throws CustomerConsole.Cancelled, CardRetained {
        Status status = null;
        for (int i = 0; i < 3; ++i) {
            this.pin = this.atm.getCustomerConsole().readPIN("PIN was incorrect\nPlease re-enter your PIN\nThen press ENTER");
            this.atm.getCustomerConsole().display("");
            this.message.setPIN(this.pin);
            status = this.atm.getNetworkToBank().sendMessage(this.message, this.balances);
            if (!status.isInvalidPIN()) {
                this.session.setPIN(this.pin);
                return status;
            }
        }
        this.atm.getCardReader().retainCard();
        this.atm.getCustomerConsole().display("Your card has been retained\nPlease contact the bank.");
        try {
            Thread.sleep(5000L);
        }
        catch (InterruptedException ex) {}
        this.atm.getCustomerConsole().display("");
        throw new CardRetained();
    }
    
    public int getSerialNumber() {
        return this.serialNumber;
    }
    
    protected abstract Message getSpecificsFromCustomer() throws CustomerConsole.Cancelled;
    
    protected abstract Receipt completeTransaction() throws CustomerConsole.Cancelled;
    
    public static class CardRetained extends Exception
    {
        public CardRetained() {
            super("Card retained due to too many invalid PINs");
        }
    }
}
