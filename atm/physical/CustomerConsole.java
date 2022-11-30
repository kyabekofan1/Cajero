// 
// Decompiled by Procyon v0.5.36
// 

package atm.physical;

import banking.Money;
import simulation.Simulation;

public class CustomerConsole
{
    public void display(final String message) {
        Simulation.getInstance().clearDisplay();
        Simulation.getInstance().display(message);
    }
    
    public int readPIN(final String prompt) throws Cancelled {
        Simulation.getInstance().clearDisplay();
        Simulation.getInstance().display(prompt);
        Simulation.getInstance().display("");
        final String input = Simulation.getInstance().readInput(1, 0);
        Simulation.getInstance().clearDisplay();
        if (input == null) {
            throw new Cancelled();
        }
        return Integer.parseInt(input);
    }
    
    public synchronized int readMenuChoice(final String prompt, final String[] menu) throws Cancelled {
        Simulation.getInstance().clearDisplay();
        Simulation.getInstance().display(prompt);
        for (int i = 0; i < menu.length; ++i) {
            Simulation.getInstance().display(String.valueOf(i + 1) + ") " + menu[i]);
        }
        final String input = Simulation.getInstance().readInput(3, menu.length);
        Simulation.getInstance().clearDisplay();
        if (input == null) {
            throw new Cancelled();
        }
        return Integer.parseInt(input) - 1;
    }
    
    public synchronized Money readAmount(final String prompt) throws Cancelled {
        Simulation.getInstance().clearDisplay();
        Simulation.getInstance().display(prompt);
        Simulation.getInstance().display("");
        final String input = Simulation.getInstance().readInput(2, 0);
        Simulation.getInstance().clearDisplay();
        if (input == null) {
            throw new Cancelled();
        }
        final int dollars = Integer.parseInt(input) / 100;
        final int cents = Integer.parseInt(input) % 100;
        return new Money(dollars, cents);
    }
    
    public static class Cancelled extends Exception
    {
        public Cancelled() {
            super("Cancelled by customer");
        }
    }
}
