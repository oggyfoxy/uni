package drawings;

public class Text implements Drawable {
    private String content;
    private int color;

    public Text(String content, int color) {
        this.content = content;
        this.color = color;
    }

    @Override
    public void display() {
        System.out.println("Displaying text: \"" + content + "\" with color " + color);
    }
}
