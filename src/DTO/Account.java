package DTO;

import DTO.Enum.Status;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

public @Data abstract class Account {
    private String accountNumber;
    private double balance;
    private LocalDate creationDate;
    private Status status;
    private Client client;
    private List<Operation> operations;
}
