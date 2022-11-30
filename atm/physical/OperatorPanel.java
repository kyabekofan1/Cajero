// 
// Decompiled by Procyon v0.5.36
// 

package atm.physical;

import simulation.Simulation;
import banking.Money;
import atm.ATM;

public class OperatorPanel
{
    private ATM atm;
    
    public OperatorPanel(final ATM atm) {
        this.atm = atm;
    }
    
    public Money getInitialCash() {
        return Simulation.getInstance().getInitialCash();
    }
}
