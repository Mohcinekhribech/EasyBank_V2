package DAO;

import DTO.CurrentAccount;
import DTO.Employee;
import DTO.SimpleOperation;
import DTO.Enum.OperationType;
import Helpers.Database;
import Interfaces.SimpleOperationInterface;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class SimpleOperationDao implements SimpleOperationInterface {
    Connection connection = Database.ConnectToDb();
    Employee employee = new Employee();
    CurrentAccount Account = new CurrentAccount();
    @Override
    public Optional<SimpleOperation> add(SimpleOperation SimpleOperation) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO SimpleOperation (date, type, price, accountNumber , registrationNumber) VALUES (?, ?, ?, ?, ?)");
            statement.setDate(1, Date.valueOf(SimpleOperation.getDate()));
            statement.setObject(2, SimpleOperation.getType(),Types.OTHER);
            statement.setDouble(3, SimpleOperation.getPrice());
            statement.setString(4, SimpleOperation.getAccount().getAccountNumber());
            statement.setString(5, SimpleOperation.getEmployee().getRegistrationNumber());
            if (statement.executeUpdate() > 0) {
                connection.commit();
                return Optional.of(SimpleOperation);
            }
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public int delete(int SimpleOperationNumber) {
        try {
            PreparedStatement statement = this.connection
                    .prepareStatement("delete from SimpleOperation where SimpleOperationnumber = ?");
            statement.setInt(1, SimpleOperationNumber);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<SimpleOperation> searchSimpleOperation(SimpleOperation SimpleOperation) {
        return null;
    }

    @Override
    public Optional<SimpleOperation> searchSimpleOperationByNum(int SimpleOperationNumber) {
        SimpleOperation SimpleOperation = new SimpleOperation();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM SimpleOperation AS op INNER JOIN (SELECT * FROM person AS pr INNER JOIN employe AS em ON em.id = pr.id"
                            +
                            "    WHERE em.registrationnumber = em.registrationnumber" +
                            ") AS employe ON employe.registrationnumber = op.registrationnumber where SimpleOperationnumber = ?;");
            statement.setInt(1, SimpleOperationNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {

                SimpleOperation.setSimpleOperationnumber(resultSet.getInt("SimpleOperationnumber"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastNAme"));
                SimpleOperation.setDate(LocalDate.parse(resultSet.getString("date")));
                SimpleOperation.setType(OperationType.valueOf(resultSet.getString("type")));
                SimpleOperation.setPrice(resultSet.getDouble("price"));
                employee.setRegistrationNumber(resultSet.getString("registrationNumber"));
                employee.setRecruitmentDate(LocalDate.parse(resultSet.getString("recrutmentDate")));
                employee.setEmail( resultSet.getString("email"));
                SimpleOperation.setEmployee(employee);
                return Optional.of(SimpleOperation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
