// 
// Decompiled by Procyon v0.5.36
// 

package atm.transaction;

import banking.Status;
import banking.Balances;
import banking.Receipt;
import atm.physical.CustomerConsole;
import banking.AccountInformation;
import banking.Message;
import banking.Card;
import atm.Session;
import atm.ATM;
import banking.Money;

public class Deposit extends Transaction
{
    private int to;
    private Money amount;
    
    public Deposit(final ATM atm, final Session session, final Card card, final int pin) {
        super(atm, session, card, pin);
    }
    
    @Override
    protected Message getSpecificsFromCustomer() throws CustomerConsole.Cancelled {
        this.to = this.atm.getCustomerConsole().readMenuChoice("Account to deposit to", AccountInformation.ACCOUNT_NAMES);
        this.amount = this.atm.getCustomerConsole().readAmount("Amount to deposit");
        return new Message(1, this.card, this.pin, this.serialNumber, -1, this.to, this.amount);
    }
    
    @Override
    protected Receipt completeTransaction() throws CustomerConsole.Cancelled {
        this.atm.getEnvelopeAcceptor().acceptEnvelope();
        final Status status = this.atm.getNetworkToBank().sendMessage(new Message(2, this.card, this.pin, this.serialNumber, -1, this.to, this.amount), this.balances);
        return new Receipt(this.atm, this.card, this, this.balances) {
            {
                (this.detailsPortion = new String[2])[0] = "DEPOSIT TO: " + AccountInformation.ACCOUNT_ABBREVIATIONS[Deposit.this.to];
                this.detailsPortion[1] = "AMOUNT: " + Deposit.this.amount.toString();
            }
        };
    }
}
