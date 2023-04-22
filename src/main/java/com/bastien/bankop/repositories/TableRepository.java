package com.bastien.bankop.repositories;

import com.bastien.bankop.entities.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    @Query(value = "SELECT t FROM Table t WHERE t.idParent = ?1")
    Optional<List<Table>> listChildrenFromTableId(Long id);

    @Query(value = "SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Table t WHERE t.name = ?1")
    boolean nameExists(String name);
}
