package com.bastien.bankop.endpoints.tree;

import com.bastien.bankop.controllers.KeywordController;
import com.bastien.bankop.controllers.OperationController;
import com.bastien.bankop.controllers.TableController;
import com.bastien.bankop.dto.TreeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/tree")
public class TreeController {

    private final TreeMapper treeMapper;

    public TreeController(
            @Autowired TableController tableController,
            @Autowired KeywordController keywordController,
            @Autowired OperationController operationController) {
        this.treeMapper = new TreeMapper(tableController, keywordController, operationController);
    }

    @PostMapping
    public TreeDTO mapTree() {
        return this.treeMapper.buildTreeDTO();
    }
}
