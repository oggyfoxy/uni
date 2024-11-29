package blockchain;

public class Transaction {
    private final Wallet originWallet;
    private final Wallet destinationWallet;
    private final int isepCoins;
    private boolean payed;

    public Transaction(Wallet originWallet, Wallet destinationWallet, int isepCoins) {
        this.originWallet = originWallet;
        this.destinationWallet = destinationWallet;
        this.isepCoins = isepCoins;
        this.payed = false; // Default value
    }


    public Wallet getOriginWallet() {
        return originWallet;
    }

    public Wallet getDestinationWallet() {
        return destinationWallet;
    }

    public int getIsepCoins() {
        return isepCoins;
    }

    public boolean isPayed() {
        return payed;
    }

    //todo: add, pay and Simulation

    public boolean pay() {
        // Check if the origin wallet has enough balance
        if (originWallet.getIsepCoins() >= isepCoins) {
            // Perform the transaction
            originWallet.setIsepCoins(originWallet.getIsepCoins() - isepCoins);
            destinationWallet.setIsepCoins(destinationWallet.getIsepCoins() + isepCoins);
            this.payed = true;
            return true;
        } else {
            System.out.println("Transaction failed: Insufficient funds in " + originWallet.getOwner() + "'s wallet.");
            return false;
        }
    }

    @Override
    public String toString() {
        return "Transaction [From: " + originWallet.getOwner() + ", To: " + destinationWallet.getOwner()
                + ", Amount: " + isepCoins + ", Payed: " + payed + "]";
    }



}
