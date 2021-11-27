package ir.demisco.cfs.service.financialDocument;

import ir.demisco.cfs.model.dto.response.*;
import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.SaveFinancialDocumentService;
import ir.demisco.cfs.service.api.TransferFinancialDocumentService;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.test.annotation.WithDmsOAuth2MockUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ActiveProfiles("unit_test")
@SpringBootTest
@WithDmsOAuth2MockUser(orgId = 1L, userId = 1000L)
public class FinancialDocumentTest {
    @Autowired
    private FinancialDocumentService financialDocumentService;

    @Autowired
    private SaveFinancialDocumentService saveFinancialDocumentService;

    @Autowired
    private TransferFinancialDocumentService transferFinancialDocumentService;




    @Test
    public void financialDocumentCreatDeleteError() {
        try {
            Boolean delete = financialDocumentService.deleteFinancialDocumentById(6651L);
            Assertions.assertNotNull(delete);
        } catch (RuleException e) {
            if (e.getMessage() == null) {
                Assertions.fail();
            }
        }
    }


    @Test
    public void financialDocumentChangeDescription() {
        List<Long> longList = new ArrayList<>();
        longList.add(9302L);
        FinancialDocumentChengDescriptionDto financialDocumentChengDescriptionDto = new FinancialDocumentChengDescriptionDto();
        financialDocumentChengDescriptionDto.setId(6304L);
        financialDocumentChengDescriptionDto.setFinancialDocumentItemIdList(longList);
        financialDocumentChengDescriptionDto.setOldDescription("بیمه");
        financialDocumentChengDescriptionDto.setNewDescription("بیمه گر");
        try {
            String financialDocumentDescription = financialDocumentService.changeDescription(financialDocumentChengDescriptionDto);
            Assertions.assertNotNull(financialDocumentDescription);
        } catch (RuleException e) {
            if (e.getMessage() == null) {
                Assertions.fail();
            }
        }

    }

    @Test
    public void financialDocumentChangeAccountSuccess() {
        List<Long> longList = new ArrayList<>();
        longList.add(9302L);
        FinancialDocumentAccountDto financialDocumentAccountDto = new FinancialDocumentAccountDto();
        financialDocumentAccountDto.setId(6304L);
        financialDocumentAccountDto.setFinancialDocumentItemIdList(longList);
        financialDocumentAccountDto.setFinancialAccountId(9054L);
        financialDocumentAccountDto.setNewFinancialAccountId(7005L);
        try {
            List<FinancialDocumentAccountMessageDto> financialDocumentAccountMessageDto =
                    financialDocumentService.changeAccountDocument(financialDocumentAccountDto);
            Assertions.assertTrue(financialDocumentAccountMessageDto.size() == 0);
        } catch (RuleException e) {
            if (e.getMessage() == null) {
                Assertions.fail();
            }
        }

    }

    @Test
    public void financialDocumentChangeAccountMessage() {
        List<Long> longList = new ArrayList<>();
        longList.add(9302L);
        FinancialDocumentAccountDto financialDocumentAccountDto = new FinancialDocumentAccountDto();
        financialDocumentAccountDto.setId(6304L);
        financialDocumentAccountDto.setFinancialDocumentItemIdList(longList);
        financialDocumentAccountDto.setFinancialAccountId(9054L);
        financialDocumentAccountDto.setNewFinancialAccountId(7011L);
        try {
            List<FinancialDocumentAccountMessageDto> financialDocumentAccountMessageDto =
                    financialDocumentService.changeAccountDocument(financialDocumentAccountDto);
            Assertions.assertNotNull(financialDocumentAccountMessageDto.get(0).getMessage());
        } catch (RuleException e) {
            if (e.getMessage() == null) {
                Assertions.fail();
            }
        }

    }

    @Test
    public void financialDocumentChangeCentricAccount() {

        List<Long> longList = new ArrayList<>();
        longList.add(9302L);
        FinancialCentricAccountDto financialCentricAccountDto = new FinancialCentricAccountDto();
        financialCentricAccountDto.setId(6304L);
        financialCentricAccountDto.setFinancialDocumentItemIdList(longList);
        financialCentricAccountDto.setFinancialAccountId(9054L);
        financialCentricAccountDto.setCentricAccountId(6109L);
        financialCentricAccountDto.setNewCentricAccountId(6801L);
        try {
            String result = financialDocumentService.changeCentricAccount(financialCentricAccountDto);
            Assertions.assertNotNull(result);
        } catch (RuleException e) {
            if (e.getMessage() == null) {
                Assertions.fail();
            }
        }
    }

