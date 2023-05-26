package com.bastien.bankop.mappers.base;

import com.bastien.bankop.dto.base.OperationDTO;
import com.bastien.bankop.entities.base.Operation;
import com.bastien.bankop.exceptions.NoEntityFoundException;
import com.bastien.bankop.repositories.base.OperationRepository;
import com.bastien.bankop.repositories.base.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OperationMapper extends AbstractBaseEntityMapper<OperationDTO, Operation> {

    @Autowired
    public OperationMapper(TableRepository tableRepository, OperationRepository operationRepository) {
        super(tableRepository, operationRepository, OperationDTO.class, Operation.class);
    }

    @Override
    public Operation toEntity(OperationDTO dto) {
        Operation superOp = super.toEntity(dto);
        if (dto.getIdMother().isPresent()) {
            Operation mother = super.ownRepo.findById(dto.getIdMother().get()).orElseThrow(NoEntityFoundException::new);
            superOp.setMother(mother);
        } else if (dto.getId().isEmpty()) {
            superOp.setMother(null); //no id and no id mother -> save operation ; null must be set
        }
        if (dto.getManuallyCategorized().isPresent())
            superOp.setManuallyCategorized(dto.getManuallyCategorized().get());
        else if (dto.getId().isEmpty()) {
            superOp.setManuallyCategorized(false); //no id and no manually categorized -> save operation ; false must be set
        }
        if (dto.getDate().isPresent())
            superOp.setDate(dto.getDate().get());
        if (dto.getPrice().isPresent())
            superOp.setPrice(dto.getPrice().get());
        return superOp;
    }

    @Override
    public OperationDTO toDTO(Operation entity) {
        OperationDTO dto = super.toDTO(entity);
        dto.setIdMother(entity.getMother() == null ? null : entity.getMother().getId());
        dto.setDate(entity.getDate());
        dto.setPrice(entity.getPrice());
        dto.setManuallyCategorized(entity.getManuallyCategorized());
        return dto;
    }
}
