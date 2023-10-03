package com.bastien.bankop.mappers.base;

import com.bastien.bankop.dto.base.AbstractBaseEntityDTO;
import com.bastien.bankop.entities.base.AbstractBaseEntity;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.exceptions.NoEntityFoundException;
import com.bastien.bankop.mappers.EntityMapper;
import com.bastien.bankop.repositories.base.TableRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractBaseEntityMapper<DTO extends AbstractBaseEntityDTO, ENT extends AbstractBaseEntity>
        implements EntityMapper<DTO, ENT, Long> {

    protected final TableRepository tableRepository;
    protected final JpaRepository<ENT, Long> ownRepo;
    private final Class<DTO> dtoClass;
    private final Class<ENT> entClass;

    protected AbstractBaseEntityMapper(
            TableRepository tableRepository,
            JpaRepository<ENT, Long> ownRepo,
            Class<DTO> dtoClass,
            Class<ENT> entClass) {
        this.tableRepository = tableRepository;
        this.ownRepo = ownRepo;
        this.dtoClass = dtoClass;
        this.entClass = entClass;
    }

    @Override
    public ENT toEntity(DTO dto) {
        try {
            if (dto.getId().isPresent()) {
                //update
                ENT entityToUpdate = this.ownRepo.findById(dto.getId().get()).orElseThrow(NoEntityFoundException::new);
                if (dto.getIdCategory().isPresent()) {
                    Table category = this.tableRepository.findById(dto.getIdCategory().get()).orElseThrow(NoEntityFoundException::new);
                    entityToUpdate.setCategory(category);
                }
                if (dto.getName().isPresent())
                    entityToUpdate.setName(dto.getName().get());
                return entityToUpdate;
            } else {
                //save
                ENT entityToSave = entClass.getDeclaredConstructor().newInstance();
                Table category = this.tableRepository.findById(dto.getIdCategory().get()).orElseThrow(NoEntityFoundException::new);
                assert category != null; //category can't be null if we save a new Base Entity
                entityToSave.setCategory(category);
                entityToSave.setName(dto.getName().get());
                return entityToSave;
            }
        } catch (ReflectiveOperationException e) {
            System.err.println("reflective exception has occurred while converting dto to entity.");
        }
        return null;
    }

    @Override
    public DTO toDTO(ENT entity) {
        DTO dto = null;
        try {
            dto = dtoClass
                    .getDeclaredConstructor(Long.class, Long.class, String.class)
                    .newInstance(
                            entity.getId(),
                            entity.getCategory() == null ? null : entity.getCategory().getId(),
                            entity.getName()
                    );
        } catch (ReflectiveOperationException e) {
            System.err.println("reflective exception has occurred while converting entity to dto.");
        }
        return dto;
    }

    @Override
    public DTO buildDTO() {
        DTO dto = null;
        try {
            dto = dtoClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            System.err.println("reflective exception has occurred while building a dto.");
        }
        return dto;
    }
}