package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.GetDocumentItemsForLedgerInputRequest;
import ir.demisco.cfs.model.dto.response.FinancialLedgerClosingTempOutputResponse;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodDocItemsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-financialLedgerPeriodDocItems")
public class FinancialLedgerPeriodDocItemsController {
    private final FinancialLedgerPeriodDocItemsService financialLedgerPeriodDocItemsService;

    public FinancialLedgerPeriodDocItemsController(FinancialLedgerPeriodDocItemsService financialLedgerPeriodDocItemsService) {
        this.financialLedgerPeriodDocItemsService = financialLedgerPeriodDocItemsService;
    }

    @PostMapping("/Get")
    public ResponseEntity<List<FinancialLedgerClosingTempOutputResponse>> responseEntity(@RequestBody GetDocumentItemsForLedgerInputRequest getDocumentItemsForLedgerInputRequest) {
        return ResponseEntity.ok(financialLedgerPeriodDocItemsService.getFinancialLedgerPeriodDocItems(getDocumentItemsForLedgerInputRequest));
    }
}
