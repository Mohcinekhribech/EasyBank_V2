package DAO;

import DTO.CurrentAccount;
import DTO.SavingAccount;
import Helpers.Database;
import Interfaces.SavingAccountInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class SavingAccountDao implements SavingAccountInterface {
    Connection connection = Database.ConnectToDb();
    @Override
    public Optional<SavingAccount> add(Optional<SavingAccount> account) throws SQLException {
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
        return Optional.empty();
    }

    @Override
    public Optional<SavingAccount> update(Optional<SavingAccount> account,String code) throws SQLException {
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
        return Optional.empty();
    }

    @Override
    public List<Map<String, String>> searchByClient(String clientCode) {
        Map<String,String> client = new HashMap<>();
        List<Map<String,String>> clients = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM account AS acc INNER JOIN savingAccount as sv ON sv.id = acc.accountNumber  where acc.client_code = ?;");
            statement.setString(1,clientCode);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                client.put("accountNumber",resultSet.getString("accountNumber"));
                client.put("balance",resultSet.getString("balance"));
                client.put("creationDate",resultSet.getString("creationDate"));
                client.put("client_code",resultSet.getString("client_code"));
                client.put("status",resultSet.getString("status"));
                client.put("interestRate",resultSet.getString("interestrate"));
                clients.add(client);
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return clients;
    }


}