    @Test
    public void financialDocumentChangeAmount() {

        List<Long> longList = new ArrayList<>();
        longList.add(9302L);
        FinancialCentricAccountDto financialCentricAccountDto = new FinancialCentricAccountDto();
        financialCentricAccountDto.setId(6304L);
        financialCentricAccountDto.setFinancialDocumentItemIdList(longList);
        try {
            Boolean result = financialDocumentService.changeAmountDocument(financialCentricAccountDto);
            Assertions.assertEquals(true, result);
        } catch (RuleException e) {
            if (e.getMessage() == null) {
                Assertions.fail();
            }
        }
    }


    @Test
    public void financialDocumentSetAmount() {

        List<Long> longList = new ArrayList<>();
        longList.add(9302L);
        FinancialCentricAccountDto financialCentricAccountDto = new FinancialCentricAccountDto();
        financialCentricAccountDto.setId(6304L);
        financialCentricAccountDto.setFinancialDocumentItemIdList(longList);
        try {
            Boolean result = financialDocumentService.changeAmountDocument(financialCentricAccountDto);
            Assertions.assertEquals(true, result);
        } catch (RuleException e) {
            if (e.getMessage() == null) {
                Assertions.fail();
            }
        }
    }

    @Test
    public void financialDocumentGetStructure() {
        RequestDocumentStructureDto requestDocumentStructureDto = new RequestDocumentStructureDto();
        requestDocumentStructureDto.setFinancialDocumentId(6304L);
        List<ResponseFinancialDocumentStructureDto> documentStructureDtoList = financialDocumentService.getDocumentStructure(requestDocumentStructureDto);
        Assertions.assertEquals(6, documentStructureDtoList.size());
    }

    @Test
    public void financialDocumentArrangeSq() {
        FinancialDocumentDto financialDocumentDto = new FinancialDocumentDto();
        financialDocumentDto.setId(6304L);
        Boolean arrangeSq = financialDocumentService.setArrangeSequence(financialDocumentDto);
        Assertions.assertEquals(true, arrangeSq);
    }


    @Test
    public void financialDocumentGet() {

        FinancialDocumentDto financialDocumentDto = new FinancialDocumentDto();
        financialDocumentDto.setId(6304L);
        FinancialDocumentSaveDto documentSaveDto = saveFinancialDocumentService.getFinancialDocumentInfo(financialDocumentDto);
        Assertions.assertEquals(12, documentSaveDto.getFinancialDocumentItemDtoList().size());
        Assertions.assertEquals(1, documentSaveDto.getFinancialDocumentItemDtoList().get(0).getDocumentReferenceList().size());
    }

    @Test
    public void financialDocumentTransferCopyItem() {
        List<Long> financialDocumentItemList = new ArrayList<>();
        financialDocumentItemList.add(9301L);
        financialDocumentItemList.add(9302L);
        FinancialDocumentTransferDto documentTransferDto = new FinancialDocumentTransferDto();
        documentTransferDto.setId(6304L);
        documentTransferDto.setDocumentNumber("10011100044");
        documentTransferDto.setTransferType(1);
        documentTransferDto.setFinancialDocumentItemIdList(financialDocumentItemList);
        documentTransferDto.setAllItemFlag(false);
        Boolean copyItem = transferFinancialDocumentService.transferDocument(documentTransferDto);
        Assertions.assertEquals(true, copyItem);
    }

    @Test
    public void financialDocumentTransferItem() {
        List<Long> financialDocumentItemList = new ArrayList<>();
        financialDocumentItemList.add(9301L);
        financialDocumentItemList.add(9302L);
        FinancialDocumentTransferDto documentTransferDto = new FinancialDocumentTransferDto();
        documentTransferDto.setId(6304L);
        documentTransferDto.setDocumentNumber("10011100044");
        documentTransferDto.setTransferType(2);
        documentTransferDto.setFinancialDocumentItemIdList(financialDocumentItemList);
        documentTransferDto.setAllItemFlag(false);
        Boolean copyItem = transferFinancialDocumentService.transferDocument(documentTransferDto);
        Assertions.assertEquals(true, copyItem);
    }

