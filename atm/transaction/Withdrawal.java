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

public class Withdrawal extends Transaction
{
    private int from;
    private Money amount;
    
    public Withdrawal(final ATM atm, final Session session, final Card card, final int pin) {
        super(atm, session, card, pin);
    }
    
    @Override
    protected Message getSpecificsFromCustomer() throws CustomerConsole.Cancelled {
        this.from = this.atm.getCustomerConsole().readMenuChoice("Account to withdraw from", AccountInformation.ACCOUNT_NAMES);
        final String[] amountOptions = { "$20", "$40", "60", "$100", "$200" };
        final Money[] amountValues = { new Money(20), new Money(40), new Money(60), new Money(100), new Money(200) };
        String amountMessage = "";
        boolean validAmount = false;
        while (!validAmount) {
            this.amount = amountValues[this.atm.getCustomerConsole().readMenuChoice(String.valueOf(amountMessage) + "Amount of cash to withdraw", amountOptions)];
            validAmount = this.atm.getCashDispenser().checkCashOnHand(this.amount);
            if (!validAmount) {
                amountMessage = "Insuficient cash available\n";
            }
        }
        return new Message(0, this.card, this.pin, this.serialNumber, this.from, -1, this.amount);
    }
    
    @Override
    protected Receipt completeTransaction() {
        this.atm.getCashDispenser().dispenseCash(this.amount);
        return new Receipt(this.atm, this.card, this, this.balances) {
            {
                (this.detailsPortion = new String[2])[0] = "WITHDRAWAL FROM: " + AccountInformation.ACCOUNT_ABBREVIATIONS[Withdrawal.this.from];
                this.detailsPortion[1] = "AMOUNT: " + Withdrawal.this.amount.toString();
            }
        };
    }
}
