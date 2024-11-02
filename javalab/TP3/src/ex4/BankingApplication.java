package ex4;

import java.util.ArrayList;
import java.util.List;

public class BankingApplication {

    public static void main(String[] args) {
        Branch Chase = new Branch("Chase");

        Client[] clients = new Client[4];

        clients[0] = new Client("James", "Johnson", "87 bd Suchet", Chase);
        SavingsAccount savingsAccount = new SavingsAccount(clients[0], 1000, 0.06);  // 6% interest rate
        clients[0].addAccount(savingsAccount);
        Chase.addClient(clients[0]);


        clients[1] = new Client("John", "Smith", "55 bd Suchet", Chase);
        FeeAccount feeAccount = new FeeAccount(clients[1], 2500);
        clients[1].addAccount(feeAccount);
        Chase.addClient(clients[1]);


        clients[2] = new Client("Alice", "D.", "23 rue de Rivoli", Chase);
        FeeAccount feeAccount1 = new FeeAccount(clients[2], 0);
        FeeAccount feeAccount2 = new FeeAccount(clients[2], 3000);
        clients[2].addAccount(feeAccount1);
        clients[2].addAccount(feeAccount2);
        Chase.addClient(clients[2]);


        clients[3] = new Client("Bob", "E.", "28 rue notre dame des champts", Chase);
        SavingsAccount savingsAccount1 = new SavingsAccount(clients[3], 2300, 0.06);
        FeeAccount feeAccount3 = new FeeAccount(clients[3], 0);
        clients[3].addAccount(savingsAccount1);
        clients[3].addAccount(feeAccount3);
        Chase.addClient(clients[3]);


        // test deposit
        System.out.println("Initial amount for Client 0 (Savings) " + savingsAccount.getBalance());
        clients[0].deposit(savingsAccount.getCode(), 430);
        System.out.println(savingsAccount.getBalance());

        System.out.println();

        // test withdrawal
        System.out.println("Initial amount for Client 2 (Fee Account 2) " + feeAccount2.getBalance());
        clients[2].withdraw(feeAccount2.getCode(), 899);
        System.out.println(feeAccount2.getBalance());
    }
}
