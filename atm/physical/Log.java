// 
// Decompiled by Procyon v0.5.36
// 

package atm.physical;

import banking.Money;
import banking.Status;
import simulation.Simulation;
import banking.Message;

public class Log
{
    public void logSend(final Message message) {
        Simulation.getInstance().printLogLine("Message:   " + message.toString());
    }
    
    public void logResponse(final Status response) {
        Simulation.getInstance().printLogLine("Response:  " + response.toString());
    }
    
    public void logCashDispensed(final Money amount) {
        Simulation.getInstance().printLogLine("Dispensed: " + amount.toString());
    }
    
    public void logEnvelopeAccepted() {
        Simulation.getInstance().printLogLine("Envelope:  received");
    }
}
