package Interfaces;

import DTO.Account;
import DTO.Client;
import DTO.Enum.Status;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountInterface<T> {
    int delete(String accNum);
    List<Map<String ,String> > showByCreationDate(LocalDate creationDate);
    List<Map<String , String>> showByStatus(Status status);
    List<Map<String,String>> show();
    Map<String,String> searchByOperationNumber(int operationNumber);
    boolean changeState(String accNum,Status status) throws SQLException;
}
