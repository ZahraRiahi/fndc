package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialLedgerCloseMonthInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerClosingTempInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerClosingTempRequest;
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
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        insertLedgerPeriodInputRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.insertLedgerPeriod(insertLedgerPeriodInputRequest));
    }

    @PostMapping("/DelClosingTemp")
    public ResponseEntity<Boolean> delClosingTemp(@RequestBody FinancialLedgerClosingTempRequest financialLedgerClosingTempRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerClosingTempRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.delClosingTemp(financialLedgerClosingTempRequest));
    }

    @PostMapping("/ClosingPermanent")
    public ResponseEntity<Boolean> closingPermanent(@RequestBody FinancialLedgerClosingTempInputRequest financialLedgerClosingTempInputRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerClosingTempInputRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.closingPermanent(financialLedgerClosingTempInputRequest));
    }

    @PostMapping("/DelClosingPermanent")
    public ResponseEntity<Boolean> delClosingPermanent(@RequestBody FinancialLedgerClosingTempRequest financialLedgerClosingTempRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerClosingTempRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.delClosingPermanent(financialLedgerClosingTempRequest));
    }

    @PostMapping("/OpeningDocument")
    public ResponseEntity<Boolean> openingDocument(@RequestBody FinancialLedgerClosingTempInputRequest financialLedgerClosingTempInputRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerClosingTempInputRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.openingDocument(financialLedgerClosingTempInputRequest));
    }

    @PostMapping("/DelOpeningDocument")
    public ResponseEntity<Boolean> delOpeningDocument(@RequestBody FinancialLedgerClosingTempRequest financialLedgerClosingTempRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerClosingTempRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.delOpeningDocument(financialLedgerClosingTempRequest));
    }

    @PostMapping("/Get")
    public ResponseEntity<DataSourceResult> ledgerPeriodList(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(ledgerPeriodService.getLedgerPeriodList(dataSourceRequest));
    }

    @PostMapping("/OpeningMonth")
    public ResponseEntity<Boolean> openingMonth(@RequestBody FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerCloseMonthInputRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.openingMonth(financialLedgerCloseMonthInputRequest));
    }

    @PostMapping("/permanentCheck")
    public ResponseEntity<Boolean> permanentCheck(@RequestBody FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerCloseMonthInputRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(ledgerPeriodService.permanentCheck(financialLedgerCloseMonthInputRequest));
    }
}
