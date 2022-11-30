// 
// Decompiled by Procyon v0.5.36
// 

package atm.physical;

import java.util.Enumeration;
import simulation.Simulation;
import banking.Receipt;

public class ReceiptPrinter
{
    public void printReceipt(final Receipt receipt) {
        final Enumeration receiptLines = receipt.getLines();
        while (receiptLines.hasMoreElements()) {
            Simulation.getInstance().printReceiptLine(receiptLines.nextElement());
        }
    }
}
