package DAO;

import DTO.Account;
import DTO.Client;
import DTO.CurrentAccount;
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

public abstract class AccountDao implements AccountInterface<Account>{
    Connection connection = Database.ConnectToDb();
    protected Client client;
    public AccountDao()
    {
    }
    public AccountDao(Client client)
    {
        this.client = client;
    }
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
    public List<Account> showByCreationDate(LocalDate creationDate) {
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select * from account acc join client cl on cl.id = acc.client_code  where creationDate = ?");
            statement.setDate(1, Date.valueOf(creationDate));
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                CurrentAccount account = new CurrentAccount();
                account.setAccountNumber(resultSet.getString("accountNumber"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setCreationDate(creationDate);
                account.setStatus( Status.valueOf(resultSet.getString("status")));
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                account.setClient(client);
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
    public List<Account> showByStatus(Status status) { 
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select * from account acc join client cl on cl.id = acc.client_code where status = ?::status;");
            statement.setString(1, String.valueOf(status));
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                CurrentAccount account = new CurrentAccount();
               account.setAccountNumber(resultSet.getString("accountNumber"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setCreationDate(LocalDate.parse(resultSet.getString("accountNumber")));
                account.setStatus( Status.valueOf(resultSet.getString("status")));
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                account.setClient(client);
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
    public List<Account> show() {
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select * from account acc join client cl on cl.id = acc.client_code;");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                  CurrentAccount account = new CurrentAccount();
               account.setAccountNumber(resultSet.getString("accountNumber"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setCreationDate(LocalDate.parse(resultSet.getString("accountNumber")));
                account.setStatus( Status.valueOf(resultSet.getString("status")));
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                account.setClient(client);
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
    public Optional<Account> searchByOperationNumber(int operationNumber) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM account where accountNumber = (select id from operation where operationNumber = ?);");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                CurrentAccount account = new CurrentAccount();
                account.setAccountNumber(resultSet.getString("accountNumber"));
                 account.setBalance(resultSet.getDouble("balance"));
                 account.setCreationDate(LocalDate.parse(resultSet.getString("accountNumber")));
                 account.setStatus( Status.valueOf(resultSet.getString("status")));
                 client.setFirstName(resultSet.getString("firstName"));
                 client.setLastName(resultSet.getString("lastName"));
                 account.setClient(client);
                 return Optional.of(account);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean changeState(String accNum,Status status)  {
        try {
            PreparedStatement statement = connection.prepareStatement("update account set status = ?::status");
            statement.setString(1, String.valueOf(status));
            return statement.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return false ;  
    }
}
