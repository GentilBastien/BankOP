package com.bastien.bankop.services;

import com.bastien.bankop.dto.OperationDTO;
import com.bastien.bankop.entities.Keyword;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperationClassifierService {

    Long findIdParentByKeywords(String label, List<Keyword> keywords);

    Long findIdParentByScalar(OperationDTO opDTO, List<OperationDTO> operations);
}
