package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeGetDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentTypeDto;
import ir.demisco.cfs.service.api.FinancialDocumentTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/Delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long financialDocumentTypeId) {
        boolean result;
        result = financialDocumentTypeService.deleteFinancialDocumentTypeById(financialDocumentTypeId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/List")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialDocumentTypeService.getFinancialDocumentTypeOrganizationIdAndFinancialSystemId(dataSourceRequest));
    }
}
