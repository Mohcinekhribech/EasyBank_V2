package DAO;

import DTO.Affectation;
import DTO.Employee;
import DTO.Mission;
import Helpers.Database;
import Interfaces.AffectationInterface;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AffectationDao implements AffectationInterface {
    Connection connection = Database.ConnectToDb();
    Mission mission = new Mission();
    Employee employee=new Employee();
    

    @Override
    public Optional<Affectation> add(Affectation affectation) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into affectaion (startDate,endDate,mission_code,employe_registrationNumber) values (?,?,?,?)");
            statement.setDate(1, Date.valueOf(affectation.getStartDate()));
            statement.setDate(2, Date.valueOf(affectation.getEndDate()));
            statement.setString(3, affectation.getMission().getCode());
            statement.setString(4, affectation.getEmployee().getRegistrationNumber());
            if (statement.execute()) {
                return Optional.of(affectation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public int delete(int id) {
        try {
            PreparedStatement statement;
            statement = connection.prepareStatement("delete from affectation where id = ?");
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Affectation> historicalAffectation(String registrationNumber) {
        List<Affectation> affects = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM affectation a join mission m on a.mission_code = m.code join person p on p.id = (select id from employe where registrationNumber = ?)  WHERE emloye_registrationNumber = ?");
            statement.setString(1, registrationNumber);
            statement.setString(2, registrationNumber);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Affectation affect = new Affectation();
                affect.setStartDate(LocalDate.parse(resultSet.getString("startDate")));
                affect.setEndDate(LocalDate.parse(resultSet.getString("endDate")));
                mission.setName(resultSet.getString("m.nom"));
                employee.setFirstName(resultSet.getString("p.prenom"));
                employee.setLastName(resultSet.getString("p.nom"));
                affect.setEmployee(employee);
                affect.setMission(mission);
                affects.add(affect);
                employee = null;
                mission = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return affects;
    }

    @Override
    public Map<String, Integer> statisticsAffectation() {
        Map<String, Integer> stringIntegerHashMaps = new HashMap<String, Integer>();
        try {
            String query = "SELECT mission.*, COUNT(affectation.*) FROM mission JOIN affectation ON mission.code = affectation.mission_code GROUP BY mission.code;";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                stringIntegerHashMaps.put(resultSet.getString("name"), resultSet.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stringIntegerHashMaps;
    }
}
