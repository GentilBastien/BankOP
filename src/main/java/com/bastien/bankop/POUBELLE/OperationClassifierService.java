package com.bastien.bankop.POUBELLE;

import com.bastien.bankop.dto.base.OperationDTO;
import com.bastien.bankop.entities.base.Keyword;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OperationClassifierService {

    Long findIdParentByKeywords(String label, List<Keyword> keywords);

    Long findIdParentByScalar(OperationDTO opDTO, List<OperationDTO> operations);
}
