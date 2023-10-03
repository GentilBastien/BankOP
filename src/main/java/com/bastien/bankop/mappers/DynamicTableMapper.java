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
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DynamicTableMapper implements DTOMapper<DynamicTableDTO> {

    private final TableService tableService;
    private final int MONTHS_IN_YEAR = Month.values().length;

    @Override
    public DynamicTableDTO buildDTO() {
        Table root = this.tableService.getEntityWithId(TableID.ROOT);
        int[] rangeYear = this.getRangeYear(root);
        int[] years = new int[rangeYear[1] - rangeYear[0] + 1];
        for (int i = rangeYear[1]; i >= rangeYear[0]; i--) {
            years[rangeYear[1] - i] = i;
        }
        return new DynamicTableDTO(
                new DynamicTableDTO.TreePriceTable(buildPriceTable(root, years), years)
        );
    }

    private DynamicTableDTO.PriceTable buildPriceTable(Table table, int[] years) {
        List<DynamicTableDTO.PriceTable> childrenPriceTable = table.getTables()
                .stream()
                .map(t -> buildPriceTable(t, years))
                .toList();
        String tableName = table.getName();
        int depth = tableService.listDepthPath(table).size();

        double[][] monthYearPrices = new double[years.length][MONTHS_IN_YEAR];
        double[] yearPrices = new double[years.length];

        for (int yearIndex = 0; yearIndex < years.length; yearIndex++) {
            yearPrices[yearIndex] = computesPrice(table, years[yearIndex], -1);
            for (int monthIndex = 0; monthIndex < MONTHS_IN_YEAR; monthIndex++) {
                monthYearPrices[yearIndex][monthIndex] = computesPrice(table, years[yearIndex], 12 - monthIndex);
            }
        }

        double[][] childrenMonthYearPrices = childrenPriceTable
                .stream()
                .map(DynamicTableDTO.PriceTable::cumulatedMonthYearPrices)
                .reduce(this::sumMatrix)
                .orElseGet(() -> new double[years.length][MONTHS_IN_YEAR]);

        double[] childrenYearPrices = childrenPriceTable
                .stream()
                .map(DynamicTableDTO.PriceTable::cumulatedYearPrices)
                .reduce(this::sumTable)
                .orElseGet(() -> new double[years.length]);

        double[][] cumulatedMonthYearPrices = sumMatrix(monthYearPrices, childrenMonthYearPrices);
        double[] cumulatedYearPrices = sumTable(yearPrices, childrenYearPrices);
        return new DynamicTableDTO.PriceTable(tableName, depth, yearPrices, monthYearPrices, cumulatedYearPrices, cumulatedMonthYearPrices, BankopUtils.emptyListToNull(childrenPriceTable));
    }


    private double computesPrice(Table table, int year, int month) {
        return table.getOperations()
                .stream()
                .filter(op -> op.getDate().getYear() == year)
                .filter(op -> month == -1 || op.getDate().getMonthValue() == month)
                .mapToDouble(Operation::getPrice)
                .sum();
    }

    private double[][] sumMatrix(double[][] m1, double[][] m2) {
        int rows = m1.length;
        int cols = m1[0].length;
        double[][] result = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = m1[i][j] + m2[i][j];
            }
        }
        return result;
    }

    private double[] sumTable(double[] t1, double[] t2) {
        double[] result = new double[t1.length];
        Arrays.setAll(result, (index) -> t1[index] + t2[index]);
        return result;
    }

    private int[] getRangeYear(Table root) {
        Comparator<Operation> operationDateComparator = Comparator.comparing(Operation::getDate);
        LinkedList<Operation> operationStream = this.tableService.listAllChildrenFrom(root)
                .stream()
                .flatMap(table -> table.getOperations().stream())
                .sorted(operationDateComparator)
                .collect(Collectors.toCollection(LinkedList::new));
        return new int[]{
                operationStream.getFirst().getDate().getYear(),
                operationStream.getLast().getDate().getYear()
        };
    }
}
