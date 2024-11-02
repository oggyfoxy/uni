package ex3;

import java.util.ArrayList;
import java.util.List;

public class Figure {
    protected float x;
    protected float y;
    protected int color;

    private static List<Figure> vector = new ArrayList<Figure>();

    public Figure(float x, float y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }


}
