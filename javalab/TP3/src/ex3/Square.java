package ex3;

import java.util.ArrayList;
import java.util.List;

public class Square extends Figure {
    private int sideLength;

    private static List<Square> squares = new ArrayList<Square>();

    public Square(float x, float y, int color, int sideLength) {
        super(x, y, color);
        this.sideLength = sideLength;
        squares.add(this);
    }

    public int getSideLength() {
        return sideLength;
    }

    public static List<Square> getInstances() {
        return squares;

    }

    @Override
    public String toString() {
        return "Square[x=" + x + ", y=" + y + ", color=" + color + ", sideLength=" + sideLength + "]";
    }


}
