package ir.demisco.cfs.app.web.controller;


import ir.demisco.cfs.service.api.FinancialDocumentItemService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialDocumentItem")
public class FinancialDocumentItemController {

    private final FinancialDocumentItemService financialDocumentItemService;

    public FinancialDocumentItemController(FinancialDocumentItemService financialDocumentItemService) {
        this.financialDocumentItemService = financialDocumentItemService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> financialDocumentItemList(@RequestBody DataSourceRequest dataSourceRequest){

        return ResponseEntity.ok(financialDocumentItemService.getFinancialDocumentItemList(dataSourceRequest));
    }
}
