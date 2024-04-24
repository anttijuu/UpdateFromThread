import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ThreadLocalRandom;

public class DotModel implements Runnable {

    public static final int DOT_SIZE = 12;

    private DotModelObserver observer;
    private static final int SLEEP_INTERVAL = 20;
    static final int MOVE_POINTS_X = 4;
    static final int MOVE_POINTS_Y = 4;
    private int maxX;
    private int maxY;
    private ArrayList<Dot> dots = new ArrayList<>();

    DotModel(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
        dots.add(new Dot(new Point(10, 10), MOVE_POINTS_X, MOVE_POINTS_Y));
    }

    void setObserver(DotModelObserver observer) {
        this.observer = observer;
    }

    void launch() {
        new Thread(this, "DotModelThread").start();
    }

    public void setSize(int width, int height) {
        maxX = width;
        maxY = height;
    }

    public void addPoint(final Point point) {
        int deltaX = point.getX() < maxX / 2 ? MOVE_POINTS_X : -MOVE_POINTS_X;
        int deltaY = point.getY() < maxY / 2 ? MOVE_POINTS_Y : -MOVE_POINTS_Y;
        deltaX += ThreadLocalRandom.current().nextInt(4) - 2;
        deltaY += ThreadLocalRandom.current().nextInt(4) - 2;
        dots.add(new Dot(point, deltaX, deltaY));
    }

    public int getDotCount() {
        return dots.size();
    }

    public final List<Dot> getDots() {
        return dots;
    }

    public void clear() {
        dots.clear();
    }

    // This code is executed in the thread "DotModelThread" (see launch() above).
    @Override
    public void run() {
        if (null == observer) {
            throw new RuntimeException("No observer to notify events about!");
        }
        try {
            while (true) {
                Thread.sleep(SLEEP_INTERVAL);
                for (int index = 0; index < dots.size(); index++) {
                    Dot dot = dots.get(index);
                    int currentX = (int)dot.point.getX();
                    int currentY = (int)dot.point.getY();
                    if (currentX + DOT_SIZE >= maxX) {
                        dot.deltaX = -dot.deltaX; // -MOVE_POINTS_X;
                    }
                    if (currentY + DOT_SIZE >= maxY) {
                        dot.deltaY = -dot.deltaY; // MOVE_POINTS_Y;
                    }
                    if (currentX <= 0) {
                        dot.deltaX = -dot.deltaX; // MOVE_POINTS_X;
                    }
                    if (currentY <= 0) {
                        dot.deltaY = -dot.deltaY; // MOVE_POINTS_Y;
                    }
                    dot.move();
                    dots.set(index, dot);
                }
                // Now the observer.dotsMoved() is called from the context
                // of *this* thread.
                observer.dotsMoved();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
