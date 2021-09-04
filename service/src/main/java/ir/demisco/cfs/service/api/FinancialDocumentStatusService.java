package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialDocumentStatusListDto;

import java.util.List;

public interface FinancialDocumentStatusService {

    List<FinancialDocumentStatusListDto> getStatusList();
}
