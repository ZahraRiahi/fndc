package ir.demisco.cfs.app.web.controller;


import ir.demisco.cfs.service.api.FinancialNumberingFormatTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialNumberingFormatType")
public class FinancialNumberingFormatTypeController {

    private final  FinancialNumberingFormatTypeService financialNumberingFormatTypeService;

    public FinancialNumberingFormatTypeController(FinancialNumberingFormatTypeService financialNumberingFormatTypeService) {
        this.financialNumberingFormatTypeService = financialNumberingFormatTypeService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialNumberingFormatTypeService.getNumberingFormatByOrganizationId(dataSourceRequest));

    }
}
