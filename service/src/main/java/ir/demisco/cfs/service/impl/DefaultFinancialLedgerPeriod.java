package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialLedgerPeriodRequest;
import ir.demisco.cfs.model.entity.FinancialLedgerMonth;
import ir.demisco.cfs.model.entity.FinancialLedgerPeriod;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodService;
import ir.demisco.cfs.service.repository.FinancialLedgerMonthRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerMonthStatusRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerPeriodRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerPeriodStatusRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cfs.service.repository.FinancialMonthRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DefaultFinancialLedgerPeriod implements FinancialLedgerPeriodService {
    private final FinancialLedgerPeriodRepository financialLedgerPeriodRepository;
    private final FinancialLedgerPeriodStatusRepository financialLedgerPeriodStatusRepository;
    private final FinancialPeriodRepository financialPeriodRepository;
    private final FinancialLedgerTypeRepository financialLedgerTypeRepository;
    private final FinancialMonthRepository financialMonthRepository;
    private  final FinancialLedgerMonthStatusRepository financialLedgerMonthStatusRepository;
    private final FinancialLedgerMonthRepository financialLedgerMonthRepository;

    public DefaultFinancialLedgerPeriod(FinancialLedgerPeriodRepository financialLedgerPeriodRepository, FinancialLedgerPeriodStatusRepository financialLedgerPeriodStatusRepository, FinancialPeriodRepository financialPeriodRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository, FinancialMonthRepository financialMonthRepository, FinancialLedgerMonthStatusRepository financialLedgerMonthStatusRepository, FinancialLedgerMonthRepository financialLedgerMonthRepository) {
        this.financialLedgerPeriodRepository = financialLedgerPeriodRepository;
        this.financialLedgerPeriodStatusRepository = financialLedgerPeriodStatusRepository;
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialMonthRepository = financialMonthRepository;
        this.financialLedgerMonthStatusRepository = financialLedgerMonthStatusRepository;
        this.financialLedgerMonthRepository = financialLedgerMonthRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean saveFinancialLedgerPeriod(FinancialLedgerPeriodRequest financialLedgerPeriodRequest) {
        if (financialLedgerPeriodRequest.getFinancialPeriodId() == null) {
            throw new RuleException("دوره ی مالی را مشخص نمایید.");
        }
        if (financialLedgerPeriodRequest.getFinancialLedgerTypeId() == null) {
            throw new RuleException("دفتر مالی را مشخص نمایید.");
        }
        FinancialLedgerPeriod financialLedgerPeriod = financialLedgerPeriodRepository.findById(financialLedgerPeriodRequest.getId() == null ? 0 : financialLedgerPeriodRequest.getId()).orElse(new FinancialLedgerPeriod());
        financialLedgerPeriod.setFinancialLedgerPeriodStatus(financialLedgerPeriodStatusRepository.getOne(1L));
        financialLedgerPeriod.setFinancialPeriod(financialPeriodRepository.getOne(financialLedgerPeriodRequest.getFinancialPeriodId()));
        financialLedgerPeriod.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialLedgerPeriodRequest.getFinancialLedgerTypeId()));
        financialLedgerPeriod.setFinancialDocumentOpening(null);
        financialLedgerPeriod.setFinancialDocumentTemprory(null);
        financialLedgerPeriod.setFinancialDocumentPermanent(null);
        financialLedgerPeriodRepository.save(financialLedgerPeriod);

        List<Long> financialMonth = financialMonthRepository.findByFinancialMonth(financialLedgerPeriodRequest.getFinancialPeriodId());

        financialMonth.forEach((Long e) -> {
            FinancialLedgerMonth financialLedgerMonth = new FinancialLedgerMonth();
            financialLedgerMonth.setFinancialLedgerMonthStatus(financialLedgerMonthStatusRepository.getOne(1L));
            financialLedgerMonth.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialLedgerPeriodRequest.getFinancialLedgerTypeId()));
            financialLedgerMonth.setFinancialMonth(financialMonthRepository.getOne(e));
            financialLedgerMonth.setFinancialLedgerPeriod(financialLedgerPeriodRepository.getOne(financialLedgerPeriod.getId()));
             financialLedgerMonthRepository.save(financialLedgerMonth);

        });


        return true;
    }
}
