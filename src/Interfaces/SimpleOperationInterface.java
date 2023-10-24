package Interfaces;

import DTO.Operation;
import DTO.SimpleOperation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SimpleOperationInterface {
    Optional<SimpleOperation> add(SimpleOperation SimpleOperation);
    int delete(int SimpleOperationNumber);
    List<SimpleOperation> searchSimpleOperation(SimpleOperation SimpleOperation);
    Optional<SimpleOperation> searchSimpleOperationByNum(int operationNumber);

}
