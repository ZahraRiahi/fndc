package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.service.api.FinancialDepartmentService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/api-financial_Department")
public class FinancialDepartmentController {

    private final FinancialDepartmentService financialDepartmentService;

    public FinancialDepartmentController(FinancialDepartmentService financialDepartmentService) {
        this.financialDepartmentService = financialDepartmentService;
    }

    @PostMapping("/List")
    public ResponseEntity<DataSourceResult> financialLedgerTypeList(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialDepartmentService.financialDepartmentList(dataSourceRequest));

    }
}
