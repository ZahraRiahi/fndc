package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.LedgerNumberingTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeResponse;
import ir.demisco.cfs.model.dto.response.LedgerNumberingTypeDto;

import java.util.List;

public interface LedgerNumberingTypeService {
    /**
     * واکشی انواع شماره گذاری دفاتر مالی
     */
    List<FinancialNumberingTypeResponse> getLedgerNumberingType(LedgerNumberingTypeRequest ledgerNumberingTypeRequest);

    /**
     * ذخیره انواع شماره گذاری دفاتر مالی
     */
    Boolean saveLedgerNumberingType(LedgerNumberingTypeDto ledgerNumberingTypeDto);



}
