package blockchain;

import java.util.ArrayList;

public class Block {
    private final ArrayList<Transaction> transactions;
    private static final int MAX_TRANSACTIONS = 10;

    public Block() {
        this.transactions = new ArrayList<>();
    }

    public boolean add(Transaction transaction) {
        // Add the transaction to the current block
        transactions.add(transaction);

        // If the block reaches its maximum size, settle all transactions
        if (transactions.size() == MAX_TRANSACTIONS) {
            System.out.println("Settling transactions in the block...");
            for (Transaction t : transactions) {
                t.pay();
            }
            return true; //Indicates block is full and ready for the next block
        }
        return false; // Block is not full yet
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Block: [\n");
        for (Transaction t : transactions) {
            sb.append("  ").append(t.toString()).append("\n");
        }
        sb.append("]");
        return sb.toString();
    }





}

