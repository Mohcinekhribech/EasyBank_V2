package Interfaces;

import java.util.List;
import java.util.Optional;

import DTO.Transfert;

public interface TransfertInterface {
    Optional<Transfert> add(Transfert Transfert);
    int delete(int transfertNumber);
    List<Transfert> searchTransfert(Transfert Transfert);
    Optional<Transfert> searchTransfertByNum(int transfertNumber);
}