package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.CheckLedgerPermissionInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerCloseMonthInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerClosingTempInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialLedgerClosingTempRequest;
import ir.demisco.cfs.model.dto.request.GetDocumentItemsForLedgerInputRequest;
import ir.demisco.cfs.model.dto.request.GetLedgerPeriodInputRequest;
import ir.demisco.cfs.model.dto.request.GetLedgerPeriodMonthStatusRequest;
import ir.demisco.cfs.model.dto.request.InsertLedgerPeriodInputRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberDto;
import ir.demisco.cfs.model.dto.response.FinancialLedgerClosingTempOutputResponse;
import ir.demisco.cfs.model.dto.response.GetLedgerPeriodOutputResponse;
import ir.demisco.cfs.model.dto.response.InsertLedgerPeriodMonthListOutputResponse;
import ir.demisco.cfs.model.entity.FinancialDocument;
import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import ir.demisco.cfs.model.entity.FinancialLedgerMonth;
import ir.demisco.cfs.model.entity.FinancialLedgerPeriod;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodDocItemsService;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodMonthStatusService;
import ir.demisco.cfs.service.api.FinancialLedgerPeriodSecurityService;
import ir.demisco.cfs.service.api.LedgerPeriodService;
import ir.demisco.cfs.service.repository.CentricAccountRepository;
import ir.demisco.cfs.service.repository.DepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialAccountRepository;
import ir.demisco.cfs.service.repository.FinancialDepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentNumberRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentStatusRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentTypeRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerMonthRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerMonthStatusRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerPeriodRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerPeriodStatusRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cfs.service.repository.FinancialMonthRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


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
    private final FinancialDocumentStatusRepository financialDocumentStatusRepository;
    private final OrganizationRepository organizationRepository;
    private final FinancialDocumentTypeRepository financialDocumentTypeRepository;
    private final FinancialLedgerTypeRepository financialLedgerTypeRepository;
    private final DepartmentRepository departmentRepository;
    private final FinancialDepartmentRepository financialDepartmentRepository;
    private final FinancialLedgerPeriodDocItemsService financialLedgerPeriodDocItemsService;
    private final FinancialAccountRepository financialAccountRepository;
    private final CentricAccountRepository centricAccountRepository;
    private final FinancialDocumentItemRepository financialDocumentItemRepository;
    private final FinancialLedgerPeriodStatusRepository financialLedgerPeriodStatusRepository;
    private final FinancialMonthRepository financialMonthRepository;
    private final FinancialLedgerMonthStatusRepository financialLedgerMonthStatusRepository;

    public DefaultLedgerPeriod(FinancialPeriodRepository financialPeriodRepository, FinancialLedgerMonthRepository financialLedgerMonthRepository, FinancialLedgerPeriodSecurityService financialLedgerPeriodSecurityService, FinancialLedgerPeriodMonthStatusService financialLedgerPeriodMonthStatusService, FinancialDocumentRepository financialDocumentRepository, FinancialDocumentService financialDocumentService, EntityManager entityManager, FinancialLedgerPeriodRepository financialLedgerPeriodRepository, FinancialDocumentNumberRepository financialDocumentNumberRepository, FinancialDocumentStatusRepository financialDocumentStatusRepository, OrganizationRepository organizationRepository, FinancialDocumentTypeRepository financialDocumentTypeRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository, DepartmentRepository departmentRepository, FinancialDepartmentRepository financialDepartmentRepository, FinancialLedgerPeriodDocItemsService financialLedgerPeriodDocItemsService, FinancialAccountRepository financialAccountRepository, CentricAccountRepository centricAccountRepository, FinancialDocumentItemRepository financialDocumentItemRepository, FinancialLedgerPeriodStatusRepository financialLedgerPeriodStatusRepository, FinancialMonthRepository financialMonthRepository, FinancialLedgerMonthStatusRepository financialLedgerMonthStatusRepository) {
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialLedgerMonthRepository = financialLedgerMonthRepository;
        this.financialLedgerPeriodSecurityService = financialLedgerPeriodSecurityService;
        this.financialLedgerPeriodMonthStatusService = financialLedgerPeriodMonthStatusService;
        this.financialDocumentRepository = financialDocumentRepository;
        this.financialDocumentService = financialDocumentService;
        this.entityManager = entityManager;
        this.financialLedgerPeriodRepository = financialLedgerPeriodRepository;
        this.financialDocumentNumberRepository = financialDocumentNumberRepository;
        this.financialDocumentStatusRepository = financialDocumentStatusRepository;
        this.organizationRepository = organizationRepository;
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.departmentRepository = departmentRepository;
        this.financialDepartmentRepository = financialDepartmentRepository;
        this.financialLedgerPeriodDocItemsService = financialLedgerPeriodDocItemsService;
        this.financialAccountRepository = financialAccountRepository;
        this.centricAccountRepository = centricAccountRepository;
        this.financialDocumentItemRepository = financialDocumentItemRepository;
        this.financialLedgerPeriodStatusRepository = financialLedgerPeriodStatusRepository;
        this.financialMonthRepository = financialMonthRepository;
        this.financialLedgerMonthStatusRepository = financialLedgerMonthStatusRepository;
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
        if (ledgerPeriodMonthStatus != 2) {
            throw new RuleException("وضعیت ماه قبل باز است و امکان انجام عملیات وجود ندارد");
        }
        List<Long> financialDocumentIdList = financialDocumentRepository.findByListFinancialDocumentId(financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId(),
                SecurityHelper.getCurrentUser().getOrganizationId());
        List<Long> financialDocumentIdListStatus = financialDocumentRepository.getFinancialDocumentListId(financialDocumentIdList);
        if (financialDocumentIdListStatus.size() != 0) {
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
            throw new RuleException("وضعیت ماه بعد بسته است و امکان انجام عملیات وجود ندارد");
        }


        String minDocNumber = financialDocumentRepository.findByDocumentIdAndLedgerMonth(financialLedgerCloseMonthInputRequest.getFinancialLedgerMonthId(),
                financialLedgerCloseMonthInputRequest.getFinancialLedgerPeriodId(), financialLedgerCloseMonthInputRequest.getFinancialPeriodId(),
                SecurityHelper.getCurrentUser().getOrganizationId());

        Long minDocId = financialDocumentRepository.findByDocumentIdAndLedgerMonthMinDocId(SecurityHelper.getCurrentUser().getOrganizationId(), financialLedgerCloseMonthInputRequest.getFinancialPeriodId()
                , financialLedgerCloseMonthInputRequest.getFinancialLedgerTypeId(), minDocNumber);

        entityManager.createNativeQuery(" UPDATE FNDC.NUMBERING_FORMAT_SERIAL NFS " +
                "      SET NFS.LAST_SERIAL = REPLACE((:minDocNumber)," +
                " NFS.SERIAL_RESETER, " +
                "               '') - 1  " +
                "  WHERE ID IN" +
                "       (SELECT NFS.ID" +
                "          FROM fndc.FINANCIAL_DOCUMENT FD" +
                "         INNER JOIN fndc.LEDGER_NUMBERING_TYPE LNT" +
                "            ON LNT.FINANCIAL_LEDGER_TYPE_ID = FD.FINANCIAL_LEDGER_TYPE_ID" +
                "           AND LNT.DELETED_DATE IS NULL" +
                "         INNER JOIN fndc.FINANCIAL_NUMBERING_TYPE NT" +
                "            ON NT.ID = LNT.FINANCIAL_NUMBERING_TYPE_ID" +
                "           AND NT.TYPE_STATUS = 3" +
                "           AND NT.DELETED_DATE IS NULL" +
                "         INNER JOIN fndc.FINANCIAL_NUMBERING_FORMAT NF" +
                "            ON NF.FINANCIAL_NUMBERING_TYPE_ID = NT.ID" +
                "           AND NF.ORGANIZATION_ID = :organizationId " +
                "           AND NF.DELETED_DATE IS NULL" +
                "         INNER JOIN fndc.FINANCIAL_NUMBERING_FORMAT_TYPE NFT" +
                "            ON NFT.ID = NF.NUMBERING_FORMAT_TYPE_ID" +
                "           AND NFT.DELETED_DATE IS NULL" +
                "          LEFT OUTER JOIN fndc.NUMBERING_FORMAT_SERIAL NFS" +
                "            ON NFS.NUMBERING_FORMAT_ID = NF.ID" +
                "           AND NFS.DELETED_DATE IS NULL" +
                "         WHERE FD.ID = :minDocId " +
                "           AND FD.DELETED_DATE IS NULL" +
                "           AND SERIAL_RESETER =" +
                "               REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(" +
                "                                                               " +
                "                                                               REPLACE(NFT.CODE," +
                "                                                                       '$SRL'," +
                "                                                                       '')" +
                "                                                              ," +
                "                                                               '$DAT6'," +
                "                                                               TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE," +
                "                                                                                       'mm/dd/yyyy')," +
                "                                                                               'mm/dd/yyyy')," +
                "                                                                       'yymmdd'," +
                "                                                                       'NLS_CALENDAR=persian'))," +
                "                                                       '$DAT'," +
                "                                                       TO_CHAR(TO_DATE(TO_CHAR(FD.DOCUMENT_DATE," +
                "                                                                               'mm/dd/yyyy')," +
                "                                                                       'mm/dd/yyyy')," +
                "                                                               'yyyymmdd'," +
                "                                                               'NLS_CALENDAR=persian'))," +
                "                                               '$LEG'," +
                "                                               (SELECT LT.CODE" +
                "                                                  FROM fndc.FINANCIAL_LEDGER_TYPE LT" +
                "                                                 WHERE LT.ID =" +
                "                                                       FD.FINANCIAL_LEDGER_TYPE_ID))," +
                "                                       '$DEP'," +
                "                                       (SELECT DP.CODE" +
                "                                          FROM ORG.DEPARTMENT DP" +
                "                                         WHERE DP.ID = FD.DEPARTMENT_ID))," +
                "                               '$ORG'," +
                "                               (SELECT OG.CODE" +
                "                                  FROM FNDC.FINANCIAL_ORGANIZATION OG" +
                "                                 WHERE OG.ORGANIZATION_ID = :organizationId))," +
                "                       '$PRI'," +
                "                       (SELECT PR.CODE" +
                "                          FROM FNPR.FINANCIAL_PERIOD PR" +
                "                         WHERE PR.ID = FD.FINANCIAL_PERIOD_ID))) "
        ).setParameter("minDocNumber", minDocNumber)
                .setParameter("organizationId", SecurityHelper.getCurrentUser().getOrganizationId())
                .setParameter("minDocId", minDocId)
                .executeUpdate();
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

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean closingTemp(FinancialLedgerClosingTempInputRequest financialLedgerClosingTempInputRequest) {
        Long financialPeriod = financialPeriodRepository.getFinancialPeriodByIdAndStatus(financialLedgerClosingTempInputRequest.getFinancialPeriodId());
        if (financialPeriod != null) {
            throw new RuleException("وضعیت دوره مالی در حالت بسته میباشد");
        }
        Long financialLedgerMonthPeriodId = financialLedgerMonthRepository.getFinancialLedgerMonthByLedgerPeriodId(financialLedgerClosingTempInputRequest.getFinancialLedgerPeriodId());
        if (financialLedgerMonthPeriodId != null) {
            throw new RuleException("تمامی ماه های عملیاتی این دوره میبایست در وضعیت بسته باشد");
        }
        Long financialLedgerPeriodId = financialLedgerPeriodRepository.getFinancialLedgerPeriodByIdClosingTemp(financialLedgerClosingTempInputRequest.getFinancialLedgerPeriodId());
        if (financialLedgerPeriodId != null) {
            throw new RuleException("سند بستن حسابهای موقت قبلا روی این دوره از دفتر مالی ثبت شده است");
        }
        CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest = new CheckLedgerPermissionInputRequest();
        checkLedgerPermissionInputRequest.setPeriodId(financialLedgerClosingTempInputRequest.getFinancialPeriodId());
        checkLedgerPermissionInputRequest.setLedgerTypeId(financialLedgerClosingTempInputRequest.getFinancialLedgerTypeId());
        checkLedgerPermissionInputRequest.setActivityCode("FINANCIAL_LEG_TEMPORARY");
        financialLedgerPeriodSecurityService.checkFinancialLedgerPeriodSecurity(checkLedgerPermissionInputRequest);

        List<Object[]> financialPeriodDateAndDes =
                financialPeriodRepository.getFinancialPeriodByDateAndDes(financialLedgerClosingTempInputRequest.getFinancialPeriodId());

        Random rand = new Random(System.currentTimeMillis());
        FinancialDocument financialDocumentSave = new FinancialDocument();
        financialDocumentSave.setDocumentDate(financialPeriodDateAndDes.get(0)[0] == null ? null : ((Timestamp) financialPeriodDateAndDes.get(0)[0]).toLocalDateTime());
        financialDocumentSave.setDescription(" سند بستن حسابهای سود و زیانی " + financialLedgerClosingTempInputRequest.getFinancialPeriodDes());
        financialDocumentSave.setFinancialDocumentStatus(financialDocumentStatusRepository.getOne(3L));
        financialDocumentSave.setPermanentDocumentNumber(null);
        financialDocumentSave.setAutomaticFlag(true);
        financialDocumentSave.setOrganization(organizationRepository.getOne(SecurityHelper.getCurrentUser().getOrganizationId()));
        financialDocumentSave.setFinancialDocumentType(financialDocumentTypeRepository.getOne(71L));
        financialDocumentSave.setFinancialPeriod(financialPeriodRepository.getOne(financialLedgerClosingTempInputRequest.getFinancialPeriodId()));
        financialDocumentSave.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialLedgerClosingTempInputRequest.getFinancialLedgerTypeId()));
        financialDocumentSave.setFinancialDepartment(financialDepartmentRepository.getOne(financialLedgerClosingTempInputRequest.getFinancialDepartmentId()));
        financialDocumentSave.setDocumentNumber("X" + rand);
        financialDocumentSave.setDepartment(departmentRepository.getOne(financialLedgerClosingTempInputRequest.getDepartmentId()));
        financialDocumentRepository.save(financialDocumentSave);
        FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
        financialDocumentNumberDto.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentNumberDto.setFinancialDocumentId(financialDocumentSave.getId());
        financialDocumentNumberDto.setNumberingType(1L);
        String documentNumberNew;
        documentNumberNew = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
        if (documentNumberNew == null) {
            throw new RuleException("اشکال در ایجاد شماره عطف");
        }
        entityManager.createNativeQuery(" Update fndc.FINANCIAL_DOCUMENT " +
                "   Set DOCUMENT_NUMBER = :newNumber " +
                " Where id = :newDocumentId ").setParameter("newNumber", documentNumberNew)
                .setParameter("newDocumentId", financialDocumentNumberDto.getFinancialDocumentId())
                .executeUpdate();
        financialDocumentNumberDto.setNumberingType(1L);
        documentNumberNew = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
        if (documentNumberNew == null) {
            throw new RuleException("اشکال در ایجاد شماره دائم");
        }

        entityManager.createNativeQuery(" Update fndc.FINANCIAL_DOCUMENT " +
                "   Set PERMANENT_DOCUMENT_NUMBER  = :newNumber " +
                " Where id = :newDocumentId ").setParameter("newNumber", documentNumberNew)
                .setParameter("newDocumentId", financialDocumentNumberDto.getFinancialDocumentId())
                .executeUpdate();
        GetDocumentItemsForLedgerInputRequest getDocumentItemsForLedgerInputRequest = new GetDocumentItemsForLedgerInputRequest();
        getDocumentItemsForLedgerInputRequest.setFinancialLedgerTypeId(financialLedgerClosingTempInputRequest.getFinancialLedgerTypeId());
        getDocumentItemsForLedgerInputRequest.setFinancialPeriodId(financialLedgerClosingTempInputRequest.getFinancialPeriodId());
        getDocumentItemsForLedgerInputRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        getDocumentItemsForLedgerInputRequest.setFinancialDepartmentId(financialLedgerClosingTempInputRequest.getFinancialDepartmentId());
        getDocumentItemsForLedgerInputRequest.setDepartmentId(financialLedgerClosingTempInputRequest.getDepartmentId());
        getDocumentItemsForLedgerInputRequest.setPermanentStatus(2L);
        getDocumentItemsForLedgerInputRequest.setFinancialPeriodDes(financialLedgerClosingTempInputRequest.getFinancialPeriodDes());

        List<FinancialLedgerClosingTempOutputResponse> getDocumentItemsForLedgerOutput = financialLedgerPeriodDocItemsService.getFinancialLedgerPeriodDocItems(getDocumentItemsForLedgerInputRequest);
        getDocumentItemsForLedgerOutput.forEach((FinancialLedgerClosingTempOutputResponse financialLedgerClosingTempOutputResponse) -> {
            FinancialDocumentItem financialDocumentItemSave = new FinancialDocumentItem();
            financialDocumentItemSave.setFinancialDocument(financialDocumentRepository.getOne(financialDocumentSave.getId()));
            financialDocumentItemSave.setSequenceNumber(financialLedgerClosingTempOutputResponse.getSequence());
            financialDocumentItemSave.setFinancialAccount(financialAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getFinancialAccountId()));
            financialDocumentItemSave.setCentricAccountId1(financialLedgerClosingTempOutputResponse.getCentricAccountId1() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId1()));
            financialDocumentItemSave.setCentricAccountId2(financialLedgerClosingTempOutputResponse.getCentricAccountId2() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId2()));
            financialDocumentItemSave.setCentricAccountId3(financialLedgerClosingTempOutputResponse.getCentricAccountId3() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId3()));
            financialDocumentItemSave.setCentricAccountId4(financialLedgerClosingTempOutputResponse.getCentricAccountId4() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId4()));
            financialDocumentItemSave.setCentricAccountId5(financialLedgerClosingTempOutputResponse.getCentricAccountId5() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId5()));
            financialDocumentItemSave.setCentricAccountId6(financialLedgerClosingTempOutputResponse.getCentricAccountId6() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId6()));
            financialDocumentItemSave.setCreditAmount(financialLedgerClosingTempOutputResponse.getRemDebit().doubleValue());
            financialDocumentItemSave.setDebitAmount(financialLedgerClosingTempOutputResponse.getRemCredit().doubleValue());
            financialDocumentItemSave.setDescription(financialLedgerClosingTempOutputResponse.getDocItemDes());
            financialDocumentItemRepository.save(financialDocumentItemSave);
        });

        entityManager.createNativeQuery(" Update FNDC.FINANCIAL_LEDGER_PERIOD LP " +
                "  SET LP.FINANCIAL_DOCUMENT_TEMPRORY_ID = :newDocId " +
                " WHERE LP.ID = :financialLedgerPeriodId " +
                "   AND LP.FINANCIAL_DOCUMENT_TEMPRORY_ID IS NULL ").setParameter("newDocId", financialDocumentSave.getId())
                .setParameter("financialLedgerPeriodId", financialLedgerClosingTempInputRequest.getFinancialLedgerPeriodId())
                .executeUpdate();
        return true;
    }

    private Long getItemForLong(Object[] financialDocumentItem, int i) {
        return financialDocumentItem[i] == null ? null : Long.parseLong(financialDocumentItem[i].toString());
    }

    private String getItemForString(Object[] item, int i) {
        return item[i] == null ? null : item[i].toString();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public DataSourceResult getLedgerPeriodMonthList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        InsertLedgerPeriodMonthListOutputResponse paramSearch = setParameter(filters);
        Pageable pageable = PageRequest.of((dataSourceRequest.getSkip() / dataSourceRequest.getTake()), dataSourceRequest.getTake());
        Page<Object[]> list = financialLedgerMonthRepository.getFinancialLedgerMonthList(paramSearch.getFinancialPeriodId(), paramSearch.getFinancialLedgerTypeId(),
                pageable);
        List<InsertLedgerPeriodMonthListOutputResponse> documentDtoList = list.stream().map(item ->
                InsertLedgerPeriodMonthListOutputResponse.builder()
                        .minDocTmpNumber(getItemForLong(item, 0))
                        .maxDocTmpNumber(getItemForLong(item, 1))
                        .minDocPrmNUmber(getItemForLong(item, 2))
                        .maxDocPrmNumber(getItemForLong(item, 3))
                        .financialPeriodId(getItemForLong(item, 4))
                        .financialLedgerTypeId(getItemForLong(item, 5))
                        .monthDescription(getItemForString(item, 6))
                        .monthStartDate((Date) item[7])
                        .endDateMonthEndDate((Date) item[8])
                        .financialLedgerMonthId(getItemForLong(item, 9))
                        .monthStatusId(getItemForLong(item, 10))
                        .monthStatusCode(getItemForString(item, 11))
                        .monthStatusDescription(getItemForString(item, 12))
                        .financialDocumentOpeningId(getItemForLong(item, 13))
                        .financialDocumentTemproryId(getItemForLong(item, 14))
                        .financialDocumentPermanentId(getItemForLong(item, 15))
                        .tempClosedFlag(getItemForLong(item, 16))
                        .hasOpeningFlag(getItemForLong(item, 17))
                        .permanentCloseFlag(getItemForLong(item, 18))
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(documentDtoList.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setData(documentDtoList);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private InsertLedgerPeriodMonthListOutputResponse setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        InsertLedgerPeriodMonthListOutputResponse insertLedgerPeriodMonthListOutputResponse = new InsertLedgerPeriodMonthListOutputResponse();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialPeriodId":
                    insertLedgerPeriodMonthListOutputResponse.setFinancialPeriodId(Long.parseLong(item.getValue().toString()));
                    break;
                case "financialLedgerTypeId":
                    insertLedgerPeriodMonthListOutputResponse.setFinancialLedgerTypeId(Long.parseLong(item.getValue().toString()));
                    break;

                default:
                    break;
            }
        }
        return insertLedgerPeriodMonthListOutputResponse;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean insertLedgerPeriod(InsertLedgerPeriodInputRequest insertLedgerPeriodInputRequest) {
        if (insertLedgerPeriodInputRequest.getFinancialPeriodId().size() == 0) {
            throw new RuleException("هیچ دوره مالی انتخاب نشده");
        }
        if (insertLedgerPeriodInputRequest.getFinancialLedgerTypeId() == null) {
            throw new RuleException("دفتر مالی انتخاب نشده");
        }
        for (Long e : insertLedgerPeriodInputRequest.getFinancialPeriodId()) {
            Long financialLedgerPeriod = financialLedgerPeriodRepository.getFinancialLedgerPeriodByIdAndLedgerType(insertLedgerPeriodInputRequest.getFinancialLedgerTypeId(), e);
            if (financialLedgerPeriod == null) {
                FinancialLedgerPeriod ledgerPeriod = new FinancialLedgerPeriod();
                ledgerPeriod.setFinancialLedgerPeriodStatus(financialLedgerPeriodStatusRepository.getOne(1L));
                ledgerPeriod.setFinancialPeriod(financialPeriodRepository.getOne(e));
            }
            FinancialLedgerPeriod ledgerPeriod = financialLedgerPeriodRepository.findById(insertLedgerPeriodInputRequest.getId() == null ? 0 : insertLedgerPeriodInputRequest.getId()).orElse(new FinancialLedgerPeriod());

            Long financialLedgerPeriodUniqueCount = financialLedgerPeriodRepository.getCountByFinancialLedgerPeriodByPeriodIdAndLedgerTypeId(e, insertLedgerPeriodInputRequest.getFinancialLedgerTypeId());
            if (financialLedgerPeriodUniqueCount != 0) {
                throw new RuleException("fin.financialLedgerPeriodUnique.save");
            }
            ledgerPeriod.setFinancialLedgerPeriodStatus(financialLedgerPeriodStatusRepository.getOne(1L));
            ledgerPeriod.setFinancialPeriod(financialPeriodRepository.getOne(e));
            ledgerPeriod.setFinancialLedgerType(financialLedgerTypeRepository.getOne(insertLedgerPeriodInputRequest.getFinancialLedgerTypeId()));
            ledgerPeriod.setFinancialDocumentOpening(null);
            ledgerPeriod.setFinancialDocumentTemprory(null);
            ledgerPeriod.setFinancialDocumentPermanent(null);
            financialLedgerPeriodRepository.save(ledgerPeriod);
            List<Long> financialMonth = financialMonthRepository.findByFinancialMonth(e.longValue());
            if (financialMonth.size() == 0) {
                throw new RuleException("به ازای این دوره مالی ماه عملیاتی یافت نشد");
            }
            financialMonth.forEach((Long e1) -> {
                FinancialLedgerMonth financialLedgerMonth = new FinancialLedgerMonth();
                financialLedgerMonth.setFinancialLedgerMonthStatus(financialLedgerMonthStatusRepository.getOne(2L));
                financialLedgerMonth.setFinancialLedgerType(financialLedgerTypeRepository.getOne(insertLedgerPeriodInputRequest.getFinancialLedgerTypeId()));
                financialLedgerMonth.setFinancialMonth(financialMonthRepository.getOne(e1));
                financialLedgerMonth.setFinancialLedgerPeriod(ledgerPeriod);
                financialLedgerMonthRepository.save(financialLedgerMonth);
            });
        }
        return true;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean delClosingTemp(FinancialLedgerClosingTempRequest financialLedgerClosingTempRequest) {
        CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest = new CheckLedgerPermissionInputRequest();
        checkLedgerPermissionInputRequest.setPeriodId(financialLedgerClosingTempRequest.getFinancialPeriodId());
        checkLedgerPermissionInputRequest.setLedgerTypeId(financialLedgerClosingTempRequest.getFinancialLedgerTypeId());
        checkLedgerPermissionInputRequest.setActivityCode("FINANCIAL_LEG_TEMPORARY");
        financialLedgerPeriodSecurityService.checkFinancialLedgerPeriodSecurity(checkLedgerPermissionInputRequest);

        Long financialPeriod = financialLedgerPeriodRepository.getFinancialLedgerPeriodByPeriodId(financialLedgerClosingTempRequest.getFinancialLedgerPeriodId());
        if (financialPeriod != null) {
            throw new RuleException("امکان انجام عملیات به دلیل وجود سند اختتامیه وجود ندارد");
        }

        Long financialPeriodTemprory = financialLedgerPeriodRepository.getFinancialLedgerPeriodByTemprory(financialLedgerClosingTempRequest.getFinancialLedgerPeriodId());
        if (financialPeriodTemprory == null) {
            throw new RuleException("سند بستن حسابهای سود و زیانی پیدا نشد");
        }
        financialDocumentNumberRepository.findByFinancialDocumentNumberAndFinancialDocumentId(financialPeriodTemprory)
                .forEach(financialDocumentNumberRepository::delete);

        financialDocumentItemRepository.findByFinancialDocumentIdByDocumentId(financialPeriodTemprory)
                .forEach(financialDocumentItemRepository::deleteById);

        entityManager.createNativeQuery(" Update FNDC.FINANCIAL_LEDGER_PERIOD LP " +
                "  SET LP.FINANCIAL_DOCUMENT_TEMPRORY_ID = null " +
                " WHERE LP.FINANCIAL_DOCUMENT_TEMPRORY_ID = :financialPeriodTemprory " +
                "   AND LP.ID = :financialLedgerPeriodId ").setParameter("financialPeriodTemprory", financialPeriodTemprory)
                .setParameter("financialLedgerPeriodId", financialLedgerClosingTempRequest.getFinancialLedgerPeriodId())
                .executeUpdate();
        financialDocumentRepository.deleteById(financialPeriodTemprory);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean closingPermanent(FinancialLedgerClosingTempInputRequest financialLedgerClosingTempInputRequest) {
        Long financialLedgerMonthPeriodId = financialLedgerMonthRepository.getFinancialLedgerMonthByLedgerPeriodId(financialLedgerClosingTempInputRequest.getFinancialLedgerPeriodId());
        if (financialLedgerMonthPeriodId != null) {
            throw new RuleException("تمامی ماه های عملیاتی این دوره میبایست در وضعیت بسته باشد");
        }
        Long financialPeriod = financialPeriodRepository.getFinancialPeriodByIdAndStatus(financialLedgerClosingTempInputRequest.getFinancialPeriodId());
        if (financialPeriod != null) {
            throw new RuleException("وضعیت دوره مالی مربوط به دفتر در حالت بسته میباشد");
        }
        Long financialPeriodId = financialLedgerPeriodRepository.getFinancialLedgerPeriodByPeriodId(financialLedgerClosingTempInputRequest.getFinancialLedgerPeriodId());
        if (financialPeriodId != null) {
            throw new RuleException("سند بستن حسابهای دائم / اختتامیه قبلا روی این دوره از دفتر مالی ثبت شده است");
        }
        CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest = new CheckLedgerPermissionInputRequest();
        checkLedgerPermissionInputRequest.setPeriodId(financialLedgerClosingTempInputRequest.getFinancialPeriodId());
        checkLedgerPermissionInputRequest.setLedgerTypeId(financialLedgerClosingTempInputRequest.getFinancialLedgerTypeId());
        checkLedgerPermissionInputRequest.setActivityCode("FINANCIAL_LEG_PERMANENT");
        financialLedgerPeriodSecurityService.checkFinancialLedgerPeriodSecurity(checkLedgerPermissionInputRequest);
        List<Object[]> financialPeriodDateAndDes =
                financialPeriodRepository.getFinancialPeriodByDateAndDes(financialLedgerClosingTempInputRequest.getFinancialPeriodId());

        Random rand = new Random(System.currentTimeMillis());
        FinancialDocument financialDocumentSave = new FinancialDocument();
        financialDocumentSave.setDocumentDate(financialPeriodDateAndDes.get(0)[0] == null ? null : ((Timestamp) financialPeriodDateAndDes.get(0)[0]).toLocalDateTime());
        financialDocumentSave.setDescription(" سند بستن حسابهای دائم / اختتامیه " + financialLedgerClosingTempInputRequest.getFinancialPeriodDes());
        financialDocumentSave.setFinancialDocumentStatus(financialDocumentStatusRepository.getOne(3L));
        financialDocumentSave.setPermanentDocumentNumber(null);
        financialDocumentSave.setAutomaticFlag(true);
        financialDocumentSave.setOrganization(organizationRepository.getOne(SecurityHelper.getCurrentUser().getOrganizationId()));
        financialDocumentSave.setFinancialDocumentType(financialDocumentTypeRepository.getOne(72L));
        financialDocumentSave.setFinancialPeriod(financialPeriodRepository.getOne(financialLedgerClosingTempInputRequest.getFinancialPeriodId()));
        financialDocumentSave.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialLedgerClosingTempInputRequest.getFinancialLedgerTypeId()));
        financialDocumentSave.setFinancialDepartment(financialDepartmentRepository.getOne(financialLedgerClosingTempInputRequest.getFinancialDepartmentId()));
        financialDocumentSave.setDocumentNumber("X" + rand);
        financialDocumentSave.setDepartment(departmentRepository.getOne(financialLedgerClosingTempInputRequest.getDepartmentId()));
        financialDocumentRepository.save(financialDocumentSave);
        FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
        financialDocumentNumberDto.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentNumberDto.setFinancialDocumentId(financialDocumentSave.getId());
        financialDocumentNumberDto.setNumberingType(1L);
        String documentNumberNew;
        documentNumberNew = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
        if (documentNumberNew == null) {
            throw new RuleException("اشکال در ایجاد شماره عطف");
        }
        entityManager.createNativeQuery(" Update fndc.FINANCIAL_DOCUMENT " +
                "   Set DOCUMENT_NUMBER = :newNumber " +
                " Where id = :newDocumentId ").setParameter("newNumber", documentNumberNew)
                .setParameter("newDocumentId", financialDocumentNumberDto.getFinancialDocumentId())
                .executeUpdate();
        financialDocumentNumberDto.setNumberingType(1L);
        documentNumberNew = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
        if (documentNumberNew == null) {
            throw new RuleException("اشکال در ایجاد شماره دائم");
        }
        entityManager.createNativeQuery(" Update fndc.FINANCIAL_DOCUMENT " +
                "   Set PERMANENT_DOCUMENT_NUMBER  = :newNumber " +
                " Where id = :newDocumentId ").setParameter("newNumber", documentNumberNew)
                .setParameter("newDocumentId", financialDocumentNumberDto.getFinancialDocumentId())
                .executeUpdate();
        GetDocumentItemsForLedgerInputRequest getDocumentItemsForLedgerInputRequest = new GetDocumentItemsForLedgerInputRequest();
        getDocumentItemsForLedgerInputRequest.setFinancialLedgerTypeId(financialLedgerClosingTempInputRequest.getFinancialLedgerTypeId());
        getDocumentItemsForLedgerInputRequest.setFinancialPeriodId(financialLedgerClosingTempInputRequest.getFinancialPeriodId());
        getDocumentItemsForLedgerInputRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        getDocumentItemsForLedgerInputRequest.setFinancialDepartmentId(financialLedgerClosingTempInputRequest.getFinancialDepartmentId());
        getDocumentItemsForLedgerInputRequest.setDepartmentId(financialLedgerClosingTempInputRequest.getDepartmentId());
        getDocumentItemsForLedgerInputRequest.setPermanentStatus(1L);
        getDocumentItemsForLedgerInputRequest.setFinancialPeriodDes(financialLedgerClosingTempInputRequest.getFinancialPeriodDes());
        List<FinancialLedgerClosingTempOutputResponse> getDocumentItemsForLedgerOutput = financialLedgerPeriodDocItemsService.getFinancialLedgerPeriodDocItems(getDocumentItemsForLedgerInputRequest);
        getDocumentItemsForLedgerOutput.forEach((FinancialLedgerClosingTempOutputResponse financialLedgerClosingTempOutputResponse) -> {
            FinancialDocumentItem financialDocumentItemSave = new FinancialDocumentItem();
            financialDocumentItemSave.setFinancialDocument(financialDocumentRepository.getOne(financialDocumentSave.getId()));
            financialDocumentItemSave.setSequenceNumber(financialLedgerClosingTempOutputResponse.getSequence());
            financialDocumentItemSave.setFinancialAccount(financialLedgerClosingTempOutputResponse.getFinancialAccountId() == 0L ? null : financialAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getFinancialAccountId()));
            financialDocumentItemSave.setCentricAccountId1(financialLedgerClosingTempOutputResponse.getCentricAccountId1() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId1()));
            financialDocumentItemSave.setCentricAccountId2(financialLedgerClosingTempOutputResponse.getCentricAccountId2() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId2()));
            financialDocumentItemSave.setCentricAccountId3(financialLedgerClosingTempOutputResponse.getCentricAccountId3() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId3()));
            financialDocumentItemSave.setCentricAccountId4(financialLedgerClosingTempOutputResponse.getCentricAccountId4() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId4()));
            financialDocumentItemSave.setCentricAccountId5(financialLedgerClosingTempOutputResponse.getCentricAccountId5() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId5()));
            financialDocumentItemSave.setCentricAccountId6(financialLedgerClosingTempOutputResponse.getCentricAccountId6() == 0L ? null : centricAccountRepository.getOne(financialLedgerClosingTempOutputResponse.getCentricAccountId6()));

            financialDocumentItemSave.setCreditAmount(financialLedgerClosingTempOutputResponse.getRemDebit().doubleValue());
            financialDocumentItemSave.setDebitAmount(financialLedgerClosingTempOutputResponse.getRemCredit().doubleValue());
            financialDocumentItemSave.setDescription(financialLedgerClosingTempOutputResponse.getDocItemDes());
            financialDocumentItemRepository.save(financialDocumentItemSave);
        });
        entityManager.createNativeQuery(" Update FNDC.FINANCIAL_LEDGER_PERIOD LP " +
                "  SET LP.FINANCIAL_DOCUMENT_PERMANENT_ID  = :newDocId " +
                " WHERE LP.ID = :financialLedgerPeriodId " +
                "   AND LP.FINANCIAL_DOCUMENT_PERMANENT_ID  IS NULL ").setParameter("newDocId", financialDocumentSave.getId())
                .setParameter("financialLedgerPeriodId", financialLedgerClosingTempInputRequest.getFinancialLedgerPeriodId())
                .executeUpdate();
        return true;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean delClosingPermanent(FinancialLedgerClosingTempRequest financialLedgerClosingTempRequest) {
        CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest = new CheckLedgerPermissionInputRequest();
        checkLedgerPermissionInputRequest.setPeriodId(financialLedgerClosingTempRequest.getFinancialPeriodId());
        checkLedgerPermissionInputRequest.setLedgerTypeId(financialLedgerClosingTempRequest.getFinancialLedgerTypeId());
        checkLedgerPermissionInputRequest.setActivityCode("FINANCIAL_LEG_PERMANENT");
        financialLedgerPeriodSecurityService.checkFinancialLedgerPeriodSecurity(checkLedgerPermissionInputRequest);

        Long financialPeriodOpen = financialLedgerPeriodRepository.getFinancialLedgerPeriodByPeriodIdOpenning(financialLedgerClosingTempRequest.getFinancialLedgerPeriodId());
        if (financialPeriodOpen != null) {
            throw new RuleException("امکان انجام عملیات به دلیل وجود سند افتتاحیه وجود ندارد");
        }
        Long financialPeriodPermanent = financialLedgerPeriodRepository.getFinancialLedgerPeriodByPeriodIdPermanent(financialLedgerClosingTempRequest.getFinancialLedgerPeriodId());
        if (financialPeriodPermanent == null) {
            throw new RuleException("سند بستن حسابهای دائم / سند اختتامیه پیدا نشد");
        }
        entityManager.createNativeQuery(" UPDATE FNDC.FINANCIAL_LEDGER_PERIOD LP " +
                "   SET LP.FINANCIAL_DOCUMENT_PERMANENT_ID = NULL " +
                " WHERE LP.FINANCIAL_DOCUMENT_PERMANENT_ID = :closePermanentDocumentId " +
                "   AND LP.ID = :financialLedgerPeriodId  ").setParameter("closePermanentDocumentId", financialPeriodPermanent.longValue())
                .setParameter("financialLedgerPeriodId", financialLedgerClosingTempRequest.getFinancialLedgerPeriodId())
                .executeUpdate();
        financialDocumentNumberRepository.findByFinancialDocumentNumberAndFinancialDocumentId(financialPeriodPermanent)
                .forEach(financialDocumentNumberRepository::delete);

        financialDocumentItemRepository.findByFinancialDocumentIdByDocumentId(financialPeriodPermanent)
                .forEach(financialDocumentItemRepository::deleteById);
        financialDocumentRepository.deleteById(financialPeriodPermanent);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean openingDocument(FinancialLedgerClosingTempInputRequest financialLedgerClosingTempInputRequest) {
        Long financialPeriod = financialPeriodRepository.getFinancialPeriodByIdAndStatus(financialLedgerClosingTempInputRequest.getFinancialPeriodId());
        if (financialPeriod != null) {
            throw new RuleException("وضعیت دوره مالی مربوط به دفتر در حالت بسته میباشد");
        }
        Long financialPeriodOpen = financialLedgerPeriodRepository.getFinancialLedgerPeriodByPeriodIdOpenning(financialLedgerClosingTempInputRequest.getFinancialLedgerPeriodId());
        if (financialPeriodOpen != null) {
            throw new RuleException("سند افتتاحیه قبلا برای این دوره ایجاد شده است");
        }
        List<Object[]> financialPeriodDStartDateAndEndDate =
                financialPeriodRepository.getFinancialPeriodByStartDateAndEndDateAndDes(financialLedgerClosingTempInputRequest.getFinancialPeriodId());
        Date endDate = financialLedgerPeriodRepository.getFinancialLedgerPeriodByOrganAndStartDate(SecurityHelper.getCurrentUser().getOrganizationId(),
                (Date) financialPeriodDStartDateAndEndDate.get(0)[0], financialLedgerClosingTempInputRequest.getFinancialLedgerTypeId());

        List<Object[]> financialLedgerEndDate =
                financialLedgerPeriodRepository.getFinancialLedgerPeriodByOrganAndEndDate(SecurityHelper.getCurrentUser().getOrganizationId(),
                        endDate, financialLedgerClosingTempInputRequest.getFinancialLedgerTypeId());
        if (!financialLedgerEndDate.isEmpty() && financialLedgerEndDate.get(0)[1] == null) {
            throw new RuleException("سند اختتامیه برای دوره قبل ایجاد نشده");
        }

        CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest = new CheckLedgerPermissionInputRequest();
        checkLedgerPermissionInputRequest.setPeriodId(financialLedgerClosingTempInputRequest.getFinancialPeriodId());
        checkLedgerPermissionInputRequest.setLedgerTypeId(financialLedgerClosingTempInputRequest.getFinancialLedgerTypeId());
        checkLedgerPermissionInputRequest.setActivityCode("FINANCIAL_LEG_PERMANENT");
        financialLedgerPeriodSecurityService.checkFinancialLedgerPeriodSecurity(checkLedgerPermissionInputRequest);

        Random rand = new Random(System.currentTimeMillis());
        FinancialDocument financialDocumentSave = new FinancialDocument();
        financialDocumentSave.setDocumentDate(financialPeriodDStartDateAndEndDate.get(0)[0] == null ? null : ((Timestamp) financialPeriodDStartDateAndEndDate.get(0)[0]).toLocalDateTime());
        financialDocumentSave.setDescription(" سند افتتاحیه " + financialLedgerClosingTempInputRequest.getFinancialPeriodDes());
        financialDocumentSave.setFinancialDocumentStatus(financialDocumentStatusRepository.getOne(3L));
        financialDocumentSave.setPermanentDocumentNumber(null);
        financialDocumentSave.setAutomaticFlag(true);
        financialDocumentSave.setOrganization(organizationRepository.getOne(SecurityHelper.getCurrentUser().getOrganizationId()));
        financialDocumentSave.setFinancialDocumentType(financialDocumentTypeRepository.getOne(70L));
        financialDocumentSave.setFinancialPeriod(financialPeriodRepository.getOne(financialLedgerClosingTempInputRequest.getFinancialPeriodId()));
        financialDocumentSave.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialLedgerClosingTempInputRequest.getFinancialLedgerTypeId()));
        financialDocumentSave.setFinancialDepartment(financialDepartmentRepository.getOne(financialLedgerClosingTempInputRequest.getFinancialDepartmentId()));
        financialDocumentSave.setDocumentNumber("X" + rand);
        financialDocumentSave.setDepartment(departmentRepository.getOne(financialLedgerClosingTempInputRequest.getDepartmentId()));
        financialDocumentRepository.save(financialDocumentSave);
        FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
        financialDocumentNumberDto.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentNumberDto.setFinancialDocumentId(financialDocumentSave.getId());
        financialDocumentNumberDto.setNumberingType(1L);
        String documentNumberNew;
        documentNumberNew = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
        if (documentNumberNew == null) {
            throw new RuleException("اشکال در ایجاد شماره عطف");
        }
        entityManager.createNativeQuery(" Update fndc.FINANCIAL_DOCUMENT " +
                "   Set DOCUMENT_NUMBER = :newNumber " +
                " Where id = :newDocumentId ").setParameter("newNumber", documentNumberNew)
                .setParameter("newDocumentId", financialDocumentNumberDto.getFinancialDocumentId())
                .executeUpdate();

        List<Object[]> financialDocumentItem = financialDocumentItemRepository.getDocumentItemByDocumentIdAndDesc(((BigDecimal) financialLedgerEndDate.get(0)[1]).longValue());

        financialDocumentItem.forEach((Object object) -> {
            FinancialDocumentItem financialDocumentItemSave = new FinancialDocumentItem();
            financialDocumentItemSave.setFinancialDocument(financialDocumentRepository.getOne(financialDocumentSave.getId()));
            financialDocumentItemSave.setSequenceNumber(((BigDecimal) financialDocumentItem.get(0)[1]).longValue());
            financialDocumentItemSave.setCreditAmount(((BigDecimal) financialDocumentItem.get(0)[3]).doubleValue());
            financialDocumentItemSave.setDebitAmount(((BigDecimal) financialDocumentItem.get(0)[2]).doubleValue());
            financialDocumentItemSave.setDescription("سند افتتاحیه " + financialLedgerClosingTempInputRequest.getFinancialPeriodDes());
            financialDocumentItemSave.setFinancialAccount(getItemForLong(financialDocumentItem.get(0), 4) == null ? null : financialAccountRepository.getOne(Long.parseLong(financialDocumentItem.get(0)[4].toString())));
            financialDocumentItemSave.setCentricAccountId1(getItemForLong(financialDocumentItem.get(0), 5) == null ? null : centricAccountRepository.getOne(Long.parseLong(financialDocumentItem.get(0)[5].toString())));
            financialDocumentItemSave.setCentricAccountId2(getItemForLong(financialDocumentItem.get(0), 6) == null ? null : centricAccountRepository.getOne(Long.parseLong(financialDocumentItem.get(0)[6].toString())));
            financialDocumentItemSave.setCentricAccountId3(getItemForLong(financialDocumentItem.get(0), 7) == null ? null : centricAccountRepository.getOne(Long.parseLong(financialDocumentItem.get(0)[7].toString())));
            financialDocumentItemSave.setCentricAccountId4(getItemForLong(financialDocumentItem.get(0), 8) == null ? null : centricAccountRepository.getOne(Long.parseLong(financialDocumentItem.get(0)[8].toString())));
            financialDocumentItemSave.setCentricAccountId5(getItemForLong(financialDocumentItem.get(0), 9) == null ? null : centricAccountRepository.getOne(Long.parseLong(financialDocumentItem.get(0)[9].toString())));
            financialDocumentItemSave.setCentricAccountId6(getItemForLong(financialDocumentItem.get(0), 10) == null ? null : centricAccountRepository.getOne(Long.parseLong(financialDocumentItem.get(0)[10].toString())));
            financialDocumentItemRepository.save(financialDocumentItemSave);
        });
        entityManager.createNativeQuery(" Update FNDC.FINANCIAL_LEDGER_PERIOD LP " +
                "   Set LP. FINANCIAL_DOCUMENT_OPENING_ID = :newDocumentId     " +
                "  WHERE LP.ID = :financialLedgerPeriodId " +
                "   AND LP. FINANCIAL_DOCUMENT_OPENING_ID IS NULL  ").setParameter("newDocumentId", financialDocumentSave.getId())
                .setParameter("financialLedgerPeriodId", financialLedgerClosingTempInputRequest.getFinancialLedgerPeriodId())
                .executeUpdate();
        return true;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean delOpeningDocument(FinancialLedgerClosingTempRequest financialLedgerClosingTempRequest) {
        Long financialPeriod = financialPeriodRepository.getFinancialPeriodByIdAndStatus(financialLedgerClosingTempRequest.getFinancialPeriodId());
        if (financialPeriod != null) {
            throw new RuleException("وضعیت دوره مالی در حالت بسته میباشد");
        }
        List<Long> financialLedgerMonth = financialLedgerMonthRepository.getFinancialLedgerMonth(financialLedgerClosingTempRequest.getFinancialLedgerPeriodId());
        if (!financialLedgerMonth.isEmpty()) {
            throw new RuleException(" وضعیت ماه عملیاتی در حالت بسته است");
        }
        List<Object[]> financialLedgerPeriod = financialLedgerPeriodRepository.getFinancialLedgerPeriodByIdOpen(financialLedgerClosingTempRequest.getFinancialLedgerPeriodId());
        Long documentPermanent = ((BigDecimal) financialLedgerPeriod.get(0)[0]).longValue();
        if (!financialLedgerPeriod.isEmpty() && ((BigDecimal) financialLedgerPeriod.get(0)[1]).longValue() == 2L) {
            throw new RuleException(" وضعیت دوره برای دفتر مالی در حالت بسته میباشد ");
        }
        CheckLedgerPermissionInputRequest checkLedgerPermissionInputRequest = new CheckLedgerPermissionInputRequest();
        checkLedgerPermissionInputRequest.setPeriodId(financialLedgerClosingTempRequest.getFinancialPeriodId());
        checkLedgerPermissionInputRequest.setLedgerTypeId(financialLedgerClosingTempRequest.getFinancialLedgerTypeId());
        checkLedgerPermissionInputRequest.setActivityCode("FINANCIAL_LEG_PERMANENT");
        financialLedgerPeriodSecurityService.checkFinancialLedgerPeriodSecurity(checkLedgerPermissionInputRequest);
        entityManager.createNativeQuery(" UPDATE FNDC.FINANCIAL_LEDGER_PERIOD LP " +
                "    SET LP.FINANCIAL_DOCUMENT_OPENING_ID = NULL " +
                "  WHERE LP.FINANCIAL_PERIOD_ID = :financialPeriodId " +
                "    AND LP.FINANCIAL_LEDGER_TYPE_ID = :financialLedgerTypeId " +
                "    AND LP. FINANCIAL_DOCUMENT_OPENING_ID IS NOT NULL   ").setParameter("financialPeriodId", financialLedgerClosingTempRequest.getFinancialPeriodId())
                .setParameter("financialLedgerTypeId", financialLedgerClosingTempRequest.getFinancialLedgerTypeId())
                .executeUpdate();

        financialDocumentNumberRepository.findByFinancialDocumentNumberAndFinancialDocumentId(documentPermanent)
                .forEach(financialDocumentNumberRepository::delete);
        financialDocumentItemRepository.findByFinancialDocumentIdByDocumentId(documentPermanent)
                .forEach(financialDocumentItemRepository::deleteById);
        financialDocumentRepository.deleteById(documentPermanent);
        return true;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public DataSourceResult getLedgerPeriodList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        GetLedgerPeriodInputRequest paramSearch = setParameterLedgerPeriod(filters);
        List<Sort.Order> sorts = new ArrayList<>();
        dataSourceRequest.getSort()
                .forEach((DataSourceRequest.SortDescriptor sortDescriptor) ->
                        {
                            if (sortDescriptor.getDir().equals("asc")) {
                                sorts.add(Sort.Order.asc(sortDescriptor.getField()));
                            } else {
                                sorts.add(Sort.Order.desc(sortDescriptor.getField()));
                            }
                        }
                );
        Pageable pageable = PageRequest.of((dataSourceRequest.getSkip() / dataSourceRequest.getTake()), dataSourceRequest.getTake(), Sort.by(sorts));
        Page<Object[]> list = financialLedgerPeriodRepository.getFinancialLedgerPeriodByPeriodIdGet(paramSearch.getFinancialLedgerPeriodId(), pageable);
        List<GetLedgerPeriodOutputResponse> financialLedgerPeriodDtoList = list.stream().map(item ->
                GetLedgerPeriodOutputResponse.builder()
                        .financialLedgerPeriodId(getItemForLong(item, 0))
                        .financialPeriodId(getItemForLong(item, 1))
                        .periodStartDate(item[2] == null ? null : ((Date) item[2]))
                        .periodEndDate(item[3] == null ? null : ((Date) item[3]))
                        .periodDescription(getItemForString(item, 4))
                        .openingDocNumber(getItemForLong(item, 5))
                        .openingDocDate(item[6] == null ? null : ((Date) item[6]))
                        .temporaryDocNumber(getItemForLong(item, 7))
                        .temporaryDocDate(item[8] == null ? null : ((Date) item[8]))
                        .permanentDocNumber(getItemForLong(item, 9))
                        .permanentDocDate(item[10] == null ? null : ((Date) item[10]))
                        .ledgerPeriodStatusId(getItemForLong(item, 11))
                        .ledgerPeriodStatusDesc(getItemForString(item, 12))
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialLedgerPeriodDtoList.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setData(financialLedgerPeriodDtoList);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private GetLedgerPeriodInputRequest setParameterLedgerPeriod(List<DataSourceRequest.FilterDescriptor> filters) {
        GetLedgerPeriodInputRequest getLedgerPeriodInputRequest = new GetLedgerPeriodInputRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialLedgerPeriodId":
                    getLedgerPeriodInputRequest.setFinancialLedgerPeriodId(Long.parseLong(item.getValue().toString()));
                    break;
                case "financialPeriodId":
                    checkFinancialPeriodSet(getLedgerPeriodInputRequest, item);
                    break;
                case "financialLedgerTypeId":
                    checkFinancialPeriodTypeIdSet(getLedgerPeriodInputRequest, item);
                    break;
                default:
                    break;
            }
        }
        return getLedgerPeriodInputRequest;
    }


    private void checkFinancialPeriodSet(GetLedgerPeriodInputRequest getLedgerPeriodInputRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            getLedgerPeriodInputRequest.setFinancialPeriodId(Long.parseLong(item.getValue().toString()));
        } else {
            getLedgerPeriodInputRequest.setFinancialPeriodId(null);
        }
    }

    private void checkFinancialPeriodTypeIdSet(GetLedgerPeriodInputRequest getLedgerPeriodInputRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            getLedgerPeriodInputRequest.setFinancialLedgerTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            getLedgerPeriodInputRequest.setFinancialLedgerTypeId(null);
        }
    }
}