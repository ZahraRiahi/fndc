package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialNumberingTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeOutputResponse;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

import java.util.List;

public interface FinancialNumberingTypeService {

    DataSourceResult getNumberingType(DataSourceRequest dataSourceRequest);

    List<FinancialNumberingTypeOutputResponse> getNumberingTypeLov(FinancialNumberingTypeRequest financialNumberingTypeRequest);
}
