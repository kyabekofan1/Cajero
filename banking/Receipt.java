// 
// Decompiled by Procyon v0.5.36
// 

package banking;

import java.util.Enumeration;
import java.util.Date;
import atm.transaction.Transaction;
import atm.ATM;

public abstract class Receipt
{
    private String[] headingPortion;
    protected String[] detailsPortion;
    private String[] balancesPortion;
    
    protected Receipt(final ATM atm, final Card card, final Transaction transaction, final Balances balances) {
        (this.headingPortion = new String[4])[0] = new Date().toString();
        this.headingPortion[1] = atm.getBankName();
        this.headingPortion[2] = "ATM #" + atm.getID() + " " + atm.getPlace();
        this.headingPortion[3] = "CARD " + (card.getNumber() + 1) + " TRANS #" + transaction.getSerialNumber();
        (this.balancesPortion = new String[2])[0] = "TOTAL BAL: " + balances.getTotal();
        this.balancesPortion[1] = "AVAILABLE: " + balances.getAvailable();
    }
    
    public Enumeration getLines() {
        return new Enumeration() {
            private int portion = 0;
            private int index = 0;
            
            @Override
            public boolean hasMoreElements() {
                return this.portion < 2 || this.index < Receipt.this.balancesPortion.length;
            }
            
            @Override
            public Object nextElement() {
                String line = null;
                switch (this.portion) {
                    case 0: {
                        line = Receipt.this.headingPortion[this.index++];
                        if (this.index >= Receipt.this.headingPortion.length) {
                            ++this.portion;
                            this.index = 0;
                            break;
                        }
                        break;
                    }
                    case 1: {
                        line = Receipt.this.detailsPortion[this.index++];
                        if (this.index >= Receipt.this.detailsPortion.length) {
                            ++this.portion;
                            this.index = 0;
                            break;
                        }
                        break;
                    }
                    case 2: {
                        line = Receipt.this.balancesPortion[this.index++];
                        break;
                    }
                }
                return line;
            }
        };
    }
}
