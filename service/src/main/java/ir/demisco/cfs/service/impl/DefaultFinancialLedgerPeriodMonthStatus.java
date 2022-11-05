package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.GetLedgerPeriodMonthStatusRequest;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodMonthStatusService;
import ir.demisco.cfs.service.repository.FinancialLedgerMonthRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerPeriodMonthStatusRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerPeriodRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class DefaultFinancialLedgerPeriodMonthStatus implements FinancialLedgerPeriodMonthStatusService {
    private final FinancialLedgerPeriodMonthStatusRepository financialLedgerPeriodMonthStatusRepository;
    private final FinancialLedgerPeriodRepository financialLedgerPeriodRepository;
    private final FinancialLedgerMonthRepository financialLedgerMonthRepository;

    public DefaultFinancialLedgerPeriodMonthStatus(FinancialLedgerPeriodMonthStatusRepository financialLedgerPeriodMonthStatusRepository, FinancialLedgerPeriodRepository financialLedgerPeriodRepository, FinancialLedgerMonthRepository financialLedgerMonthRepository) {
        this.financialLedgerPeriodMonthStatusRepository = financialLedgerPeriodMonthStatusRepository;
        this.financialLedgerPeriodRepository = financialLedgerPeriodRepository;
        this.financialLedgerMonthRepository = financialLedgerMonthRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Long getLedgerPeriodMonthStatus(GetLedgerPeriodMonthStatusRequest getLedgerPeriodMonthStatusRequest) {
        List<Object[]> list = financialLedgerPeriodMonthStatusRepository.getFinancialLedgerPeriodMonthStatusList(getLedgerPeriodMonthStatusRequest.getFinancialLedgerMonthId()
                , getLedgerPeriodMonthStatusRequest.getFinancialLedgerPeriodId());
        Long statusId = null;
        if (!list.isEmpty()) {
            if ((getLedgerPeriodMonthStatusRequest.getCheckOtherPeriods() == 1 && (Long.parseLong(list.get(0)[0].toString()) == 1 && getLedgerPeriodMonthStatusRequest.getNextPrevMonth() == -1))
                    || (Long.parseLong(list.get(0)[0].toString()) == 12) && getLedgerPeriodMonthStatusRequest.getNextPrevMonth() == 1) {
                statusId = financialLedgerPeriodRepository.getFinancialLedgerPeriodByLedgerAndNextAndDate(Long.parseLong(list.get(0)[3].toString()),
                        getLedgerPeriodMonthStatusRequest.getNextPrevMonth(), (Date) list.get(0)[1], (Date) list.get(0)[2]);

            }
            if (((Long.parseLong(list.get(0)[0].toString()) > 1 && Long.parseLong(list.get(0)[0].toString()) < 12) || getLedgerPeriodMonthStatusRequest.getCheckOtherPeriods() == 0)
                    || ((getLedgerPeriodMonthStatusRequest.getCheckOtherPeriods() == 1 && (Long.parseLong(list.get(0)[0].toString()) == 1 && getLedgerPeriodMonthStatusRequest.getNextPrevMonth() == 1))
                    || (Long.parseLong(list.get(0)[0].toString()) == 12) && getLedgerPeriodMonthStatusRequest.getNextPrevMonth() == -1)) {
                statusId = financialLedgerMonthRepository.getLedgerMonthByLedgerPeriodAndPrevMonth(getLedgerPeriodMonthStatusRequest.getFinancialLedgerPeriodId(),
                        getLedgerPeriodMonthStatusRequest.getNextPrevMonth(), getLedgerPeriodMonthStatusRequest.getFinancialLedgerMonthId());
            }
        }
        if (statusId != null) {
            return statusId;
        } else {
            return 1L;
        }
    }
}
