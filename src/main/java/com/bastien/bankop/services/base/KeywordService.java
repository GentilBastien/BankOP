package com.bastien.bankop.services.base;

import com.bastien.bankop.dto.base.KeywordDTO;
import com.bastien.bankop.entities.base.Keyword;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.exceptions.MalFormedDTOException;
import com.bastien.bankop.exceptions.MalFormedEntityException;
import com.bastien.bankop.mappers.base.KeywordMapper;
import com.bastien.bankop.repositories.base.KeywordRepository;
import com.bastien.bankop.utils.TableID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeywordService extends AbstractBaseEntityService<KeywordDTO, Keyword> {

    @Autowired
    public KeywordService(KeywordRepository repo, KeywordMapper mapper) {
        super(repo, mapper);
    }

    public List<Keyword> listKeywordsFromTable(Table table) {
        return List.copyOf(table.getKeywords());
    }

    public Long classifyLabelWithKeyword(String label) {
        return getEntities()
                .stream()
                .filter(keyword -> label.contains(keyword.getName()))
                .findFirst()
                .map(Keyword::getCategory)
                .map(Table::getId)
                .orElse(TableID.VIDE);
    }

    @Override
    protected void validateEntity(Keyword entity) throws MalFormedEntityException {
        this.checkName(entity.getName());
    }

    @Override
    protected void validateDTOBeforeSave(KeywordDTO dto) throws MalFormedDTOException {
        super.validateDTOBeforeSave(dto);
        dto.getIdCategory().orElseThrow(() -> new MalFormedDTOException("dto does not contain a category."));
        dto.getName().orElseThrow(() -> new MalFormedDTOException("dto does not contain a keyword."));
    }

    @Override
    protected void validateEntityBeforeDelete(Keyword entity) throws IllegalStateException {

    }

    /////////////////////////////////
    //            UTILS            //
    /////////////////////////////////

    private void checkName(String name) {
        String err = "Invalid keyword name -> ";
        if (name == null)
            throw new MalFormedEntityException(err + "value is null.");
        if (name.length() <= 2)
            throw new MalFormedEntityException(err + "name is too short.");

        this.getEntities()
                .stream()
                .map(Keyword::getName)
                .filter(kw -> kw.contains(name) || name.contains(kw))
                .findFirst().ifPresent(kw -> {
                    throw new MalFormedEntityException(err + name + " is not valid because " + kw + " already exists.");
                });
    }
}
