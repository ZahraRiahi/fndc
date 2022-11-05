package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialLedgerPeriodFilterModelRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerPeriodModelRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerPeriodRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodLedgerGetResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodOutResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodOutputResponse;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

import java.util.List;

public interface FinancialLedgerPeriodService {
    Boolean saveFinancialLedgerPeriod(FinancialLedgerPeriodRequest financialLedgerPeriodRequest);

    DataSourceResult getFinancialLedgerPeriodList(DataSourceRequest dataSourceRequest);

    List<FinancialPeriodLedgerGetResponse> getFinancialGetByPeriod(Long financialPeriodId);

    List<FinancialPeriodOutputResponse> getFinancialGetByLedgerType(Long financialLedgerTypeId);

    Boolean deleteFinancialLedgerPeriod(FinancialLedgerPeriodFilterModelRequest financialLedgerPeriodFilterModelRequest);

    List<FinancialPeriodOutResponse> getNotAssignLedger(FinancialLedgerPeriodModelRequest financialLedgerPeriodModelRequest);
}
