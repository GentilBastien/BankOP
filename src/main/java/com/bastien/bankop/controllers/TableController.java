package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.TableDTO;
import com.bastien.bankop.entities.Table;
import com.bastien.bankop.exceptions.MalFormedDTOException;
import com.bastien.bankop.exceptions.NoTableFoundException;
import com.bastien.bankop.exceptions.TableNameException;
import com.bastien.bankop.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1/tables")
public class TableController {

    /**
     * The <code>TableService</code> instance used for this controller.
     */
    private final TableService tableService;

    @Autowired
    public TableController(@Qualifier("tableServiceDB") TableService tableService) {
        this.tableService = tableService;
    }

    //////////////////////////////////
    //          Table GET           //
    //////////////////////////////////

    /**
     * Get a <code>Table</code> from the <code>TableService</code>.
     *
     * @param id The id of the <code>Table</code>.
     * @return A <code>Table</code> with the given id.
     * @throws NoTableFoundException if the <code>Table</code> is not found.
     */
    @GetMapping(path = "/get/{id}")
    public Table getTable(@PathVariable Long id) {
        return this.tableService.getTable(id);
    }

    /**
     * Get all the <code>Table</code> from the <code>TableService</code>.
     *
     * @return A <code>List</code> of all the <code>Table</code>.
     */
    @GetMapping(path = "/get")
    public List<Table> listTables() {
        return this.tableService.listTables();
    }

    /**
     * Get all the parents of a <code>Table</code> id in a list.
     *
     * @param id The id of the <code>Table</code>.
     * @return A <code>List</code> of all the parent <code>Table</code> with the given id.
     * @throws NoTableFoundException if the <code>Table</code> is not found.
     */
    @GetMapping(path = "/get/path/{id}")
    public List<Table> listDepthPath(@PathVariable Long id) {
        Table table = this.getTable(id);
        LinkedList<Table> listParents = new LinkedList<>(List.of(table));
        return this.tableService.listDepthPath(listParents);
    }

    /**
     * Get every direct child of a <code>Table</code> in a list.
     *
     * @param id The id of the <code>Table</code>.
     * @return A <code>List</code> of all the direct child of the <code>Table</code> with the given id or the empty list
     * if it has no direct children.
     */
    @GetMapping(path = "/get/children/{id}")
    public List<Table> listChildrenFromTableId(@PathVariable Long id) {
        return this.tableService.listChildrenFromTableId(id);
    }

    /**
     * Get every child of a <code>Table</code> in a list including the <code>Table</code> itself.
     *
     * @param id The id of the <code>Table</code>.
     * @return A <code>List</code> of all the child of the <code>Table</code> with the given id and itself.
     * @throws NoTableFoundException if the <code>Table</code> is not found.
     */
    @GetMapping(path = "/get/all-children/{id}")
    public List<Table> listAllChildrenFromTableId(@PathVariable Long id) {
        return this.tableService.listAllChildrenFromTableId(id);
    }

    /////////////////////////////////////
    //          Table CREATE           //
    /////////////////////////////////////

    /**
     * Inserts a new <code>Table</code> using the TableRequest with all elements as non-optional except the id. Verify
     * that the name is valid and that the parent id exists.
     *
     * @param tableDTO The body of the request payload. Name and parent id must be present (non-null).
     * @throws NullPointerException      if the request is null.
     * @throws MalFormedDTOException if the request does not contain a name or a parent id.
     * @throws TableNameException        if the table name is not valid or already exists.
     * @throws NoTableFoundException     if the parent id does not exist.
     */
    @PostMapping(path = "/post")
    public void registerTable(@RequestBody TableDTO tableDTO) {
        Objects.requireNonNull(tableDTO, "The dto is null.");
        String name = tableDTO.getName().orElseThrow(() -> new MalFormedDTOException("name."));
        Long idParent = tableDTO.getIdParent().orElseThrow(() -> new MalFormedDTOException("parent id."));

        Table table = new Table();
        table.setIdParent(idParent);
        table.setName(name);
        this.tableService.saveTable(table);
    }

    /////////////////////////////////////
    //          Table DELETE           //
    /////////////////////////////////////

    /**
     * Deletes a <code>Table</code> using the <code>TableService</code>.
     *
     * @param id The id of the <code>Table</code> to delete.
     * @throws IllegalStateException If the table has children or if it is an immutable table.
     */
    @DeleteMapping(path = "/delete/{id}")
    public void deleteTable(@PathVariable Long id) {
        this.tableService.deleteTable(id);
    }

    /**
     * Deletes a <code>Table</code> using the <code>TableService</code> and all of its children.
     *
     * @param id The id of the <code>Table</code> to delete, including all its children.
     */
    @DeleteMapping(path = "/delete/recursive/{id}")
    public void deleteTableRecursively(@PathVariable Long id) {
        this.tableService.deleteTableRecursively(id);
    }

    /////////////////////////////////////
    //          Table UPDATE           //
    /////////////////////////////////////

    /**
     * Updates a <code>Table</code> using the <code>TableRequest</code> with all elements as optional but at least one
     * must be present (non-null).
     *
     * @param tableDTO The body of the request payload. All elements are optional except the id, but at least one must be
     *                 present (non-null).
     * @throws NullPointerException      if the request is null.
     * @throws MalFormedDTOException if the request does not contain an ID, or a name and a parent id.
     * @throws TableNameException        if the table name is not valid or already exists.
     * @throws NoTableFoundException     if the id does not refer to an existing table.
     * @throws IllegalStateException     if the id table to update is one of the immutable tables or if the new id
     *                                   parent makes a circular reference.
     */
    @PutMapping(path = "/put")
    public void updateTable(@RequestBody TableDTO tableDTO) {
        Objects.requireNonNull(tableDTO, "The dto is null.");
        Long id = tableDTO.getId().orElseThrow(() -> new MalFormedDTOException("id."));
        Long idParent = tableDTO.getIdParent().orElse(null);
        String name = tableDTO.getName().orElse(null);
        if (idParent == null && name == null)
            throw new MalFormedDTOException("At least name or idParent must be present in the request body.");

        Table t = this.getTable(id);
        if (idParent != null && !idParent.equals(t.getIdParent()))
            t.setIdParent(idParent);
        if (name != null)
            t.setName(name);

        tableService.saveTable(t);
    }

    /////////////////////////////////////
    //          Table MAPPER           //
    /////////////////////////////////////

    public TableDTO mapToDTO(Long id) {
        Table table = this.getTable(id);
        return new TableDTO(id, table.getIdParent(), table.getName());
    }
}
