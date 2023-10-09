package Interfaces;

import java.util.List;
import java.util.Optional;

import DTO.EmployeAffectation;

public interface EmployeAffectationInterface {
    Optional<EmployeAffectation> create(EmployeAffectation employeAffectation);
    Optional<EmployeAffectation> update(EmployeAffectation employeAffectation);
    List<EmployeAffectation> getAll();
}
