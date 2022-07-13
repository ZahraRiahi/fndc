package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialLedgerPeriodRequest;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialLedgerPeriodController")
public class FinancialLedgerPeriodController {
    private final FinancialLedgerPeriodService financialLedgerPeriodService;

    public FinancialLedgerPeriodController(FinancialLedgerPeriodService financialLedgerPeriodService) {
        this.financialLedgerPeriodService = financialLedgerPeriodService;
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody FinancialLedgerPeriodRequest financialLedgerPeriodRequest) {
        boolean result;
        result = financialLedgerPeriodService.saveFinancialLedgerPeriod(financialLedgerPeriodRequest);
        return ResponseEntity.ok(result);
    }
}
