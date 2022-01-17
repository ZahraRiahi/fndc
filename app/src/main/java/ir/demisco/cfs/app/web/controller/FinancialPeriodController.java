package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodStatusResponse;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-financialPeriod")
public class FinancialPeriodController {

    private final FinancialPeriodService financialPeriodService;

    public FinancialPeriodController(FinancialPeriodService financialPeriodService) {
        this.financialPeriodService = financialPeriodService;
    }


    @PostMapping("/GetCurrent")
    public ResponseEntity<List<FinancialPeriodResponse>> responseEntity(@RequestBody FinancialPeriodRequest financialPeriodRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        return ResponseEntity.ok(financialPeriodService.getFinancialAccountByDateAndOrgan(financialPeriodRequest, organizationId));
    }
}