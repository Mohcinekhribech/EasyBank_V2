package DAO;

import DTO.CurrentAccount;
import DTO.Employee;
import DTO.Operation;
import DTO.Enum.OperationType;
import Helpers.Database;
import Interfaces.OperationInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OperationDao implements OperationInterface {
    Connection connection = Database.ConnectToDb();
    Employee employee = new Employee();
    CurrentAccount Account = new CurrentAccount();
    @Override
    public Optional<Operation> add(Operation operation) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO operation (date, type, price, accountNumber , registrationNumber) VALUES (?, ?::operationType, ?, ?, ?)");
            statement.setDate(1, Date.valueOf(operation.getDate()));
            statement.setString(2, String.valueOf(operation.getType()));
            statement.setDouble(3, operation.getPrice());
            statement.setString(4, operation.getAccount().getAccountNumber());
            statement.setString(5, operation.getEmployee().getRegistrationNumber());
            if (statement.executeUpdate() > 0) {
                connection.commit();
                return Optional.of(operation);
            }
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public int delete(int operationNumber) {
        try {
            PreparedStatement statement = this.connection
                    .prepareStatement("delete from operation where operationnumber = ?");
            statement.setInt(1, operationNumber);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Operation> searchOperation(Operation operation) {
        return null;
    }

    @Override
    public Optional<Operation> searchOperationByNum(int operationNumber) {
        Operation operation = new Operation();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM operation AS op INNER JOIN (SELECT * FROM person AS pr INNER JOIN employe AS em ON em.id = pr.id"
                            +
                            "    WHERE em.registrationnumber = em.registrationnumber" +
                            ") AS employe ON employe.registrationnumber = op.registrationnumber where operationnumber = ?;");
            statement.setInt(1, operationNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                operation.setOperationNumber(resultSet.getInt("operationnumber"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastNAme"));
                operation.setDate(LocalDate.parse(resultSet.getString("date")));
                operation.setType(OperationType.valueOf(resultSet.getString("type")));
                operation.setPrice(resultSet.getDouble("price"));
                employee.setRegistrationNumber(resultSet.getString("registrationNumber"));
                employee.setRecruitmentDate(LocalDate.parse(resultSet.getString("recrutmentDate")));
                employee.setEmail( resultSet.getString("email"));
                operation.setEmployee(employee);
                return Optional.of(operation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
