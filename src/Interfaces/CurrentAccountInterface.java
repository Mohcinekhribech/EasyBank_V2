package Interfaces;

import DTO.CurrentAccount;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CurrentAccountInterface {
    Optional<CurrentAccount> add(Optional<CurrentAccount> account) throws SQLException;
    Optional<CurrentAccount> update(Optional<CurrentAccount> account,String code) throws SQLException;
    List<Map<String,String>> searchByClient(String clientCode);
}
