package com.bastien.bankop.repositories;

import com.bastien.bankop.entities.ConfigurationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationFilterRepository extends JpaRepository<ConfigurationFilter, Long> {
}
