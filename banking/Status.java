// 
// Decompiled by Procyon v0.5.36
// 

package banking;

public abstract class Status
{
    @Override
    public String toString() {
        if (this.isSuccess()) {
            return "SUCCESS";
        }
        if (this.isInvalidPIN()) {
            return "INVALID PIN";
        }
        return "FAILURE " + this.getMessage();
    }
    
    public abstract boolean isSuccess();
    
    public abstract boolean isInvalidPIN();
    
    public abstract String getMessage();
}
