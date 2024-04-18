public class DotModel implements Runnable {

    public static final int DOT_SIZE = 10;

    private DotModelObserver observer;
    private static final int SLEEP_INTERVAL = 40;
    private static final int MOVE_POINTS_X = 2;
    private static final int MOVE_POINTS_Y = 2;
    private int maxX;
    private int maxY;
    private int currentX = 11;
    private int currentY = 42;
    private int deltaX = MOVE_POINTS_X;
    private int deltaY = MOVE_POINTS_Y;

    DotModel(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
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

    // This code is executed in the thread "DotModelThread".
    @Override
    public void run() {
        if (null == observer) {
            throw new RuntimeException("No observer to notify events about!");
        }
        try {
            while (true) {
                Thread.sleep(SLEEP_INTERVAL);
                currentX += deltaX;
                currentY += deltaY;
                if (currentX + DOT_SIZE >= maxX) {
                    deltaX = -MOVE_POINTS_X;
                }
                if (currentY + DOT_SIZE >= maxY) {
                    deltaY = -MOVE_POINTS_Y;
                }
                if (currentX <= 0) {
                    deltaX = MOVE_POINTS_X;
                }
                if (currentY <= 0) {
                    deltaY = MOVE_POINTS_Y;
                }
                observer.dotMovedTo(currentX, currentY);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
