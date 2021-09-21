package ir.demisco.cfs.app.web.controller;


import ir.demisco.cfs.model.dto.response.FinancialDocumentStatusListDto;
import ir.demisco.cfs.service.api.FinancialDocumentStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-financialDocumentStatus")
public class FinancialDocumentStatusController {

    private final FinancialDocumentStatusService financialDocumentStatusService;

    public FinancialDocumentStatusController(FinancialDocumentStatusService financialDocumentStatusService) {
        this.financialDocumentStatusService = financialDocumentStatusService;
    }

    @GetMapping("/Get")
    public ResponseEntity<List<FinancialDocumentStatusListDto>> responseEntity(){
        return ResponseEntity.ok(financialDocumentStatusService.getStatusList());
    }
}
