package DAO;

import DTO.Mission;
import Helpers.Database;
import Interfaces.MissionInterface;

import java.sql.*;
import java.util.*;

public class MissionDao implements MissionInterface {
    Connection connection = Database.ConnectToDb();

    @Override
    public Optional<Mission> add(Mission mission) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO mission (code, name, description) VALUES (?, ?, ?)");
            statement.setString(1, mission.getCode());
            statement.setString(2, mission.getName());
            statement.setString(3, mission.getDescription());
            if (statement.executeUpdate() > 0) {
                connection.commit();
                return Optional.of(mission);
            }
            connection.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();

    }

    @Override
    public int delete(String code) {
        try {
            PreparedStatement statement = this.connection.prepareStatement("delete from mission where code = ?");
            statement.setString(1, code);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Mission> getAll() {
        List<Mission> missions = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("select * from mission");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Mission mission = new Mission();
                mission.setCode(resultSet.getString("code"));
                mission.setName(resultSet.getString("name"));
                resultSet.getString("description");
                missions.add(mission);
            }
            return missions;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
