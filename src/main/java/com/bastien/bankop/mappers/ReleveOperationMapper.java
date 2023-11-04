package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.ReleveOperationDTO;
import com.bastien.bankop.entities.base.Operation;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.services.base.OperationService;
import com.bastien.bankop.utils.BankopUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReleveOperationMapper implements DTOBuilder<ReleveOperationDTO> {

    private final OperationService operationService;

    @Override
    public ReleveOperationDTO buildDTO() {
        final List<ReleveOperationDTO.ReleveRow> rows = this.operationService
                .getEntities()
                .stream()
                .map(this::buildRow)
                .toList();
        final Operation[] minMaxDate = sortAndGetMinMax(Comparator.comparing(Operation::getDate));
        final Operation[] minMaxPrice = sortAndGetMinMax(Comparator.comparing(Operation::getPrice));
        final LocalDate minDate = minMaxDate.length > 0 ? minMaxDate[0].getDate() : null;
        final LocalDate maxDate = minMaxDate.length > 0 ? minMaxDate[1].getDate() : null;
        final Double minPrice = minMaxPrice.length > 0 ? minMaxPrice[0].getPrice() : null;
        final Double maxPrice = minMaxPrice.length > 0 ? minMaxPrice[1].getPrice() : null;
        final String[] categories = this.operationService.getEntities()
                .stream()
                .map(Operation::getCategory)
                .distinct() //distinct tables not distinct names
                .map(Table::getName)
                .toArray(String[]::new);
        return new ReleveOperationDTO(BankopUtils.emptyListToNull(rows), minDate, maxDate, minPrice, maxPrice, categories);
    }

    private Operation[] sortAndGetMinMax(Comparator<Operation> operationComparator) {
        final List<Operation> entities = List.copyOf(this.operationService.getEntities());
        if (entities.isEmpty()) {
            return new Operation[]{};
        } else {
            return new Operation[]{
                    Collections.min(entities, operationComparator),
                    Collections.max(entities, operationComparator)
            };
        }

    }

    private ReleveOperationDTO.ReleveRow buildRow(Operation operation) {
        return new ReleveOperationDTO.ReleveRow(operation.getDate(), operation.getName(), operation.getPrice(), operation.getCategory().getName());
    }
}
