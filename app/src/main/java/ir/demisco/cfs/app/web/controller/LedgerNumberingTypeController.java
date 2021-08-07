package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeResponse;
import ir.demisco.cfs.service.api.LedgerNumberingTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api-ledgerNumberingType")
public class LedgerNumberingTypeController {

    private final LedgerNumberingTypeService ledgerNumberingTypeService;

    public LedgerNumberingTypeController(LedgerNumberingTypeService ledgerNumberingTypeService) {
        this.ledgerNumberingTypeService = ledgerNumberingTypeService;
    }


    @PostMapping({"/Get"})
    public ResponseEntity<List<FinancialNumberingTypeResponse>> responseEntity(@RequestParam Long ledgerNumberingTypeId) {
        return ResponseEntity.ok(ledgerNumberingTypeService.getLedgerNumberingType(ledgerNumberingTypeId));

    }
}
