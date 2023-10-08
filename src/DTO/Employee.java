package DTO;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

public final @Data class Employee extends Person {
    private String registrationNumber ;
    private LocalDate recruitmentDate;
    private List<Operation> operations;
    private List<Affectation> affectations;
}
