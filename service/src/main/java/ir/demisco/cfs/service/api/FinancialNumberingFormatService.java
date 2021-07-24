package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialNumberingFormatDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialNumberingFormatDto;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface FinancialNumberingFormatService {

    DataSourceResult getNumberingFormatByOrganizationId(Long organizationId, DataSourceRequest dataSourceRequest);

    ResponseFinancialNumberingFormatDto save(FinancialNumberingFormatDto financialNumberingFormatDto);

    ResponseFinancialNumberingFormatDto upDate(FinancialNumberingFormatDto financialNumberingFormatDto);

    boolean deleteNumberingFormatById(Long numberingFormatId);
}
