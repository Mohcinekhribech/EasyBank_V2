package DAO;

import DTO.Client;
import Helpers.Database;
import Interfaces.ClientInterface;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ClientDao implements ClientInterface {
    Connection connection = Database.ConnectToDb();
    @Override
    public Optional<Client> add(Optional<Client> client) throws SQLException {
        if(client.isPresent()) {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO person (firstName, lastName, dateofbirth, phonenumber) VALUES (?, ?, ?, ?)");
            statement.setString(1, client.get().getFirstName());
            statement.setString(2, client.get().getLastName());
            statement.setDate(3, Date.valueOf(client.get().getDateOfBirth()));
            statement.setString(4, client.get().getPhoneNumber());
            if(statement.executeUpdate()>0)
            {
                PreparedStatement statement1 = connection.prepareStatement("INSERT INTO client (id, code , adress) VALUES ((SELECT id FROM person WHERE firstName = ? AND lastName = ? AND dateofbirth = ?), ?, ?)");
                statement1.setString(1, client.get().getFirstName());
                statement1.setString(2, client.get().getLastName());
                statement1.setDate(3, Date.valueOf(client.get().getDateOfBirth()));
                statement1.setString(4, client.get().getCode());
                statement1.setString(5, client.get().getAdress());
                if(statement1.executeUpdate()>0)
                {
                    connection.commit();
                    return client;
                }
            }
        }
        connection.rollback();
        return Optional.empty();
    }
    @Override
    public int delete(String code) {
        try{
            PreparedStatement statement = this.connection.prepareStatement("delete from person where id = (select id from client where code = ?)");
            statement.setString(1,code);
            return statement.executeUpdate();
        }catch(Exception e)
        {
            throw new RuntimeException();
        }
    }

    @Override
    public Optional<Client> update(Client client, String code) throws SQLException {
        connection.setAutoCommit(false);
        PreparedStatement statement = connection.prepareStatement("UPDATE  person set  firstName= ?, lastName=? , dateofbirth = ?, phonenumber = ? where id = (select id from client where code = ?) ");
        statement.setString(1, client.getFirstName());
        statement.setString(2, client.getLastName());
        statement.setDate(3, client.getDateOfBirth()!=null?Date.valueOf(client.getDateOfBirth()):null);
        statement.setString(4, client.getPhoneNumber());
        statement.setString(5,code);
        if(statement.executeUpdate()>0)
        {
            PreparedStatement statement1 = connection.prepareStatement("UPDATE client set  adress = ? where code = ?");
            statement1.setString(1,client.getAdress());
            statement1.setString(2,code);
            if(statement1.executeUpdate()>0)
            {
                connection.commit();
                return Optional.of(client);
            }
        }
        connection.rollback();
        return Optional.empty();
    }

    @Override
    public Map<String,String> searchByCode(String code) {
        Map<String,String> client = new HashMap<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person AS pr INNER JOIN client as c ON c.id = pr.id  where c.code = ?;");
            statement.setString(1,code);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                client.put("firstName",resultSet.getString("firstName"));
                client.put("lastName",resultSet.getString("lastName"));
                client.put("dateOfBirth",resultSet.getString("dateOfBirth"));
                client.put("phoneNumber",resultSet.getString("phoneNumber"));
                client.put("code",resultSet.getString("code"));
                client.put("adress",resultSet.getString("adress"));
                return client;
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    public List<Map<String , String>> Search(Client client) {
        Map<String,String> cl = new HashMap<>();
        List<Map<String , String>> clients = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person AS pr INNER JOIN client as cl ON cl.id = pr.id  where cl.code = ? OR firstname = ? OR lastName = ? OR phonenumber = ? OR dateOfBirth = ? OR adress = ?;");
            statement.setString(1,client.getCode());
            statement.setString(2,client.getFirstName());
            statement.setString(3,client.getLastName());
            statement.setString(4,client.getPhoneNumber());
            statement.setDate(5, client.getDateOfBirth()!=null?Date.valueOf(client.getDateOfBirth()):null);
            statement.setString(6,client.getAdress());
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                cl.put("firstName",resultSet.getString("firstName"));
                cl.put("lastName",resultSet.getString("lastName"));
                cl.put("dateOfBirth",resultSet.getString("dateOfBirth"));
                cl.put("phoneNumber",resultSet.getString("phoneNumber"));
                cl.put("code",resultSet.getString("code"));
                cl.put("adress",resultSet.getString("adress"));
                clients.add(cl);
            }
            return clients;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Map<String , String>> showClients() {
        Map<String,String> cl = new HashMap<>();
        List<Map<String , String>> clients = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM person AS pr INNER JOIN client as cl ON cl.id = pr.id ;");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                cl.put("firstName",resultSet.getString("firstName"));
                cl.put("lastName",resultSet.getString("lastName"));
                cl.put("dateOfBirth",resultSet.getString("dateOfBirth"));
                cl.put("phoneNumber",resultSet.getString("phoneNumber"));
                cl.put("code",resultSet.getString("code"));
                cl.put("adress",resultSet.getString("adress"));
                clients.add(cl);
            }
            return clients;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
