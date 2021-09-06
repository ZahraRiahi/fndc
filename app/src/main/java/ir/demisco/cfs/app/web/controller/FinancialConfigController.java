package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.service.api.FinancialConfigService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialConfig")
public class FinancialConfigController {
    private final FinancialConfigService financialConfigService;

    public FinancialConfigController(FinancialConfigService financialConfigService) {
        this.financialConfigService = financialConfigService;
    }

    @PostMapping("/Get")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialConfigService.getFinancialConfigByOrganizationIdAndUserAndDepartment(dataSourceRequest));
    }


}
