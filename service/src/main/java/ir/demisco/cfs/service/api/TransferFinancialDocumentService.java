package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.response.FinancialDocumentTransferDto;

public interface TransferFinancialDocumentService {

    boolean transferDocument(FinancialDocumentTransferDto financialDocumentTransferDto);
}
