package Interfaces;

import DTO.CurrentAccount;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CurrentAccountInterface {
    Optional<CurrentAccount> add(Optional<CurrentAccount> account);
    Optional<CurrentAccount> update(Optional<CurrentAccount> account,String code);
    List<CurrentAccount> searchByClient(String clientCode);
}