    @Test
    public void financialDocumentTransferCopyItemInNewDocument() {
        List<Long> financialDocumentItemList = new ArrayList<>();
        Date date = new Date(2021, 11, 18);
        financialDocumentItemList.add(9301L);
        financialDocumentItemList.add(9302L);
        FinancialDocumentTransferDto documentTransferDto = new FinancialDocumentTransferDto();
        documentTransferDto.setId(6304L);
        documentTransferDto.setDocumentNumber("10011100044");
        documentTransferDto.setTransferType(3);
        documentTransferDto.setFinancialDocumentItemIdList(financialDocumentItemList);
        documentTransferDto.setDate(date);
        documentTransferDto.setAllItemFlag(false);
        Boolean copyItem = transferFinancialDocumentService.transferDocument(documentTransferDto);
        Assertions.assertEquals(true, copyItem);
    }

    @Test
    public void financialDocumentTransferItemInNewDocument() {
        List<Long> financialDocumentItemList = new ArrayList<>();
        Date date = new Date(2021, 11, 18);
        financialDocumentItemList.add(9301L);
        financialDocumentItemList.add(9302L);
        FinancialDocumentTransferDto documentTransferDto = new FinancialDocumentTransferDto();
        documentTransferDto.setId(6304L);
        documentTransferDto.setDocumentNumber("10011100044");
        documentTransferDto.setTransferType(4);
        documentTransferDto.setFinancialDocumentItemIdList(financialDocumentItemList);
        documentTransferDto.setDate(date);
        documentTransferDto.setAllItemFlag(false);
        Boolean copyItem = transferFinancialDocumentService.transferDocument(documentTransferDto);
        Assertions.assertEquals(true, copyItem);
    }

    @Test
    public void financialDocumentTransferChangeDate() {
        List<Long> financialDocumentItemList = new ArrayList<>();
        Date date = new Date(121, 10, 28);
        financialDocumentItemList.add(9301L);
        financialDocumentItemList.add(9302L);
        FinancialDocumentTransferDto documentTransferDto = new FinancialDocumentTransferDto();
        documentTransferDto.setId(6304L);
        documentTransferDto.setDocumentNumber("10011100044");
        documentTransferDto.setTransferType(5);
        documentTransferDto.setFinancialDocumentItemIdList(financialDocumentItemList);
        documentTransferDto.setDate(date);
        documentTransferDto.setAllItemFlag(false);
        try {
            Boolean copyItem = transferFinancialDocumentService.transferDocument(documentTransferDto);
            Assertions.assertEquals(true, copyItem);
        } catch (RuleException e) {

            if (e.getMessage() == null) {
                Assertions.fail();
            }
        }
    }

    @Test
    public void financialDocumentTransferExchangeDocument() {
        List<Long> financialDocumentItemList = new ArrayList<>();
        Date date = new Date(121, 10, 28);
        financialDocumentItemList.add(9301L);
        financialDocumentItemList.add(9302L);
        FinancialDocumentTransferDto documentTransferDto = new FinancialDocumentTransferDto();
        documentTransferDto.setId(6304L);
        documentTransferDto.setDocumentNumber("10211500060");
        documentTransferDto.setTransferType(6);
        documentTransferDto.setFinancialDocumentItemIdList(financialDocumentItemList);
        documentTransferDto.setDate(date);
        documentTransferDto.setAllItemFlag(false);
        try {
            Boolean copyItem = transferFinancialDocumentService.transferDocument(documentTransferDto);
            Assertions.assertEquals(true, copyItem);
        }catch (RuleException e){
            if(e.getMessage()==null){
                Assertions.fail();
            }
        }
    }

    @Test
    public void financialDocumentCopyDocument() {
        List<Long> financialDocumentItemList = new ArrayList<>();
        Date date = new Date(121, 10, 28);
        financialDocumentItemList.add(9301L);
        financialDocumentItemList.add(9302L);
        FinancialDocumentTransferDto documentTransferDto = new FinancialDocumentTransferDto();
        documentTransferDto.setId(6304L);
        documentTransferDto.setDocumentNumber("10211500060");
        documentTransferDto.setTransferType(7);
        documentTransferDto.setFinancialDocumentItemIdList(financialDocumentItemList);
        documentTransferDto.setDate(date);
        documentTransferDto.setAllItemFlag(false);
        try {
            Boolean copyItem = transferFinancialDocumentService.transferDocument(documentTransferDto);
            Assertions.assertEquals(true, copyItem);
        }catch (RuleException e){
            if(e.getMessage()==null){
                Assertions.fail();
            }
        }
    }

