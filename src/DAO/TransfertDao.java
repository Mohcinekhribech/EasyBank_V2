package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import DTO.CurrentAccount;
import DTO.Employee;
import DTO.SimpleOperation;
import DTO.Transfert;
import DTO.Enum.OperationType;
import Helpers.Database;
import Interfaces.TransfertInterface;

public class TransfertDao implements TransfertInterface{
    Connection connection = Database.ConnectToDb();
    Employee employee = new Employee();
    CurrentAccount Account = new CurrentAccount();

    @Override
    public Optional<Transfert> add(Transfert transfert) {
       try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO transfer (fromAccount, toAccount,date, price , registrationNumber) VALUES (?,?,?,?,?)");
            statement.setString(1, transfert.getFromAccount().getAccountNumber());
            statement.setString(2, transfert.getToAccount().getAccountNumber());
            if (statement.executeUpdate() > 0) {
                connection.commit();
                return Optional.of(transfert);
            }
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public int delete(int transfertNumber) {
        try {
            PreparedStatement statement = this.connection
                    .prepareStatement("delete from transfer where transferNumber = ?");
            statement.setInt(1, transfertNumber);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Transfert> searchTransfert(Transfert Transfert) {
        return null;
    }

    @Override
    public Optional<Transfert> searchTransfertByNum(int transfertNumber) {
        Transfert transfert = new Transfert();
         try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM transfert AS op INNER JOIN (SELECT * FROM person AS pr INNER JOIN employe AS em ON em.id = pr.id"
                            +
                            "    WHERE em.registrationnumber = em.registrationnumber" +
                            ") AS employe ON employe.registrationnumber = op.registrationnumber where transfertNumber = ?;");
            statement.setInt(1, transfertNumber);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                transfert.setTransfertNumber(resultSet.getInt("transfertNumber"));
                employee.setFirstName(resultSet.getString("firstName"));
                employee.setLastName(resultSet.getString("lastNAme"));
                transfert.setDate(LocalDate.parse(resultSet.getString("date")));
                transfert.setPrice(resultSet.getDouble("price"));
                employee.setRegistrationNumber(resultSet.getString("registrationNumber"));
                employee.setRecruitmentDate(LocalDate.parse(resultSet.getString("recrutmentDate")));
                employee.setEmail( resultSet.getString("email"));
                transfert.setEmployee(employee);
                return Optional.of(transfert);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    
}
