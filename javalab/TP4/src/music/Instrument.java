package music;

public abstract class Instrument {
    protected String name;
    private double purchasePrice;
    private double sellingPrice;

    public Instrument(String name, double purchasePrice, double sellingPrice) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public String getName() {
        return name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void displayInfo() {
        System.out.println("Instrument: " + name + ", Purchase Price: " + purchasePrice + ", Selling Price: " + sellingPrice);
    }
}

