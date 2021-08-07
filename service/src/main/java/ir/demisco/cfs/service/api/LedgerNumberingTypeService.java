package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeResponse;

import java.util.List;

public interface LedgerNumberingTypeService {
    /**
     * واکشی انواع شماره گذاری دفاتر مالی
     *
     * @param ledgerNumberingTypeId
     * @return
     */
    List<FinancialNumberingTypeResponse> getLedgerNumberingType(Long ledgerNumberingTypeId);

}
