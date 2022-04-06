package ir.demisco.cfs.app.web.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ir.demisco.cfs.model.dto.request.FinancialDocumentTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeGetDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentTypeDto;
import ir.demisco.cfs.service.api.DocumentTypeOrgRelService;
import ir.demisco.cfs.service.api.FinancialDocumentTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api-financialDocumentType")
public class FinancialDocumentTypeController {
    private final DocumentTypeOrgRelService documentTypeOrgRelService;
    private final FinancialDocumentTypeService financialDocumentTypeService;

    public FinancialDocumentTypeController(DocumentTypeOrgRelService documentTypeOrgRelService, FinancialDocumentTypeService financialDocumentTypeService) {
        this.documentTypeOrgRelService = documentTypeOrgRelService;
        this.financialDocumentTypeService = financialDocumentTypeService;
    }

    @PostMapping("/get")
    public ResponseEntity<List<FinancialDocumentTypeGetDto>> responseEntity(@RequestBody FinancialDocumentTypeRequest financialDocumentTypeRequest) {

        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Long userId = SecurityHelper.getCurrentUser().getUserId();
        return ResponseEntity.ok(financialDocumentTypeService.getNumberingFormatByOrganizationId(organizationId, userId, financialDocumentTypeRequest));

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

    @PostMapping("/save")
    public ResponseEntity<ResponseFinancialDocumentTypeDto> financialDocumentTypeSave(@RequestBody FinancialDocumentTypeDto financialDocumentTypeDto) {
        if (financialDocumentTypeDto.getId() == null) {
            ResponseFinancialDocumentTypeDto responseFinancialDocumentTypeDto = financialDocumentTypeService.save(financialDocumentTypeDto);
            documentTypeOrgRelService.save(responseFinancialDocumentTypeDto.getId(), SecurityHelper.getCurrentUser().getOrganizationId());
            financialDocumentTypeDto.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
            return ResponseEntity.ok(responseFinancialDocumentTypeDto);
        } else {
            ResponseFinancialDocumentTypeDto financialCodingTypeDtoUpdate = financialDocumentTypeService.update(financialDocumentTypeDto);
            documentTypeOrgRelService.save(financialCodingTypeDtoUpdate.getId(), SecurityHelper.getCurrentUser().getOrganizationId());
            financialDocumentTypeDto.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
            return ResponseEntity.ok(financialCodingTypeDtoUpdate);
        }
    }
}
