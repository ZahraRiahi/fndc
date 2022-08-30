package ir.demisco.cfs.app.web.controller;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import ir.demisco.cfs.model.dto.request.FinancialDocumentTransferRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodLedgerStatusRequest;
import ir.demisco.cfs.model.dto.response.FinancialCentricAccountDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentAccountDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentAccountMessageDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentChangeDescriptionDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentSaveDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentTransferOutputResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodStatusResponse;
import ir.demisco.cfs.model.dto.response.RequestDocumentStructureDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentSetStatusDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentStatusDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentStructureDto;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.SaveFinancialDocumentService;
import ir.demisco.cfs.service.api.TransferFinancialDocumentService;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api-financialDocument")
public class FinancialDocumentController {

    private final FinancialDocumentService financialDocumentService;
    private final TransferFinancialDocumentService transferFinancialDocumentService;
    private final SaveFinancialDocumentService saveFinancialDocumentService;

    public FinancialDocumentController(FinancialDocumentService financialDocumentService, TransferFinancialDocumentService transferFinancialDocumentService, SaveFinancialDocumentService saveFinancialDocumentService) {
        this.financialDocumentService = financialDocumentService;
        this.transferFinancialDocumentService = transferFinancialDocumentService;
        this.saveFinancialDocumentService = saveFinancialDocumentService;
    }

