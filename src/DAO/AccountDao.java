package DAO;

import DTO.Account;
import DTO.Client;
import DTO.Enum.Status;
import Helpers.Database;
import Interfaces.AccountInterface;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class AccountDao implements AccountInterface<Account>{
    Connection connection = Database.ConnectToDb();

    @Override
    public int delete(String accNum) {
        try{
            PreparedStatement statement = this.connection.prepareStatement("delete from account where accountNumber = ?");
            statement.setString(1,accNum);
            return statement.executeUpdate();
        }catch(Exception e)
        {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Map<String, String>> showByCreationDate(LocalDate creationDate) {
        Map<String,String> account = new HashMap<>();
        List<Map<String,String>> accounts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select * from account where creationDate = ?");
            statement.setDate(1, Date.valueOf(creationDate));

            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                account.put("accountNumber",resultSet.getString("accountNumber"));
                account.put("balance", String.valueOf(resultSet.getDouble("balance")));
                account.put("creationDate",resultSet.getString("creationDate"));
                account.put("client_code",resultSet.getString("client_code"));
                account.put("status",resultSet.getString("status"));
                accounts.add(account);
            }
            return accounts;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String , String>> showByStatus(Status status) {
        Map<String,String> account = new HashMap<>();
        List<Map<String,String>> accounts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM account where status = ?::status;");
            statement.setString(1, String.valueOf(status));
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                account.put("accountNumber",resultSet.getString("accountNumber"));
                account.put("balance", String.valueOf(resultSet.getDouble("balance")));
                account.put("creationDate",resultSet.getString("creationDate"));
                account.put("client_code",resultSet.getString("client_code"));
                account.put("status",resultSet.getString("status"));
                accounts.add(account);
            }
            return accounts;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, String>> show() {
        Map<String,String> account = new HashMap<>();
        List<Map<String,String>> accounts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM account ;");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                account.put("accountNumber",resultSet.getString("accountNumber"));
                account.put("balance", String.valueOf(resultSet.getDouble("balance")));
                account.put("creationDate",resultSet.getString("creationDate"));
                account.put("client_code",resultSet.getString("client_code"));
                account.put("status",resultSet.getString("status"));
                accounts.add(account);
            }
            return accounts;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, String> searchByOperationNumber(int operationNumber) {
        Map<String,String> account = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM account where accountNumber = (select id from operation where operationNumber = ?);");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                account.put("accountNumber",resultSet.getString("accountNumber"));
                account.put("balance", String.valueOf(resultSet.getDouble("balance")));
                account.put("creationDate",resultSet.getString("creationDate"));
                account.put("client_code",resultSet.getString("client_code"));
                account.put("status",resultSet.getString("status"));
            }
            return account;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean changeState(String accNum,Status status) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("update account set status = ?::status");
        statement.setString(1, String.valueOf(status));
        return statement.executeUpdate()>0;
    }
}
