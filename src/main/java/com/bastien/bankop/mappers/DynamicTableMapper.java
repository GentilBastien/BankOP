package com.bastien.bankop.mappers;

import com.bastien.bankop.controllers.OperationController;
import com.bastien.bankop.controllers.TableController;
import com.bastien.bankop.dto.DynamicTableDTO;
import com.bastien.bankop.dto.TableNodeYearDetailDTO;
import com.bastien.bankop.dto.TableYearDetailDTO;
import com.bastien.bankop.entities.Operation;
import com.bastien.bankop.entities.Table;
import com.bastien.bankop.utils.BankopUtils;
import com.bastien.bankop.utils.TableID;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DynamicTableMapper {

    private final TableController tableController;

    private final OperationController operationController;

    public DynamicTableMapper(
            TableController tableController,
            OperationController operationController) {
        this.tableController = tableController;
        this.operationController = operationController;
    }

    public DynamicTableDTO buildDynamicTableDTO(List<Integer> years) {
        return new DynamicTableDTO(
                years.stream()
                        .map(year ->
                                new TableYearDetailDTO(year,
                                        this.buildTableNodeYearDetailDTO(TableID.ROOT, year)))
                        .toList()
        );
    }

    private TableNodeYearDetailDTO buildTableNodeYearDetailDTO(Long tableId, Integer year) {
        List<TableNodeYearDetailDTO> childrenTemporalTableYear =
                this.tableController.listChildrenFromTableId(tableId).stream()
                        .map(Table::getId)
                        .map(subtableId -> this.buildTableNodeYearDetailDTO(subtableId, year))
                        .toList();

        double[] sumMonthPricesFromChildren = new double[12];
        Arrays.setAll(sumMonthPricesFromChildren, i ->
                childrenTemporalTableYear.stream()
                        .mapToDouble(temporalTableYear -> temporalTableYear.monthPrices()[i])
                        .sum());
        double sumYearPricesFromChildren = Arrays.stream(sumMonthPricesFromChildren).sum();


        String tableName = this.tableController.getTable(tableId).getName();
        String tablePath = this.tableController.listDepthPath(tableId)
                .stream()
                .map(Table::getName)
                .collect(Collectors.joining(" > "));

        List<Operation> operationChildren = this.operationController.listOperationsFromTableId(tableId)
                .stream()
                .filter(op -> op.getDate().getYear() == year)
                .toList();


        double[] monthPrices = new double[12];
        Arrays.setAll(monthPrices, i ->
                operationChildren.stream()
                        .filter(op -> op.getDate().getMonthValue() == i + 1)
                        .mapToDouble(Operation::getPrice)
                        .sum()
        );
        double yearPrice = Arrays.stream(monthPrices).sum();


        double cumulatedYearPrice = yearPrice + sumYearPricesFromChildren;
        double[] cumulatedMonthPrices = IntStream.range(0, monthPrices.length)
                .mapToDouble(i -> monthPrices[i] + sumMonthPricesFromChildren[i])
                .toArray();


        return new TableNodeYearDetailDTO(
                tableName,
                tablePath,
                yearPrice,
                monthPrices,
                cumulatedYearPrice,
                cumulatedMonthPrices,
                BankopUtils.emptyListToNull(childrenTemporalTableYear));
    }
}
