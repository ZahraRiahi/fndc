package ir.demisco.cfs.service.ledgerType;

import ir.demisco.cfs.model.dto.request.LedgerNumberingTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialNumberingTypeResponse;
import ir.demisco.cfs.model.dto.response.LedgerNumberingTypeDto;
import ir.demisco.cfs.service.api.FinancialNumberingTypeService;
import ir.demisco.cfs.service.api.LedgerNumberingTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.test.annotation.WithDmsOAuth2MockUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@ActiveProfiles("unit_test")
@SpringBootTest
@WithDmsOAuth2MockUser(orgId = 1L, userId = 1000L)
public class FinancialNumberingTypeTest {

    @Autowired
    private  LedgerNumberingTypeService ledgerNumberingTypeService;

    @Test
    public void ledgerNumberingTypeGet(){
        LedgerNumberingTypeRequest ledgerNumberingTypeRequest=new LedgerNumberingTypeRequest();
        ledgerNumberingTypeRequest.setFinancialLedgerTypeId(2L);
        List<FinancialNumberingTypeResponse> financialNumberingTypeResponseList=
                ledgerNumberingTypeService.getLedgerNumberingType(ledgerNumberingTypeRequest);
        Assertions.assertNotNull(financialNumberingTypeResponseList);
        Assertions.assertTrue(financialNumberingTypeResponseList.size()>0);
        Assertions.assertTrue(financialNumberingTypeResponseList.get(4).getFlgExists()==0);

    }

    @Test
    public void ledgerNumberingTypeGetNull(){
        LedgerNumberingTypeRequest ledgerNumberingTypeRequest=new LedgerNumberingTypeRequest();
        List<FinancialNumberingTypeResponse> financialNumberingTypeResponseList=
                ledgerNumberingTypeService.getLedgerNumberingType(ledgerNumberingTypeRequest);
        Assertions.assertNotNull(financialNumberingTypeResponseList);
        Assertions.assertTrue(financialNumberingTypeResponseList.size()>0);

    }

    @Test
    public void ledgerNumberingTypeSaveSuccess(){
        LedgerNumberingTypeDto ledgerNumberingTypeDto=new LedgerNumberingTypeDto();
        ledgerNumberingTypeDto.setFinancialLedgerTypeId(2L);
        ledgerNumberingTypeDto.setFinancialNumberingTypeId(4L);
        Boolean ledgerNumberingType=ledgerNumberingTypeService.saveLedgerNumberingType(ledgerNumberingTypeDto);
        Assertions.assertEquals(true, ledgerNumberingType);
    }
}
