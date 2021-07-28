package ir.demisco.cfs.service.api;


import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeGetDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentTypeDto;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

import java.util.List;

public interface FinancialDocumentTypeService {
    List<FinancialDocumentTypeGetDto> getNumberingFormatByOrganizationId(Long organizationId, ResponseFinancialDocumentTypeDto responseFinancialDocumentTypeDto);

    Boolean deleteFinancialDocumentTypeById(Long financialDocumentTypeId);

    DataSourceResult getFinancialDocumentTypeOrganizationIdAndFinancialSystemId(DataSourceRequest dataSourceRequest);

}
