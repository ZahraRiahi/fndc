package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialNumberingTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeOutputResponse;
import ir.demisco.cfs.service.api.FinancialNumberingTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-financialNumberingType")
public class FinancialNumberingTypeController {

    private final FinancialNumberingTypeService financialNumberingTypeService;

    public FinancialNumberingTypeController(FinancialNumberingTypeService financialNumberingTypeService) {
        this.financialNumberingTypeService = financialNumberingTypeService;
    }

    @PostMapping("/List")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialNumberingTypeService.getNumberingType(dataSourceRequest));
    }

    @PostMapping("/list")
    public ResponseEntity<List<FinancialNumberingTypeOutputResponse>> responseEntity(@RequestBody FinancialNumberingTypeRequest financialNumberingTypeRequest) {
        return ResponseEntity.ok(financialNumberingTypeService.getNumberingTypeLov(financialNumberingTypeRequest));
    }
}
