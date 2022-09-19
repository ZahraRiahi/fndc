package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.CheckLedgerPermissionInputRequest;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodSecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialLedgerPeriodSecurity")
public class FinancialLedgerPeriodSecurityController {
    private final FinancialLedgerPeriodSecurityService financialLedgerPeriodSecurityService;

    public FinancialLedgerPeriodSecurityController(FinancialLedgerPeriodSecurityService financialLedgerPeriodSecurityService) {
        this.financialLedgerPeriodSecurityService = financialLedgerPeriodSecurityService;
    }

    @PostMapping("/check")
    public ResponseEntity<Boolean> checkFinancialLedgerPeriodSecurity(@RequestBody CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest) {
        return ResponseEntity.ok(financialLedgerPeriodSecurityService.checkFinancialLedgerPeriodSecurity(checkLedgerPermissionInputRequest));
    }
}
