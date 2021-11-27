package ir.demisco.cfs.service.DocumentType;

import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentTypeGetDto;
import ir.demisco.cfs.model.dto.response.FinancialSystemDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentTypeDto;
import ir.demisco.cfs.model.entity.FinancialDocumentType;
import ir.demisco.cfs.service.api.FinancialDocumentTypeService;
import ir.demisco.cfs.service.api.FinancialSystemService;
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
public class FinancialDocumentTypeTest {

    @Autowired
    private FinancialDocumentTypeService financialDocumentTypeService;

    @Autowired
    private  FinancialSystemService financialSystemService;

    @Test
    public void financialDocumentTypeGet() {
        ResponseFinancialDocumentTypeDto dto = new ResponseFinancialDocumentTypeDto();
        dto.setSearchStatusFlag(true);
        List<FinancialDocumentTypeGetDto> financialDocumentTypeGetDtos =
                financialDocumentTypeService.getNumberingFormatByOrganizationId(1L, dto);
        ResponseFinancialDocumentTypeDto financialDocumentTypeDt1 = new ResponseFinancialDocumentTypeDto();
        financialDocumentTypeDt1.setSearchStatusFlag(false);
        List<FinancialDocumentTypeGetDto> financialDocumentTypeGetDtos1 =
                financialDocumentTypeService.getNumberingFormatByOrganizationId(1L, financialDocumentTypeDt1);
        Assertions.assertNotNull(financialDocumentTypeGetDtos);
//        Assertions.assertEquals(3,financialDocumentTypeGetDtos.size());
//        Assertions.assertEquals(1,financialDocumentTypeGetDtos1.size());
        Assertions.assertTrue(financialDocumentTypeGetDtos1.size()>0);
        Assertions.assertTrue(financialDocumentTypeGetDtos.size()>2);
    }

    @Test
    public void financialDocumentTypeList(){
        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialSystem.id", 1L, DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("id", null, DataSourceRequest.Operators.EQUALS));
        DataSourceResult numberingTypeList=financialDocumentTypeService.getFinancialDocumentTypeOrganizationIdAndFinancialSystemId(dataSourceRequest);
        Assertions.assertNotNull(numberingTypeList);

    }

    @Test
    public void financialDocumentTypeSaveSuccess(){
        FinancialDocumentTypeDto financialDocumentTypeDtoSave=new FinancialDocumentTypeDto();
        financialDocumentTypeDtoSave.setOrganizationId(1L);
        financialDocumentTypeDtoSave.setFinancialSystemId(1L);
        financialDocumentTypeDtoSave.setDescription("test-type");
        financialDocumentTypeDtoSave.setActiveFlag(true);
        ResponseFinancialDocumentTypeDto save=financialDocumentTypeService.save(financialDocumentTypeDtoSave);
        Assertions.assertNotNull(save);
        Assertions.assertEquals(true,save.getActiveFlag());
        Assertions.assertEquals(false,save.getAutomaticFlag());
    }

    @Test
    public void financialDocumentTypeUpdateSuccess(){
        FinancialDocumentTypeDto financialDocumentTypeDtoUpdate=new FinancialDocumentTypeDto();
        financialDocumentTypeDtoUpdate.setId(151L);
        financialDocumentTypeDtoUpdate.setOrganizationId(1L);
        financialDocumentTypeDtoUpdate.setFinancialSystemId(1L);
        financialDocumentTypeDtoUpdate.setDescription("سند حسابرسی");
        financialDocumentTypeDtoUpdate.setActiveFlag(true);
        ResponseFinancialDocumentTypeDto update=financialDocumentTypeService.update(financialDocumentTypeDtoUpdate);
        Assertions.assertNotNull(update);
        Assertions.assertEquals(true,update.getActiveFlag());
    }

    @Test
    public void financialDocumentTypeDelete() {
        Boolean aBoolean = financialDocumentTypeService.deleteFinancialDocumentTypeById(6L);
        Assertions.assertEquals(true, aBoolean);

    }

    @Test
    public void financialSystemGet(){
        List<FinancialSystemDto> financialSystemDtos=financialSystemService.getFinancialSystem();
        Assertions.assertNotNull(financialSystemDtos);
        Assertions.assertTrue(financialSystemDtos.size()>0);

    }
}
