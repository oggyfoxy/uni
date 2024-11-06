package music;

public class Flute extends WindInstrument {
    private boolean supportTrills;
    private int soundQuality;  // 1 to 5

    public Flute(String name, double purchasePrice, double sellingPrice, String type,
                 boolean supportTrills, int soundQuality) {
        super(name, purchasePrice, sellingPrice, type);
        this.supportTrills = supportTrills;
        this.soundQuality = soundQuality;
    }

    @Override
    public String technicalDocument() {
        return "Flute[name=" + name + ", type=" + type + ", supportTrills=" + supportTrills +
                ", soundQuality=" + soundQuality + "]";
    }
}
