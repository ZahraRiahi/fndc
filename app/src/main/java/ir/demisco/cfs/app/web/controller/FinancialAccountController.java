package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.service.api.FinancialAccountService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialAccount")
public class FinancialAccountController {
    private final FinancialAccountService financialAccountService;

    public FinancialAccountController(FinancialAccountService financialAccountService) {
        this.financialAccountService = financialAccountService;
    }

    @PostMapping("/turnOverReport")
    public ResponseEntity<DataSourceResult> financialDocumentResponseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialAccountService.getFinancialDocument(dataSourceRequest));
    }

    @PostMapping("/CentricTurnOverReport")
    public ResponseEntity<DataSourceResult> financialDocumentCentricTurnOverResponseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialAccountService.getFinancialDocumentCentricTurnOver(dataSourceRequest));
    }

    @PostMapping("/BalanceReport")
    public ResponseEntity<DataSourceResult> financialDocumentBalanceReportEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialAccountService.getFinancialDocumentBalanceReport(dataSourceRequest));
    }

    @PostMapping("/CentricBalanceReport")
    public ResponseEntity<DataSourceResult> financialDocumentCentricBalanceReportEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialAccountService.getFinancialDocumentCentricBalanceReport(dataSourceRequest));
    }
}
