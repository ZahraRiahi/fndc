package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.GetLedgerPeriodMonthStatusRequest;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodMonthStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialLedgerPeriodMonthStatus")
public class FinancialLedgerPeriodMonthStatusController {
    private final FinancialLedgerPeriodMonthStatusService financialLedgerPeriodMonthStatusService;

    public FinancialLedgerPeriodMonthStatusController(FinancialLedgerPeriodMonthStatusService financialLedgerPeriodMonthStatusService) {
        this.financialLedgerPeriodMonthStatusService = financialLedgerPeriodMonthStatusService;
    }

    @PostMapping("/Get")
    public ResponseEntity<Long> getLedgerPeriodMonthStatus(@RequestBody GetLedgerPeriodMonthStatusRequest getLedgerPeriodMonthStatusRequest) {
        Long result;
        result = financialLedgerPeriodMonthStatusService.getLedgerPeriodMonthStatus(getLedgerPeriodMonthStatusRequest);
        return ResponseEntity.ok(result);
    }
}
