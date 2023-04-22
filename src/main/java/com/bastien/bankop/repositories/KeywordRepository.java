package com.bastien.bankop.repositories;

import com.bastien.bankop.entities.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, String> {

    @Query(value = "SELECT kw FROM Keyword kw WHERE kw.idParent = ?1")
    Optional<List<Keyword>> listKeywordsFromTableId(Long tableId);
}
