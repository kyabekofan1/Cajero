// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import banking.Card;
import banking.Status;
import banking.Balances;
import banking.Message;
import banking.Money;

public class SimulatedBank
{
    private static final int[] PIN;
    private static final int[][] ACCOUNT_NUMBER;
    private static Money[] WITHDRAWALS_TODAY;
    private static final Money DAILY_WITHDRAWAL_LIMIT;
    private Money[] BALANCE;
    private Money[] AVAILABLE_BALANCE;
    
    static {
        PIN = new int[] { 0, 42, 1234 };
        ACCOUNT_NUMBER = new int[][] { new int[3], { 1, 2, 0 }, { 1, 0, 3 } };
        SimulatedBank.WITHDRAWALS_TODAY = new Money[] { new Money(0), new Money(0), new Money(0) };
        DAILY_WITHDRAWAL_LIMIT = new Money(300);
    }
    
    public SimulatedBank() {
        this.BALANCE = new Money[] { new Money(0), new Money(100), new Money(1000), new Money(5000) };
        this.AVAILABLE_BALANCE = new Money[] { new Money(0), new Money(100), new Money(1000), new Money(5000) };
    }
    
    public Status handleMessage(final Message message, final Balances balances) {
        final int cardNumber = message.getCard().getNumber();
        if (cardNumber < 1 || cardNumber >= SimulatedBank.PIN.length) {
            return new Failure("Invalid card");
        }
        if (message.getPIN() != SimulatedBank.PIN[cardNumber]) {
            return new InvalidPIN();
        }
        switch (message.getMessageCode()) {
            case 0: {
                return this.withdrawal(message, balances);
            }
            case 1: {
                return this.initiateDeposit(message);
            }
            case 2: {
                return this.completeDeposit(message, balances);
            }
            case 3: {
                return this.transfer(message, balances);
            }
            case 4: {
                return this.inquiry(message, balances);
            }
            default: {
                return null;
            }
        }
    }
    
    private Status withdrawal(final Message message, final Balances balances) {
        final int cardNumber = message.getCard().getNumber();
        final int accountNumber = SimulatedBank.ACCOUNT_NUMBER[cardNumber][message.getFromAccount()];
        if (accountNumber == 0) {
            return new Failure("Invalid account type");
        }
        final Money amount = message.getAmount();
        final Money limitRemaining = new Money(SimulatedBank.DAILY_WITHDRAWAL_LIMIT);
        limitRemaining.subtract(SimulatedBank.WITHDRAWALS_TODAY[cardNumber]);
        if (!amount.lessEqual(limitRemaining)) {
            return new Failure("Daily withdrawal limit exceeded");
        }
        if (!amount.lessEqual(this.AVAILABLE_BALANCE[accountNumber])) {
            return new Failure("Insufficient available balance");
        }
        SimulatedBank.WITHDRAWALS_TODAY[cardNumber].add(amount);
        this.BALANCE[accountNumber].subtract(amount);
        this.AVAILABLE_BALANCE[accountNumber].subtract(amount);
        balances.setBalances(this.BALANCE[accountNumber], this.AVAILABLE_BALANCE[accountNumber]);
        return new Success(null);
    }
    
    private Status initiateDeposit(final Message message) {
        final int cardNumber = message.getCard().getNumber();
        final int accountNumber = SimulatedBank.ACCOUNT_NUMBER[cardNumber][message.getToAccount()];
        if (accountNumber == 0) {
            return new Failure("Invalid account type");
        }
        return new Success(null);
    }
    
    private Status completeDeposit(final Message message, final Balances balances) {
        final int cardNumber = message.getCard().getNumber();
        final int accountNumber = SimulatedBank.ACCOUNT_NUMBER[cardNumber][message.getToAccount()];
        if (accountNumber == 0) {
            return new Failure("Invalid account type");
        }
        final Money amount = message.getAmount();
        this.BALANCE[accountNumber].add(amount);
        this.BALANCE[accountNumber].subtract(new Money(0, 10));
        balances.setBalances(this.BALANCE[accountNumber], this.AVAILABLE_BALANCE[accountNumber]);
        return new Success(null);
    }
    
    private Status transfer(final Message message, final Balances balances) {
        final int cardNumber = message.getCard().getNumber();
        final int fromAccountNumber = SimulatedBank.ACCOUNT_NUMBER[cardNumber][message.getFromAccount()];
        if (fromAccountNumber == 0) {
            return new Failure("Invalid from account type");
        }
        final int toAccountNumber = SimulatedBank.ACCOUNT_NUMBER[cardNumber][message.getToAccount()];
        if (toAccountNumber == 0) {
            return new Failure("Invalid to account type");
        }
        if (fromAccountNumber == toAccountNumber) {
            return new Failure("Can't transfer money from\nan account to itself");
        }
        final Money amount = message.getAmount();
        if (!amount.lessEqual(this.AVAILABLE_BALANCE[fromAccountNumber])) {
            return new Failure("Insufficient available balance");
        }
        this.BALANCE[fromAccountNumber].subtract(amount);
        this.AVAILABLE_BALANCE[fromAccountNumber].subtract(amount);
        this.BALANCE[toAccountNumber].add(amount);
        this.AVAILABLE_BALANCE[toAccountNumber].add(amount);
        balances.setBalances(this.BALANCE[toAccountNumber], this.AVAILABLE_BALANCE[toAccountNumber]);
        return new Success(null);
    }
    
    private Status inquiry(final Message message, final Balances balances) {
        final int cardNumber = message.getCard().getNumber();
        final int accountNumber = SimulatedBank.ACCOUNT_NUMBER[cardNumber][message.getFromAccount()];
        if (accountNumber == 0) {
            return new Failure("Invalid account type");
        }
        balances.setBalances(this.BALANCE[accountNumber], this.AVAILABLE_BALANCE[accountNumber]);
        return new Success(null);
    }
    
    public Status checkPIN(final Card card, final int pin) {
        if (card.getNumber() < 1 || card.getNumber() >= SimulatedBank.PIN.length) {
            return new Failure("Invalid card");
        }
        if (pin != SimulatedBank.PIN[card.getNumber()]) {
            return new InvalidPIN();
        }
        return new Success(null);
    }
    
    private static class Success extends Status
    {
        @Override
        public boolean isSuccess() {
            return true;
        }
        
        @Override
        public boolean isInvalidPIN() {
            return false;
        }
        
        @Override
        public String getMessage() {
            return null;
        }
    }
    
    private static class Failure extends Status
    {
        private String message;
        
        public Failure(final String message) {
            this.message = message;
        }
        
        @Override
        public boolean isSuccess() {
            return false;
        }
        
        @Override
        public boolean isInvalidPIN() {
            return false;
        }
        
        @Override
        public String getMessage() {
            return this.message;
        }
    }
    
    private static class InvalidPIN extends Failure
    {
        public InvalidPIN() {
            super("Invalid PIN");
        }
        
        @Override
        public boolean isInvalidPIN() {
            return true;
        }
    }
}
