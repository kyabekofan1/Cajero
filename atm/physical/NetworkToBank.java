// 
// Decompiled by Procyon v0.5.36
// 

package atm.physical;

import simulation.Simulation;
import banking.Status;
import banking.Balances;
import banking.Message;
import java.net.InetAddress;

public class NetworkToBank
{
    private Log log;
    private InetAddress bankAddress;
    
    public NetworkToBank(final Log log, final InetAddress bankAddress) {
        this.log = log;
        this.bankAddress = bankAddress;
    }
    
    public void openConnection() {
    }
    
    public void closeConnection() {
    }
    
    public Status sendMessage(final Message message, final Balances balances) {
        this.log.logSend(message);
        final Status result = Simulation.getInstance().sendMessage(message, balances);
        this.log.logResponse(result);
        return result;
    }
}
