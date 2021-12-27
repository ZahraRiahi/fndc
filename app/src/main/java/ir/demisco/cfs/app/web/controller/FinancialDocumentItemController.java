package ir.demisco.cfs.app.web.controller;


import ir.demisco.cfs.model.dto.request.FinancialDocumentItemRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentItemResponse;
import ir.demisco.cfs.model.dto.response.FinancialDocumentSaveDto;
import ir.demisco.cfs.service.api.FinancialDocumentItemService;
import ir.demisco.cfs.service.api.SaveFinancialDocumentService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<FinancialDocumentItemResponse>> getFinancialDocumentInfo(@RequestBody FinancialDocumentItemRequest financialDocumentItemRequest) {
        return ResponseEntity.ok(saveFinancialDocumentService.getFinancialDocumentItem(financialDocumentItemRequest));
    }

}
