package com.bastien.bankop.services.impl;

import com.bastien.bankop.entities.Keyword;
import com.bastien.bankop.exceptions.KeywordNameException;
import com.bastien.bankop.exceptions.NoKeywordFoundException;
import com.bastien.bankop.repositories.KeywordRepository;
import com.bastien.bankop.services.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("keywordServiceDB")
public class KeywordServiceDB implements KeywordService {

    private final KeywordRepository keywordRepository;

    @Autowired
    public KeywordServiceDB(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    @Override
    public Keyword getKeyword(String kw) {
        Optional<Keyword> optionalKeyword = this.keywordRepository.findById(kw);
        return optionalKeyword.orElseThrow(
                () -> new NoKeywordFoundException("No keyword named " + kw + " has been found."));
    }

    @Override
    public List<Keyword> listKeywords() {
        return this.keywordRepository.findAll();
    }

    @Override
    public List<Keyword> listKeywordsFromTableId(Long tableId) {
        Optional<List<Keyword>> optionalKeywords = this.keywordRepository.listKeywordsFromTableId(tableId);
        return optionalKeywords.orElse(List.of());
    }

    /**
     * Checks if a name is valid for a <code>Keyword</code>. A valid name must be non-null and length greater than 2. It
     * also needs not to contain another keyword name or to be contained.
     *
     * @param name The name to check.
     */
    @Override
    public void checkValidName(String name) {
        String err = "Invalid keyword name -> ";
        if (name == null)
            throw new KeywordNameException(err + "value is null.");
        if (name.length() <= 2)
            throw new KeywordNameException(err + "name is too short.");

        this.listKeywords().stream().map(Keyword::getKeyword)
                .filter(kw -> kw.equals(name) || kw.contains(name) || name.contains(kw)).findFirst().ifPresent(kw -> {
                    throw new KeywordNameException(err + name + " is not valid because " + kw + " already exists.");
                });
    }

    @Override
    public void registerKeyword(Keyword kw) {
        this.checkValidName(kw.getKeyword());
        this.keywordRepository.save(kw);
    }

    @Override
    public void deleteKeyword(String kw) {
        this.keywordRepository.deleteById(kw);
    }

    @Override
    public void deleteKeywordsFromTableId(Long parentId) {
        this.listKeywordsFromTableId(parentId)
                .stream()
                .map(Keyword::getKeyword)
                .forEach(this.keywordRepository::deleteById);
    }

    @Override
    public void moveKeywords(Long idParent, List<Keyword> keywords) {
        keywords.forEach(kw -> {
                    kw.setIdParent(idParent);
                    this.keywordRepository.save(kw);
                });
    }
}
