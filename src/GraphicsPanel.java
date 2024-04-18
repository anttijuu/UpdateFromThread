import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GraphicsPanel extends JPanel implements DotModelObserver, ComponentListener  {

    private JLabel coordsLabel = new JLabel("");
    private Point center = new Point(DotModel.DOT_SIZE, DotModel.DOT_SIZE);
    private DotModel model;

    GraphicsPanel(DotModel model) {
        super();
        add(coordsLabel);
        this.model = model;
        addComponentListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        coordsLabel.setText(String.format("%d,%d", (int)center.getX(), (int)center.getY()));
        g.drawRect(0, 0, getWidth(), getHeight());
        g.drawOval((int)center.getX(), (int)center.getY(), DotModel.DOT_SIZE, DotModel.DOT_SIZE);
    }

    @Override
    public void dotMovedTo(int newX, int newY) {
        // Swing GUI updates should not be done from your thread. Instead,
        // tell the Swing to invoke the following code later in the Swing event
        // handling thread. Try to comment out lines #1 and #2 and see from the log
        // that then the code is executed from the DotModel thread. In some cases
        // this will lead to exceptions thrown, indicating that wrong thread is using
        // The Swing components. In some cases it does not. Unsure why that happens or not.
        SwingUtilities.invokeLater(() -> { // #1
            System.out.format("Thread: %s%n", Thread.currentThread().getName());
            center.setLocation(newX, newY);
            repaint();
        }); // #2
    }

    @Override
    public void componentResized(ComponentEvent e) {
        Dimension newDimension = getSize();
        model.setSize((int)newDimension.getWidth(), (int)newDimension.getHeight());
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
    
}
