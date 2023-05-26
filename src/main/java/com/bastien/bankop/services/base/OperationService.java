package com.bastien.bankop.services.base;

import com.bastien.bankop.dto.base.OperationDTO;
import com.bastien.bankop.entities.base.Keyword;
import com.bastien.bankop.entities.base.Operation;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.exceptions.MalFormedDTOException;
import com.bastien.bankop.exceptions.MalFormedEntityException;
import com.bastien.bankop.mappers.base.OperationMapper;
import com.bastien.bankop.repositories.base.OperationRepository;
import com.bastien.bankop.utils.TableID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OperationService extends AbstractBaseEntityService<OperationDTO, Operation> {

    private final KeywordService keywordService;

    @Autowired
    public OperationService(OperationRepository repo, OperationMapper mapper, KeywordService keywordService) {
        super(repo, mapper);
        this.keywordService = keywordService;
    }

    public boolean hasSubOperations(Operation o) {
        return o.getSubOperations() != null && !o.getSubOperations().isEmpty();
    }

    public List<OperationDTO> classifyOperationsByKeywords(List<OperationDTO> operationDTOList) {
        Set<Keyword> keywords = keywordService.getEntities();
        operationDTOList.forEach(dto -> this.classifyOperationsByKeywords(dto, keywords));
        return operationDTOList;
    }

    @Override
    protected void validateEntity(Operation entity) throws MalFormedEntityException {
        if (this.hasSubOperations(entity) && !entity.getCategory().getId().equals(TableID.SCINDES))
            throw new MalFormedEntityException("operation with sub-operations must be in SCINDES table.");
        if (!this.hasSubOperations(entity) && entity.getCategory().getId().equals(TableID.SCINDES))
            throw new MalFormedEntityException("operation without sub-operations can't be into SCINDES table.");
        Operation mother = entity.getMother();
        if (mother != null) {
            if (this.hasSubOperations(entity))
                throw new IllegalStateException("operation cannot have a mother if it has already children.");
            if (mother.getMother() != null)
                throw new IllegalStateException("operation cannot have a mother that is already a mother.");
        }
    }

    @Override
    protected void validateDTOBeforeSave(OperationDTO dto) throws MalFormedDTOException {
        super.validateDTOBeforeSave(dto);
        if (dto.getIdCategory().isEmpty()) dto.setIdCategory(TableID.VIDE);
        dto.getDate().orElseThrow(() -> new MalFormedDTOException("dto does not contain a date."));
        dto.getName().orElseThrow(() -> new MalFormedDTOException("dto does not contain a label."));
        dto.getPrice().orElseThrow(() -> new MalFormedDTOException("dto does not contain a price."));
    }

    @Override
    protected void validateEntityBeforeDelete(Operation entity) throws IllegalStateException {
        if (this.hasSubOperations(entity))
            throw new IllegalStateException("Cannot delete operation with sub-operations.");
    }

    /////////////////////////////////
    //            UTILS            //
    /////////////////////////////////

    private void classifyOperationsByKeywords(OperationDTO operationDTO, Set<Keyword> keywords) {
        assert operationDTO.getName().isPresent();
        String name = operationDTO.getName().get();
        Optional<Long> optionalIdCategory = keywords.stream()
                .filter(keyword -> {
                    String keywordName = keyword.getName();
                    return keywordName.equals(name)
                            || keywordName.contains(name)
                            || name.contains(keywordName);
                })
                .findFirst()
                .map(Keyword::getCategory)
                .map(Table::getId);
        operationDTO.setIdCategory(optionalIdCategory.orElse(TableID.VIDE));
    }
}
