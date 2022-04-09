package ir.demisco.cfs.service.api;

import ir.demisco.cfs.model.dto.request.FinancialPeriodLedgerStatusRequest;
import ir.demisco.cfs.model.dto.response.FinancialCentricAccountDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentAccountDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentChangeDescriptionDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodStatusResponse;
import ir.demisco.cfs.model.dto.response.RequestDocumentStructureDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentSetStatusDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentStatusDto;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentStructureDto;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FinancialDocumentService {


    DataSourceResult getFinancialDocumentList(DataSourceRequest dataSourceRequest);

    ResponseEntity<ResponseFinancialDocumentSetStatusDto> changeStatus(ResponseFinancialDocumentStatusDto responseFinancialDocumentStatusDto);

    String creatDocumentNumber(FinancialDocumentNumberDto financialDocumentNumberDto);

    String changeDescription(FinancialDocumentChangeDescriptionDto financialDocumentDto);

    boolean deleteFinancialDocumentById(Long financialDocumentId);

    String changeAccountDocument(FinancialDocumentAccountDto financialDocumentAccountDto);

    String changeCentricAccount(FinancialCentricAccountDto financialCentricAccountDto);

    Boolean changeAmountDocument(FinancialCentricAccountDto financialCentricAccountDto);

    Boolean setAmountDocument(FinancialCentricAccountDto financialCentricAccountDto);

    Boolean setArrangeSequence(FinancialDocumentDto financialDocumentDto);

    DataSourceResult documentByStructure(DataSourceRequest dataSourceRequest);

    List<ResponseFinancialDocumentStructureDto> getDocumentStructure(RequestDocumentStructureDto requestDocumentStructureDto);

    FinancialPeriodStatusResponse getFinancialPeriodStatus(FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest);

}
