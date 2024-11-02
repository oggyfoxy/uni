package ex4;

public class Account {
    private static int accountCounter = 0;

    private String code;
    protected Client owner;
    protected double balance = 0.0;


    public Account(Client owner, double initialBalance) {
        this.owner = owner;
        this.balance = initialBalance;
        accountCounter++;
        this.code = getClass().getSimpleName() + "_" + accountCounter;
    }


    public double getBalance() {
        return balance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("deposit " + amount);
        }
        else {
            System.out.println("Deposit amount must be positive.");
        }
    }



    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("withdrew " + amount);
        }
        else {
            System.out.println("Insufficient funds.");
        }
    }

    @Override
    public String toString() {
        return "Account [code=" + code + ", owner=" + owner + ", balance=" + balance + "]";
    }

}
