package Interfaces;

import DTO.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClientInterface {
    Optional<Client> add(Optional<Client> client);
    int delete(String code);
    Optional<Client> update(Client client,String code);
    Optional<Client> searchByCode(String code);
    List<Client> showClients();
}
