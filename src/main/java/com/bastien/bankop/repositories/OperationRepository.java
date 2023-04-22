package com.bastien.bankop.repositories;

import com.bastien.bankop.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query(value = "SELECT op FROM Operation op WHERE op.idParent = ?1")
    Optional<List<Operation>> listOperationsFromTableId(Long id);

    @Query(value = "SELECT op FROM Operation op WHERE op.idMother = ?1")
    Optional<List<Operation>> listSubOperationsFromMotherId(Long id);
}
