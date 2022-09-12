package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.FinancialLedgerPeriodRequest;
import ir.demisco.cfs.model.dto.response.FinancialPeriodLedgerGetResponse;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-financialLedgerPeriod")
public class FinancialLedgerPeriodController {
    private final FinancialLedgerPeriodService financialLedgerPeriodService;

    public FinancialLedgerPeriodController(FinancialLedgerPeriodService financialLedgerPeriodService) {
        this.financialLedgerPeriodService = financialLedgerPeriodService;
    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> save(@RequestBody FinancialLedgerPeriodRequest financialLedgerPeriodRequest) {
        boolean result;
        result = financialLedgerPeriodService.saveFinancialLedgerPeriod(financialLedgerPeriodRequest);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/Get")
    public ResponseEntity<DataSourceResult> financialLedgerPeriodList(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialLedgerPeriodService.getFinancialLedgerPeriodList(dataSourceRequest));
    }

    @GetMapping("/GetByPeriod/{financialPeriodId}")
    public ResponseEntity<List<FinancialPeriodLedgerGetResponse>>  responseEntityFinancialDocumentItem(@PathVariable Long financialPeriodId) {
        return ResponseEntity.ok(financialLedgerPeriodService.getFinancialGetByPeriod(financialPeriodId));
    }
}
