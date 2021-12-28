package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialLedgerTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentLedgerDto;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentLedgerResponse;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;
import ir.demisco.cfs.service.api.FinancialLedgerTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api-financialLedgerType")
public class FinancialLedgerTypeController {

    private final FinancialLedgerTypeService financialLedgerTypeService;

    public FinancialLedgerTypeController(FinancialLedgerTypeService financialLedgerTypeService) {
        this.financialLedgerTypeService = financialLedgerTypeService;
    }

    @GetMapping("/Get")
    public ResponseEntity<List<FinancialLedgerTypeDto>> responseEntity() {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        return ResponseEntity.ok(financialLedgerTypeService.getFinancialLedgerType(organizationId));

    }

    @PostMapping("/List")
    public ResponseEntity<DataSourceResult> financialLedgerTypeList(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialLedgerTypeService.financialLedgerTypeList(dataSourceRequest));
    }

    @PostMapping("/Save")
    public ResponseEntity<Boolean> saveFinancialLedgerType(@RequestBody FinancialLedgerTypeRequest financialLedgerTypeRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerTypeRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(financialLedgerTypeService.saveFinancialLedgerType(financialLedgerTypeRequest));
    }

    @PostMapping("/GetCurrent")
    public ResponseEntity<List<FinancialDepartmentLedgerResponse>> responseEntity(@RequestBody FinancialDepartmentLedgerDto financialDepartmentLedgerDto) {
        return ResponseEntity.ok(financialLedgerTypeService.getFinancialLedgerByDepartmentId(financialDepartmentLedgerDto));
    }
}

