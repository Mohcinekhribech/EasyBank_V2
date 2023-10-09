package Interfaces;

import DTO.Client;
import DTO.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeInterface {
    Optional<Employee> add(Optional<Employee> employee) ;
    int delete(String registrationNumber);
    Optional<Employee> update(Employee employee,String registrationNumber) ;
    Optional<Employee> SearchByRegistrationNumber(String registratonNumber);
    List<Employee> Search(Employee employee);
    List<Employee> getAll() ;
}
