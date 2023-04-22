package com.bastien.bankop.controllers;

import com.bastien.bankop.entities.Table;
import com.bastien.bankop.exceptions.MalFormedRequestException;
import com.bastien.bankop.exceptions.NoTableFoundException;
import com.bastien.bankop.exceptions.TableNameException;
import com.bastien.bankop.requests.TableRequest;
import com.bastien.bankop.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
     * @param request The body of the request payload. Name and parent id must be present (non-null).
     * @throws NullPointerException      if the request is null.
     * @throws MalFormedRequestException if the request does not contain a name or a parent id.
     * @throws TableNameException        if the table name is not valid or already exists.
     * @throws NoTableFoundException     if the parent id does not exist.
     */
    @PostMapping(path = "/post")
    public void registerTable(@RequestBody TableRequest request) {
        Objects.requireNonNull(request, "The request is null.");
        String name = request.name().orElseThrow(() -> new MalFormedRequestException("name."));
        Long idParent = request.idParent().orElseThrow(() -> new MalFormedRequestException("parent id."));

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
     * @param request The body of the request payload. All elements are optional except the id, but at least one must be
     *                present (non-null).
     * @throws NullPointerException      if the request is null.
     * @throws MalFormedRequestException if the request does not contain an ID, or a name and a parent id.
     * @throws TableNameException        if the table name is not valid or already exists.
     * @throws NoTableFoundException     if the id does not refer to an existing table.
     * @throws IllegalStateException     if the id table to update is one of the immutable tables or if the new id
     *                                   parent makes a circular reference.
     */
    @PutMapping(path = "/put")
    public void updateTable(@RequestBody TableRequest request) {
        Objects.requireNonNull(request, "The request is null.");
        Long id = request.id().orElseThrow(() -> new MalFormedRequestException("id."));
        Long idParent = request.idParent().orElse(null);
        String name = request.name().orElse(null);
        if (idParent == null && name == null)
            throw new MalFormedRequestException("At least name or idParent must be present in the request body.");

        Table t = this.getTable(id);
        if (idParent != null && !idParent.equals(t.getIdParent()))
            t.setIdParent(idParent);
        if (name != null)
            t.setName(name);

        tableService.saveTable(t);
    }
}
