package ir.demisco.cfs.app.web.controller;


import ir.demisco.cfs.model.dto.response.*;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.SaveFinancialDocumentService;
import ir.demisco.cfs.service.api.TransferFinancialDocumentService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-financialDocument")
public class FinancialDocumentController {

    private final FinancialDocumentService financialDocumentService;
    private final TransferFinancialDocumentService transferFinancialDocumentService;
    private final SaveFinancialDocumentService  saveFinancialDocumentService;

    public FinancialDocumentController(FinancialDocumentService financialDocumentService, TransferFinancialDocumentService transferFinancialDocumentService, SaveFinancialDocumentService saveFinancialDocumentService) {
        this.financialDocumentService = financialDocumentService;
        this.transferFinancialDocumentService = transferFinancialDocumentService;
        this.saveFinancialDocumentService = saveFinancialDocumentService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> financialDocumentList(@RequestBody DataSourceRequest dataSourceRequest){

        return ResponseEntity.ok(financialDocumentService.getFinancialDocumentList(dataSourceRequest));
    }

    @PostMapping("/SetStatus")
    public ResponseEntity<ResponseFinancialDocumentSetStatusDto> responseEntitySetStatus(@RequestBody ResponseFinancialDocumentStatusDto responseFinancialDocumentStatusDto)
    {
        return ResponseEntity.ok(financialDocumentService.changeStatus(responseFinancialDocumentStatusDto));
    }

    @PostMapping("/CreateNumber")
    public ResponseEntity<Long> creatNumber(@RequestBody FinancialDocumentNumberDto financialDocumentNumberDto){
        Long result;
        result = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/ChangeDescription")
    public ResponseEntity<FinancialDocumentAccountMessageDto> changeDescription(@RequestBody FinancialDocumentChengDescriptionDto financialDocumentDto){
        String result;
        FinancialDocumentAccountMessageDto financialDocumentAccountMessageDto=new FinancialDocumentAccountMessageDto();
        result = financialDocumentService.changeDescription(financialDocumentDto);
        financialDocumentAccountMessageDto.setMessage(result);
        return ResponseEntity.ok(financialDocumentAccountMessageDto);
    }

    @GetMapping("/Delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Long financialDocumentId) {
        boolean result;
        result = financialDocumentService.deleteFinancialDocumentById(financialDocumentId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/ChangeAccount")
    public ResponseEntity<FinancialDocumentAccountMessageDto>  changeAccount(@RequestBody FinancialDocumentAccountDto financialDocumentAccountDto){
     return  ResponseEntity.ok(financialDocumentService.changeAccountDocument(financialDocumentAccountDto));
    }

    @PostMapping("/ChangeCentricAccount")
    public ResponseEntity<FinancialDocumentAccountMessageDto> changeCentricAccount(@RequestBody FinancialCentricAccountDto financialCentricAccountDto){
        String result;
        FinancialDocumentAccountMessageDto financialDocumentAccountMessageDto=new FinancialDocumentAccountMessageDto();
        result = financialDocumentService.changeCentricAccount(financialCentricAccountDto);
        financialDocumentAccountMessageDto.setMessage(result);
        return ResponseEntity.ok(financialDocumentAccountMessageDto);
    }

    @PostMapping("/ChangeAmount")
    public ResponseEntity<Boolean> changeAmount(@RequestBody FinancialCentricAccountDto financialCentricAccountDto){

        Boolean result;
        result=financialDocumentService.changeAmountDocument(financialCentricAccountDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/SetAmount")
    public ResponseEntity<Boolean> setAmount(@RequestBody FinancialCentricAccountDto financialCentricAccountDto){

        Boolean result;
        result=financialDocumentService.setAmountDocument(financialCentricAccountDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/Save")
    public ResponseEntity<FinancialDocumentSaveDto> saveFinancialDocument(@RequestBody FinancialDocumentSaveDto requestFinancialDocumentSaveDto){
        if(requestFinancialDocumentSaveDto.getFinancialDocumentId()==null) {
            return ResponseEntity.ok(saveFinancialDocumentService.saveDocument(requestFinancialDocumentSaveDto));
        }else{
            return ResponseEntity.ok(saveFinancialDocumentService.updateDocument(requestFinancialDocumentSaveDto));
        }

    }

    @PostMapping("/ArrangeSequence")
    public ResponseEntity<Boolean> arrangeSequence(@RequestBody FinancialDocumentDto financialDocumentDto){
        boolean result;
        result=financialDocumentService.setArrangeSequence(financialDocumentDto);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/TransferDocument")
    public ResponseEntity<Boolean> transferDocument(@RequestBody  FinancialDocumentTransferDto  financialDocumentTransferDto){
        boolean result;
        result=transferFinancialDocumentService.transferDocument(financialDocumentTransferDto);
        return ResponseEntity.ok(result);

    }
}
