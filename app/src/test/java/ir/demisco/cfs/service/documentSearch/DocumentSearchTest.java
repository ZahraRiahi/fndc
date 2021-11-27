package ir.demisco.cfs.service.documentSearch;

import ir.demisco.cfs.model.dto.response.ApplicationUserDto;
import ir.demisco.cfs.service.api.ApplicationUserService;
import ir.demisco.cfs.service.api.FinancialDocumentItemService;
import ir.demisco.cfs.service.api.FinancialDocumentService;
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
public class DocumentSearchTest {

    @Autowired
    private  FinancialDocumentService financialDocumentService;

    @Autowired
    private  FinancialDocumentItemService financialDocumentItemService;

    @Test
    public void financialDocumentList(){
        List<Long> longList=new ArrayList<>();
        longList.add(1L);
        longList.add(2L);
        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("startDate","2021-10-10T19:48:23.558Z",DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("endDate","2021-11-10T19:48:23.558Z",DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialNumberingType.id",2,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromNumber.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toNumber.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialDocumentStatusDtoList",longList,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("description",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromAccount.code",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toAccount.code",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("centricAccount.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("centricAccountType.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("user.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("priceType.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromPriceAmount",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toPriceAmount",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("tolerance",null,DataSourceRequest.Operators.EQUALS));
        DataSourceResult list=financialDocumentService.getFinancialDocumentList(dataSourceRequest);
        Assertions.assertNotNull(list);
    }


    @Test
    public void financialDocumentListTest(){
        List<Long> longList=new ArrayList<>();
        longList.add(1L);
        longList.add(2L);
        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("startDate","2021-10-10T19:48:23.558Z",DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("endDate","2021-11-10T19:48:23.558Z",DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialNumberingType.id",2,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromNumber.id",10211500060L,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toNumber.id",10211500064L,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialDocumentStatusDtoList",longList,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("description",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromAccount.code",9,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toAccount.code",1001004000,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("centricAccount.id",6801,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("centricAccountType.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("user.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("priceType.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromPriceAmount",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toPriceAmount",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("tolerance",null,DataSourceRequest.Operators.EQUALS));
        DataSourceResult list=financialDocumentService.getFinancialDocumentList(dataSourceRequest);
        Assertions.assertNotNull(list);
    }

    @Test
    public void financialDocumentItemList(){
        List<Long> longList=new ArrayList<>();
        longList.add(1L);
        longList.add(2L);
        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("startDate","2021-10-10T19:48:23.558Z",DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("endDate","2021-11-10T19:48:23.558Z",DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialNumberingType.id",2,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromNumber.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toNumber.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialDocumentStatusDtoList",longList,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("description",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromAccount.code",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toAccount.code",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("centricAccount.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("centricAccountType.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("user.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("priceType.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromPriceAmount",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toPriceAmount",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("tolerance",null,DataSourceRequest.Operators.EQUALS));
        DataSourceResult listDocumentItem=financialDocumentItemService.getFinancialDocumentItemList(dataSourceRequest);
        Assertions.assertNotNull(listDocumentItem);
    }

    @Test
    public void financialDocumentItemListTest(){
        List<Long> longList=new ArrayList<>();
        longList.add(1L);
        longList.add(2L);
        DataSourceRequest dataSourceRequest=new DataSourceRequest();
        dataSourceRequest.setSkip(0);
        dataSourceRequest.setTake(10);
        dataSourceRequest.getFilter().setLogic("and");
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("startDate","2021-10-10T19:48:23.558Z",DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("endDate","2021-11-10T19:48:23.558Z",DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialNumberingType.id",2,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromNumber.id",10211500060L,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toNumber.id",10211500064L,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("financialDocumentStatusDtoList",longList,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("description",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromAccount.code",9,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toAccount.code",1001004000,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("centricAccount.id",6801,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("centricAccountType.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("user.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("priceType.id",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("fromPriceAmount",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("toPriceAmount",null,DataSourceRequest.Operators.EQUALS));
        dataSourceRequest.getFilter().getFilters().add(DataSourceRequest.FilterDescriptor
                .create("tolerance",null,DataSourceRequest.Operators.EQUALS));
        DataSourceResult listFinancialDocumentItem=financialDocumentService.getFinancialDocumentList(dataSourceRequest);
        Assertions.assertNotNull(listFinancialDocumentItem);
    }


}
