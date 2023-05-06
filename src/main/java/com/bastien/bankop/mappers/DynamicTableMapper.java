package com.bastien.bankop.mappers;

import com.bastien.bankop.dto.DynamicTableDTO;
import com.bastien.bankop.entities.base.Operation;
import com.bastien.bankop.entities.base.Table;
import com.bastien.bankop.services.base.TableService;
import com.bastien.bankop.utils.BankopUtils;
import com.bastien.bankop.utils.TableID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DynamicTableMapper implements DTOVoidMapper<DynamicTableDTO> {

    private final TableService tableService;
    private final int MONTHS_IN_YEAR = Month.values().length;

    @Override
    public DynamicTableDTO buildDTO() {
        Table root = this.tableService.getEntityWithId(TableID.ROOT);
        int minYear = this.getRangeYear(root, true);
        int maxYear = this.getRangeYear(root, false);
        return new DynamicTableDTO(
                IntStream.range(minYear, maxYear)
                        .mapToObj(year -> new DynamicTableDTO.Year(year, this.buildYearTable(root, year)))
                        .toList()
        );
    }

    private DynamicTableDTO.YearTable buildYearTable(Table table, Integer year) {
        List<DynamicTableDTO.YearTable> childrenYearTable = table.getTables()
                .stream()
                .map(subtableId -> this.buildYearTable(subtableId, year))
                .toList();
        double[] sumMonthPricesFromChildren = new double[MONTHS_IN_YEAR];
        Arrays.setAll(sumMonthPricesFromChildren, i -> childrenYearTable
                .stream()
                .mapToDouble(yearTable -> yearTable.monthPrices()[i])
                .sum());
        double sumYearPricesFromChildren = Arrays.stream(sumMonthPricesFromChildren).sum();
        String tableName = table.getName();
        String tablePath = this.tableService.listDepthPath(table)
                .stream()
                .map(Table::getName)
                .collect(Collectors.joining(" > "));
        List<Operation> operationsYear = table.getOperations()
                .stream()
                .filter(op -> op.getDate().getYear() == year)
                .toList();
        double[] monthPrices = new double[MONTHS_IN_YEAR];
        Arrays.setAll(monthPrices, i -> operationsYear
                .stream()
                //Index 0 in array corresponds to January (1)
                .filter(op -> op.getDate().getMonthValue() == i + 1)
                .mapToDouble(Operation::getPrice)
                .sum()
        );

        double yearPrice = Arrays.stream(monthPrices).sum();
        double cumulatedYearPrice = yearPrice + sumYearPricesFromChildren;
        double[] cumulatedMonthPrices = IntStream.range(0, MONTHS_IN_YEAR)
                .mapToDouble(i -> monthPrices[i] + sumMonthPricesFromChildren[i])
                .toArray();
        return new DynamicTableDTO.YearTable(
                tableName,
                tablePath,
                yearPrice,
                monthPrices,
                cumulatedYearPrice,
                cumulatedMonthPrices,
                BankopUtils.emptyListToNull(childrenYearTable));
    }

    private int getRangeYear(Table root, boolean minimumRequired) {
        Stream<Operation> operationStream = this.tableService.listAllChildrenFrom(root)
                .stream()
                .map(Table::getOperations)
                .flatMap(Collection::stream);
        Comparator<Operation> operationDateComparator = Comparator.comparing(Operation::getDate);
        Operation operationDate = minimumRequired ?
                operationStream.min(operationDateComparator).orElseThrow() :
                operationStream.max(operationDateComparator).orElseThrow();
        return operationDate.getDate().getYear();
    }
}
