package drawings;

public abstract class Figure implements Drawable, Moveable {
    protected int x_coordinate;
    protected int y_coordinate;
    protected int color;

    public Figure(int x, int y, int color) {
        this.x_coordinate = x;
        this.y_coordinate = y;
        this.color = color;
    }

    @Override
    public void move(int deltaX, int deltaY) {
        x_coordinate += deltaX;
        y_coordinate += deltaY;
        System.out.println("Moved to new position: (" + x_coordinate + ", " + y_coordinate + ")");
    }

    @Override
    public abstract void display();
}
