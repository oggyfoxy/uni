package blockchain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    private static final ArrayList<Wallet> wallets = new ArrayList<>();
    private static final ArrayList<Block> blockchain = new ArrayList<>();

    public static void main(String[] args) {
        initializeWallets();
        blockchain.add(new Block());
        simulation();

        displayLastNBlocks(3);

        Map<String, int[]> walletActivity = generateWalletActivity();
        displayWalletActivity(walletActivity);

        Map<String, Map<String, Integer>> adjacencyMatrix = generateAdjacencyMatrix();
        displayAdjacencyMatrix(adjacencyMatrix);

        exportToGraphviz(adjacencyMatrix);
    }

    private static void initializeWallets() {
        String[] names = {"Alice", "Bob", "Charlie", "Diana", "Eve"};
        for (String name : names) {
            Wallet wallet = new Wallet(name);
            wallet.setIsepCoins(100); // Each wallet starts with 100 ISEP coins
            wallets.add(wallet);
        }
    }

    private static void simulation() {
        Random random = new Random();
        int totalTransactions = 55;
        Block currentBlock = blockchain.get(blockchain.size() - 1);

        System.out.println("Starting simulation...");

        for (int i = 0; i < totalTransactions; i++) {
            Wallet origin = wallets.get(random.nextInt(wallets.size()));
            Wallet destination = getRandomWallet(random, origin);

            int amount = random.nextInt(20) + 1;

            Transaction transaction = new Transaction(origin, destination, amount);
            if (transaction.pay()) {
                if (currentBlock.add(transaction)) {
                    currentBlock = new Block();
                    blockchain.add(currentBlock);
                }
            } else {
                System.out.println("Transaction failed: Insufficient funds.");
            }
        }

        System.out.println("Simulation completed. Blockchain contains " + blockchain.size() + " blocks.");
        printBlockchain();
    }

    private static Wallet getRandomWallet(Random random, Wallet exclude) {
        Wallet wallet;
        do {
            wallet = wallets.get(random.nextInt(wallets.size()));
        } while (wallet == exclude);
        return wallet;
    }

    private static void printBlockchain() {
        for (int i = 0; i < blockchain.size(); i++) {
            Block block = blockchain.get(i);
            System.out.println("Block " + (i + 1) + ":");
            System.out.println(block);
        }
    }

    private static void displayLastNBlocks(int n) {
        if (n <= 0) {
            System.out.println("Invalid block count.");
            return;
        }
        System.out.println("Displaying the last " + n + " blocks:");
        for (int i = blockchain.size() - 1; i >= Math.max(blockchain.size() - n, 0); i--) {
            System.out.println(blockchain.get(i));
        }
    }

    private static Map<String, int[]> generateWalletActivity() {
        Map<String, int[]> activityMap = new HashMap<>();

        for (Block block : blockchain) {
            for (Transaction transaction : block.getTransactions()) {
                String sender = transaction.getOriginWallet().getOwner();
                activityMap.putIfAbsent(sender, new int[]{0, 0});
                activityMap.get(sender)[0]++;
                activityMap.get(sender)[1] -= transaction.getIsepCoins();

                String receiver = transaction.getDestinationWallet().getOwner();
                activityMap.putIfAbsent(receiver, new int[]{0, 0});
                activityMap.get(receiver)[0]++;
                activityMap.get(receiver)[1] += transaction.getIsepCoins();
            }
        }

        return activityMap;
    }

    private static void displayWalletActivity(Map<String, int[]> activityMap) {
        for (Map.Entry<String, int[]> entry : activityMap.entrySet()) {
            System.out.println("Wallet: " + entry.getKey() + ", Transactions: " + entry.getValue()[0]
                    + ", Net ISEP Coins: " + entry.getValue()[1]);
        }
    }

    private static Map<String, Map<String, Integer>> generateAdjacencyMatrix() {
        Map<String, Map<String, Integer>> adjacencyMatrix = new HashMap<>();

        for (Block block : blockchain) {
            for (Transaction transaction : block.getTransactions()) {
                String sender = transaction.getOriginWallet().getOwner();
                String receiver = transaction.getDestinationWallet().getOwner();

                adjacencyMatrix.putIfAbsent(sender, new HashMap<>());
                adjacencyMatrix.get(sender).put(receiver, adjacencyMatrix.get(sender).getOrDefault(receiver, 0)
                        + transaction.getIsepCoins());
            }
        }

        return adjacencyMatrix;
    }

    private static void displayAdjacencyMatrix(Map<String, Map<String, Integer>> adjacencyMatrix) {
        System.out.println("\nAdjacency Matrix:");
        for (String sender : adjacencyMatrix.keySet()) {
            System.out.println(sender + " -> " + adjacencyMatrix.get(sender));
        }
    }

    private static void exportToGraphviz(Map<String, Map<String, Integer>> adjacencyMatrix) {
        System.out.println("\ndigraph G {");
        for (String sender : adjacencyMatrix.keySet()) {
            for (Map.Entry<String, Integer> entry : adjacencyMatrix.get(sender).entrySet()) {
                System.out.println("  \"" + sender + "\" -> \"" + entry.getKey() + "\" [label=\"" + entry.getValue() + "\"];");
            }
        }
        System.out.println("}");
    }
}
