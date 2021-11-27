package ir.demisco.cfs.service.ledgerType;

import ir.demisco.cfs.model.dto.request.FinancialDepartmentLedgerRequest;
import ir.demisco.cfs.service.api.FinancialDepartmentLedgerService;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.test.annotation.WithDmsOAuth2MockUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("unit_test")
@SpringBootTest
@WithDmsOAuth2MockUser(orgId = 1L, userId = 1000L)
public class FinancialDepartmentLedgerTest {

    @Autowired
    private  FinancialDepartmentLedgerService financialDepartmentLedgerService;

    @Test
    public void financialDepartmentLedgerSave(){
        FinancialDepartmentLedgerRequest financialDepartmentLedgerRequest=new FinancialDepartmentLedgerRequest();
        List<FinancialDepartmentLedgerRequest> departmentLedgerRequests=new ArrayList<>();
        financialDepartmentLedgerRequest.setFinancialDepartmentId(5L);
        financialDepartmentLedgerRequest.setFinancialLedgerTypeId(2L);
        departmentLedgerRequests.add(financialDepartmentLedgerRequest);
        Boolean financialDepartmentLedger=financialDepartmentLedgerService.saveFinancialDepartmentLedger(departmentLedgerRequests);
        Assertions.assertEquals(true, financialDepartmentLedger);
    }

//    @Test
//    public void financialDepartmentLedgerSaveError(){
//        FinancialDepartmentLedgerRequest financialDepartmentLedgerRequest=new FinancialDepartmentLedgerRequest();
//        List<FinancialDepartmentLedgerRequest> departmentLedgerRequests=new ArrayList<>();
//        financialDepartmentLedgerRequest.setFinancialDepartmentId(1L);
//        financialDepartmentLedgerRequest.setFinancialLedgerTypeId(null);
//        departmentLedgerRequests.add(financialDepartmentLedgerRequest);
//        try {
//            Boolean financialDepartmentLedger = financialDepartmentLedgerService.saveFinancialDepartmentLedger(departmentLedgerRequests);
//            Assertions.assertNotNull(financialDepartmentLedger);
//            Assertions.fail();
//        } catch (RuleException e) {
//            if (e.getMessage() == null) {
//                Assertions.fail();
//            }
//        }
//    }

    @Test
    public void financialDepartmentLedgerSaveDelete() {
        FinancialDepartmentLedgerRequest financialDepartmentLedgerRequest = new FinancialDepartmentLedgerRequest();
        List<FinancialDepartmentLedgerRequest> departmentLedgerRequests = new ArrayList<>();
        financialDepartmentLedgerRequest.setFinancialDepartmentLedgerId(4102L);
        financialDepartmentLedgerRequest.setFinancialDepartmentId(1L);
        financialDepartmentLedgerRequest.setFinancialLedgerTypeId(null);
        departmentLedgerRequests.add(financialDepartmentLedgerRequest);
        Boolean financialDepartmentLedger = financialDepartmentLedgerService.saveFinancialDepartmentLedger(departmentLedgerRequests);
        Assertions.assertNotNull(financialDepartmentLedger);
    }
}
