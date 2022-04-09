package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.request.LedgerNumberingTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeResponse;
import ir.demisco.cfs.model.dto.response.LedgerNumberingTypeDto;
import ir.demisco.cfs.service.api.LedgerNumberingTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api-ledgerNumberingType")
public class LedgerNumberingTypeController {

    private final LedgerNumberingTypeService ledgerNumberingTypeService;

    public LedgerNumberingTypeController(LedgerNumberingTypeService ledgerNumberingTypeService) {
        this.ledgerNumberingTypeService = ledgerNumberingTypeService;
    }


    @PostMapping("/Get")
    public ResponseEntity<List<FinancialNumberingTypeResponse>> responseEntity(@RequestBody LedgerNumberingTypeRequest ledgerNumberingTypeRequest) {
        return ResponseEntity.ok(ledgerNumberingTypeService.getLedgerNumberingType(ledgerNumberingTypeRequest));

    }

    @PostMapping("/Save")
    public ResponseEntity<Boolean> saveLedgerNumberingType(@RequestBody LedgerNumberingTypeDto ledgerNumberingTypeDto) {
        return ResponseEntity.ok(ledgerNumberingTypeService.saveLedgerNumberingType(ledgerNumberingTypeDto));

    }

}
