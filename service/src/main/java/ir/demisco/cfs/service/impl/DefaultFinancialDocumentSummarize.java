package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDocumentSummarizeRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentSummarizeResponse;
import ir.demisco.cfs.service.api.FinancialDocumentSummarizeService;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultFinancialDocumentSummarize implements FinancialDocumentSummarizeService {
    private final FinancialDocumentItemRepository financialDocumentItemRepository;

    public DefaultFinancialDocumentSummarize(FinancialDocumentItemRepository financialDocumentItemRepository) {
        this.financialDocumentItemRepository = financialDocumentItemRepository;
    }

    @Override
    @Transactional
    public FinancialDocumentSummarizeResponse getFinancialDocumentByFinancialDocumentId(FinancialDocumentSummarizeRequest financialDocumentSummarizeRequest) {
        List<Object[]> financialDocumentItemListObject = financialDocumentItemRepository.findFinancialDocumentItemByFinancialDocumentId(financialDocumentSummarizeRequest.getFinancialDocumentId());
        Optional<FinancialDocumentSummarizeResponse> financialDocumentSummarizeResponseStream = financialDocumentItemListObject.stream().map(e -> FinancialDocumentSummarizeResponse.builder()
                .recordsCount(e[0] == null ? 0 : Long.parseLong(e[0].toString()))
                .sumDebitAmount(e[1] == null ? 0 : Double.valueOf(e[1].toString()).longValue())
                .sumCreditAmount(e[2] == null ? 0 : Double.valueOf(e[2].toString()).longValue())
                .remainAmount(e[3] == null ? 0 : Double.valueOf(e[3].toString()).longValue())
                .build()).findAny();
        if(financialDocumentSummarizeResponseStream.isPresent()) {
            FinancialDocumentSummarizeResponse financialDocumentSummarizeResponse = financialDocumentSummarizeResponseStream.get();
            if (!financialDocumentSummarizeRequest.getFinancialDocumentItems().isEmpty()) {
                List<Object[]> financialDocumentIdList = financialDocumentItemRepository.findFinancialDocumentItemByFinancialDocumentIdList(financialDocumentSummarizeRequest.getFinancialDocumentId(), financialDocumentSummarizeRequest.getFinancialDocumentItems());
                financialDocumentIdList.forEach((Object[] e) -> {
                    financialDocumentSummarizeResponse.setSelectedRecordsCount(Long.parseLong(e[0].toString()));
                    financialDocumentSummarizeResponse.setSelectedSumDebitAmount(Double.valueOf(e[1].toString()).longValue());
                    financialDocumentSummarizeResponse.setSelectedSumCreditAmount(Double.valueOf(e[2].toString()).longValue());
                    financialDocumentSummarizeResponse.setSelectedRemainAmount(Double.valueOf(e[3].toString()).longValue());
                });

            }
            return financialDocumentSummarizeResponse;
        }
        else
        {
            throw  new RuleException("fin.financialAccount.notInformation");
        }
    }


}
