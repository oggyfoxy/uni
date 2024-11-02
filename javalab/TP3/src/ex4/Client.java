package ex4;

import java.util.List;
import java.util.ArrayList;

public class Client {
    private static int clientCounter = 0;

    private String code;
    private String firstName;
    private String lastName;
    private String address;
    private Branch myBranch;
    private List<Account> myAccounts;

    public Client(String firstName, String lastName, String address, Branch myBranch) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.myBranch = myBranch;
        this.myAccounts = new ArrayList<>();

        clientCounter++;
        this.code = getClass().getSimpleName() + "_" + clientCounter;

    }



    public Account getAccount(int index) {
        if (index >= 0 && index < myAccounts.size()) {
            return myAccounts.get(index);
        } else {
            System.out.println("Account index out of range.");
            return null;
        }
    }

    public void addAccount(Account acc) {
        myAccounts.add(acc);
    }

    // Deposit to a specific account by code
    public void deposit(String accountCode, double amount) {
        for (int i = 0; i < getNbAccounts(); i++) {
            Account account = getAccount(i);
            if (account != null && account.getCode().equals(accountCode)) {
                account.deposit(amount);
                return;
            }
        }
        System.out.println("Account with code " + accountCode + " not found for deposit.");
    }

    // Withdraw from a specific account by code
    public void withdraw(String accountCode, double amount) {
        for (int i = 0; i < getNbAccounts(); i++) {
            Account account = getAccount(i);
            if (account != null && account.getCode().equals(accountCode)) {
                account.withdraw(amount);
                return;
            }
        }
        System.out.println("Account with code " + accountCode + " not found for withdrawal.");
    }

    public String getCode() {
        return code;
    }

    public int getNbAccounts() {
        return myAccounts.size();
    }

    @Override
    public String toString() {
        return "Client[code=" + code + ", name=" + firstName + " " + lastName +
                ", address=" + address + ", branch=" + myBranch.getNumber() + "]";    }

}
