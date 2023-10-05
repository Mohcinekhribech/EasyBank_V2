package DAO;

import DTO.Operation;
import Helpers.Database;
import Interfaces.OperationInterface;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OperationDao implements OperationInterface {
    Connection connection = Database.ConnectToDb();
        @Override
        public Optional<Operation> add(Operation operation) throws SQLException {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO operation (date, type, price, accountNumber , registrationNumber) VALUES (?, ?::operationType, ?, ?, ?)");
            statement.setDate(1, Date.valueOf(operation.getDate()));
            statement.setString(2, String.valueOf(operation.getType()));
            statement.setDouble(3, operation.getPrice());
            statement.setString(4, operation.getAccount().getAccountNumber());
            statement.setString(5,operation.getEmployee().getRegistrationNumber());
            if(statement.executeUpdate()>0)
            {
                connection.commit();
                return Optional.of(operation);
            }   
            connection.rollback();
            return Optional.empty();
        }

    @Override
    public int delete(int operationNumber) {
        try{
            PreparedStatement statement = this.connection.prepareStatement("delete from operation where operationnumber = ?");
            statement.setInt(1,operationNumber);
            return statement.executeUpdate();
        }catch(Exception e)
        {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Operation> searchOperation(Operation operation) {
        return null;
    }

    @Override
    public Map<String,String> searchOperationByNum(int operationNumber) {
        Map<String,String> operation = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM operation AS op INNER JOIN (SELECT * FROM person AS pr INNER JOIN employe AS em ON em.id = pr.id" +
                    "    WHERE em.registrationnumber = em.registrationnumber" +
                    ") AS employe ON employe.registrationnumber = op.registrationnumber where operationnumber = ?;");
            statement.setInt(1,operationNumber);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                operation.put("operationnumber", String.valueOf(resultSet.getInt("operationnumber")));
                operation.put("firstName",resultSet.getString("firstName"));
                operation.put("lastName",resultSet.getString("lastNAme"));
                operation.put("date",resultSet.getString("date"));
                operation.put("type",resultSet.getString("type"));
                operation.put("price",resultSet.getString("price"));
                operation.put("registrationNumber",resultSet.getString("registrationNumber"));
                operation.put("recrutmentDate",resultSet.getString("recrutmentDate"));
                operation.put("email",resultSet.getString("email"));
                return operation;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
