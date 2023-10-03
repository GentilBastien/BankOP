package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.ReleveOperationDTO;
import com.bastien.bankop.entities.base.Operation;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.services.base.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReleveOperationMapper implements DTOMapper<ReleveOperationDTO> {

    private final OperationService operationService;

    @Override
    public ReleveOperationDTO buildDTO() {
        final ReleveOperationDTO.ReleveRow[] rows = this.operationService
                .getEntities()
                .stream()
                .map(this::buildRow)
                .toArray(ReleveOperationDTO.ReleveRow[]::new);
        final Operation[] minMaxDate = sortAndGetMinMax(Comparator.comparing(Operation::getDate));
        final Operation[] minMaxPrice = sortAndGetMinMax(Comparator.comparing(Operation::getPrice));
        final LocalDate minDate = minMaxDate[0].getDate();
        final LocalDate maxDate = minMaxDate[1].getDate();
        final Double minPrice = minMaxPrice[0].getPrice();
        final Double maxPrice = minMaxPrice[1].getPrice();
        final String[] categories = this.operationService.getEntities()
                .stream()
                .map(Operation::getCategory)
                .distinct() //distinct tables not distinct names
                .map(Table::getName)
                .toArray(String[]::new);
        return new ReleveOperationDTO(rows, minDate, maxDate, minPrice, maxPrice, categories);
    }

    private Operation[] sortAndGetMinMax(Comparator<Operation> operationComparator) {
        return new Operation[]{
                Collections.min(List.copyOf(this.operationService.getEntities()), operationComparator),
                Collections.max(List.copyOf(this.operationService.getEntities()), operationComparator)
        };
    }

    private ReleveOperationDTO.ReleveRow buildRow(Operation operation) {
        return new ReleveOperationDTO.ReleveRow(operation.getDate(), operation.getName(), operation.getPrice(), operation.getCategory().getName());
    }
}
