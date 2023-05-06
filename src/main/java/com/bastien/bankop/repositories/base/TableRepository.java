package com.bastien.bankop.repositories.base;

import com.bastien.bankop.entities.base.Table;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {
}
