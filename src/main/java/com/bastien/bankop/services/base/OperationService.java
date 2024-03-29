package com.bastien.bankop.services.base;

import com.bastien.bankop.dto.ResponseImportOperationDTO;
import com.bastien.bankop.dto.base.OperationDTO;
import com.bastien.bankop.entities.base.Operation;
import com.bastien.bankop.exceptions.MalFormedDTOException;
import com.bastien.bankop.exceptions.MalFormedEntityException;
import com.bastien.bankop.mappers.base.OperationMapper;
import com.bastien.bankop.repositories.base.OperationRepository;
import com.bastien.bankop.utils.TableID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationService extends AbstractBaseEntityService<OperationDTO, Operation> {

    @Autowired
    public OperationService(OperationRepository repo, OperationMapper mapper) {
        super(repo, mapper);
    }

    public boolean hasSubOperations(Operation o) {
        return o.getSubOperations() != null && !o.getSubOperations().isEmpty();
    }

    public void setDoublonsInDatabase(List<ResponseImportOperationDTO> list) {
        for (ResponseImportOperationDTO result : list) {
            if (getEntities().stream().anyMatch(op -> this.isOperationEqualsToOperationDto(op, result))) {
                result.setDoublon(ResponseImportOperationDTO.DATABASE);
            }
        }
    }

    public boolean isOperationEqualsToOperationDto(Operation op, ResponseImportOperationDTO dto) {
        return dto.getCategoryId().equals(op.getCategory().getId())
                && dto.getDate().equals(op.getDate())
                && dto.getName().equals(op.getName())
                && dto.getPrice().equals(op.getPrice());
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
}
