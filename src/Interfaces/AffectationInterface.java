package Interfaces;

import DTO.Affectation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AffectationInterface {
    Optional<Affectation> add(Affectation affectation);
    int delete(int id);
    List<Affectation> historicalAffectation(String registrationNumber);
    Map<String, Integer> statisticsAffectation();
}