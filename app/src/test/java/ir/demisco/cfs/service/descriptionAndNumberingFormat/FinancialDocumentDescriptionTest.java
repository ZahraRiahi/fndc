package ir.demisco.cfs.service.descriptionAndNumberingFormat;


import ir.demisco.cfs.model.dto.response.FinancialDocumentDescriptionOrganizationDto;
import ir.demisco.cfs.service.api.FinancialDocumentDescriptionService;
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
public class FinancialDocumentDescriptionTest {

    @Autowired
    private FinancialDocumentDescriptionService financialDocumentDescriptionService;

    @Test
    public void financialDocumentDescriptionListSuccess(){
        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        Long organizationId=1L;
        DataSourceResult financialDocumentDescriptionList=financialDocumentDescriptionService.getFinancialDocumentByOrganizationId(organizationId,dataSourceRequest);
        Assertions.assertNotNull(financialDocumentDescriptionList);

    }

    @Test
    public void financialDocumentDescriptionSave(){
        FinancialDocumentDescriptionOrganizationDto financialDocumentDescriptionOrganizationDto=new FinancialDocumentDescriptionOrganizationDto();
        financialDocumentDescriptionOrganizationDto.setId(null);
        financialDocumentDescriptionOrganizationDto.setOrganizationId(1L);
        financialDocumentDescriptionOrganizationDto.setDescription("تست شرح");
        financialDocumentDescriptionOrganizationDto=financialDocumentDescriptionService.save(financialDocumentDescriptionOrganizationDto);
        Assertions.assertEquals(1, financialDocumentDescriptionOrganizationDto.getOrganizationId());
        Assertions.assertEquals("تست شرح", financialDocumentDescriptionOrganizationDto.getDescription());
    }
    @Test
    public void financialDocumentDescriptionUpdate(){
        FinancialDocumentDescriptionOrganizationDto financialDocumentDescriptionOrganizationDto=new FinancialDocumentDescriptionOrganizationDto();
        financialDocumentDescriptionOrganizationDto.setId(3156L);
        financialDocumentDescriptionOrganizationDto.setOrganizationId(1L);
        financialDocumentDescriptionOrganizationDto.setDescription("خرید غیر نقدی");
        financialDocumentDescriptionOrganizationDto=financialDocumentDescriptionService.update(financialDocumentDescriptionOrganizationDto);
        Assertions.assertEquals(1, financialDocumentDescriptionOrganizationDto.getOrganizationId());
        Assertions.assertEquals("خرید غیر نقدی", financialDocumentDescriptionOrganizationDto.getDescription());
    }

    @Test
    public void moneyUsedDelete() {
        Boolean aBoolean = financialDocumentDescriptionService.deleteDocumentDescriptionById(3008L);
        Assertions.assertEquals(true, aBoolean);

    }
}
