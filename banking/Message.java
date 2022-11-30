// 
// Decompiled by Procyon v0.5.36
// 

package banking;

public class Message
{
    public static final int WITHDRAWAL = 0;
    public static final int INITIATE_DEPOSIT = 1;
    public static final int COMPLETE_DEPOSIT = 2;
    public static final int TRANSFER = 3;
    public static final int INQUIRY = 4;
    private int messageCode;
    private Card card;
    private int pin;
    private int serialNumber;
    private int fromAccount;
    private int toAccount;
    private Money amount;
    
    public Message(final int messageCode, final Card card, final int pin, final int serialNumber, final int fromAccount, final int toAccount, final Money amount) {
        this.messageCode = messageCode;
        this.card = card;
        this.pin = pin;
        this.serialNumber = serialNumber;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }
    
    @Override
    public String toString() {
        String result = "";
        switch (this.messageCode) {
            case 0: {
                result = String.valueOf(result) + "WITHDRAW";
                break;
            }
            case 1: {
                result = String.valueOf(result) + "INIT_DEP";
                break;
            }
            case 2: {
                result = String.valueOf(result) + "COMP_DEP";
                break;
            }
            case 3: {
                result = String.valueOf(result) + "TRANSFER";
                break;
            }
            case 4: {
                result = String.valueOf(result) + "INQUIRY ";
                break;
            }
        }
        result = String.valueOf(result) + " CARD# " + this.card.getNumber();
        result = String.valueOf(result) + " TRANS# " + this.serialNumber;
        if (this.fromAccount >= 0) {
            result = String.valueOf(result) + " FROM  " + this.fromAccount;
        }
        else {
            result = String.valueOf(result) + " NO FROM";
        }
        if (this.toAccount >= 0) {
            result = String.valueOf(result) + " TO  " + this.toAccount;
        }
        else {
            result = String.valueOf(result) + " NO TO";
        }
        if (!this.amount.lessEqual(new Money(0))) {
            result = String.valueOf(result) + " " + this.amount;
        }
        else {
            result = String.valueOf(result) + " NO AMOUNT";
        }
        return result;
    }
    
    public void setPIN(final int pin) {
        this.pin = pin;
    }
    
    public int getMessageCode() {
        return this.messageCode;
    }
    
    public Card getCard() {
        return this.card;
    }
    
    public int getPIN() {
        return this.pin;
    }
    
    public int getSerialNumber() {
        return this.serialNumber;
    }
    
    public int getFromAccount() {
        return this.fromAccount;
    }
    
    public int getToAccount() {
        return this.toAccount;
    }
    
    public Money getAmount() {
        return this.amount;
    }
}
