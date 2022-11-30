import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.Component;
import java.awt.Frame;
import simulation.Simulation;
import java.net.InetAddress;
import atm.ATM;

// 
// Decompiled by Procyon v0.5.36
// 

public class ATMMain
{
    public static void main(final String[] args) {
        final ATM theATM = new ATM(42, "Gordon College", "First National Bank of Podunk", null);
        final Simulation theSimulation = new Simulation(theATM);
        final Frame mainFrame = new Frame("ATM Simulation");
        mainFrame.add(theSimulation.getGUI());
        final MenuBar menuBar = new MenuBar();
        final Menu fileMenu = new Menu("File");
        final MenuItem quitItem = new MenuItem("Quit", new MenuShortcut(81));
        quitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(quitItem);
        menuBar.add(fileMenu);
        mainFrame.setMenuBar(menuBar);
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                System.exit(0);
            }
        });
        new Thread(theATM).start();
        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
