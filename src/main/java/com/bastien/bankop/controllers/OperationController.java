package com.bastien.bankop.controllers;

import com.bastien.bankop.dto.OperationDTO;
import com.bastien.bankop.entities.Operation;
import com.bastien.bankop.exceptions.MalFormedDTOException;
import com.bastien.bankop.exceptions.NoOperationFoundException;
import com.bastien.bankop.services.OperationService;
import com.bastien.bankop.utils.TableID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(path = "/api/v1/operations")
public class OperationController {

    private final OperationService operationService;

    @Autowired
    public OperationController(@Qualifier("operationServiceDB") OperationService operationService) {
        this.operationService = operationService;
    }

    //////////////////////////////////////
    //          Operation GET           //
    //////////////////////////////////////

    @GetMapping(path = "/get/{id}")
    public Operation getOperation(@PathVariable Long id) {
        return this.operationService.getOperation(id);
    }

    @GetMapping(path = "/get")
    public List<Operation> listOperations() {
        return this.operationService.listOperations();
    }

    @GetMapping(path = "/get/parent/{idParent}")
    public List<Operation> listOperationsFromTableId(@PathVariable Long idParent) {
        return this.operationService.listOperationsFromTableId(idParent);
    }

    @GetMapping(path = "/get/sub-op/{idMother}")
    public List<Operation> listSubOperationsFromMotherId(@PathVariable Long idMother) {
        return this.operationService.listSubOperationsFromMotherId(idMother);
    }

    /////////////////////////////////////////
    //          Operation CREATE           //
    /////////////////////////////////////////

    /**
     * Registers a new <code>Operation</code> using the OperationRequest with all required fields as non-optional,
     * except the id that is auto-generated. Important notes :
     * <ul>
     *     <li>The <code>Operation</code> may not have a mother.</li>
     *     <li>If it has a mother, then this mother can't be a sub-operation as well.</li>
     *     <li>The <code>Operation</code> may not have a parent. If it is the case, the default parent is assigned.</li>
     *     <li>The <code>Operation</code> must have a date, a label, and a price.</li>
     *     <li>The manuallyCategorized property is by default set to false.</li>
     * </ul>
     *
     * @param dto The body of request payload.
     * @throws NullPointerException      if the request is null.
     * @throws MalFormedDTOException if the request does not contain a date, a label, or a price.
     * @throws IllegalStateException     if <code>Operation</code> refers to an operation that has sub-operations
     *                                   already.
     * @throws NoOperationFoundException if the <code>Operation</code> to refer to (as a mother operation) is not
     *                                   found.
     */
    @PostMapping(path = "/post")
    public void registerOperation(@RequestBody OperationDTO dto) {
        Objects.requireNonNull(dto, "The dto is null.");
        Long idParent = dto.getIdParent().orElse(TableID.VIDE);
        LocalDate date = dto.getDate().orElseThrow(() -> new MalFormedDTOException("date."));
        String label = dto.getLabel().orElseThrow(() -> new MalFormedDTOException("label."));
        Double price = dto.getPrice().orElseThrow(() -> new MalFormedDTOException("price."));
        Boolean manuallyCategorized = dto.getManuallyCategorized().orElse(false);

        Operation operation = new Operation();
        operation.setIdMother(null);
        operation.setIdParent(idParent);
        operation.setDate(date);
        operation.setLabel(label);
        operation.setPrice(price);
        operation.setManuallyCategorized(manuallyCategorized);

        this.operationService.saveOperation(operation);
    }

    /////////////////////////////////////////
    //          Operation DELETE           //
    /////////////////////////////////////////

    /**
     * Deletes an <code>Operation</code> using the <code>OperationService</code>.
     *
     * @param id The id of the <code>Operation</code> to delete.
     * @throws IllegalStateException if the <code>Operation</code> to delete has sub-operations.
     */
    @DeleteMapping(path = "/delete/{id}")
    public void deleteOperation(@PathVariable Long id) {
        this.operationService.deleteOperation(id);
    }

    /**
     * Deletes an <code>Operation</code> using the <code>OperationService</code> and all of its sub-operations.
     *
     * @param id The id of the <code>Operation</code> to delete, including its children.
     */
    @DeleteMapping(path = "/delete/mother/{id}")
    public void deleteOperationAndSubOperations(@PathVariable Long id) {
        this.operationService.deleteOperationAndSubOperations(id);
    }

    /////////////////////////////////////////
    //          Operation UPDATE           //
    /////////////////////////////////////////

    /**
     * Update one or more fields of an operation. Important note: null fields mean that no changes is intended. It does
     * not mean that the field new value will be null.
     *
     * @param dto The body of the operation request payload. All fields are optional except the id field.
     * @throws NullPointerException      if the request is null.
     * @throws MalFormedDTOException if the request does not contain an operation to update.
     * @throws NoOperationFoundException if the id does not refer to an existing operation.
     * @throws IllegalStateException     if the id table to update is one of the immutable tables or if the new id
     *                                   parent makes a circular reference.
     */
    @PutMapping(path = "/put")
    public void updateOperation(@RequestBody OperationDTO dto) {
        Objects.requireNonNull(dto, "The dto is null.");
        Long id = dto.getId().orElseThrow(() -> new MalFormedDTOException("id."));
        Operation operation = this.getOperation(id);

        Long idMother = dto.getIdMother().orElse(null);
        Long idParent = dto.getIdParent().orElse(null);
        LocalDate date = dto.getDate().orElse(null);
        String label = dto.getLabel().orElse(null);
        Double price = dto.getPrice().orElse(null);
        Boolean manuallyCategorized = dto.getManuallyCategorized().orElse(null);

        if (idMother != null)
            operation.setIdMother(idMother);
        if (idParent != null)
            operation.setIdParent(idParent);
        if (date != null)
            operation.setDate(date);
        if (label != null)
            operation.setLabel(label);
        if (price != null)
            operation.setPrice(price);
        if (manuallyCategorized != null)
            operation.setManuallyCategorized(manuallyCategorized);

        this.operationService.saveOperation(operation);
    }

    /////////////////////////////////////////
    //          Operation MAPPER           //
    /////////////////////////////////////////

    public OperationDTO mapToDTO(Long id) {
        Operation o = this.getOperation(id);
        return new OperationDTO(
                id,
                o.getIdParent(),
                o.getIdMother(),
                o.getDate(),
                o.getLabel(),
                o.getPrice(),
                o.getManuallyCategorized()
        );
    }
}
