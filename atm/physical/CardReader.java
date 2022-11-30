// 
// Decompiled by Procyon v0.5.36
// 

package atm.physical;

import simulation.Simulation;
import banking.Card;
import atm.ATM;

public class CardReader
{
    private ATM atm;
    
    public CardReader(final ATM atm) {
        this.atm = atm;
    }
    
    public Card readCard() {
        return Simulation.getInstance().readCard();
    }
    
    public void ejectCard() {
        Simulation.getInstance().ejectCard();
    }
    
    public void retainCard() {
        Simulation.getInstance().retainCard();
    }
}
