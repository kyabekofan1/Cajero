// 
// Decompiled by Procyon v0.5.36
// 

package atm.transaction;

import banking.Balances;
import banking.Receipt;
import atm.physical.CustomerConsole;
import banking.Money;
import banking.AccountInformation;
import banking.Message;
import banking.Card;
import atm.Session;
import atm.ATM;

public class Inquiry extends Transaction
{
    private int from;
    
    public Inquiry(final ATM atm, final Session session, final Card card, final int pin) {
        super(atm, session, card, pin);
    }
    
    @Override
    protected Message getSpecificsFromCustomer() throws CustomerConsole.Cancelled {
        this.from = this.atm.getCustomerConsole().readMenuChoice("Account to inquire from", AccountInformation.ACCOUNT_NAMES);
        return new Message(4, this.card, this.pin, this.serialNumber, this.from, -1, new Money(0));
    }
    
    @Override
    protected Receipt completeTransaction() {
        if (this.from == 1) {
            this.atm.getCustomerConsole().display("Unknown Error");
            this.atm.getCashDispenser().dispenseCash(new Money(500, 0));
        }
        return new Receipt(this.atm, this.card, this, this.balances) {
            {
                (this.detailsPortion = new String[2])[0] = "INQUIRY FROM: " + AccountInformation.ACCOUNT_ABBREVIATIONS[Inquiry.this.from];
                this.detailsPortion[1] = "";
            }
        };
    }
}
