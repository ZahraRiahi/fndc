package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialDocumentItemOutPutResponse;
import ir.demisco.cfs.service.api.FinancialDocumentItemService;
import ir.demisco.cfs.service.api.SaveFinancialDocumentService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-financialDocumentItem")
public class FinancialDocumentItemController {

    private final FinancialDocumentItemService financialDocumentItemService;
    private final SaveFinancialDocumentService saveFinancialDocumentService;


    public FinancialDocumentItemController(FinancialDocumentItemService financialDocumentItemService, SaveFinancialDocumentService saveFinancialDocumentService) {
        this.financialDocumentItemService = financialDocumentItemService;
        this.saveFinancialDocumentService = saveFinancialDocumentService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> financialDocumentItemList(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialDocumentItemService.getFinancialDocumentItemList(dataSourceRequest));
    }

    @PostMapping("/Get")
    public ResponseEntity<DataSourceResult> getFinancialDocumentInfo(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(saveFinancialDocumentService.getFinancialDocumentItem(dataSourceRequest));
    }

    @GetMapping("/GetById/{financialDocumentItemId}")
    public ResponseEntity<FinancialDocumentItemOutPutResponse> responseEntityFinancialDocumentItem(@PathVariable Long financialDocumentItemId) {
        return ResponseEntity.ok(financialDocumentItemService.getFinancialDocumentItemById(financialDocumentItemId));

    }
}
