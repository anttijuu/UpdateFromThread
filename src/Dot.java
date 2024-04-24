import java.awt.Point;
import java.awt.Color;

import java.util.concurrent.ThreadLocalRandom;

class Dot {
    Point point;
    Color color;
    int deltaX = DotModel.MOVE_POINTS_X;
    int deltaY = DotModel.MOVE_POINTS_Y;
    Dot(final Point point, int deltaX, int deltaY) {
        this.point = point;
        this.deltaX = deltaX;
        this.deltaY = deltaY;

        int red = ThreadLocalRandom.current().nextInt(256);
        int green = ThreadLocalRandom.current().nextInt(256);
        int blue = ThreadLocalRandom.current().nextInt(256);

        color = new Color(red, green, blue);
    }

    void move() {
        this.point.move(((int)point.getX() + deltaX), ((int)point.getY() + deltaY));
    }

}