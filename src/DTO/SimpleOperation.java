package DTO;

import DTO.Enum.OperationType;
import lombok.Data;

@Data
public class SimpleOperation extends Operation{
    private int simpleOperationnumber;
    private OperationType type;
    private Account account;
}
