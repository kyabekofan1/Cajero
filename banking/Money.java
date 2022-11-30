// 
// Decompiled by Procyon v0.5.36
// 

package banking;

public class Money
{
    private long cents;
    
    public Money(final int dollars) {
        this(dollars, 0);
    }
    
    public Money(final int dollars, final int cents) {
        this.cents = 100L * dollars + cents;
    }
    
    public Money(final Money toCopy) {
        this.cents = toCopy.cents;
    }
    
    @Override
    public String toString() {
        return "$" + this.cents / 100L + ((this.cents % 100L >= 10L) ? ("." + this.cents % 100L) : (".0" + this.cents % 100L));
    }
    
    public void add(final Money amountToAdd) {
        this.cents += amountToAdd.cents;
    }
    
    public void subtract(final Money amountToSubtract) {
        this.cents -= amountToSubtract.cents;
    }
    
    public boolean lessEqual(final Money compareTo) {
        return this.cents <= compareTo.cents;
    }
}
