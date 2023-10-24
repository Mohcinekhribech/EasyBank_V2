package DTO;

import lombok.Data;

@Data
public class Transfert extends Operation{
    private int transfertNumber;
    private Account fromAccount;
    private Account toAccount;
}
