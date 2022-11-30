import java.awt.Panel;
import java.awt.Component;
import simulation.Simulation;
import java.net.InetAddress;
import atm.ATM;
import java.applet.Applet;

// 
// Decompiled by Procyon v0.5.36
// 

public class ATMApplet extends Applet
{
    @Override
    public void init() {
        final ATM theATM = new ATM(42, "Gordon College", "First National Bank of Podunk", null);
        final Simulation theSimulation = new Simulation(theATM);
        new Thread(theATM).start();
        final Panel gui = theSimulation.getGUI();
        this.setBackground(gui.getBackground());
        this.add(gui);
    }
}
