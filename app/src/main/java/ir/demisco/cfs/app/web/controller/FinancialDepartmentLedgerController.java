package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialDepartmentLedgerRequest;
import ir.demisco.cfs.service.api.FinancialDepartmentLedgerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api-financialDepartmentLedger")
public class FinancialDepartmentLedgerController {
    private final FinancialDepartmentLedgerService financialDepartmentLedgerService;

    public FinancialDepartmentLedgerController(FinancialDepartmentLedgerService financialDepartmentLedgerService) {
        this.financialDepartmentLedgerService = financialDepartmentLedgerService;
    }

    @PostMapping("/Save")
    public ResponseEntity<Boolean> financialDepartmentLedger(@RequestBody List<FinancialDepartmentLedgerRequest> financialDepartmentLedgerRequests) {
        return ResponseEntity.ok(financialDepartmentLedgerService.saveFinancialDepartmentLedger(financialDepartmentLedgerRequests));
    }

}