    @Test
    public void financialDocumentSave() {

        Date date = new Date(121, 11, 28);
        FinancialDocumentSaveDto documentSaveDto=new FinancialDocumentSaveDto();
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList=new ArrayList<>();
        ResponseFinancialDocumentItemDto responseFinancialDocumentItemDto=new ResponseFinancialDocumentItemDto();
        List<FinancialDocumentReferenceDto> list=new ArrayList<>();
        FinancialDocumentReferenceDto referenceDt0=new FinancialDocumentReferenceDto();
        List<FinancialDocumentItemCurrencyDto>  itemCurrencyDtos=new ArrayList<>();
        FinancialDocumentItemCurrencyDto itemCurrencyDto=new FinancialDocumentItemCurrencyDto();
        itemCurrencyDto.setForeignDebitAmount(new BigDecimal(40000));
        itemCurrencyDto.setForeignCreditAmount(new BigDecimal(1000));
        itemCurrencyDto.setExchangeRate(2000L);
        itemCurrencyDto.setMoneyTypeId(1L);
        itemCurrencyDto.setMoneyPricingReferenceId(4L);
        itemCurrencyDtos.add(itemCurrencyDto);
        referenceDt0.setReferenceNumber(888777L);
        referenceDt0.setReferenceDate(date);
        referenceDt0.setReferenceDescription("تست سند1");
        list.add(referenceDt0);

        responseFinancialDocumentItemDto.setSequenceNumber(525L);
        responseFinancialDocumentItemDto.setFinancialAccountId(7005L);
        responseFinancialDocumentItemDto.setCreditAmount(10000D);
        responseFinancialDocumentItemDto.setDebitAmount(0D);
        responseFinancialDocumentItemDto.setDescription("ردیف سند");
        responseFinancialDocumentItemDto.setCentricAccountId1(6151L);
        responseFinancialDocumentItemDto.setCentricAccountId2(5L);
        responseFinancialDocumentItemDto.setCentricAccountId3(null);
        responseFinancialDocumentItemDto.setCentricAccountId4(null);
        responseFinancialDocumentItemDto.setCentricAccountId5(null);
        responseFinancialDocumentItemDto.setCentricAccountId6(6109L);
        responseFinancialDocumentItemDto.setDocumentReferenceList(list);
        responseFinancialDocumentItemDto.setDocumentItemCurrencyList(itemCurrencyDtos);
        financialDocumentItemDtoList.add(responseFinancialDocumentItemDto);
        documentSaveDto.setDocumentDate(date);
        documentSaveDto.setDocumentNumber("555555");
        documentSaveDto.setFinancialDocumentTypeId(6L);
        documentSaveDto.setFinancialDocumentStatusId(1L);
        documentSaveDto.setFinancialPeriodId(13172L);
        documentSaveDto.setFinancialLedgerTypeId(10L);
        documentSaveDto.setDepartmentId(1L);
        documentSaveDto.setDescription("سند پرداخت");
        documentSaveDto.setAutomaticFlag(true);
        documentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
        documentSaveDto=saveFinancialDocumentService.saveDocument(documentSaveDto);
        Assertions.assertNotNull(documentSaveDto);
        Assertions.assertTrue(documentSaveDto.getFinancialDocumentItemDtoList().size()==1);
        Assertions.assertTrue(documentSaveDto.getFinancialDocumentItemDtoList().get(0).getDocumentItemCurrencyList().size()==1);
        Assertions.assertTrue(documentSaveDto.getFinancialDocumentItemDtoList().get(0).getDocumentReferenceList().size()==1);
    }

