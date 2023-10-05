package Interfaces;

import DTO.CurrentAccount;
import DTO.SavingAccount;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SavingAccountInterface {
    Optional<SavingAccount> add(Optional<SavingAccount> account) throws SQLException;
    Optional<SavingAccount> update(Optional<SavingAccount> account,String code) throws SQLException;
    List<Map<String,String>> searchByClient(String clientCode);
}
