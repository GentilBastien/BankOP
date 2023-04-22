package com.bastien.bankop.services;

import com.bastien.bankop.entities.Table;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public interface TableService {

    Table getTable(Long id);

    List<Table> listTables();

    List<Table> listChildrenFromTableId(Long id);

    List<Table> listAllChildrenFromTableId(Long id);

    List<Table> listDepthPath(LinkedList<Table> listParents);

    boolean hasChildren(Long id);

    void checkNameExists(String name);

    void checkValidName(String name);

    void checkCircularReference(Long id);

    void saveTable(Table table);

    void deleteTable(Long id);

    void deleteTableRecursively(Long id);
}
