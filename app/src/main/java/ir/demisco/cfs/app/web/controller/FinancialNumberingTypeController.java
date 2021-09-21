package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.service.api.FinancialNumberingTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialNumberingType")
public class FinancialNumberingTypeController {

    private final FinancialNumberingTypeService  financialNumberingTypeService;

    public FinancialNumberingTypeController(FinancialNumberingTypeService financialNumberingTypeService) {
        this.financialNumberingTypeService = financialNumberingTypeService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialNumberingTypeService.getNumberingType(dataSourceRequest));

    }
}
