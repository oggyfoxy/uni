package drawings;

import java.util.ArrayList;

public class GraphicalElement {

    public static void displayElements(ArrayList<Drawable> elements) {
        for (Drawable element : elements) {
            element.display();
        }
    }
}
