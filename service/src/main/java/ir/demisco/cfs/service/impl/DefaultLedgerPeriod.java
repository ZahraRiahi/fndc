package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.CheckLedgerPermissionInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerCloseMonthInputRequest;
import ir.demisco.cfs.model.dto.request.GetLedgerPeriodMonthStatusRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberDto;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodMonthStatusService;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodSecurityService;
import ir.demisco.cfs.service.api.LedgerPeriodService;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerMonthRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class DefaultLedgerPeriod implements LedgerPeriodService {
    private final FinancialPeriodRepository financialPeriodRepository;
    private final FinancialLedgerMonthRepository financialLedgerMonthRepository;
    private final FinancialLedgerPeriodSecurityService financialLedgerPeriodSecurityService;
    private final FinancialLedgerPeriodMonthStatusService financialLedgerPeriodMonthStatusService;
    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialDocumentService financialDocumentService;
    private final EntityManager entityManager;

    public DefaultLedgerPeriod(FinancialPeriodRepository financialPeriodRepository, FinancialLedgerMonthRepository financialLedgerMonthRepository, FinancialLedgerPeriodSecurityService financialLedgerPeriodSecurityService, FinancialLedgerPeriodMonthStatusService financialLedgerPeriodMonthStatusService, FinancialDocumentRepository financialDocumentRepository, FinancialDocumentService financialDocumentService, EntityManager entityManager) {
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialLedgerMonthRepository = financialLedgerMonthRepository;
        this.financialLedgerPeriodSecurityService = financialLedgerPeriodSecurityService;
        this.financialLedgerPeriodMonthStatusService = financialLedgerPeriodMonthStatusService;
        this.financialDocumentRepository = financialDocumentRepository;
        this.financialDocumentService = financialDocumentService;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean closeMonth(FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest) {
        Long financialPeriod = financialPeriodRepository.getFinancialPeriodByIdAndStatus(financialLedgerCloseMonthInputRequest.getFinancialPeriodId());
        if (financialPeriod != null) {
            throw new RuleException("وضعیت دوره مالی در حالت بسته میباشد");
        }
        Long financialLedgerMonth = financialLedgerMonthRepository.getFinancialLedgerMonthById(financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId());
        if (financialLedgerMonth != null) {
            throw new RuleException("ماه عملیاتی در وضعیت دائم شده (بسته) میباشد");
        }
        CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest = new CheckLedgerPermissionInputRequest();
        checkLedgerPermissionInputRequest.setPeriodId(financialLedgerCloseMonthInputRequest.getFinancialPeriodId());
        checkLedgerPermissionInputRequest.setLedgerTypeId(financialLedgerCloseMonthInputRequest.getFinancialLedgerTypeId());
        checkLedgerPermissionInputRequest.setActivityCode("FINANCIAL_LEG_MONTH");
        financialLedgerPeriodSecurityService.checkFinancialLedgerPeriodSecurity(checkLedgerPermissionInputRequest);

        GetLedgerPeriodMonthStatusRequest getLedgerPeriodMonthStatusRequest = new GetLedgerPeriodMonthStatusRequest();
        getLedgerPeriodMonthStatusRequest.setNextPrevMonth(-1L);
        getLedgerPeriodMonthStatusRequest.setCheckOtherPeriods(1L);
        getLedgerPeriodMonthStatusRequest.setFinancialLedgerMonthId(financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId());
        getLedgerPeriodMonthStatusRequest.setFinancialLedgerPeriodId(financialLedgerCloseMonthInputRequest.getFinancialLedgerPeriodId());
        Long ledgerPeriodMonthStatus = financialLedgerPeriodMonthStatusService.getLedgerPeriodMonthStatus(getLedgerPeriodMonthStatusRequest);
        if (ledgerPeriodMonthStatus == 2) {
            throw new RuleException("وضعیت ماه قبل باز است و امکان انجام عملیات وجود ندارد");
        }
        List<Long> financialDocumentIdList = financialDocumentRepository.findByListFinancialDocumentId(financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId(),
                SecurityHelper.getCurrentUser().getOrganizationId());
        List<Long> financialDocumentIdListStatus = financialDocumentRepository.getFinancialDocumentListId(financialDocumentIdList);
        if (financialDocumentIdListStatus != null) {
            throw new RuleException("تمامی اسناد میبایست در وضعیت قطعی باشد");
        }
        financialDocumentIdList.forEach((Long e) -> {
            Long financialDocumentNumber = financialDocumentRepository.getFinancialDocumentByNumberAndId(e.longValue());
            if (financialDocumentNumber != null) {
                throw new RuleException("شماره دائم برای سند / سند هایی از قبل وجود دارد");
            }
            FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
            financialDocumentNumberDto.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
            financialDocumentNumberDto.setFinancialDocumentId(e.longValue());
            financialDocumentNumberDto.setNumberingType(3L);
            String newNumber = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);

            entityManager.createNativeQuery(" update fndc.financial_document T" +
                    "   set   T.PERMANENT_DOCUMENT_NUMBER = :newNumber " +
                    "   WHERE T.id = :financialDocumentId ").setParameter("newNumber", newNumber)
                    .setParameter("financialDocumentId", financialDocumentNumberDto.getFinancialDocumentId())
                    .executeUpdate();
        });

        entityManager.createNativeQuery(" update fndc.financial_ledger_month lm" +
                "   set   lm.FIN_LEDGER_MONTH_STAT_ID = 2 " +
                "   WHERE lm.id = :financialLedgerMonthId ")
                .setParameter("financialLedgerMonthId", financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId())
                .executeUpdate();

        entityManager.createNativeQuery(" update fndc.financial_ledger_period lp" +
                "   set   lp.FIN_LEDGER_PERIOD_STAT_ID = 2 " +
                "   WHERE lp.id = :financialLedgerPeriodId " +
                " and not exists ( select 1 from fndc.financial_ledger_month lm " +
                " where lm.FINANCIAL_LEDGER_PERIOD_ID = lp.id" +
                " and lm.FIN_LEDGER_MONTH_STAT_ID = 1)")
                .setParameter("financialLedgerPeriodId", financialLedgerCloseMonthInputRequest.getFinancialLedgerPeriodId())
                .executeUpdate();
        return true;
    }
}
