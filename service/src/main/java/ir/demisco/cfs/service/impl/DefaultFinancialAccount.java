package ir.demisco.cfs.service.impl;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import ir.demisco.cfs.model.dto.request.FinancialAccountBalanceRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentCentricTurnOverRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentReportRequest;
import ir.demisco.cfs.model.dto.response.FinancialAccountBalanceResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountReportResponse;
import ir.demisco.cfs.model.dto.response.FinancialDocumentCentricTurnOverResponse;
import ir.demisco.cfs.service.api.FinancialAccountService;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialAccount implements FinancialAccountService {
    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialPeriodRepository financialPeriodRepository;

    public DefaultFinancialAccount(FinancialDocumentRepository financialDocumentRepository, FinancialPeriodRepository financialPeriodRepository, EntityManager entityManager) {
        this.financialDocumentRepository = financialDocumentRepository;
        this.financialPeriodRepository = financialPeriodRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public DataSourceResult getFinancialDocument(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialDocumentReportRequest financialDocumentReportRequest = setParameter(filters);
        getFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentReportRequest);
        if (financialDocumentReportRequest.getFinancialAccountId() == null) {
            throw new RuleException("fin.financialAccount.insertFinancialAccount");
        }
        if (financialDocumentReportRequest.getDocumentNumberingTypeId() == null) {
            throw new RuleException("fin.financialAccount.insertDocumentNumberingType");
        }
        if (financialDocumentReportRequest.getSummarizingType() == null) {
            throw new RuleException("fin.financialAccount.insertSummarizingType");
        }
        if (financialDocumentReportRequest.getOrganizationId() == null) {
            throw new RuleException("fin.financialAccount.insertOrganization");
        }
        if (financialDocumentReportRequest.getDateFilterFlg() == null) {
            throw new RuleException("fin.financialAccount.selectDateFilterFlg");
        }
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list = financialPeriodRepository.findByFinancialPeriodByParam(SecurityHelper.getCurrentUser().getOrganizationId(),
                financialDocumentReportRequest.getLedgerTypeId(),
                financialDocumentReportRequest.getPeriodStartDate(),
                financialDocumentReportRequest.getDateFilterFlg(),
                financialDocumentReportRequest.getFromDate(),
                financialDocumentReportRequest.getDocumentNumberingTypeId(),
                financialDocumentReportRequest.getFromNumber(),
                financialDocumentReportRequest.getCentricAccount1(),
                financialDocumentReportRequest.getCentricAccountId1(),
                financialDocumentReportRequest.getCentricAccount2(),
                financialDocumentReportRequest.getCentricAccountId2(),
                financialDocumentReportRequest.getReferenceNumberObject(),
                financialDocumentReportRequest.getReferenceNumber(),
                financialDocumentReportRequest.getToNumber(),
                financialDocumentReportRequest.getFinancialAccountId(),
                financialDocumentReportRequest.getSummarizingType(),
                financialDocumentReportRequest.getToDate(),
                pageable);
        List<FinancialAccountReportResponse> financialAccountReportResponse = list.stream().map(item ->
                FinancialAccountReportResponse.builder()
                        .documentDate((item[0] == null ? null : DateUtil.convertStringToDate(item[0].toString())))
                        .documentNumber(item[1] == null ? null : item[1].toString())
                        .description(item[2] == null ? null : item[2].toString())
                        .debitAmount(item[3] == null ? null : ((BigDecimal) item[3]).doubleValue())
                        .creditAmount(item[4] == null ? null : ((BigDecimal) item[4]).doubleValue())
                        .remainDebit(item[5] == null ? null : ((BigDecimal) item[5]).doubleValue())
                        .remainCredit(item[6] == null ? null : ((BigDecimal) item[6]).doubleValue())
                        .remainAmount(item[7] == null ? null : ((BigDecimal) item[7]).doubleValue())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialAccountReportResponse);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }


    private void getFinancialDocumentByNumberingTypeAndFromNumber(FinancialDocumentReportRequest financialDocumentReportRequest) {

        if (financialDocumentReportRequest.getDateFilterFlg() == 0) {
            setFromDateAndToDate(financialDocumentReportRequest);
        } else {
            setFromNumberAndToNumber(financialDocumentReportRequest);
        }
        LocalDateTime startDate = financialDocumentReportRequest.getFromDate();
        LocalDateTime periodStartDate;
        periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganization(SecurityHelper.getCurrentUser().getOrganizationId());

        if (startDate.isBefore(periodStartDate)) {
            periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganizationStartDate(SecurityHelper.getCurrentUser().getOrganizationId(), startDate);
        }
        if (periodStartDate == null) {
            periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganization2(SecurityHelper.getCurrentUser().getOrganizationId());
        }

        if (periodStartDate == null) {
            throw new RuleException("fin.financialAccount.notExistPeriod");
        }

        financialDocumentReportRequest.setPeriodStartDate(periodStartDate);
    }

    private void setFromNumberAndToNumber(FinancialDocumentReportRequest financialDocumentReportRequest) {
        String fromNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialDocumentReportRequest.getDocumentNumberingTypeId(),
                financialDocumentReportRequest.getFromDate(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentReportRequest.setFromNumber(fromNumber);

        String toNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(financialDocumentReportRequest.getDocumentNumberingTypeId(),
                financialDocumentReportRequest.getToDate(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentReportRequest.setToNumber(toNumber);

        if (fromNumber == null || toNumber == null) {
            throw new RuleException("fin.financialAccount.notExistDocumentInDate");
        }
    }

    private void setFromDateAndToDate(FinancialDocumentReportRequest financialDocumentReportRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentReportRequest.getDocumentNumberingTypeId()
                , financialDocumentReportRequest.getFromNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentReportRequest.setFromDate(fromDate);
        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentReportRequest.getDocumentNumberingTypeId()
                , financialDocumentReportRequest.getToNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentReportRequest.setToDate(toDate);
        if (fromDate == null || toDate == null) {
            throw new RuleException("fin.financialAccount.notCorrectDocumentNumber");
        }

    }

    private FinancialDocumentReportRequest setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialDocumentReportRequest financialDocumentReportRequest = new FinancialDocumentReportRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialAccountId":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setFinancialAccountId(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "fromDate":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));

                    }
                    break;

                case "toDate":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));

                    }
                    break;

                case "documentNumberingTypeId":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setDocumentNumberingTypeId(Long.parseLong(item.getValue().toString()));
                    }
                    break;

                case "centricAccountId1":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setCentricAccountId1(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "centricAccountId2":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setCentricAccountId2(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "referenceNumber":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setReferenceNumber(Long.parseLong(item.getValue().toString()));
                    }
                    break;

                case "fromNumber":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setFromNumber(item.getValue().toString());
                    }
                    break;
                case "toNumber":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setToNumber(item.getValue().toString());
                    }
                    break;

                case "summarizingType":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setSummarizingType(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "ledgerTypeId":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "organizationId":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setOrganizationId(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "dateFilterFlg":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setDateFilterFlg(Long.parseLong(item.getValue().toString()));
                    }
                    break;

            }
        }

        if (financialDocumentReportRequest.getCentricAccountId1() == null) {
            financialDocumentReportRequest.setCentricAccount1(null);
            financialDocumentReportRequest.setCentricAccountId1(0L);
        }
        if (financialDocumentReportRequest.getCentricAccountId2() == null) {
            financialDocumentReportRequest.setCentricAccount2(null);
            financialDocumentReportRequest.setCentricAccountId2(0L);
        }
        if (financialDocumentReportRequest.getReferenceNumber() == null) {
            financialDocumentReportRequest.setReferenceNumberObject(null);
            financialDocumentReportRequest.setReferenceNumber(0L);
        }

        return financialDocumentReportRequest;
    }

    private LocalDateTime parseStringToLocalDateTime(Object input, boolean truncateDate) {
        if (input instanceof String) {
            try {
//                Date date = ISO8601Utils.parse((String) input);
                Date date = ISO8601Utils.parse((String) input, new ParsePosition(0));
                LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                return truncateDate ? DateUtil.truncate(localDateTime) : localDateTime;
            } catch (Exception var4) {
                if (((String) input).equalsIgnoreCase("current_date")) {
                    return truncateDate ? DateUtil.truncate(LocalDateTime.now()) : LocalDateTime.now();
                } else {
                    return ((String) input).equalsIgnoreCase("current_timestamp") ? LocalDateTime.now() : LocalDateTime.parse((String) input);
                }
            }
        } else if (input instanceof LocalDateTime) {
            return truncateDate ? DateUtil.truncate((LocalDateTime) input) : (LocalDateTime) input;
        } else {
            throw new IllegalArgumentException("Filter for LocalDateTime has error :" + input + " with class" + input.getClass());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataSourceResult getFinancialDocumentCentricTurnOver(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest = setParameterCentricTurnOver(filters);
        getFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentCentricTurnOverRequest);
        if (financialDocumentCentricTurnOverRequest.getFinancialAccountId() == null) {
            throw new RuleException("fin.financialAccount.insertFinancialAccount");
        }
        if (financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId() == null) {
            throw new RuleException("fin.financialAccount.insertDocumentNumberingType");
        }
        if (financialDocumentCentricTurnOverRequest.getDateFilterFlg() == null) {
            throw new RuleException("fin.financialAccount.selectDateFilterFlg");
        }
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list = financialPeriodRepository.findByFinancialAccountCentricTurnOver(100L,
                financialDocumentCentricTurnOverRequest.getLedgerTypeId(),
                financialDocumentCentricTurnOverRequest.getPeriodStartDate(),
                financialDocumentCentricTurnOverRequest.getDateFilterFlg(),
                financialDocumentCentricTurnOverRequest.getFromDate(),
                financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId(),
                financialDocumentCentricTurnOverRequest.getFromNumber(),
                financialDocumentCentricTurnOverRequest.getCentricAccount1(),
                financialDocumentCentricTurnOverRequest.getCentricAccountId1(),
                financialDocumentCentricTurnOverRequest.getCentricAccount2(),
                financialDocumentCentricTurnOverRequest.getCentricAccountId2(),
                financialDocumentCentricTurnOverRequest.getReferenceNumberObject(),
                financialDocumentCentricTurnOverRequest.getReferenceNumber(),
                financialDocumentCentricTurnOverRequest.getFinancialAccountId(),
                financialDocumentCentricTurnOverRequest.getToDate(),
                financialDocumentCentricTurnOverRequest.getToNumber(),

                pageable);

        List<FinancialDocumentCentricTurnOverResponse> financialDocumentCentricTurnOverResponse = list.stream().map(item ->
                FinancialDocumentCentricTurnOverResponse.builder()
                        .accountId(Long.parseLong(item[0] == null ? null : item[0].toString()))
                        .accountCode(item[1] == null ? "" : item[1].toString())
                        .accountDescription(item[2] == null ? "" : item[2].toString())
                        .centricAccountId1(Long.parseLong(item[3] == null ? "0" : item[3].toString()))
                        .centricAccountId2(Long.parseLong(item[4] == null ? "0" : item[4].toString()))
                        .centricAccountId3(Long.parseLong(item[5] == null ? "0" : item[5].toString()))
                        .centricAccountId4(Long.parseLong(item[6] == null ? "0" : item[6].toString()))
                        .centricAccountId5(Long.parseLong(item[7] == null ? "0" : item[7].toString()))
                        .centricAccountId6(Long.parseLong(item[8] == null ? "0" : item[8].toString()))
                        .centricAccountDes1(item[9] == null ? "" : item[9].toString())
                        .centricAccountDes2(item[10] == null ? "" : item[10].toString())
                        .centricAccountDes3(item[11] == null ? "" : item[11].toString())
                        .centricAccountDes4(item[12] == null ? "" : item[12].toString())
                        .centricAccountDes5(item[13] == null ? "" : item[13].toString())
                        .centricAccountDes6(item[14] == null ? "" : item[14].toString())
                        .debitAmount(item[15] == null ? null : ((BigDecimal) item[15]).doubleValue())
                        .creditAmount(item[16] == null ? null : ((BigDecimal) item[16]).doubleValue())
                        .remainDebit(item[17] == null ? null : ((BigDecimal) item[17]).doubleValue())
                        .remainCredit(item[18] == null ? null : ((BigDecimal) item[18]).doubleValue())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialDocumentCentricTurnOverResponse);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private FinancialDocumentCentricTurnOverRequest setParameterCentricTurnOver(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest = new FinancialDocumentCentricTurnOverRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialAccountId":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setFinancialAccountId(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "fromDate":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));

                    }
                    break;

                case "toDate":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));

                    }
                    break;

                case "documentNumberingTypeId":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setDocumentNumberingTypeId(Long.parseLong(item.getValue().toString()));
                    }
                    break;

                case "centricAccountId1":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setCentricAccountId1(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialDocumentCentricTurnOverRequest.setCentricAccountId1(0L);
                        financialDocumentCentricTurnOverRequest.setCentricAccount1(null);
                    }
                    break;
                case "centricAccountId2":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setCentricAccountId2(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialDocumentCentricTurnOverRequest.setCentricAccountId2(0L);
                        financialDocumentCentricTurnOverRequest.setCentricAccount2(null);
                    }
                    break;

                case "fromNumber":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setFromNumber(item.getValue().toString());
                    }
                    break;
                case "toNumber":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setToNumber(item.getValue().toString());
                    }
                    break;

                case "ledgerTypeId":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
                    }
                    break;

                case "dateFilterFlg":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setDateFilterFlg(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "flgHasRemind":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setFlgHasRemind((Boolean) (item.getValue()));
                    }
                    break;
                case "flgRelatedOther":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setFlgRelatedOther((Boolean) (item.getValue()));
                    }
                    break;

                case "flgWithParentLevel":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setFlgWithParentLevel((Boolean) (item.getValue()));
                    }
                    break;
                case "referenceNumber":
                    if (item.getValue() != null) {
                        financialDocumentCentricTurnOverRequest.setReferenceNumber(Long.parseLong(item.getValue().toString()));
                    } else {
                        financialDocumentCentricTurnOverRequest.setReferenceNumber(0L);
                        financialDocumentCentricTurnOverRequest.setReferenceNumberObject(null);
                    }
                    break;
            }
        }

        if (financialDocumentCentricTurnOverRequest.getCentricAccountId1() == null) {
            financialDocumentCentricTurnOverRequest.setCentricAccount1(null);
            financialDocumentCentricTurnOverRequest.setCentricAccountId1(0L);
        }
        if (financialDocumentCentricTurnOverRequest.getCentricAccountId2() == null) {
            financialDocumentCentricTurnOverRequest.setCentricAccount2(null);
            financialDocumentCentricTurnOverRequest.setCentricAccountId2(0L);
        }
        return financialDocumentCentricTurnOverRequest;
    }

    private void getFinancialDocumentByNumberingTypeAndFromNumber(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest) {

        if (financialDocumentCentricTurnOverRequest.getDateFilterFlg() == 0) {
            setFromDateAndToDateCentricTurnOver(financialDocumentCentricTurnOverRequest);
        } else {
            setFromNumberAndToNumberCentricTurnOver(financialDocumentCentricTurnOverRequest);
        }

        LocalDateTime startDate = financialDocumentCentricTurnOverRequest.getFromDate();
        LocalDateTime periodStartDate;
        periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganization(100L);

        if (startDate.isBefore(periodStartDate)) {
            periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganizationStartDate(100L, startDate);
        }
        if (periodStartDate == null) {
            periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganization2(100L);
        }

        if (periodStartDate == null) {
            throw new RuleException("fin.financialAccount.notExistPeriod");
        }

        financialDocumentCentricTurnOverRequest.setPeriodStartDate(periodStartDate);
    }

    private void setFromDateAndToDateCentricTurnOver(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId()
                , financialDocumentCentricTurnOverRequest.getFromNumber(), 100L);
        financialDocumentCentricTurnOverRequest.setFromDate(fromDate);
        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId()
                , financialDocumentCentricTurnOverRequest.getToNumber(), 100L);
        financialDocumentCentricTurnOverRequest.setToDate(toDate);
        if (fromDate == null || toDate == null) {
            throw new RuleException("fin.financialAccount.notCorrectDocumentNumber");
        }

    }

    private void setFromNumberAndToNumberCentricTurnOver(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest) {
        String fromNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId(),
                financialDocumentCentricTurnOverRequest.getFromDate(), 100L);
        financialDocumentCentricTurnOverRequest.setFromNumber(fromNumber);

        String toNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId(),
                financialDocumentCentricTurnOverRequest.getToDate(), 100L);
        financialDocumentCentricTurnOverRequest.setToNumber(toNumber);
        if (fromNumber == null || toNumber == null) {
            throw new RuleException("fin.financialAccount.notExistDocumentInDate");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DataSourceResult getFinancialDocumentBalanceReport(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialAccountBalanceRequest financialAccountBalanceRequest = setParameterBalanceReport(filters);
        int length = 0;
        if (financialAccountBalanceRequest.getFromFinancialAccountCode() == null || financialAccountBalanceRequest.getToFinancialAccountCode() == null) {
            throw new RuleException("fin.financialAccount.mandatoryFinancialAccountCode");
        }
        if (financialAccountBalanceRequest.getFromFinancialAccountCode() != null || financialAccountBalanceRequest.getToFinancialAccountCode() != null) {
            if (financialAccountBalanceRequest.getFromFinancialAccountCode() == null) {
                financialAccountBalanceRequest.setFromFinancialAccountCode("");
            }
            if (financialAccountBalanceRequest.getToFinancialAccountCode() == null) {
                financialAccountBalanceRequest.setToFinancialAccountCode("");
            }
            if (financialAccountBalanceRequest.getFromFinancialAccountCode().length() != financialAccountBalanceRequest.getToFinancialAccountCode().length()) {
                throw new RuleException("fin.financialAccount.financialAccountBalance");
            }
            length = financialAccountBalanceRequest.getFromFinancialAccountCode().length();
        }


        getFinancialDocumentByNumberingTypeAndFromNumberBalance(financialAccountBalanceRequest);
//        if (financialAccountBalanceRequest.getFinancialAccountId() == null) {
//            throw new RuleException("لطفا شناسه ی حساب مالی را وارد نمایید.");
//        }
        if (financialAccountBalanceRequest.getDocumentNumberingTypeId() == null) {
            throw new RuleException("fin.financialAccount.insertDocumentNumberingType");
        }
//        if (financialAccountBalanceRequest.getSummarizingType() == null) {
//            throw new RuleException("لطفا نوع جمع بندی را وارد نمایید.");
//        }
        if (financialAccountBalanceRequest.getOrganizationId() == null) {
            throw new RuleException("fin.financialAccount.insertOrganization");
        }
        if (financialAccountBalanceRequest.getDateFilterFlg() == null) {
            throw new RuleException("fin.financialAccount.selectDateFilterFlg");
        }
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list = financialPeriodRepository.findByFinancialPeriodByBalanceReport(financialAccountBalanceRequest.getFromDate(),
                financialAccountBalanceRequest.getToDate(),
                financialAccountBalanceRequest.getFromNumber(),
                financialAccountBalanceRequest.getToNumber(),
                financialAccountBalanceRequest.getDocumentNumberingTypeId(),
                financialAccountBalanceRequest.getLedgerTypeId(),
                financialAccountBalanceRequest.getStructureLevel(),
                financialAccountBalanceRequest.getShowHigherLevels(),
                financialAccountBalanceRequest.getPeriodStartDate(),
                length,
                financialAccountBalanceRequest.getFromFinancialAccountCode(),
                financialAccountBalanceRequest.getToFinancialAccountCode(),
                financialAccountBalanceRequest.getOrganizationId(),
                financialAccountBalanceRequest.getHasRemain(),
                pageable);

        List<FinancialAccountBalanceResponse> financialAccountBalanceResponse = list.stream().map(item ->
                FinancialAccountBalanceResponse.builder()
                        .financialAccountParentId(Long.parseLong(item[0] == null ? null : item[0].toString()))
                        .financialAccountId(Long.parseLong(item[1] == null ? null : item[1].toString()))
                        .financialAccountCode(item[2] == null ? null : item[2].toString())
                        .financialAccountDescription(item[3] == null ? null : item[3].toString())
                        .financialAccountLevel(Long.parseLong(item[4] == null ? null : item[4].toString()))
                        .sumDebit(item[5] == null ? null : ((BigDecimal) item[5]).doubleValue())
                        .sumCredit(item[6] == null ? null : ((BigDecimal) item[6]).doubleValue())
                        .befDebit(item[7] == null ? null : ((BigDecimal) item[7]).doubleValue())
                        .befCredit(item[8] == null ? null : ((BigDecimal) item[8]).doubleValue())
                        .remDebit(item[9] == null ? null : ((BigDecimal) item[9]).doubleValue())
                        .remCredit(item[10] == null ? null : ((BigDecimal) item[10]).doubleValue())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialAccountBalanceResponse);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private FinancialAccountBalanceRequest setParameterBalanceReport(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialAccountBalanceRequest financialAccountBalanceRequest = new FinancialAccountBalanceRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "fromDate":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
                    }
                    break;
                case "toDate":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
                    }
                    break;

                case "fromNumber":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setFromNumber(item.getValue().toString());
                    }
                    break;
                case "toNumber":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setToNumber(item.getValue().toString());
                    }
                    break;
                case "documentNumberingTypeId":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setDocumentNumberingTypeId(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "ledgerTypeId":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "structureLevel":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setStructureLevel(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "hasRemain":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setHasRemain((Boolean) item.getValue());
                    }
                    break;
                case "showHigherLevels":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setShowHigherLevels((Boolean) item.getValue());
                    }
                    break;
                case "organizationId":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setOrganizationId(Long.parseLong(item.getValue().toString()));
                    }
                    break;

                case "fromFinancialAccountCode":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setFromFinancialAccountCode(item.getValue().toString());
                    }
                    break;
                case "toFinancialAccountCode":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setToFinancialAccountCode(item.getValue().toString());
                    }
                    break;
                case "dateFilterFlg":
                    if (item.getValue() != null) {
                        financialAccountBalanceRequest.setDateFilterFlg(Long.parseLong(item.getValue().toString()));
                    }
                    break;

            }
        }

        return financialAccountBalanceRequest;

    }

    private void getFinancialDocumentByNumberingTypeAndFromNumberBalance(FinancialAccountBalanceRequest financialAccountBalanceRequest) {

        if (financialAccountBalanceRequest.getDateFilterFlg() == 0) {
            setFromDateAndToDateCentricTurnOverBalance(financialAccountBalanceRequest);
        } else {
            setFromNumberAndToNumberCentricTurnOverBalance(financialAccountBalanceRequest);
        }

        LocalDateTime startDate = financialAccountBalanceRequest.getFromDate();
        LocalDateTime periodStartDate;
        periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganization(100L);

        if (startDate.isBefore(periodStartDate)) {
            periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganizationStartDate(100L, startDate);
        }
        if (periodStartDate == null) {
            periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganization2(100L);
        }

        if (periodStartDate == null) {
            throw new RuleException("fin.financialAccount.notExistPeriod");
        }

        financialAccountBalanceRequest.setPeriodStartDate(periodStartDate);
    }

    private void setFromDateAndToDateCentricTurnOverBalance(FinancialAccountBalanceRequest financialAccountBalanceRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialAccountBalanceRequest.getDocumentNumberingTypeId()
                , financialAccountBalanceRequest.getFromNumber(), 100L);
        financialAccountBalanceRequest.setFromDate(fromDate);
        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialAccountBalanceRequest.getDocumentNumberingTypeId()
                , financialAccountBalanceRequest.getToNumber(), 100L);
        financialAccountBalanceRequest.setToDate(toDate);
        if (fromDate == null || toDate == null) {
            throw new RuleException("fin.financialAccount.notCorrectDocumentNumber");
        }

    }

    private void setFromNumberAndToNumberCentricTurnOverBalance(FinancialAccountBalanceRequest financialAccountBalanceRequest) {
        String fromNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialAccountBalanceRequest.getDocumentNumberingTypeId(),
                financialAccountBalanceRequest.getFromDate(), 100L);
        financialAccountBalanceRequest.setFromNumber(fromNumber);
        String toNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(financialAccountBalanceRequest.getDocumentNumberingTypeId(),
                financialAccountBalanceRequest.getToDate(), 100L);
        financialAccountBalanceRequest.setToNumber(toNumber);
        if (fromNumber == null || toNumber == null) {
            throw new RuleException("fin.financialAccount.notExistDocumentInDate");
        }
    }
}

