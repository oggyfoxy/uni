package drawings;

import java.util.ArrayList;

public class TestProgram {

    public static void main(String[] args) {
        ArrayList<Drawable> elements = new ArrayList<>();

        Square square = new Square(10, 20, 0xFF0000, 5);  // Red square with side length 5
        Rectangle rectangle = new Rectangle(15, 25, 0x00FF00, 10, 20);  // Green rectangle
        Text text = new Text("Hello World", 0x0000FF);  // Blue text

        elements.add(square);
        elements.add(rectangle);
        elements.add(text);

        System.out.println("Initial Display:");
        GraphicalElement.displayElements(elements);

        System.out.println("\nMoving Square and Rectangle:");
        square.move(5, 5);
        rectangle.move(-3, -3);

        System.out.println("\nDisplay after moving:");
        GraphicalElement.displayElements(elements);
    }
}
