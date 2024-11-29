package music;

public class Guitar extends StringInstrument {
    private boolean isElectric;
    private boolean hasAmpAndPedals;
    private int gauge; // 1 to 3

    public Guitar(String name, double purchasePrice, double sellingPrice, double length, double width,
                  boolean isElectric, boolean hasAmpAndPedals, int gauge) {
        super(name, purchasePrice, sellingPrice, length, width);
        this.isElectric = isElectric;
        this.hasAmpAndPedals = hasAmpAndPedals;
        this.gauge = gauge;
    }

    @Override
    public String technicalDocument() {
        String name = "";
        return "Guitar[name=" + name + ", isElectric=" + isElectric + ", length=" + length +
                ", width=" + width + ", hasAmpAndPedals=" + hasAmpAndPedals + ", gauge=" + gauge + "]";
    }
}
