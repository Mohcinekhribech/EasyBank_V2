package Interfaces;

import DTO.Mission;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MissionInterface {
    Optional<Mission> add(Mission mission);
    int delete(String code);
    List<Mission> getAll();
}