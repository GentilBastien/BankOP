package com.bastien.bankop.services.impl;

import com.bastien.bankop.dto.OperationDTO;
import com.bastien.bankop.entities.Keyword;
import com.bastien.bankop.services.OperationClassifierService;
import com.bastien.bankop.utils.TableID;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service("operationClassifier")
public class OperationClassifierImpl implements OperationClassifierService {

    @Override
    public Long findIdParentByKeywords(String label, List<Keyword> keywords) {
        return keywords.stream()
                .filter(keyword -> label.contains(keyword.getKeyword()))
                .map(Keyword::getIdParent)
                .findFirst()
                .orElse(TableID.VIDE);
    }

    @Override
    public Long findIdParentByScalar(OperationDTO opDTO, List<OperationDTO> operations) {
        Comparator<OperationDTO> myComp = new OperationComparator();
        return operations.stream()
                .min(myComp)
                .map(OperationDTO::getIdParent)
                .flatMap(i -> i)
                .orElse(TableID.VIDE);
    }

    private static class OperationComparator implements Comparator<OperationDTO> {

        @Override
        public int compare(OperationDTO o1, OperationDTO o2) {
            return 0;
        }
    }
}
