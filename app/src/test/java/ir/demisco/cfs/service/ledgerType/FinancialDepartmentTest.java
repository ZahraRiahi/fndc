package ir.demisco.cfs.service.ledgerType;

import ir.demisco.cfs.service.api.FinancialDepartmentService;
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
public class FinancialDepartmentTest {

    @Autowired
    private  FinancialDepartmentService financialDepartmentService;


    @Test
    public void financialDepartmentList(){
        DataSourceResult financialDepartment=financialDepartmentService.financialDepartmentList();
        Assertions.assertTrue(financialDepartment.getData().size()>0);
    }
}
