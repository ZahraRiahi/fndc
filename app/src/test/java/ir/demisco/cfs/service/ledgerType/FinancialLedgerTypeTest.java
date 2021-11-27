package ir.demisco.cfs.service.ledgerType;

import ir.demisco.cfs.model.dto.request.FinancialLedgerTypeRequest;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentLedgerDto;
import ir.demisco.cfs.model.dto.response.FinancialDepartmentLedgerResponse;
import ir.demisco.cfs.model.dto.response.FinancialLedgerTypeDto;
import ir.demisco.cfs.service.api.FinancialLedgerTypeService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
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
public class FinancialLedgerTypeTest {

    @Autowired
    private  FinancialLedgerTypeService financialLedgerTypeService;

    @Test
    public void financialLedgerTypeGet(){

        List<FinancialLedgerTypeDto> financialLedgerTypeDtoList=financialLedgerTypeService.getFinancialLedgerType(1L);
        Assertions.assertTrue(financialLedgerTypeDtoList.size()>0);
    }

    @Test
    public void financialLedgerTypeListSuccess(){
        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialCodingType.id",4101,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("id",1,DataSourceRequest.Operators.EQUALS));
        DataSourceResult financialLedgerTypeList1=financialLedgerTypeService.financialLedgerTypeList(dataSourceRequest);
        Assertions.assertNotNull(financialLedgerTypeList1);
    }

    @Test
    public void financialLedgerTypeListTest(){
        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialCodingType.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("id",null,DataSourceRequest.Operators.EQUALS));
        DataSourceResult financialLedgerTypeListTest=financialLedgerTypeService.financialLedgerTypeList(dataSourceRequest);
        Assertions.assertNotNull(financialLedgerTypeListTest);
        Assertions.assertTrue(financialLedgerTypeListTest.getData().size()>0);
    }

    @Test
    public void financialLedgerTypeSave(){
        FinancialLedgerTypeRequest financialLedgerTypeRequest=new FinancialLedgerTypeRequest();
        List<Long> numberingTypeIdList=new ArrayList<>();
        numberingTypeIdList.add(2L);
        numberingTypeIdList.add(4L);
        financialLedgerTypeRequest.setFinancialLedgerTypeId(null);
        financialLedgerTypeRequest.setDescription(null);
        financialLedgerTypeRequest.setOrganizationId(1L);
        financialLedgerTypeRequest.setFinancialCodingTypeId(7001L);
        financialLedgerTypeRequest.setActiveFlag(true);
        financialLedgerTypeRequest.setNumberingTypeIdList(numberingTypeIdList);
        Boolean departmentLedgerResponses=financialLedgerTypeService.saveFinancialLedgerType(financialLedgerTypeRequest);
        Assertions.assertEquals(true, departmentLedgerResponses);
    }

    @Test
    public void financialLedgerGetCurrent(){
        FinancialDepartmentLedgerDto financialDepartmentLedgerDto=new FinancialDepartmentLedgerDto();
        financialDepartmentLedgerDto.setFinancialDepartmentId(1L);
        List<FinancialDepartmentLedgerResponse> departmentLedgerResponses=financialLedgerTypeService.getFinancialLedgerByDepartmentId(financialDepartmentLedgerDto);
        Assertions.assertNotNull(departmentLedgerResponses);

    }

}
