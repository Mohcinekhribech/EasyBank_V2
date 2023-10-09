package DTO;

import java.time.LocalDate;

import lombok.Data;

public @Data class EmployeAffectation {
     private Agence agence;
     private Employee employee;
     private LocalDate creationDate;
}
