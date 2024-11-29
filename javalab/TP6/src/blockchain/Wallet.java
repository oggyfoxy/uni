package blockchain;

import java.util.Random;

public class Wallet {
    private final String owner;
    private final int token;
    private int isepCoins;

    public Wallet(String owner) {
        this.owner = owner;
        this.token = new Random().nextInt(100000); // Random token generation
        this.isepCoins = 0;
    }

    public String getOwner() {
        return owner;
    }

    public int getToken() {
        return token;
    }

    public int getIsepCoins() {
        return isepCoins;
    }

    public void setIsepCoins(int isepCoins) {
        this.isepCoins = isepCoins;
    }
}
