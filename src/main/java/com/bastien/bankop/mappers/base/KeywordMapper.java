package com.bastien.bankop.mappers.base;

import com.bastien.bankop.dto.base.KeywordDTO;
import com.bastien.bankop.entities.base.Keyword;
import com.bastien.bankop.repositories.base.KeywordRepository;
import com.bastien.bankop.repositories.base.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KeywordMapper extends AbstractBaseEntityMapper<KeywordDTO, Keyword> {

    @Autowired
    public KeywordMapper(TableRepository tableRepository, KeywordRepository keywordRepository) {
        super(tableRepository, keywordRepository, KeywordDTO.class, Keyword.class);
    }
}
