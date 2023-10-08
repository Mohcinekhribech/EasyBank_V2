package DTO;

import java.util.List;

import lombok.Data;

public @Data class Agence {
    private String code ;
    private String name ;
    private String adress;
    private String phoneNumber;
    private List<Account> accounts;
}
