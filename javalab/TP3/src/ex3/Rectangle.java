package ex3;

import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Figure {
    private int length;
    private int width;

    private static List<Rectangle> rectangleList = new ArrayList<Rectangle>();

    public Rectangle(float x, float y, int length, int width) {
        super(x, y, length);
        this.length = length;
        this.width = width;
        rectangleList.add(this);
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public static List<Rectangle> getInstances() {
        return rectangleList;
    }

    @Override
    public String toString() {
        return "Rectangle[x=" + x + ", y=" + y + ", color=" + color + ", width=" + width + ", length=" + length + "]";
    }
}
