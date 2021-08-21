package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.*;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;

public interface FinancialDocumentService {


    DataSourceResult getFinancialDocumentList(DataSourceRequest dataSourceRequest);

    FinancialDocumentDto changeStatus(ResponseFinancialDocumentStatusDto responseFinancialDocumentStatusDto);

    String creatDocumentNumber(FinancialDocumentNumberDto financialDocumentNumberDto);

    String changeDescription(FinancialDocumentChengDescriptionDto financialDocumentDto);

    boolean deleteFinancialDocumentById(Long financialDocumentId);

    FinancialDocumentAccountMessageDto changeAccountDocument(FinancialDocumentAccountDto financialDocumentAccountDto);
}
