package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialDocumentTransferRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentTransferOutputResponse;

public interface TransferFinancialDocumentService {

    FinancialDocumentTransferOutputResponse transferDocument(FinancialDocumentTransferRequest financialDocumentTransferRequest);

}
