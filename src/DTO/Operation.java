package DTO;

import DTO.Enum.OperationType;
import lombok.Data;

import java.time.LocalDate;

@Data
public abstract class Operation {
    protected int operationNumber;
    protected LocalDate date;
    protected Employee employee;
    protected double price;
}
