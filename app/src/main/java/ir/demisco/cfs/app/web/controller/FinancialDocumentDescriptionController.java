package ir.demisco.cfs.app.web.controller;

import ir.demisco.cfs.service.api.FinancialDocumentDescriptionService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialDocumentDescription")
public class FinancialDocumentDescriptionController {

    private final FinancialDocumentDescriptionService financialDocumentDescriptionService;


    public FinancialDocumentDescriptionController(FinancialDocumentDescriptionService financialDocumentDescriptionService) {
        this.financialDocumentDescriptionService = financialDocumentDescriptionService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
//        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Long organizationId = 100L;
        return ResponseEntity.ok(financialDocumentDescriptionService.getFinancialDocumentByOrganizationId(organizationId,dataSourceRequest));

    }
}
