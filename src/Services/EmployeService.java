package Services;

import java.util.List;
import java.util.Optional;

import DTO.Agence;
import DTO.Employee;
import Interfaces.AgenceInterface;
import Interfaces.EmployeInterface;

public class EmployeService {
    private EmployeInterface employeDao;

    public EmployeService(EmployeInterface employeDao) {
        this.employeDao = employeDao;
    }

    public Optional<Employee> add(Employee employee) {
        if (employee != null) {
            return this.employeDao.add(Optional.of(employee));
        }
        return Optional.empty();
    }

    public int delete(String registrationNumber) {
        return this.employeDao.delete(registrationNumber);
    }

    public Optional<Employee> update(Employee employee, String code) {
        return this.employeDao.update(employee, code);
    }

    public Optional<Employee> searchByRegistrationNumber(String registrationNumber) {
        return this.employeDao.SearchByRegistrationNumber(registrationNumber);
    }

    public List<Employee> search(Employee employee) {
        return this.employeDao.Search(employee);
    }
}