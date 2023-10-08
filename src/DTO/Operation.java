package DTO;

import DTO.Enum.OperationType;
import lombok.Data;

import java.time.LocalDate;

public @Data class Operation {
    private int operationNumber;
    private LocalDate date;
    private Account account;
    private Employee employee;
    private OperationType type;
    private double price;
}
