package ir.demisco.cfs.service.api;


import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeGetDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentTypeDto;

import java.util.List;

public interface FinancialDocumentTypeService {
    List<FinancialDocumentTypeGetDto> getNumberingFormatByOrganizationId(Long organizationId, ResponseFinancialDocumentTypeDto responseFinancialDocumentTypeDto);
}
