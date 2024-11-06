package drawings;

public class Square extends Figure {
    private int sideLength;

    public Square(int x, int y, int color, int sideLength) {
        super(x, y, color);
        this.sideLength = sideLength;
    }

    @Override
    public void display() {
        System.out.println("Displaying a Square with side length " + sideLength +
                " at position (" + x_coordinate + ", " + y_coordinate + ") and color " + color);
    }
}
