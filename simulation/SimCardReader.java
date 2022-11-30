// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Rectangle;
import java.awt.Button;

class SimCardReader extends Button
{
    private Rectangle originalBounds;
    
    SimCardReader(final Simulation simulation) {
        super("Click to insert card");
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                simulation.cardInserted();
            }
        });
        this.setVisible(false);
    }
    
    void animateInsertion() {
        this.originalBounds = this.getBounds();
        final Rectangle currentBounds = new Rectangle(this.originalBounds.x, this.originalBounds.y, this.originalBounds.width, this.originalBounds.height);
        while (currentBounds.width > 0 && currentBounds.height > 0) {
            this.setBounds(currentBounds.x, currentBounds.y, currentBounds.width, currentBounds.height);
            this.repaint();
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException ex) {}
            final Rectangle rectangle = currentBounds;
            --rectangle.height;
            currentBounds.width = this.originalBounds.width * currentBounds.height / this.originalBounds.height;
            currentBounds.x = this.originalBounds.x + (this.originalBounds.width - currentBounds.width) / 2;
            currentBounds.y = this.originalBounds.y + (this.originalBounds.height - currentBounds.height) / 2;
        }
        this.setVisible(false);
    }
    
    void animateEjection() {
        this.setLabel("Ejecting card");
        this.setVisible(true);
        final Rectangle currentBounds = new Rectangle(this.originalBounds.x + this.originalBounds.width / 2, this.originalBounds.y + this.originalBounds.height / 2, this.originalBounds.width / this.originalBounds.height, 1);
        while (currentBounds.height <= this.originalBounds.height && currentBounds.width <= this.originalBounds.width) {
            this.setBounds(currentBounds.x, currentBounds.y, currentBounds.width, currentBounds.height);
            this.repaint();
            try {
                Thread.sleep(100L);
            }
            catch (InterruptedException ex) {}
            final Rectangle rectangle = currentBounds;
            ++rectangle.height;
            currentBounds.width = this.originalBounds.width * currentBounds.height / this.originalBounds.height;
            currentBounds.x = this.originalBounds.x + (this.originalBounds.width - currentBounds.width) / 2;
            currentBounds.y = this.originalBounds.y + (this.originalBounds.height - currentBounds.height) / 2;
        }
        this.setLabel("Click to insert card");
    }
    
    void animateRetention() {
        this.setLabel("Click to insert card");
        this.setVisible(true);
    }
}
