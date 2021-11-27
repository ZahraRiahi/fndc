package ir.demisco.cfs.service.financialConfig;

import ir.demisco.cfs.model.dto.request.FinancialConfigRequest;
import ir.demisco.cfs.service.api.FinancialConfigService;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.test.annotation.WithDmsOAuth2MockUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("unit_test")
@SpringBootTest
@WithDmsOAuth2MockUser(orgId = 1L, userId = 1000L)
public class FinancialConfig {

    @Autowired
    private FinancialConfigService financialConfigService;

    @Test
    public void financialConfigTest() {
        DataSourceRequest dataSourceRequest = new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("user.id", "1000", DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("organization.id", "1", DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialDepartment.id", "5", DataSourceRequest.Operators.EQUALS));
        DataSourceResult configList = financialConfigService.getFinancialConfigByOrganizationIdAndUserAndDepartment(dataSourceRequest, 1L);
        Assertions.assertNotNull(configList);
    }

//    @Test
//    public void financialConfigSaveSuccess() {
//        FinancialConfigRequest financialConfigRequest = new FinancialConfigRequest();
//        financialConfigRequest.setOrganizationId(1L);
//        financialConfigRequest.setFinancialDepartmentId(5L);
//        financialConfigRequest.setUserId(112L);
//        financialConfigRequest.setFinancialDocumentTypeId(151L);
//        financialConfigRequest.setDocumentDescription(null);
//        Boolean financialConfigSave = financialConfigService.saveOrUpdateFinancialConfig(financialConfigRequest);
//        Assertions.assertEquals(true, financialConfigSave);
//    }

    @Test
    public void financialConfigSaveError() {
        FinancialConfigRequest financialConfigRequest = new FinancialConfigRequest();
        financialConfigRequest.setOrganizationId(1L);
        financialConfigRequest.setFinancialDepartmentId(5L);
        financialConfigRequest.setUserId(1000L);
        financialConfigRequest.setFinancialDocumentTypeId(151L);
        financialConfigRequest.setDocumentDescription(null);
        financialConfigRequest.setFinancialLedgerTypeId(2L);
        financialConfigRequest.setFinancialPeriodId(13172L);

        try {
            Boolean financialConfigSave = financialConfigService.saveOrUpdateFinancialConfig(financialConfigRequest);
            Assertions.fail();
        } catch (RuleException e) {
            if (e.getMessage() == null) {
                Assertions.fail();
            }
        }
    }
}
