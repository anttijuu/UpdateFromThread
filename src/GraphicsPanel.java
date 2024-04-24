import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GraphicsPanel extends JPanel implements DotModelObserver, ComponentListener, MouseListener  {

    private JLabel dotsCountLabel = new JLabel("1 dot");
    private DotModel model;

    GraphicsPanel(DotModel model) {
        super();
        add(dotsCountLabel);
        this.model = model;
        addComponentListener(this);
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawRect(0, 0, getWidth(), getHeight());
        List<Dot> dots = model.getDots();
        for (Dot dot : dots) {
            g.setColor(dot.color);
            g.fillOval((int) dot.point.getX(), (int)dot.point.getY(), DotModel.DOT_SIZE, DotModel.DOT_SIZE);
        }
    }

    @Override
    public void dotsMoved() {
        // Swing GUI updates should not be done from the DotModel thread. Instead,
        // tell the Swing to invoke the following code later in the Swing event
        // handling thread. Try to comment out lines #1 and #2 and see from the log
        // that then the code is executed from the DotModel thread. In some cases
        // this will lead to exceptions thrown, indicating that wrong thread is using
        // The Swing components. In some cases it does not. Unsure why that happens or not.
        SwingUtilities.invokeLater(() -> { // #1
            // System.out.format("Thread: %s%n", Thread.currentThread().getName());
            int dotCount = model.getDotCount();
            dotsCountLabel.setText(String.format("%d dot%s", dotCount, dotCount != 1 ? "s" : ""));
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isAltDown()) {
            model.clear();
        } else {
            model.addPoint(e.getPoint());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
