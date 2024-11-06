package music;

import java.util.ArrayList;

public class Store {
    private double treasury;
    private ArrayList<Instrument> instruments;
    private ArrayList<Integer> quantities;

    public Store(double treasury) {
        this.treasury = treasury;
        this.instruments = new ArrayList<>();
        this.quantities = new ArrayList<>();
    }

    public void deliver(Instrument instrument, int quantity) {
        double totalCost = instrument.getPurchasePrice() * quantity;

        if (treasury >= totalCost) {
            int index = instruments.indexOf(instrument);
            if (index == -1) {
                instruments.add(instrument);
                quantities.add(quantity);
            } else {
                quantities.set(index, quantities.get(index) + quantity);
            }
            treasury -= totalCost;
            System.out.println("Delivered " + quantity + " of " + instrument.getName());
        } else {
            System.out.println("Not enough funds to stock " + instrument.getName());
        }
    }

    public void sell(Instrument instrument) {
        int index = instruments.indexOf(instrument);

        if (index >= 0 && quantities.get(index) > 0) {
            quantities.set(index, quantities.get(index) - 1);
            treasury += instrument.getSellingPrice();
            System.out.println("Sold 1 of " + instrument.getName());
        } else {
            System.out.println("Instrument not in stock or out of stock: " + instrument.getName());
        }
    }

    public void displayStock() {
        System.out.println("\nStore Inventory:");

        for (int i = 0; i < instruments.size(); i++) {
            Instrument instrument = instruments.get(i);
            int quantity = quantities.get(i);
            instrument.displayInfo();

            System.out.println("Quantity: " + quantity);
        }
        System.out.println("Treasury: " + treasury);
    }
}
