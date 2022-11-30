// 
// Decompiled by Procyon v0.5.36
// 

package atm.transaction;

import banking.Balances;
import banking.Receipt;
import atm.physical.CustomerConsole;
import banking.AccountInformation;
import banking.Message;
import banking.Card;
import atm.Session;
import atm.ATM;
import banking.Money;

public class Transfer extends Transaction
{
    private int from;
    private int to;
    private Money amount;
    
    public Transfer(final ATM atm, final Session session, final Card card, final int pin) {
        super(atm, session, card, pin);
    }
    
    @Override
    protected Message getSpecificsFromCustomer() throws CustomerConsole.Cancelled {
        this.from = this.atm.getCustomerConsole().readMenuChoice("Account to transfer from", AccountInformation.ACCOUNT_NAMES);
        this.to = this.atm.getCustomerConsole().readMenuChoice("Account to transfer to", AccountInformation.ACCOUNT_NAMES);
        this.amount = this.atm.getCustomerConsole().readAmount("Amount to transfer");
        return new Message(3, this.card, this.pin, this.serialNumber, this.from, this.to, this.amount);
    }
    
    @Override
    protected Receipt completeTransaction() {
        return new Receipt(this.atm, this.card, this, this.balances) {
            {
                (this.detailsPortion = new String[2])[0] = "TRANSFER FROM: " + AccountInformation.ACCOUNT_ABBREVIATIONS[Transfer.this.to] + " TO: " + AccountInformation.ACCOUNT_ABBREVIATIONS[Transfer.this.from];
                this.detailsPortion[1] = "AMOUNT: " + Transfer.this.amount.toString();
            }
        };
    }
}
