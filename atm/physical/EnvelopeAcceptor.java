// 
// Decompiled by Procyon v0.5.36
// 

package atm.physical;

import simulation.Simulation;

public class EnvelopeAcceptor
{
    private Log log;
    
    public EnvelopeAcceptor(final Log log) {
        this.log = log;
    }
    
    public void acceptEnvelope() throws CustomerConsole.Cancelled {
        final boolean inserted = Simulation.getInstance().acceptEnvelope();
        if (inserted) {
            this.log.logEnvelopeAccepted();
            return;
        }
        throw new CustomerConsole.Cancelled();
    }
}
