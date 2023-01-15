package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialDocumentReportDriverRequest;
import ir.demisco.cfs.service.api.FinancialAccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-reportTurnOver")
public class ReportTurnOverController {
    private final FinancialAccountService financialAccountService;

    public ReportTurnOverController(FinancialAccountService financialAccountService) {
        this.financialAccountService = financialAccountService;
    }

    @PostMapping("/report")
    public ResponseEntity<byte[]> responseEntity(@RequestBody FinancialDocumentReportDriverRequest financialDocumentReportDriverRequest) {
        return ResponseEntity.ok(financialAccountService.report(financialDocumentReportDriverRequest));
    }
}
