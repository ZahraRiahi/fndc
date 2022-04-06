package ir.demisco.cfs.app.web.controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import ir.demisco.cfs.model.dto.response.FinancialNumberingFormatDto;
import ir.demisco.cfs.service.api.FinancialNumberingFormatService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialNumberingFormat")
public class FinancialNumberingFormatController {

    private final FinancialNumberingFormatService financialNumberingFormatService;

    public FinancialNumberingFormatController(FinancialNumberingFormatService financialNumberingFormatService) {
        this.financialNumberingFormatService = financialNumberingFormatService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        return ResponseEntity.ok(financialNumberingFormatService.getNumberingFormatByOrganizationId(organizationId, dataSourceRequest));

    }

    @PostMapping("/save")
    public ResponseEntity<Boolean> financialNumberingFormatSave(@RequestBody FinancialNumberingFormatDto financialNumberingFormatDto) {
        if (financialNumberingFormatDto.getId() == null) {
            return ResponseEntity.ok(financialNumberingFormatService.save(financialNumberingFormatDto));
        } else {
            return ResponseEntity.ok(financialNumberingFormatService.upDate(financialNumberingFormatDto));
        }
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long numberingFormatId) {
        boolean result;
        result = financialNumberingFormatService.deleteNumberingFormatById(numberingFormatId);
        return ResponseEntity.ok(result);

    }
}

