package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialDocumentSummarizeRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentSummarizeResponse;
import ir.demisco.cfs.service.api.FinancialDocumentSummarizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialDocumentSummarize")
public class FinancialDocumentSummarizeController {
    private final FinancialDocumentSummarizeService financialDocumentSummarizeService;

    public FinancialDocumentSummarizeController(FinancialDocumentSummarizeService financialDocumentSummarizeService) {
        this.financialDocumentSummarizeService = financialDocumentSummarizeService;
    }

    @PostMapping("/Get")
    public ResponseEntity<FinancialDocumentSummarizeResponse> updateCentricAccount(@RequestBody FinancialDocumentSummarizeRequest financialDocumentSummarizeRequest) {
        return ResponseEntity.ok(financialDocumentSummarizeService.getFinancialDocumentByFinancialDocumentId(financialDocumentSummarizeRequest));
    }
}
