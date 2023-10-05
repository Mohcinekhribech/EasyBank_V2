package Interfaces;

import DTO.Affectation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AffectationInterface {
    Optional<Affectation> add(Affectation affectation) throws SQLException;
    int delete(int id) throws SQLException;
    List<Map<String, String>> historicalAffectation(String registrationNumber) throws SQLException;
    Map<String, Integer> statisticsAffectation() throws SQLException;
}