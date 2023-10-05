package DAO;

import DTO.Affectation;
import Helpers.Database;
import Interfaces.AffectationInterface;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AffectationDao implements AffectationInterface {
    Connection connection = Database.ConnectToDb();
    @Override
    public Optional<Affectation> add(Affectation affectation) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("insert into affectaion (startDate,endDate,mission_code,employe_registrationNumber) values (?,?,?,?)");
        statement.setDate(1, Date.valueOf(affectation.getStartDate()));
        statement.setDate(2,Date.valueOf(affectation.getEndDate()));
        statement.setString(3,affectation.getMission().getCode());
        statement.setString(4,affectation.getEmployee().getRegistrationNumber());
        if(statement.execute())
        {
            return Optional.of(affectation);
        }
        return Optional.empty();
    }

    @Override
    public int delete(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("delete from affectation where id = ?");
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    @Override
    public List<Map<String, String>> historicalAffectation(String registrationNumber) throws SQLException {
        List<Map<String, String>> affects = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM affectation a join mission m on a.mission_code = m.code join person p on p.id = (select id from employe where registrationNumber = ?)  WHERE emloye_registrationNumber = ?");
        statement.setString(1, registrationNumber);
        statement.setString(2, registrationNumber);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Map<String, String> affect = new HashMap<>();
            affect.put("startDate", String.valueOf(resultSet.getDate("startDate").toLocalDate()));
            affect.put("endDate", String.valueOf(resultSet.getDate("endDate").toLocalDate()));
            affect.put("mission",resultSet.getString("m.nom"));            affect.put("employe",resultSet.getString("p.nom")+" "+resultSet.getString("p.prenom"));
            affects.add(affect);
        }
        return affects;
    }

    @Override
    public Map<String, Integer> statisticsAffectation() throws SQLException {
        String query = "SELECT mission.*, COUNT(affectation.*) FROM mission JOIN affectation ON mission.code = affectation.mission_code GROUP BY mission.code;";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        Map<String, Integer> stringIntegerHashMaps = new HashMap<String, Integer>();
        while (resultSet.next()) {
            stringIntegerHashMaps.put(resultSet.getString("name"), resultSet.getInt("count"));
        }
        return stringIntegerHashMaps;
    }
}
