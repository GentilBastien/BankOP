package com.bastien.bankop.services;

import com.bastien.bankop.entities.Keyword;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface KeywordService {

    Keyword getKeyword(String kw);

    List<Keyword> listKeywords();

    List<Keyword> listKeywordsFromTableId(Long id);

    void checkValidName(String name);

    void registerKeyword(Keyword kw);

    void deleteKeyword(String kw);

    void deleteKeywordsFromTableId(Long parentId);

    void moveKeywords(Long idParent, List<Keyword> keywords);
}
