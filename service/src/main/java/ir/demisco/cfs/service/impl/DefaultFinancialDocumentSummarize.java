package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDocumentSummarizeRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentSummarizeResponse;
import ir.demisco.cfs.service.api.FinancialDocumentSummarizeService;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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

        FinancialDocumentSummarizeResponse financialDocumentSummarizeResponse = financialDocumentItemListObject.stream().map(e -> FinancialDocumentSummarizeResponse.builder().recordsCount(Long.parseLong(e[0].toString()))
                .sumDebitAmount(Double.valueOf(e[1].toString()).longValue())
                .sumCreditAmount(Double.valueOf(e[2].toString()).longValue())
                .remainAmount(Double.valueOf(e[3].toString()).longValue())
                .build()).findAny().get();

        if (!financialDocumentSummarizeRequest.getFinancialDocumentItems().isEmpty()) {
            List<Object[]> financialDocumentIdList = financialDocumentItemRepository.findFinancialDocumentItemByFinancialDocumentIdList(financialDocumentSummarizeRequest.getFinancialDocumentId(), financialDocumentSummarizeRequest.getFinancialDocumentItems());
            financialDocumentIdList.forEach(e -> {
                financialDocumentSummarizeResponse.setSelectedRecordsCount(Long.parseLong(e[0].toString()));
                financialDocumentSummarizeResponse.setSelectedSumDebitAmount(Double.valueOf(e[1].toString()).longValue());
                financialDocumentSummarizeResponse.setSelectedSumCreditAmount(Double.valueOf(e[2].toString()).longValue());
                financialDocumentSummarizeResponse.setSelectedRemainAmount(Double.valueOf(e[3].toString()).longValue());
            });

        }
        return financialDocumentSummarizeResponse;
    }


}
