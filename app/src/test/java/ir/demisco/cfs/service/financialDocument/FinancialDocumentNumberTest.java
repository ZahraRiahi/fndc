package ir.demisco.cfs.service.financialDocument;

import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberSaveDto;
import ir.demisco.cfs.service.api.FinancialDocumentNumberService;
import ir.demisco.cloud.core.test.annotation.WithDmsOAuth2MockUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("unit_test")
@SpringBootTest
@WithDmsOAuth2MockUser(orgId = 1L, userId = 1000L)
public class FinancialDocumentNumberTest {

    @Autowired
    private  FinancialDocumentNumberService financialDocumentNumberService;

    @Test
    public void  financialDocumentNumberSave(){

        FinancialDocumentNumberSaveDto financialDocumentNumberSaveDto=new FinancialDocumentNumberSaveDto();
        financialDocumentNumberSaveDto.setFinancialDocumentId(6304L);
        financialDocumentNumberSaveDto.setFinancialNumberingTypeId(1L);
        financialDocumentNumberSaveDto=financialDocumentNumberService.documentNumberSave(financialDocumentNumberSaveDto);
        Assertions.assertEquals(1,financialDocumentNumberSaveDto.getFinancialNumberingTypeId());
    }
}
