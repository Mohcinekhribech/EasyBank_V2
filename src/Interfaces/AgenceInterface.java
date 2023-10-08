package Interfaces;

import java.util.List;
import java.util.Optional;

import DTO.Agence;

public interface AgenceInterface {
    Optional<Agence> add(Agence agence) ;
    int delete(String code);
    Optional<Agence> searchByCode(String code);
    Optional<Agence> searchByAdress(String adress);
    Optional<Agence> update(Agence agence,String code);
}
