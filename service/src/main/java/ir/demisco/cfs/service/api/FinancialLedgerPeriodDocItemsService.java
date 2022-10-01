package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.GetDocumentItemsForLedgerInputRequest;
import ir.demisco.cfs.model.dto.response.FinancialLedgerClosingTempOutputResponse;

import java.util.List;

public interface FinancialLedgerPeriodDocItemsService {
    List<FinancialLedgerClosingTempOutputResponse> getFinancialLedgerPeriodDocItems(GetDocumentItemsForLedgerInputRequest getDocumentItemsForLedgerInputRequest);

}
