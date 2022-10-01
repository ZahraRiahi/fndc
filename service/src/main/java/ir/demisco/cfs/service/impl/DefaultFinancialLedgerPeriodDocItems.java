package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.GetDocumentItemsForLedgerInputRequest;
import ir.demisco.cfs.model.dto.response.FinancialLedgerClosingTempOutputResponse;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodDocItemsService;
import ir.demisco.cfs.service.repository.FinancialLedgerPeriodDocItemsRepository;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialLedgerPeriodDocItems implements FinancialLedgerPeriodDocItemsService {
    private final FinancialLedgerPeriodDocItemsRepository financialLedgerPeriodDocItemsRepository;

    public DefaultFinancialLedgerPeriodDocItems(FinancialLedgerPeriodDocItemsRepository financialLedgerPeriodDocItemsRepository) {
        this.financialLedgerPeriodDocItemsRepository = financialLedgerPeriodDocItemsRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public List<FinancialLedgerClosingTempOutputResponse> getFinancialLedgerPeriodDocItems(GetDocumentItemsForLedgerInputRequest getDocumentItemsForLedgerInputRequest) {
        List<Object[]> financialLedgerPeriodDocItems = financialLedgerPeriodDocItemsRepository.findByFinancialDocumentItemByIdAndFinancialDocumentId(getDocumentItemsForLedgerInputRequest.getPermanentStatus(),
                SecurityHelper.getCurrentUser().getOrganizationId(), getDocumentItemsForLedgerInputRequest.getFinancialLedgerTypeId(),
                getDocumentItemsForLedgerInputRequest.getFinancialPeriodId(), getDocumentItemsForLedgerInputRequest.getFinancialPeriodDes());

        return financialLedgerPeriodDocItems.stream().map(objects -> FinancialLedgerClosingTempOutputResponse.builder().sequence(objects[0] == null ? 0 : Long.parseLong(objects[0].toString()))
                .financialAccountId(objects[1] == null ? 0 : Long.parseLong(objects[1].toString()))
                .centricAccountId1(objects[2] == null ? 0 : Long.parseLong(objects[2].toString()))
                .centricAccountId2(objects[3] == null ? 0 : Long.parseLong(objects[3].toString()))
                .centricAccountId2(objects[4] == null ? 0 : Long.parseLong(objects[4].toString()))
                .centricAccountId2(objects[5] == null ? 0 : Long.parseLong(objects[5].toString()))
                .centricAccountId2(objects[6] == null ? 0 : Long.parseLong(objects[6].toString()))
                .remDebit(objects[7] == null ? 0 : Long.parseLong(objects[7].toString()))
                .remCredit(objects[8] == null ? 0 : Long.parseLong(objects[8].toString()))
                .docItemDes(objects[9] == null ? null : objects[9].toString())
                .build()).collect(Collectors.toList());
    }
}
