package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialDocumentHeaderResponse;
import ir.demisco.cfs.service.api.FinancialDocumentHeaderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialDocumentHeader")
public class FinancialDocumentHeaderController {
private final FinancialDocumentHeaderService financialDocumentHeaderService;

    public FinancialDocumentHeaderController(FinancialDocumentHeaderService financialDocumentHeaderService) {
        this.financialDocumentHeaderService = financialDocumentHeaderService;
    }

    @GetMapping("/Get/{financialDocumentId}")
    public ResponseEntity<FinancialDocumentHeaderResponse> responseEntity(@PathVariable Long financialDocumentId) {
        return ResponseEntity.ok(financialDocumentHeaderService.getFinancialDocumentHeaderByDocumentId(financialDocumentId));

    }
}
