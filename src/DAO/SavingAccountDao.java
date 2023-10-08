package DAO;

import DTO.Client;
import DTO.CurrentAccount;
import DTO.SavingAccount;
import DTO.Enum.Status;
import Helpers.Database;
import Interfaces.SavingAccountInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class SavingAccountDao extends AccountDao implements SavingAccountInterface {
    Connection connection = Database.ConnectToDb();
    public SavingAccountDao(Client client) {
        super(client);
    }
    public SavingAccountDao() {
    }
    @Override
    public Optional<SavingAccount> add(Optional<SavingAccount> account)  {
        try{
        if(account.isPresent()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO account (accountNumber, balance, creationDate, client_code,status) VALUES (?, ?, ?, ?,?::status)");
            statement.setString(1, account.get().getAccountNumber());
            statement.setDouble(2, account.get().getBalance());
            statement.setDate(3, java.sql.Date.valueOf(account.get().getCreationDate()));
            statement.setString(4,account.get().getClient().getCode());
            statement.setString(5, String.valueOf(account.get().getStatus()));
            if(statement.executeUpdate()>0)
            {
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO SavingAccount (id, interestrate) VALUES ((SELECT accountNumber FROM account WHERE accountNumber = ? ), ?)");
                statement1.setString(1, account.get().getAccountNumber());
                statement1.setDouble(2, account.get().getInterestRate());
                if(statement1.executeUpdate()>0)
                {
                    connection.commit();
                    return account;
                }
            }
        }
        connection.rollback();
    }catch(SQLException e)
    {
        e.printStackTrace();
    }
        return Optional.empty();
    }

    @Override
    public Optional<SavingAccount> update(Optional<SavingAccount> account,String code)  {
        try{
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement(
                "UPDATE  account set  balance= ?, creationDate=? , status = ? where client_code = ? ");
        statement.setDouble(1, account.get().getBalance());
        statement.setDate(2, java.sql.Date.valueOf(account.get().getCreationDate()));
        statement.setString(3, account.get().getClient().getCode());
        statement.setString(4, String.valueOf(account.get().getStatus()));
        statement.setString(5, code);
        if (statement.executeUpdate() > 0) {
            PreparedStatement statement1 = connection.prepareStatement(
                    "UPDATE savingAccount set  interesrate = ? where id = (select id from account where client_code = ?)");
            statement1.setDouble(1, account.get().getInterestRate());
            statement1.setString(2, code);
            if (statement1.executeUpdate() > 0) {
                connection.commit();
                return account;
            }
        }
        connection.rollback();
    }catch(SQLException e)
    {
        e.printStackTrace();
    }
        return Optional.empty();
    }

    @Override
    public List<SavingAccount> searchByClient(String clientCode) {
        List<SavingAccount> accounts = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM account AS acc INNER JOIN savingAccount as sv ON sv.id = acc.accountNumber  where acc.client_code = ?;");
            statement.setString(1,clientCode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                SavingAccount account = new SavingAccount();
                account.setAccountNumber(resultSet.getString("accountNumber"));
                account.setBalance(resultSet.getDouble("balance"));
                account.setCreationDate(LocalDate.parse(resultSet.getString("crationDate")));
                account.setStatus(Status.valueOf(resultSet.getString("status")));
                account.setInterestRate(resultSet.getDouble("interesrate"));
                client.setFirstName(resultSet.getString("firstName"));
                client.setLastName(resultSet.getString("lastName"));
                account.setClient(client);
                accounts.add(account);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return accounts;
    }


}
