package ir.demisco.cfs.service.descriptionAndNumberingFormat;

import ir.demisco.cfs.model.dto.response.FinancialNumberingFormatDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialNumberingFormatDto;
import ir.demisco.cfs.model.entity.FinancialNumberingFormatType;
import ir.demisco.cfs.service.api.FinancialNumberingFormatService;
import ir.demisco.cfs.service.api.FinancialNumberingFormatTypeService;
import ir.demisco.cfs.service.api.FinancialNumberingTypeService;
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
public class FinancialNumberingFormatTest {

    @Autowired
    private  FinancialNumberingFormatService financialNumberingFormatService;

    @Autowired
    private FinancialNumberingFormatTypeService financialNumberingFormatTypeService;

    @Autowired
    private  FinancialNumberingTypeService financialNumberingTypeService;

    @Test
    public void financialNumberingFormatListSuccess(){

        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        Long organizationId=1L;
        DataSourceResult financialNumberingFormatList=financialNumberingFormatService.getNumberingFormatByOrganizationId(organizationId,dataSourceRequest);
        Assertions.assertNotNull(financialNumberingFormatList);
    }

    @Test
    public void financialNumberingFormatSaveSuccess(){
        FinancialNumberingFormatDto financialNumberingFormatDto=new FinancialNumberingFormatDto();
        ResponseFinancialNumberingFormatDto numberingFormatDto;
        financialNumberingFormatDto.setOrganizationId(1L);
        financialNumberingFormatDto.setFinancialNumberingTypeId(4L);
        financialNumberingFormatDto.setFinancialNumberingFormatTypeId(null);
        Boolean dto=financialNumberingFormatService.save(financialNumberingFormatDto);
        Assertions.assertEquals(true, dto);

    }

    @Test
    public void financialNumberingFormatSaveError(){
        FinancialNumberingFormatDto financialNumberingFormatDto=new FinancialNumberingFormatDto();
        ResponseFinancialNumberingFormatDto numberingFormatDto;
        financialNumberingFormatDto.setOrganizationId(1L);
        financialNumberingFormatDto.setFinancialNumberingTypeId(4L);
        financialNumberingFormatDto.setFinancialNumberingFormatTypeId(1L);
        try {
            Boolean dto = financialNumberingFormatService.save(financialNumberingFormatDto);
            Assertions.fail();
        }catch (RuleException e) {
            if (e.getMessage() == null) {
                Assertions.fail();
            }
        }
    }

    @Test
    public void financialNumberingFormatUpdateSuccess(){
        FinancialNumberingFormatDto financialNumberingFormatDto=new FinancialNumberingFormatDto();
        ResponseFinancialNumberingFormatDto numberingFormatDto;
        financialNumberingFormatDto.setId(3001L);
        financialNumberingFormatDto.setOrganizationId(1L);
        financialNumberingFormatDto.setFinancialNumberingTypeId(4L);
        financialNumberingFormatDto.setFinancialNumberingFormatTypeId(5L);
        financialNumberingFormatDto.setDescription("100-H");
        Boolean dto=financialNumberingFormatService.upDate(financialNumberingFormatDto);
        Assertions.assertEquals(true, dto);
//        Assertions.assertEquals(5, numberingFormatDto.getFinancialNumberingFormatTypeId());
//        Assertions.assertEquals(4, numberingFormatDto.getFinancialNumberingTypeId());
//        Assertions.assertEquals("100-H", numberingFormatDto.getDescription());
    }

    @Test
    public void financialNumberingFormatDelete() {
        Boolean aBoolean = financialNumberingFormatService.deleteNumberingFormatById(1002L);
        Assertions.assertEquals(true, aBoolean);

    }

    @Test
    public void FinancialNumberingFormatTypSuccess(){
        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        DataSourceResult numberingFormatTypeList=financialNumberingFormatTypeService.getNumberingFormatType(dataSourceRequest);
        Assertions.assertNotNull(numberingFormatTypeList);
        Assertions.assertEquals(7,numberingFormatTypeList.getData().size());
    }

    @Test
    public void FinancialNumberingTypSuccess(){
        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        DataSourceResult numberingTypeList=financialNumberingTypeService.getNumberingType(dataSourceRequest);
        Assertions.assertNotNull(numberingTypeList);
        Assertions.assertEquals(5,numberingTypeList.getData().size());
    }

}
