package ir.demisco.cfs.app.web.controller;


import ir.demisco.cfs.model.dto.response.FinancialDocumentAccountDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentAccountMessageDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentChengDescriptionDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberDto;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-financialDocument")
public class FinancialDocumentController {

    private final FinancialDocumentService financialDocumentService;

    public FinancialDocumentController(FinancialDocumentService financialDocumentService) {
        this.financialDocumentService = financialDocumentService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> financialDocumentList(@RequestBody DataSourceRequest dataSourceRequest){

        return ResponseEntity.ok(financialDocumentService.getFinancialDocumentList(dataSourceRequest));
    }

//    @PostMapping("/SetStatus")
//    public ResponseEntity<FinancialDocumentDto> responseEntitySetStatus(@RequestBody ResponseFinancialDocumentStatusDto responseFinancialDocumentStatusDto)
//    {
//        return ResponseEntity.ok(financialDocumentService.changeStatus(responseFinancialDocumentStatusDto));
//    }

    @PostMapping("/CreateNumber")
    public ResponseEntity<String> creatNumber(@RequestBody FinancialDocumentNumberDto financialDocumentNumberDto){
        String result;
        result = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/ChangeDescription")
    public ResponseEntity<String> changeDescription(@RequestBody FinancialDocumentChengDescriptionDto financialDocumentDto){
        String result;
        result = financialDocumentService.changeDescription(financialDocumentDto);
        return ResponseEntity.ok(result);
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
}
