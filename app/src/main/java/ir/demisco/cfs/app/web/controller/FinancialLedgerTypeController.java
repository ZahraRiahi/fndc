package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialLedgerTypeRequest;
import ir.demisco.cfs.model.dto.request.FinancialSecurityFilterRequest;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentLedgerDto;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentLedgerResponse;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;
import ir.demisco.cfs.service.api.FinancialLedgerTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api-financialLedgerType")
public class FinancialLedgerTypeController {

    private final FinancialLedgerTypeService financialLedgerTypeService;

    public FinancialLedgerTypeController(FinancialLedgerTypeService financialLedgerTypeService) {
        this.financialLedgerTypeService = financialLedgerTypeService;
    }

    @PostMapping("/Get")
    public ResponseEntity<List<FinancialLedgerTypeDto>> responseEntity(@RequestBody FinancialSecurityFilterRequest financialSecurityFilterRequest ) {
        return ResponseEntity.ok(financialLedgerTypeService.getFinancialLedgerType(financialSecurityFilterRequest));
    }

    @PostMapping("/List")
    public ResponseEntity<DataSourceResult> financialLedgerTypeList(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialLedgerTypeService.financialLedgerTypeList(dataSourceRequest));
    }

    @PostMapping("/Save")
    public ResponseEntity<Boolean> saveFinancialLedgerType(@RequestBody FinancialLedgerTypeRequest financialLedgerTypeRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialLedgerTypeRequest.setOrganizationId(organizationId);
        financialLedgerTypeService.saveFinancialLedgerType(financialLedgerTypeRequest);
        return ResponseEntity.ok(true);
    }

    @PostMapping("/GetCurrent")
    public ResponseEntity<List<FinancialDepartmentLedgerResponse>> responseEntity(@RequestBody FinancialDepartmentLedgerDto financialDepartmentLedgerDto) {
        return ResponseEntity.ok(financialLedgerTypeService.getFinancialLedgerByDepartmentId(financialDepartmentLedgerDto));
    }
}