    @Test
    public void financialDocumentSaveDocument() {

        Date date = new Date(121, 11, 28);
        FinancialDocumentSaveDto documentSaveDto=new FinancialDocumentSaveDto();
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList=new ArrayList<>();
        ResponseFinancialDocumentItemDto responseFinancialDocumentItemDto=new ResponseFinancialDocumentItemDto();
        List<FinancialDocumentReferenceDto> list=new ArrayList<>();
        FinancialDocumentReferenceDto referenceDt0=new FinancialDocumentReferenceDto();
        FinancialDocumentReferenceDto referenceDt1=new FinancialDocumentReferenceDto();
        List<FinancialDocumentItemCurrencyDto>  itemCurrencyDtos=new ArrayList<>();
        FinancialDocumentItemCurrencyDto itemCurrencyDto=new FinancialDocumentItemCurrencyDto();
        itemCurrencyDto.setForeignDebitAmount(new BigDecimal(50000));
        itemCurrencyDto.setForeignCreditAmount(new BigDecimal(1000));
        itemCurrencyDto.setExchangeRate(2000L);
        itemCurrencyDto.setMoneyTypeId(1L);
        itemCurrencyDto.setMoneyPricingReferenceId(4L);
        itemCurrencyDtos.add(itemCurrencyDto);
        referenceDt0.setReferenceNumber(888777L);
        referenceDt0.setReferenceDate(date);
        referenceDt0.setReferenceDescription("تست سند1");
        referenceDt1.setReferenceNumber(88887444L);
        referenceDt1.setReferenceDate(date);
        referenceDt1.setReferenceDescription("تست سند1");
        list.add(referenceDt0);
        list.add(referenceDt1);
        responseFinancialDocumentItemDto.setSequenceNumber(525L);
        responseFinancialDocumentItemDto.setFinancialAccountId(7005L);
        responseFinancialDocumentItemDto.setCreditAmount(10000D);
        responseFinancialDocumentItemDto.setDebitAmount(500D);
        responseFinancialDocumentItemDto.setDescription("ردیف سند");
        responseFinancialDocumentItemDto.setCentricAccountId1(6151L);
        responseFinancialDocumentItemDto.setCentricAccountId2(5L);
        responseFinancialDocumentItemDto.setCentricAccountId3(null);
        responseFinancialDocumentItemDto.setCentricAccountId4(null);
        responseFinancialDocumentItemDto.setCentricAccountId5(null);
        responseFinancialDocumentItemDto.setCentricAccountId6(6109L);
        responseFinancialDocumentItemDto.setDocumentReferenceList(list);
        responseFinancialDocumentItemDto.setDocumentItemCurrencyList(itemCurrencyDtos);
        financialDocumentItemDtoList.add(responseFinancialDocumentItemDto);
        documentSaveDto.setDocumentDate(date);
        documentSaveDto.setDocumentNumber("555555");
        documentSaveDto.setFinancialDocumentTypeId(6L);
        documentSaveDto.setFinancialDocumentStatusId(1L);
        documentSaveDto.setFinancialPeriodId(13172L);
        documentSaveDto.setFinancialLedgerTypeId(10L);
        documentSaveDto.setDepartmentId(1L);
        documentSaveDto.setDescription("سند پرداخت");
        documentSaveDto.setAutomaticFlag(true);
        documentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
        documentSaveDto=saveFinancialDocumentService.saveDocument(documentSaveDto);
        Assertions.assertNotNull(documentSaveDto);
        Assertions.assertTrue(documentSaveDto.getFinancialDocumentItemDtoList().size()==1);
        Assertions.assertTrue(documentSaveDto.getFinancialDocumentItemDtoList().get(0).getDocumentItemCurrencyList().size()==1);
        Assertions.assertTrue(documentSaveDto.getFinancialDocumentItemDtoList().get(0).getDocumentReferenceList().size()==2);
    }

