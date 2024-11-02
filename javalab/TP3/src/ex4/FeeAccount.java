package ex4;

public class FeeAccount extends Account {

    private static final double OPERATION_INTEREST = 1.0;

    public FeeAccount(Client owner, double initialBalance) {
        super(owner, initialBalance);
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += (amount - OPERATION_INTEREST);
            System.out.println("deposit " + amount);
        }
        else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= (amount + OPERATION_INTEREST) ;
            System.out.println("withdrew " + amount + " with fee " + OPERATION_INTEREST);
        }
        else {
            System.out.println("Insufficient funds.");
        }

    }

    @Override
    public String toString() {
        return "FeeAccount[code=" + getCode() + ", owner=" + owner.getCode() + ", balance=" + balance +
                ", operationFee=" + OPERATION_INTEREST + "]";    }
}

