package ir.demisco.cfs.app.web.controller;


import ir.demisco.cfs.model.entity.FinancialNumberingFormat;
import ir.demisco.cfs.service.api.FinancialNumberingFormatService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api-financialNumberingFormat")
public class FinancialNumberingFormatController {

    private final FinancialNumberingFormatService  financialNumberingFormatService;

    public FinancialNumberingFormatController(FinancialNumberingFormatService financialNumberingFormatService) {
        this.financialNumberingFormatService = financialNumberingFormatService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> responseEntity(@RequestBody DataSourceRequest dataSourceRequest) {

        //        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Long organizationId = 100L;
        return ResponseEntity.ok(financialNumberingFormatService.getNumberingFormatByOrganizationId(organizationId,dataSourceRequest));

    }
}

