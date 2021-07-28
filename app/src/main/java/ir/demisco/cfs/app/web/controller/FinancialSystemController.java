package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialSystemDto;
import ir.demisco.cfs.service.api.FinancialSystemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api-financial_system")
public class FinancialSystemController {
    private final FinancialSystemService financialSystemService;

    public FinancialSystemController(FinancialSystemService financialSystemService) {
        this.financialSystemService = financialSystemService;
    }

    @PostMapping("/list")
    public ResponseEntity<List<FinancialSystemDto>> responseEntity() {
        return ResponseEntity.ok(financialSystemService.getFinancialSystem());
    }
}
