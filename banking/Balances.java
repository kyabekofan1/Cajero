// 
// Decompiled by Procyon v0.5.36
// 

package banking;

public class Balances
{
    private Money total;
    private Money available;
    
    public void setBalances(final Money total, final Money available) {
        this.total = total;
        this.available = available;
    }
    
    public Money getTotal() {
        return this.total;
    }
    
    public Money getAvailable() {
        return this.available;
    }
}
