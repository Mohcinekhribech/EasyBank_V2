package Interfaces;

import DTO.Client;
import DTO.Employee;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployeInterface {
    Optional<Employee> add(Optional<Employee> employee) throws SQLException;
    int delete(String registrationNumber);
    Optional<Employee> update(Employee employee,String registrationNumber) throws SQLException;
    Map<String,String> SearchByRegistrationNumber(String registratonNumber);
    List<Map<String,String>> Search(Employee employee);
    List<Map<String,String>> getAll() ;
}
