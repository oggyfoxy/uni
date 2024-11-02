package ex4;

public class SavingsAccount extends Account {


    private double interestRate;


    public SavingsAccount(Client owner, double balance, double interestRate) {
        super(owner, balance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void SetInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public void calculateInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Interest added: " + interest);

    }

    @Override
    public String toString() {
        return "SavingsAccount[code=" + getCode() + ", owner=" + owner.getCode() +
                ", balance=" + balance + ", interestRate=" + interestRate + "]";    }


}
