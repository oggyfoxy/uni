package drawings;

public class Rectangle extends Figure {
    private int width;
    private int height;

    public Rectangle(int x, int y, int color, int width, int height) {
        super(x, y, color);
        this.width = width;
        this.height = height;
    }

    @Override
    public void display() {
        System.out.println("Displaying a Rectangle with width " + width + " and height " + height +
                " at position (" + x_coordinate + ", " + y_coordinate + ") and color " + color);
    }
}
