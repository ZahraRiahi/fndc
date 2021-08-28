package ir.demisco.cfs.app.web.controller;


import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberSaveDto;
import ir.demisco.cfs.service.api.FinancialDocumentNumberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialDocumentNumber")
public class FinancialDocumentNumberController {

    private final FinancialDocumentNumberService financialDocumentNumberService;

    public FinancialDocumentNumberController(FinancialDocumentNumberService financialDocumentNumberService) {
        this.financialDocumentNumberService = financialDocumentNumberService;
    }


    @PostMapping("/save")
    public ResponseEntity<FinancialDocumentNumberSaveDto> saveDocumentNumber(@RequestBody FinancialDocumentNumberSaveDto financialDocumentNumberSaveDto){
        return ResponseEntity.ok(financialDocumentNumberService.documentNumberSave(financialDocumentNumberSaveDto));
    }

}
