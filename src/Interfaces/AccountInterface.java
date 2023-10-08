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
    abstract List<Account> showByCreationDate(LocalDate creationDate);
    abstract List<Account> showByStatus(Status status);
    abstract List<Account> show();
    abstract Optional<Account> searchByOperationNumber(int operationNumber);
    boolean changeState(String accNum,Status status);
}
