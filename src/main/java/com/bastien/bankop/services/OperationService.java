package com.bastien.bankop.services;

import com.bastien.bankop.entities.Operation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperationService {

    Operation getOperation(Long id);

    List<Operation> listOperations();

    List<Operation> listOperationsFromTableId(Long parentId);

    List<Operation> listSubOperationsFromMotherId(Long motherId);

    boolean hasSubOperations(Long id);

    void saveOperation(Operation operation);

    void deleteOperation(Long id);

    void deleteOperationAndSubOperations(Long id);
}
