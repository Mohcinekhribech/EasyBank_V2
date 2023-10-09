package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import DTO.Agence;
import DTO.EmployeAffectation;
import DTO.Employee;
import Helpers.Database;
import Interfaces.EmployeAffectationInterface;

public class EmployeAffectationDao implements EmployeAffectationInterface {
    Connection connection = Database.ConnectToDb();

    @Override
    public Optional<EmployeAffectation> create(EmployeAffectation employeAffectation) {
        try {
            PreparedStatement statement = this.connection.prepareStatement(
                    "insert into EmployeAffectation (creationDate,employe_registrationNumber,agence_code) values (?,?,?)");
            statement.setDate(1,Date.valueOf(employeAffectation.getCreationDate()));
            statement.setString(2, employeAffectation.getEmployee().getRegistrationNumber());
            statement.setString(3, employeAffectation.getAgence().getCode());
            if(statement.execute())
            return Optional.of(employeAffectation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<EmployeAffectation> update(EmployeAffectation employeAffectation) {
        try {
             PreparedStatement statement = this.connection.prepareStatement(
                    "insert into EmployeAffectation (creationDate,employe_registrationNumber,agence_code) values (?,?,?)");
            statement.setDate(1,Date.valueOf(employeAffectation.getCreationDate()));
            statement.setString(2, employeAffectation.getEmployee().getRegistrationNumber());
            statement.setString(3, employeAffectation.getAgence().getCode());
            if(statement.executeUpdate()>0)
            return Optional.of(employeAffectation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<EmployeAffectation> getAll() {
        try {
            List<EmployeAffectation> employeAffectations = new ArrayList<>();
            PreparedStatement statement = this.connection.prepareStatement("select * from EmployeAffectation ea join agence a on a.code = ea.agence_code join Employe e on e.registrationNumber = ea.employe_registrationNumber");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                EmployeAffectation employeAffectation = new EmployeAffectation();
                Agence agence = new Agence();
                Employee employee = new Employee();
                agence.setAdress(resultSet.getString("address"));
                agence.setCode(resultSet.getString("code"));
                agence.setName(resultSet.getString("name"));
                agence.setPhoneNumber(resultSet.getString("phoneNumber"));
                employeAffectation.setAgence(agence);
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastName"));
                employee.setDateOfBirth(LocalDate.parse(resultSet.getString("dateOfBirth")));
                employee.setEmail(resultSet.getString("email"));
                employee.setRecruitmentDate(LocalDate.parse(resultSet.getString("recruitmentDate")));
                employeAffectation.setEmployee(employee);
                employeAffectation.setCreationDate(LocalDate.parse(resultSet.getString("creationDate")));
                employeAffectations.add(employeAffectation);
            }
            return employeAffectations;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}