// 
// Decompiled by Procyon v0.5.36
// 

package atm.physical;

import simulation.Simulation;
import banking.Money;

public class CashDispenser
{
    private Log log;
    private Money cashOnHand;
    
    public CashDispenser(final Log log) {
        this.log = log;
        this.cashOnHand = new Money(0);
    }
    
    public void setInitialCash(final Money initialCash) {
        this.cashOnHand = initialCash;
    }
    
    public boolean checkCashOnHand(final Money amount) {
        return amount.lessEqual(this.cashOnHand);
    }
    
    public void dispenseCash(final Money amount) {
        this.cashOnHand.subtract(amount);
        Simulation.getInstance().dispenseCash(amount);
        this.log.logCashDispensed(amount);
    }
}
