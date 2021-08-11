package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.service.api.FinancialDepartmentService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/api-financialDepartment")
public class FinancialDepartmentController {

    private final FinancialDepartmentService financialDepartmentService;

    public FinancialDepartmentController(FinancialDepartmentService financialDepartmentService) {
        this.financialDepartmentService = financialDepartmentService;
    }

    @GetMapping("/List")
    public ResponseEntity<DataSourceResult> financialLedgerTypeList() {
        return ResponseEntity.ok(financialDepartmentService.financialDepartmentList());
    }
}
