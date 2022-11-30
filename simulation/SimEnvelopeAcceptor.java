// 
// Decompiled by Procyon v0.5.36
// 

package simulation;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Button;

class SimEnvelopeAcceptor extends Button
{
    private boolean inserted;
    private static long MAXIMUM_WAIT_TIME;
    
    static {
        SimEnvelopeAcceptor.MAXIMUM_WAIT_TIME = 20000L;
    }
    
    SimEnvelopeAcceptor() {
        super("Click to insert envelope");
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                synchronized (SimEnvelopeAcceptor.this) {
                    SimEnvelopeAcceptor.access$0(SimEnvelopeAcceptor.this, true);
                    SimEnvelopeAcceptor.this.notify();
                }
                // monitorexit(this.this$0)
            }
        });
    }
    
    public synchronized boolean acceptEnvelope() {
        this.inserted = false;
        this.setVisible(true);
        try {
            this.wait(SimEnvelopeAcceptor.MAXIMUM_WAIT_TIME);
        }
        catch (InterruptedException ex) {}
        if (this.inserted) {
            final Rectangle originalBounds = this.getBounds();
            final Rectangle currentBounds = new Rectangle(originalBounds.x, originalBounds.y, originalBounds.width, originalBounds.height);
            while (currentBounds.width > 0 && currentBounds.height > 0) {
                this.setBounds(currentBounds.x, currentBounds.y, currentBounds.width, currentBounds.height);
                this.repaint();
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException ex2) {}
                final Rectangle rectangle = currentBounds;
                --rectangle.height;
                currentBounds.width = originalBounds.width * currentBounds.height / originalBounds.height;
                currentBounds.x = originalBounds.x + (originalBounds.width - currentBounds.width) / 2;
                currentBounds.y = originalBounds.y + (originalBounds.height - currentBounds.height) / 2;
            }
            this.setVisible(false);
            this.setBounds(originalBounds);
        }
        else {
            this.setVisible(false);
        }
        return this.inserted;
    }
    
    public synchronized void cancelRequested() {
        this.notify();
    }
    
    static /* synthetic */ void access$0(final SimEnvelopeAcceptor simEnvelopeAcceptor, final boolean inserted) {
        simEnvelopeAcceptor.inserted = inserted;
    }
}
