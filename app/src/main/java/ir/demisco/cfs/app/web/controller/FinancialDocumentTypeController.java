package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeGetDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentTypeDto;
import ir.demisco.cfs.service.api.FinancialDocumentTypeService;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-financialDocumentType")
public class FinancialDocumentTypeController {

    private final FinancialDocumentTypeService financialDocumentTypeService;

    public FinancialDocumentTypeController(FinancialDocumentTypeService financialDocumentTypeService) {
        this.financialDocumentTypeService = financialDocumentTypeService;
    }

    @PostMapping("/get")
    public ResponseEntity<List<FinancialDocumentTypeGetDto>> responseEntity(@RequestBody ResponseFinancialDocumentTypeDto responseFinancialDocumentTypeDto) {

//        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Long organizationId = 100L;
        return ResponseEntity.ok(financialDocumentTypeService.getNumberingFormatByOrganizationId(organizationId, responseFinancialDocumentTypeDto));

    }
}
