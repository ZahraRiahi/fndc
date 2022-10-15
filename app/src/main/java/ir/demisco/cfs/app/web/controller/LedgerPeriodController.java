package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialLedgerCloseMonthInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerClosingTempInputRequest;
import ir.demisco.cfs.model.dto.request.InsertLedgerPeriodInputRequest;
import ir.demisco.cfs.service.api.LedgerPeriodService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
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
    public ResponseEntity<Boolean> openMonth(@RequestBody FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerCloseMonthInputRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.openMonth(financialLedgerCloseMonthInputRequest));
    }
    @PostMapping("/ClosingTemp")
    public ResponseEntity<Boolean> closingTemp(@RequestBody FinancialLedgerClosingTempInputRequest financialLedgerClosingTempInputRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerClosingTempInputRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.closingTemp(financialLedgerClosingTempInputRequest));
    }

    @PostMapping("/MonthList")
    public ResponseEntity<DataSourceResult> ledgerPeriodMonthList(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(ledgerPeriodService.getLedgerPeriodMonthList(dataSourceRequest));
    }

    @PostMapping("/InsertLedgerPeriod")
    public ResponseEntity<Boolean> insertLedgerPeriod(@RequestBody InsertLedgerPeriodInputRequest insertLedgerPeriodInputRequest) {
        return ResponseEntity.ok(ledgerPeriodService.insertLedgerPeriod(insertLedgerPeriodInputRequest));
    }
}
