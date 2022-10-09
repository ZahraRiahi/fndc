package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialLedgerCloseMonthInputRequest;
import ir.demisco.cfs.service.api.LedgerPeriodService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-ledgerPeriod")
public class LedgerPeriodController {
    private final LedgerPeriodService ledgerPeriodService;

    public LedgerPeriodController(LedgerPeriodService ledgerPeriodService) {
        this.ledgerPeriodService = ledgerPeriodService;
    }

    @PostMapping("/CloseMonth")
    public ResponseEntity<Boolean> closeMonth(@RequestBody FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerCloseMonthInputRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.closeMonth(financialLedgerCloseMonthInputRequest));
    }

    @PostMapping("/OpenMonth")
    public ResponseEntity<Boolean> OpenMonth(@RequestBody FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerCloseMonthInputRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.openMonth(financialLedgerCloseMonthInputRequest));
    }
}
