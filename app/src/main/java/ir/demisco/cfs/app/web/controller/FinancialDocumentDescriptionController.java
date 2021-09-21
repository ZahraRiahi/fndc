package ir.demisco.cfs.app.web.controller;


import ir.demisco.cfs.model.dto.response.FinancialDocumentDescriptionOrganizationDto;
import ir.demisco.cfs.service.api.FinancialDocumentDescriptionService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-financialDocumentDescription")
public class FinancialDocumentDescriptionController {

    private final FinancialDocumentDescriptionService financialDocumentDescriptionService;


    public FinancialDocumentDescriptionController(FinancialDocumentDescriptionService financialDocumentDescriptionService) {
        this.financialDocumentDescriptionService = financialDocumentDescriptionService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        return ResponseEntity.ok(financialDocumentDescriptionService.getFinancialDocumentByOrganizationId(organizationId,dataSourceRequest));
    }

    @PostMapping("/save")
    public ResponseEntity<FinancialDocumentDescriptionOrganizationDto> documentDescription(@RequestBody FinancialDocumentDescriptionOrganizationDto documentDescriptionDto){
        if (documentDescriptionDto.getId() == null) {
            return ResponseEntity.ok(financialDocumentDescriptionService.save(documentDescriptionDto));
        } else {
            return ResponseEntity.ok(financialDocumentDescriptionService.update(documentDescriptionDto));
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long  documentDescriptionId) {
        boolean result;
        result = financialDocumentDescriptionService.deleteDocumentDescriptionById(documentDescriptionId);
        return ResponseEntity.ok(result);

    }
}
