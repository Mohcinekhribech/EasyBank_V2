package DTO;

public class SavingAccount extends Account{
    private double interestRate ;
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
