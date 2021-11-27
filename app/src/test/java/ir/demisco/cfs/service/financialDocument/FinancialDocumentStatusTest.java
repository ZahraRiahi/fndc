package ir.demisco.cfs.service.financialDocument;

import ir.demisco.cfs.model.dto.response.FinancialDocumentStatusListDto;
import ir.demisco.cfs.service.api.FinancialDocumentStatusService;
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
public class FinancialDocumentStatusTest {

    @Autowired
    private  FinancialDocumentStatusService financialDocumentStatusService;


    @Test
    public void financialDocumentStatusGet(){

        List<FinancialDocumentStatusListDto> financialDocumentStatusListDtos=financialDocumentStatusService.getStatusList();
         Assertions.assertEquals(3,financialDocumentStatusListDtos.size());
    }
}
