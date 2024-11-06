package music;

public class Piano extends StringInstrument {
    private String type;  // grand, upright, digital
    private boolean weightedKeys;  // only for digital pianos
    private int samplingQuality;   // from 1 to 3 for digital pianos

    public Piano(String name, double purchasePrice, double sellingPrice, double length, double width,
                 String type, boolean weightedKeys, int samplingQuality) {
        super(name, purchasePrice, sellingPrice, length, width);
        this.type = type;
        this.weightedKeys = weightedKeys;
        this.samplingQuality = samplingQuality;
    }

    @Override
    public String technicalDocument() {
        return "Piano[name=" + name + ", type=" + type + ", length=" + length + ", width=" + width +
                ", weightedKeys=" + weightedKeys + ", samplingQuality=" + samplingQuality + "]";
    }
}
