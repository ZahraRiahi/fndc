package ir.demisco.cfs.app.web.controller;


import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialDocument")
public class FinancialDocumentController {

    private final FinancialDocumentService financialDocumentService;

    public FinancialDocumentController(FinancialDocumentService financialDocumentService) {
        this.financialDocumentService = financialDocumentService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> financialDocumentList(@RequestBody DataSourceRequest dataSourceRequest){

        return ResponseEntity.ok(financialDocumentService.getFinancialDocumentList(dataSourceRequest));
    }
}
