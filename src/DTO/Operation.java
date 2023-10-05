package DTO;

import DTO.Enum.OperationType;

import java.time.LocalDate;

public class Operation {
    private int operationNumber;
    private LocalDate date;
    private Account account;
    private Employee employee;
    private OperationType type;
    private double price;

    public Employee getEmployee() {
        return employee;
    }
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getOperationNumber() {
        return operationNumber;
    }

    public void setOperationNumber(int operationNumber) {
        this.operationNumber = operationNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OperationType getType() {
        return type;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
