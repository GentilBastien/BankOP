package com.bastien.bankop.services.impl;

import com.bastien.bankop.entities.Operation;
import com.bastien.bankop.exceptions.NoOperationFoundException;
import com.bastien.bankop.repositories.OperationRepository;
import com.bastien.bankop.services.OperationService;
import com.bastien.bankop.utils.TableID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("operationServiceDB")
public class OperationServiceDB implements OperationService {

    private final OperationRepository operationRepository;

    @Autowired
    public OperationServiceDB(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation getOperation(Long id) {
        Optional<Operation> optionalOperation = this.operationRepository.findById(id);
        return optionalOperation.orElseThrow(() -> new NoOperationFoundException("No operation found with id " + id));
    }

    @Override
    public List<Operation> listOperations() {
        return this.operationRepository.findAll();
    }

    @Override
    public List<Operation> listOperationsFromTableId(Long parentId) {
        Optional<List<Operation>> optionalOperations = this.operationRepository.listOperationsFromTableId(parentId);
        return optionalOperations.orElse(List.of());
    }

    @Override
    public List<Operation> listSubOperationsFromMotherId(Long motherId) {
        Optional<List<Operation>> optionalOperations = this.operationRepository.listSubOperationsFromMotherId(motherId);
        return optionalOperations.orElse(List.of());
    }

    @Override
    public boolean hasSubOperations(Long motherId) {
        return !this.listSubOperationsFromMotherId(motherId).isEmpty();
    }

    @Override
    public void saveOperation(Operation operation) {
        Long id = operation.getId();
        if (this.hasSubOperations(id) && !operation.getIdParent().equals(TableID.SCINDES))
            throw new IllegalStateException("operation with sub-operations must be in SCINDES table and can't be moved.");
        if (!this.hasSubOperations(id) && operation.getIdParent().equals(TableID.SCINDES))
            throw new IllegalStateException("operation without sub-operations can't be moved into SCINDES table.");

        Long idMother = operation.getIdMother();
        if (idMother != null) {
            if (this.hasSubOperations(id))
                throw new IllegalStateException("operation cannot have a mother if it has already children.");
            Operation mother = this.getOperation(idMother);
            if (mother.getIdMother() != null)
                throw new IllegalStateException("operation cannot have a mother that is already a mother.");
            if (!mother.getIdParent().equals(TableID.SCINDES)) {
                mother.setIdParent(TableID.SCINDES);
                this.operationRepository.save(mother);
            }
        }
        this.operationRepository.save(operation);
    }

    @Override
    public void deleteOperation(Long id) {
        if (this.hasSubOperations(id))
            throw new IllegalStateException("Cannot delete operation with sub-operations.");
        this.operationRepository.deleteById(id);
    }

    @Override
    public void deleteOperationAndSubOperations(Long id) {
        this.listSubOperationsFromMotherId(id).stream()
                .map(Operation::getId)
                .forEach(this.operationRepository::deleteById);
    }
}