    @PostMapping("/list")
    public ResponseEntity<DataSourceResult> financialDocumentList(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialDocumentService.getFinancialDocumentList(dataSourceRequest));
    }

    @PostMapping("/SetStatus")
    public ResponseEntity<ResponseFinancialDocumentSetStatusDto> responseEntitySetStatus(@RequestBody ResponseFinancialDocumentStatusDto responseFinancialDocumentStatusDto) {
        return financialDocumentService.changeStatus(responseFinancialDocumentStatusDto);
    }

    @PostMapping("/CreateNumber")
    public ResponseEntity<String> creatNumber(@RequestBody FinancialDocumentNumberDto financialDocumentNumberDto) {
        String result;
        result = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/CreateNumberUpdate")
    public ResponseEntity<String> creatNumberUpdate(@RequestBody FinancialDocumentNumberDto financialDocumentNumberDto) {
        String result;
        result = financialDocumentService.creatDocumentNumberUpdate(financialDocumentNumberDto);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/ChangeDescription")
    public ResponseEntity<FinancialDocumentAccountMessageDto> changeDescription(@RequestBody FinancialDocumentChangeDescriptionDto financialDocumentDto) {
        String result;
        FinancialDocumentAccountMessageDto financialDocumentAccountMessageDto = new FinancialDocumentAccountMessageDto();
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
    public ResponseEntity<FinancialDocumentAccountMessageDto> changeAccount(@RequestBody FinancialDocumentAccountDto financialDocumentAccountDto) {
        String result;
        FinancialDocumentAccountMessageDto financialDocumentAccountMessageDto = new FinancialDocumentAccountMessageDto();
        result = financialDocumentService.changeAccountDocument(financialDocumentAccountDto);
        financialDocumentAccountMessageDto.setMessage(result);
        return ResponseEntity.ok(financialDocumentAccountMessageDto);

    }

    @PostMapping("/ChangeCentricAccount")
    public ResponseEntity<FinancialDocumentAccountMessageDto> changeCentricAccount(@RequestBody FinancialCentricAccountDto financialCentricAccountDto) {
        String result;
        FinancialDocumentAccountMessageDto financialDocumentAccountMessageDto = new FinancialDocumentAccountMessageDto();
        result = financialDocumentService.changeCentricAccount(financialCentricAccountDto);
        financialDocumentAccountMessageDto.setMessage(result);
        return ResponseEntity.ok(financialDocumentAccountMessageDto);
    }

    @PostMapping("/ChangeAmount")
    public ResponseEntity<Boolean> changeAmount(@RequestBody FinancialCentricAccountDto financialCentricAccountDto) {
        Boolean result;
        result = financialDocumentService.changeAmountDocument(financialCentricAccountDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/SetAmount")
    public ResponseEntity<Boolean> setAmount(@RequestBody FinancialCentricAccountDto financialCentricAccountDto) {
        Boolean result;
        result = financialDocumentService.setAmountDocument(financialCentricAccountDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/Save")
    public ResponseEntity<FinancialDocumentSaveDto> saveFinancialDocument(@RequestBody FinancialDocumentSaveDto requestFinancialDocumentSaveDto) {
        if (requestFinancialDocumentSaveDto.getFinancialDocumentId() == null) {
            return ResponseEntity.ok(saveFinancialDocumentService.saveDocument(requestFinancialDocumentSaveDto));
        } else {
            return ResponseEntity.ok(saveFinancialDocumentService.updateDocument(requestFinancialDocumentSaveDto));
        }
    }

    @PostMapping("/ArrangeSequence")
    public ResponseEntity<Boolean> arrangeSequence(@RequestBody FinancialDocumentDto financialDocumentDto) {
        boolean result;
        result = financialDocumentService.setArrangeSequence(financialDocumentDto);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/TransferDocument")
    public ResponseEntity<FinancialDocumentTransferOutputResponse> transferDocument(@RequestBody FinancialDocumentTransferRequest financialDocumentTransferRequest) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        financialDocumentTransferRequest.setOrganizationId(organizationId);
        return ResponseEntity.ok(transferFinancialDocumentService.transferDocument(financialDocumentTransferRequest));
    }

    private LocalDateTime parseStringToLocalDateTime(Object input, boolean truncateDate) {
        if (input instanceof String) {
            return checkTry(input, truncateDate);
        } else if (input instanceof LocalDateTime) {
            return truncateDate ? DateUtil.truncate((LocalDateTime) input) : (LocalDateTime) input;
        } else {
            throw new IllegalArgumentException("Filter for LocalDateTime has error :" + input + " with class" + input.getClass());
        }
    }

    private LocalDateTime checkTry(Object input, boolean truncateDate) {
        try {
            Date date = ISO8601Utils.parse((String) input, new ParsePosition(0));
            LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return truncateDate ? DateUtil.truncate(localDateTime) : localDateTime;
        } catch (ParseException var4) {
            if (((String) input).equalsIgnoreCase("current_date")) {
                return truncateDate ? DateUtil.truncate(LocalDateTime.now()) : LocalDateTime.now();
            } else {
                return ((String) input).equalsIgnoreCase("current_timestamp") ? LocalDateTime.now() : LocalDateTime.parse((String) input);
            }
        }
    }

    @PostMapping("/Get")
    public ResponseEntity<FinancialDocumentSaveDto> getFinancialDocumentInfo(@RequestBody FinancialDocumentDto financialDocumentDto) {
        return ResponseEntity.ok(saveFinancialDocumentService.getFinancialDocumentInfo(financialDocumentDto));
    }


    @PostMapping("/DocumentByStructure")
    public ResponseEntity<DataSourceResult> responseDocumentByStructure(@RequestBody DataSourceRequest dataSourceRequest) {
        return ResponseEntity.ok(financialDocumentService.documentByStructure(dataSourceRequest));
    }

    @PostMapping("/GetStructure")
    public ResponseEntity<List<ResponseFinancialDocumentStructureDto>> responseDocumentByStructure(@RequestBody RequestDocumentStructureDto requestDocumentStructureDto) {
        return ResponseEntity.ok(financialDocumentService.getDocumentStructure(requestDocumentStructureDto));
    }

    @PostMapping("/GetPeriodLedgerStatus")
    public ResponseEntity<FinancialPeriodStatusResponse> responseEntity(@RequestBody FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest) {
        return ResponseEntity.ok(financialDocumentService.getFinancialPeriodStatus(financialPeriodLedgerStatusRequest));
    }
}
