package com.bastien.bankop.services.base;

import com.bastien.bankop.dto.base.TableDTO;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.exceptions.MalFormedDTOException;
import com.bastien.bankop.exceptions.MalFormedEntityException;
import com.bastien.bankop.mappers.base.TableMapper;
import com.bastien.bankop.repositories.base.TableRepository;
import com.bastien.bankop.utils.TableID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TableService extends AbstractBaseEntityService<TableDTO, Table> {

    @Autowired
    public TableService(TableRepository repo, TableMapper mapper) {
        super(repo, mapper);
    }

    public List<Table> listAllChildrenFrom(Table table) {
        List<Table> concatChildren = new LinkedList<>();
        this.aggregateAllChildrenFromTableId(table, concatChildren);
        return concatChildren;
    }

    public List<Table> listDepthPath(Table table) {
        return this.listDepthPath(new LinkedList<>(List.of(table)));
    }

    public boolean hasChildren(Table table) {
        return table.getTables() != null && !table.getTables().isEmpty();
    }

    public boolean hasOperations(Table table) {
        return table.getOperations() != null && !table.getOperations().isEmpty();
    }

    public boolean hasKeywords(Table table) {
        return table.getKeywords() != null && !table.getKeywords().isEmpty();
    }

    @Override
    protected void validateEntity(Table entity) throws MalFormedEntityException {
        this.getEntityWithId(entity.getCategory().getId()); // check if category id exists
        this.checkCircularReferences(entity); // check if category leads to circular references
        this.checkName(entity); //check name
    }

    @Override
    protected void validateDTOBeforeSave(TableDTO dto) throws MalFormedDTOException {
        super.validateDTOBeforeSave(dto);
        dto.getIdCategory().orElseThrow(() -> new MalFormedDTOException("DTO does not contain a category."));
        dto.getName().orElseThrow(() -> new MalFormedDTOException("DTO does not contain a name."));
    }

    @Override
    protected void validateDTOBeforeUpdate(TableDTO dto) throws MalFormedDTOException {
        super.validateDTOBeforeUpdate(dto);
        if (dto.getId().stream().anyMatch(id -> id.equals(TableID.ROOT) || id.equals(TableID.SCINDES) || id.equals(TableID.VIDE)))
            throw new MalFormedDTOException("Cannot update this table.");
        if (dto.getIdCategory().isPresent()) {
            Long idCategory = dto.getIdCategory().get();
            if (idCategory.equals(TableID.SCINDES) || idCategory.equals(TableID.VIDE))
                throw new MalFormedDTOException("Cannot move this table here.");
        }
    }

    @Override
    protected void validateEntityBeforeDelete(Table entity) throws IllegalStateException {
        Long id = entity.getId();
        if (id.equals(TableID.ROOT) || id.equals(TableID.SCINDES) || id.equals(TableID.VIDE))
            throw new IllegalStateException("Cannot delete this table.");
        if (this.hasChildren(entity))
            throw new IllegalStateException("Cannot delete the table " + entity + " because it has children.");
        if (this.hasOperations(entity))
            throw new IllegalStateException("Cannot delete the table " + entity + " because it has operations.");
        if (this.hasKeywords(entity))
            throw new IllegalStateException("Cannot delete the table " + entity + " because it has keywords.");
    }

    /////////////////////////////////
    //            UTILS            //
    /////////////////////////////////

    private List<Table> listDepthPath(LinkedList<Table> listParents) {
        if (listParents == null || listParents.isEmpty())
            throw new IllegalArgumentException("the argument is null or empty.");
        Table first = listParents.peek();
        Table parent = first.getCategory();
        if (parent == null)
            return listParents;
        listParents.addFirst(parent);
        return this.listDepthPath(listParents);
    }

    private void aggregateAllChildrenFromTableId(Table table, List<Table> concatChildren) {
        concatChildren.add(table);
        Set<Table> children = table.getTables();
        children.forEach(child -> this.aggregateAllChildrenFromTableId(child, concatChildren));
    }

    private void checkName(Table entity) {
        String name = entity.getName();
        if (name == null || name.length() <= 3)
            throw new MalFormedEntityException("Table name is too short -> " + name + ".");
        if (super.getEntities().stream().anyMatch(e -> !Objects.equals(entity, e) && e.getName().equals(name)))
            throw new MalFormedEntityException("This name already exists for a table -> " + name + ".");
    }

    private void checkCircularReferences(Table table) {
        Objects.requireNonNull(table);
        Collection<Table> list = new ArrayList<>();
        list.add(table);
        if (this.hasCircularReferences(table.getCategory(), list))
            throw new IllegalStateException("Circular reference in table " + table + ".");
    }

    private boolean hasCircularReferences(Table table, Collection<Table> visitedTables) {
        if (table == null) return false;
        if (visitedTables.contains(table)) return true;
        visitedTables.add(table);
        return this.hasCircularReferences(table.getCategory(), visitedTables);
    }
}
