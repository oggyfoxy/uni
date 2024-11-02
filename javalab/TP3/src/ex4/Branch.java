package ex4;

import java.util.ArrayList;
import java.util.List;

public class Branch {

    private static int branchCounter = 0;

    private String number;
    private String address;
    private List<Client> clients;


    public Branch(String address) {
        this.address = address;
        this.clients = new ArrayList<>();

        branchCounter++;
        this.number = getClass().getSimpleName() + "-" + branchCounter;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;

    }


    public void addAccount(Client client, Account account) {
        if (clients.contains(client)) {
            client.addAccount(account);
        }
        else {
            System.out.println("Client not found in this branch.");
        }
    }

    public void addClient(Client client) {
        clients.add(client);

    }

    public Account getAccount(String code) {
        for(Client client : clients) {
            for (int i = 0; i < client.getNbAccounts(); i++) {
                Account account = client.getAccount(i);
                if (account.getCode().equals(code)) {
                    return account;
                }
            }
        }
        System.out.println("Account with client code " + code + " not found.");
        return null;
    }

    public Client getClient(String code) {
        for (Client client : clients) {
            if (client.getCode().equals(code)) {
                return client;
            }
        }
        System.out.println("Client with code " + code + " not found.");
        return null;

    }

    public int getNbClients() {
        return clients.size();
    }

    public int getNbAccounts() {
        int totalAccounts = 0;
        for (Client client : clients) {
            totalAccounts += client.getNbAccounts();
        }
        return totalAccounts;
    }

    @Override
    public String toString() {
        return "Branch [number=" + number + ", address=" + address + ", clients=" + clients + "]";
    }



}
