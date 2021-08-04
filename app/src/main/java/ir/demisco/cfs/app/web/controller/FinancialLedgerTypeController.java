package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;
import ir.demisco.cfs.service.api.FinancialLedgerTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/Get")
    public ResponseEntity<List<FinancialLedgerTypeDto>> responseEntity() {
//        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        return ResponseEntity.ok(financialLedgerTypeService.getFinancialLedgerType(100L));

    }

    @PostMapping("/List")
    public ResponseEntity<DataSourceResult> financialLedgerTypeList(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialLedgerTypeService.financialLedgerTypeList(dataSourceRequest));
    }
}