    @Test
    public void financialDocumentUpdate() {

        Date date = new Date(121, 11, 28);
        FinancialDocumentSaveDto documentSaveDto=new FinancialDocumentSaveDto();
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList=new ArrayList<>();
        ResponseFinancialDocumentItemDto responseFinancialDocumentItemDto=new ResponseFinancialDocumentItemDto();

        responseFinancialDocumentItemDto.setId(6353L);
        responseFinancialDocumentItemDto.setSequenceNumber(525L);
        responseFinancialDocumentItemDto.setFinancialAccountId(7005L);
        responseFinancialDocumentItemDto.setCreditAmount(10000D);
        responseFinancialDocumentItemDto.setDebitAmount(500D);
        responseFinancialDocumentItemDto.setDescription("ردیف سند");
        responseFinancialDocumentItemDto.setCentricAccountId1(6151L);
        responseFinancialDocumentItemDto.setCentricAccountId2(5L);
        responseFinancialDocumentItemDto.setCentricAccountId3(null);
        responseFinancialDocumentItemDto.setCentricAccountId4(null);
        responseFinancialDocumentItemDto.setCentricAccountId5(null);
        responseFinancialDocumentItemDto.setCentricAccountId6(6109L);
        financialDocumentItemDtoList.add(responseFinancialDocumentItemDto);
        documentSaveDto.setFinancialDocumentId(6303L);
        documentSaveDto.setDocumentDate(date);
        documentSaveDto.setDocumentNumber("555555");
        documentSaveDto.setFinancialDocumentTypeId(6L);
        documentSaveDto.setFinancialDocumentStatusId(1L);
        documentSaveDto.setFinancialPeriodId(13172L);
        documentSaveDto.setFinancialLedgerTypeId(10L);
        documentSaveDto.setDepartmentId(1L);
        documentSaveDto.setDescription("سند پرداخت");
        documentSaveDto.setAutomaticFlag(true);
        documentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
        documentSaveDto=saveFinancialDocumentService.updateDocument(documentSaveDto);
        Assertions.assertNotNull(documentSaveDto);
        Assertions.assertTrue(documentSaveDto.getFinancialDocumentItemDtoList().size()==1);

    }

    @Test
    public void financialDocumentUpdateDocument() {

        Date date = new Date(121, 11, 28);
        FinancialDocumentSaveDto documentSaveDto=new FinancialDocumentSaveDto();
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList=new ArrayList<>();
        ResponseFinancialDocumentItemDto responseFinancialDocumentItemDto=new ResponseFinancialDocumentItemDto();
        documentSaveDto.setFinancialDocumentId(6303L);
        documentSaveDto.setDocumentDate(date);
        documentSaveDto.setDocumentNumber("555555");
        documentSaveDto.setFinancialDocumentTypeId(6L);
        documentSaveDto.setFinancialDocumentStatusId(1L);
        documentSaveDto.setFinancialPeriodId(13172L);
        documentSaveDto.setFinancialLedgerTypeId(10L);
        documentSaveDto.setDepartmentId(1L);
        documentSaveDto.setDescription("سند پرداخت");
        documentSaveDto.setAutomaticFlag(true);
        documentSaveDto=saveFinancialDocumentService.updateDocument(documentSaveDto);
        Assertions.assertNotNull(documentSaveDto);
        Assertions.assertTrue(documentSaveDto.getFinancialDocumentItemDtoList()==null);

    }

    @Test
    public void financialDocumentSetStatus(){


        ResponseFinancialDocumentStatusDto  financialDocumentStatusDto=new ResponseFinancialDocumentStatusDto();
        financialDocumentStatusDto.setId(6304L);
        financialDocumentStatusDto.setFinancialDocumentStatusCode("20");
        try {
            ResponseEntity<ResponseFinancialDocumentSetStatusDto> documentSetStatusDto =
                    financialDocumentService.changeStatus(financialDocumentStatusDto);
            Assertions.assertNotNull(documentSetStatusDto.getBody().getFinancialDocumentErrorDtoList());
        }catch(RuleException e){

            if(e.getMessage()==null){
                Assertions.fail();
            }

        }
    }

//    @Test
//    public void financialDocumentSetStatusSuccess(){
//
//
//        ResponseFinancialDocumentStatusDto  financialDocumentStatusDto=new ResponseFinancialDocumentStatusDto();
//        financialDocumentStatusDto.setId(8257L);
//        financialDocumentStatusDto.setFinancialDocumentStatusCode("20");
//        try {
//            ResponseEntity<ResponseFinancialDocumentSetStatusDto> documentSetStatusDto =
//                    financialDocumentService.changeStatus(financialDocumentStatusDto);
//            Assertions.assertNotNull(documentSetStatusDto.getBody().getFinancialDocumentErrorDtoList());
//        }catch(RuleException e){
//
//            if(e.getMessage()==null){
//                Assertions.fail();
//            }
//
//        }
//    }


}
