package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodStatusResponse;

import java.util.List;


public interface FinancialPeriodService {

    FinancialPeriodStatusResponse getFinancialPeriodStatus(FinancialPeriodStatusRequest financialPeriodStatusRequest);

    List<FinancialPeriodResponse> getFinancialAccountByDateAndOrgan(FinancialPeriodRequest financialPeriodRequest, Long organizationId);
}
