package DTO;

import java.util.List;

import lombok.Data;

public final @Data class Client extends Person{
    private String code;
    private String adress;
    private List<Account> accounts;
}
