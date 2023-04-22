package com.bastien.bankop.services.impl;

import com.bastien.bankop.entities.Table;
import com.bastien.bankop.exceptions.NoTableFoundException;
import com.bastien.bankop.exceptions.TableNameException;
import com.bastien.bankop.repositories.TableRepository;
import com.bastien.bankop.services.TableService;
import com.bastien.bankop.utils.TableID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("tableServiceDB")
public class TableServiceDB implements TableService {
    private final TableRepository tableRepository;

    @Autowired
    public TableServiceDB(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @Override
    public Table getTable(Long id) {
        Optional<Table> optionalTable = this.tableRepository.findById(id);
        return optionalTable.orElseThrow(() -> new NoTableFoundException("No table found with id " + id));
    }

    @Override
    public List<Table> listTables() {
        return this.tableRepository.findAll();
    }

    @Override
    public List<Table> listChildrenFromTableId(Long id) {
        Optional<List<Table>> optionalTables = this.tableRepository.listChildrenFromTableId(id);
        return optionalTables.orElse(List.of());
    }

    @Override
    public List<Table> listAllChildrenFromTableId(Long id) {
        List<Table> concatChildren = new LinkedList<>();
        this.aggregateAllChildrenFromTableId(this.getTable(id), concatChildren);
        return concatChildren;
    }

    /**
     * Recursively call this method to get all the children of a <code>Table</code>.
     */
    private void aggregateAllChildrenFromTableId(Table table, List<Table> concatChildren) {
        Objects.requireNonNull(table, "the argument is null.");
        Objects.requireNonNull(concatChildren, "the argument is null.");
        concatChildren.add(table);
        List<Table> children = this.listChildrenFromTableId(table.getId());
        children.forEach(child -> this.aggregateAllChildrenFromTableId(child, concatChildren));
    }

    /**
     * This method is used to recursively get all the parents of a <code>Table</code>. The first call is made with a
     * list of one table. It recursively gets the first table of the list and append to the list the parent of this
     * table.
     */
    @Override
    public List<Table> listDepthPath(LinkedList<Table> listParents) {
        if (listParents == null || listParents.isEmpty())
            throw new IllegalArgumentException("the argument is null or empty.");

        Table head = listParents.peek();
        if (head.getId().equals(TableID.ROOT))
            return listParents;

        Long idParent = head.getIdParent();
        listParents.addFirst(this.getTable(idParent));
        return this.listDepthPath(listParents);
    }

    @Override
    public boolean hasChildren(Long id) {
        return !this.listChildrenFromTableId(id).isEmpty();
    }

    @Override
    public void checkNameExists(String name) {
        if (this.tableRepository.nameExists(name))
            throw new TableNameException("This name already exists for a table -> " + name);
    }

    /**
     * Check if a name is valid. A name is valid if it is not null and has a length greater than 3.
     *
     * @param name The name of a <code>Table</code>.
     * @throws TableNameException if the <code>Table</code> name is invalid.
     */
    @Override
    public void checkValidName(String name) {
        String err = "Invalid table name -> ";
        if (name == null)
            throw new TableNameException(err + "value is null.");
        if (name.length() <= 3)
            throw new TableNameException(err + "name is too short.");
    }

    @Override
    public void checkCircularReference(Long id) {
        if (this.hasCircularReference(this.getTable(id), new HashSet<>()))
            throw new IllegalStateException("Circular reference in table " + id + ".");
    }

    /**
     * If the table in parameter is contained in the visited table, then there is a circular reference. This is
     * Depth-First Search.
     */
    private boolean hasCircularReference(Table table, Set<Table> visitedTables) {
        Objects.requireNonNull(visitedTables, "table param is null.");
        Objects.requireNonNull(visitedTables, "visitedTables param is null.");

        if (visitedTables.contains(table))
            return true;
        visitedTables.add(table);

        Long idParent = table.getIdParent();
        if (idParent == null)
            return false;
        Table parentTable = this.getTable(idParent);
        return this.hasCircularReference(parentTable, visitedTables);
    }

    @Override
    public void saveTable(Table table) {
        Long id = table.getId();
        String name = table.getName();

        if (id.equals(TableID.ROOT) || id.equals(TableID.SCINDES) || id.equals(TableID.VIDE))
            throw new IllegalStateException("Cannot update this table.");

        this.checkNameExists(name);
        this.checkValidName(name);
        this.getTable(table.getIdParent());
        this.checkCircularReference(id);

        this.tableRepository.save(table);
    }

    @Override
    public void deleteTable(Long id) {
        if (id.equals(TableID.ROOT) || id.equals(TableID.SCINDES) || id.equals(TableID.VIDE))
            throw new IllegalStateException("Cannot delete this table.");
        if (this.hasChildren(id))
            throw new IllegalStateException("Cannot delete the table id(" + id + ") because it has children.");
        this.tableRepository.deleteById(id);
    }

    @Override
    public void deleteTableRecursively(Long id) {
        if (id.equals(TableID.ROOT) || id.equals(TableID.SCINDES) || id.equals(TableID.VIDE))
            throw new IllegalStateException("Cannot delete this table.");
        this.listAllChildrenFromTableId(id)
                .stream()
                .map(Table::getId)
                .forEach(this.tableRepository::deleteById);
    }
}
