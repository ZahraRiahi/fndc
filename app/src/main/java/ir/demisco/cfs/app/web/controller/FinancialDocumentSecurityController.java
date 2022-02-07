package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialDocumentSecurityInputRequest;
import ir.demisco.cfs.service.api.FinancialDocumentSecurityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialDocumentSecurity")
public class FinancialDocumentSecurityController {
    private final FinancialDocumentSecurityService financialDocumentSecurityService;

    public FinancialDocumentSecurityController(FinancialDocumentSecurityService financialDocumentSecurityService) {
        this.financialDocumentSecurityService = financialDocumentSecurityService;
    }

    @PostMapping("/Get")
    public ResponseEntity<Boolean> saveFinancialConfig(@RequestBody FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest) {
        return ResponseEntity.ok(financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest));
    }
}
