package Helpers;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection = null;
    public static Connection ConnectToDb()
    {
        try  {
            Class.forName("org.postgresql.Driver");
            if(connection == null)
            {
                 connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+"postgres","postgres","1832003");
                 return connection;
            }
            return connection;
        }catch (Exception e){
                throw new RuntimeException(e);
        }
    }
}
