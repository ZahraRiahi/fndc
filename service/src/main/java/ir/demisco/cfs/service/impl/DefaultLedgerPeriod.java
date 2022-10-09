package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.CheckLedgerPermissionInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerCloseMonthInputRequest;
import ir.demisco.cfs.model.dto.request.GetLedgerPeriodMonthStatusRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberDto;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodMonthStatusService;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodSecurityService;
import ir.demisco.cfs.service.api.LedgerPeriodService;
import ir.demisco.cfs.service.repository.FinancialDocumentNumberRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerMonthRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerPeriodRepository;
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
    private final FinancialLedgerPeriodRepository financialLedgerPeriodRepository;
    private final FinancialDocumentNumberRepository financialDocumentNumberRepository;

    public DefaultLedgerPeriod(FinancialPeriodRepository financialPeriodRepository, FinancialLedgerMonthRepository financialLedgerMonthRepository, FinancialLedgerPeriodSecurityService financialLedgerPeriodSecurityService, FinancialLedgerPeriodMonthStatusService financialLedgerPeriodMonthStatusService, FinancialDocumentRepository financialDocumentRepository, FinancialDocumentService financialDocumentService, EntityManager entityManager, FinancialLedgerPeriodRepository financialLedgerPeriodRepository, FinancialDocumentNumberRepository financialDocumentNumberRepository) {
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialLedgerMonthRepository = financialLedgerMonthRepository;
        this.financialLedgerPeriodSecurityService = financialLedgerPeriodSecurityService;
        this.financialLedgerPeriodMonthStatusService = financialLedgerPeriodMonthStatusService;
        this.financialDocumentRepository = financialDocumentRepository;
        this.financialDocumentService = financialDocumentService;
        this.entityManager = entityManager;
        this.financialLedgerPeriodRepository = financialLedgerPeriodRepository;
        this.financialDocumentNumberRepository = financialDocumentNumberRepository;
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

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean openMonth(FinancialLedgerCloseMonthInputRequest financialLedgerCloseMonthInputRequest) {
        Long financialPeriod = financialPeriodRepository.getFinancialPeriodByIdAndStatus(financialLedgerCloseMonthInputRequest.getFinancialPeriodId());
        if (financialPeriod != null) {
            throw new RuleException("وضعیت دوره مالی در حالت بسته میباشد");
        }
        Long financialLedgerMonth = financialLedgerMonthRepository.getOpenFinancialLedgerMonthById(financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId());
        if (financialLedgerMonth != null) {
            throw new RuleException("وضعیت ماه انتخاب شده در  حال حاضر باز است");
        }
        Long openFinancialLedgerMonth = financialLedgerMonthRepository.getOpenFinancialLedgerMonthByIdAndPeriodId(financialLedgerCloseMonthInputRequest.getFinancialLedgerPeriodId(),
                financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId(), financialLedgerCloseMonthInputRequest.getFinancialPeriodId());
        if (openFinancialLedgerMonth != null) {
            throw new RuleException("تعداد ماه باز نمیتواند از حداکثر تعداد ماه باز دوره بیشتر باشد.امکان انجام عملیات وجود ندارد");
        }
        Long financialLedgerPeriod = financialLedgerPeriodRepository.getFinancialLedgerPeriodById(financialLedgerCloseMonthInputRequest.getFinancialLedgerPeriodId());
        if (financialLedgerPeriod != null) {
            throw new RuleException("سند بستن حسابهای موقت / دائم ثبت شده است");
        }
        CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest = new CheckLedgerPermissionInputRequest();
        checkLedgerPermissionInputRequest.setPeriodId(financialLedgerCloseMonthInputRequest.getFinancialPeriodId());
        checkLedgerPermissionInputRequest.setLedgerTypeId(financialLedgerCloseMonthInputRequest.getFinancialLedgerTypeId());
        checkLedgerPermissionInputRequest.setActivityCode("FINANCIAL_LEG_MONTH");

        GetLedgerPeriodMonthStatusRequest getLedgerPeriodMonthStatusRequest = new GetLedgerPeriodMonthStatusRequest();
        getLedgerPeriodMonthStatusRequest.setNextPrevMonth(1L);
        getLedgerPeriodMonthStatusRequest.setCheckOtherPeriods(1L);
        getLedgerPeriodMonthStatusRequest.setFinancialLedgerMonthId(financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId());
        getLedgerPeriodMonthStatusRequest.setFinancialLedgerPeriodId(financialLedgerCloseMonthInputRequest.getFinancialLedgerPeriodId());
        Long ledgerPeriodMonthStatus = financialLedgerPeriodMonthStatusService.getLedgerPeriodMonthStatus(getLedgerPeriodMonthStatusRequest);
        if (ledgerPeriodMonthStatus == 2) {
            throw new RuleException("وضعیت ماه قبل بسته است و امکان انجام عملیات وجود ندارد");
        }

        financialDocumentNumberRepository.getFinancialDocumentNumberByOrgAndPeriodId(financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId(),
                SecurityHelper.getCurrentUser().getOrganizationId(), financialLedgerCloseMonthInputRequest.getFinancialLedgerTypeId(),
                financialLedgerCloseMonthInputRequest.getFinancialPeriodId())
                .forEach(financialDocumentNumberRepository::delete);

        entityManager.createNativeQuery(" UPDATE FNDC.FINANCIAL_DOCUMENT DOC " +
                "   SET DOC.PERMANENT_DOCUMENT_NUMBER = NULL " +
                " WHERE DOC.ID IN " +
                "       (SELECT FD.ID " +
                "          FROM FNDC.FINANCIAL_DOCUMENT FD " +
                "         INNER JOIN FNDC.FINANCIAL_LEDGER_MONTH LM " +
                "            ON LM.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
                "           AND LM.ID = :financialLedgerMonthId " +
                "         INNER JOIN FNDC.FINANCIAL_LEDGER_PERIOD LP " +
                "            ON LP.ID = LM.FINANCIAL_LEDGER_PERIOD_ID " +
                "           AND LP.FINANCIAL_PERIOD_ID = FD.FINANCIAL_PERIOD_ID " +
                "           AND LP.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
                "         INNER JOIN FNDC.FINANCIAL_LEDGER_TYPE LT " +
                "            ON LT.ID = FD.FINANCIAL_LEDGER_TYPE_ID " +
                "           AND LT.ORGANIZATION_ID = :organizationId " +
                "         INNER JOIN FNPR.FINANCIAL_MONTH FM " +
                "            ON FM.ID = LM.FINANCIAL_MONTH_ID " +
                "         WHERE FD.ORGANIZATION_ID = LT.ORGANIZATION_ID " +
                "           AND FD.DOCUMENT_DATE BETWEEN FM.START_DATE AND FM.END_DATE " +
                "           AND FD.FINANCIAL_LEDGER_TYPE_ID =:financialLedgerTypeId " +
                "           AND FD.FINANCIAL_PERIOD_ID = :financialPeriodId) ").setParameter("financialLedgerMonthId", financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId())
                .setParameter("organizationId", SecurityHelper.getCurrentUser().getOrganizationId())
                .setParameter("financialLedgerTypeId", financialLedgerCloseMonthInputRequest.getFinancialLedgerTypeId())
                .setParameter("financialPeriodId", financialLedgerCloseMonthInputRequest.getFinancialPeriodId())
                .executeUpdate();
        entityManager.createNativeQuery(" UPDATE FNDC.FINANCIAL_LEDGER_PERIOD FP " +
                "   SET FP.FIN_LEDGER_PERIOD_STAT_ID = 1 " +
                " WHERE FP.ID = :financialLedgerPeriodId " +
                "   AND FP.FIN_LEDGER_PERIOD_STAT_ID = 2 ")
                .setParameter("financialLedgerPeriodId", financialLedgerCloseMonthInputRequest.getFinancialLedgerPeriodId())
                .executeUpdate();

        entityManager.createNativeQuery("  UPDATE FNDC.FINANCIAL_LEDGER_MONTH LM " +
                "   SET LM.FIN_LEDGER_MONTH_STAT_ID = 1 " +
                " WHERE LM.ID = :financialLedgerMonthId ")
                .setParameter("financialLedgerMonthId", financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId())
                .executeUpdate();

        return true;

    }
}
