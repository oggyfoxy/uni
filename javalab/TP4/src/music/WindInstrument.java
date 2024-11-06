package music;

public abstract class WindInstrument extends Instrument {
    protected String type;

    public WindInstrument(String name, double purchasePrice, double sellingPrice, String type) {
        super(name, purchasePrice, sellingPrice);
        this.type = type;
    }

    public abstract String technicalDocument();
}
