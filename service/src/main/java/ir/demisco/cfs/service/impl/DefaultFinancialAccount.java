package ir.demisco.cfs.service.impl;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import ir.demisco.cfs.model.dto.request.FinancialAccountBalanceRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentCentricTurnOverRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentReportRequest;
import ir.demisco.cfs.model.dto.response.FinancialAccountBalanceResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountCentricTurnOverOutputResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountCentricTurnOverRecordsResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountTurnOverOutputResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountTurnOverRecordsResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountTurnOverSummarizeResponse;
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
import java.util.ArrayList;
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
        financialDocumentReportRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentReportRequest.setSummarizingType(financialDocumentReportRequest.getSummarizingType() == null ? 1 : financialDocumentReportRequest.getSummarizingType());
        getFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentReportRequest);
        checkFinancialDocumentReport(financialDocumentReportRequest);
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
        List<FinancialAccountTurnOverOutputResponse> financialAccountTurnOverOutputResponses = new ArrayList<>();
        List<FinancialAccountTurnOverRecordsResponse> recordsResponseList = new ArrayList<>();
        FinancialAccountTurnOverOutputResponse response = new FinancialAccountTurnOverOutputResponse();
        list.forEach(item -> {

            if (item[17] != null && (Long.parseLong(item[17].toString()) == 1 || Long.parseLong(item[17].toString()) == 2)) {
                FinancialAccountTurnOverRecordsResponse recordsResponse = new FinancialAccountTurnOverRecordsResponse();
                recordsResponse.setDocumentDate(item[0] == null ? null : convertDate(item[0].toString()));
                recordsResponse.setDocumentNumber(item[2] == null ? null : item[2].toString());
                recordsResponse.setDescription(item[3] == null ? null : item[3].toString());
                recordsResponse.setDebitAmount(item[7] == null ? null : Double.parseDouble(item[7].toString()));
                recordsResponse.setCreditAmount(item[8] == null ? null : Double.parseDouble(item[8].toString()));
                recordsResponse.setRemainDebit(item[9] == null ? null : Double.parseDouble(item[9].toString()));
                recordsResponse.setRemainCredit(item[10] == null ? null : Double.parseDouble(item[10].toString()));
                recordsResponse.setRemainAmount(item[11] == null ? null : Double.parseDouble(item[11].toString()));
                recordsResponse.setRecordType(item[17] == null ? null : Long.parseLong(item[17].toString()));
                recordsResponseList.add(recordsResponse);
                response.setFinancialAccountTurnOverRecordsResponseModel(recordsResponseList);
            } else {
                FinancialAccountTurnOverSummarizeResponse accountTurnOverSummarizeResponse = new FinancialAccountTurnOverSummarizeResponse();
                FinancialAccountTurnOverOutputResponse outputResponse = new FinancialAccountTurnOverOutputResponse();
                accountTurnOverSummarizeResponse.setSumDebit(item[12] == null ? null : Double.parseDouble(item[12].toString()));
                accountTurnOverSummarizeResponse.setSumCredit(item[13] == null ? null : Double.parseDouble(item[13].toString()));
                accountTurnOverSummarizeResponse.setSummarizeDebit(item[14] == null ? null : Double.parseDouble(item[14].toString()));
                accountTurnOverSummarizeResponse.setSummarizeCredit(item[15] == null ? null : Double.parseDouble(item[15].toString()));
                accountTurnOverSummarizeResponse.setSummarizeAmount(item[16] == null ? null : Double.parseDouble(item[16].toString()));
                accountTurnOverSummarizeResponse.setRecordType(item[17] == null ? null : Long.parseLong(item[17].toString()));
                outputResponse.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
                response.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
            }
        });
        financialAccountTurnOverOutputResponses.add(response);

        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialAccountTurnOverOutputResponses);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private void checkFinancialDocumentReport(FinancialDocumentReportRequest financialDocumentReportRequest) {
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
    }

    private Date convertDate(String date) {
        if (date.length() == 7) {
            return DateUtil.jalaliToGregorian(date, "yyyy/MM");
        } else {
            return DateUtil.jalaliToGregorian(date);
        }
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
            throw new RuleException("fin.financialAccount.notInformation");
        }
    }

    private void setFromDateAndToDate(FinancialDocumentReportRequest financialDocumentReportRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentReportRequest.getDocumentNumberingTypeId()
                , financialDocumentReportRequest.getFromNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentReportRequest.setFromDate(fromDate);
        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentReportRequest.getDocumentNumberingTypeId()
                , financialDocumentReportRequest.getFromNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentReportRequest.setToDate(toDate);
        if (fromDate == null || toDate == null) {
            throw new RuleException("fin.financialAccount.notInformation");
        }

    }

    private FinancialDocumentReportRequest setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialDocumentReportRequest financialDocumentReportRequest = new FinancialDocumentReportRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialAccountId":
                    checkFinancialAccountIdSet(financialDocumentReportRequest, item);
                    break;
                case "fromDate":
                    checkFromDateForDate(financialDocumentReportRequest, item);
                    break;

                case "toDate":
                    checkToDateForDate(financialDocumentReportRequest, item);
                    break;

                case "documentNumberingTypeId":
                    checkDocumentNumberingTypeIdSet(financialDocumentReportRequest, item);
                    break;

                case "centricAccountId1":
                    checkCentricAccountId1Set(financialDocumentReportRequest, item);
                    break;
                case "centricAccountId2":
                    checkCentricAccountId2Set(financialDocumentReportRequest, item);
                    break;
                case "referenceNumber":
                    checkReferenceNumberSet(financialDocumentReportRequest, item);
                    break;

                case "fromNumber":
                    checkFromNumberSet(financialDocumentReportRequest, item);
                    break;
                case "toNumber":
                    checkToNumberSet(financialDocumentReportRequest, item);
                    break;
                case "summarizingType":
                    checkSummarizingTypeSet(financialDocumentReportRequest, item);
                    break;
                case "ledgerTypeId":
                    checkLedgerTypeIdSet(financialDocumentReportRequest, item);

                    break;
                case "organizationId":
                    checkOrganizationIdSet(financialDocumentReportRequest, item);
                    break;
                case "dateFilterFlg":
                    if (item.getValue() != null) {
                        financialDocumentReportRequest.setDateFilterFlg(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                default:

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

    private void checkDocumentNumberingTypeIdSet(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setDocumentNumberingTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.documentNumberingType.is.null");
        }
    }

    private void checkFromDateForDate(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            throw new RuleException("fin.request.fromDate.is.null");
        }
    }

    private void checkToDateForDate(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            throw new RuleException("fin.request.fromDate.is.null");
        }
    }

    private void checkFinancialAccountIdSet(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setFinancialAccountId(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.financialAccount.is.null");
        }
    }

    private void checkCentricAccountId1Set(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setCentricAccountId1(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.centricAccountId1.is.null");
        }
    }

    private void checkCentricAccountId2Set(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setCentricAccountId2(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.centricAccountId2.is.null");
        }
    }

    private void checkReferenceNumberSet(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setReferenceNumber(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.referenceNumber.is.null");
        }
    }

    private void checkFromNumberSet(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setFromNumber(item.getValue().toString());
        } else {
            throw new RuleException("fin.document.fromNumber.is.null");
        }
    }

    private void checkToNumberSet(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setToNumber(item.getValue().toString());
        } else {
            throw new RuleException("fin.document.fromNumber.is.null");
        }
    }

    private void checkSummarizingTypeSet(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setSummarizingType(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.summarizingType.is.null");
        }
    }

    private void checkLedgerTypeIdSet(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.ledgerType.is.null");
        }
    }

    private void checkOrganizationIdSet(FinancialDocumentReportRequest financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setOrganizationId(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.organization.is.null");
        }
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
        if (financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId() == null) {
            throw new RuleException("fin.financialAccount.insertDocumentNumberingType");
        }
        if (financialDocumentCentricTurnOverRequest.getDateFilterFlg() == null) {
            throw new RuleException("fin.financialAccount.selectDateFilterFlg");
        }
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list = financialPeriodRepository.findByFinancialAccountCentricTurnOver(SecurityHelper.getCurrentUser().getOrganizationId(),
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
                financialDocumentCentricTurnOverRequest.getFinancialAccount(),
                financialDocumentCentricTurnOverRequest.getFinancialAccountId(),
                financialDocumentCentricTurnOverRequest.getToDate(),
                financialDocumentCentricTurnOverRequest.getToNumber(),
                pageable);
        List<FinancialAccountCentricTurnOverOutputResponse> financialAccountCentricTurnOverOutputResponses = new ArrayList<>();
        List<FinancialAccountCentricTurnOverRecordsResponse> recordsResponseList = new ArrayList<>();
        FinancialAccountCentricTurnOverOutputResponse response = new FinancialAccountCentricTurnOverOutputResponse();
        list.forEach(item -> {

            if (item[25] != null && (Long.parseLong(item[25].toString()) == 1 || Long.parseLong(item[25].toString()) == 2)) {
                FinancialAccountCentricTurnOverRecordsResponse recordsResponse = new FinancialAccountCentricTurnOverRecordsResponse();
                recordsResponse.setAccountId(item[0] == null ? null : Long.parseLong(item[0].toString()));
                recordsResponse.setAccountCode(item[1] == null ? null : item[1].toString());
                recordsResponse.setAccountDescription(item[2] == null ? null : item[2].toString());
                recordsResponse.setCentricAccountId1(item[3] == null ? null : Long.parseLong(item[3].toString()));
                recordsResponse.setCentricAccountId2(item[4] == null ? null : Long.parseLong(item[4].toString()));
                recordsResponse.setCentricAccountId3(item[5] == null ? null : Long.parseLong(item[5].toString()));
                recordsResponse.setCentricAccountId4(item[6] == null ? null : Long.parseLong(item[6].toString()));
                recordsResponse.setCentricAccountId5(item[7] == null ? null : Long.parseLong(item[7].toString()));
                recordsResponse.setCentricAccountId6(item[8] == null ? null : Long.parseLong(item[8].toString()));
                recordsResponse.setCentricAccountDes1(item[9] == null ? null : item[9].toString());
                recordsResponse.setCentricAccountDes2(item[10] == null ? null : item[10].toString());
                recordsResponse.setCentricAccountDes3(item[11] == null ? null : item[11].toString());
                recordsResponse.setCentricAccountDes4(item[12] == null ? null : item[12].toString());
                recordsResponse.setCentricAccountDes5(item[13] == null ? null : item[13].toString());
                recordsResponse.setCentricAccountDes6(item[14] == null ? null : item[14].toString());
                recordsResponse.setDebitAmount(item[15] == null ? null : Double.parseDouble(item[15].toString()));
                recordsResponse.setCreditAmount(item[16] == null ? null : Double.parseDouble(item[16].toString()));
                recordsResponse.setRemainDebit(item[17] == null ? null : Double.parseDouble(item[17].toString()));
                recordsResponse.setRemainCredit(item[18] == null ? null : Double.parseDouble(item[18].toString()));
                recordsResponse.setRemainAmount(item[19] == null ? null : Double.parseDouble(item[19].toString()));
                recordsResponseList.add(recordsResponse);
                response.setFinancialAccountCentricTurnOverRecordsModel(recordsResponseList);
            } else {
                FinancialAccountTurnOverSummarizeResponse accountTurnOverSummarizeResponse = new FinancialAccountTurnOverSummarizeResponse();
                FinancialAccountTurnOverOutputResponse outputResponse = new FinancialAccountTurnOverOutputResponse();
                accountTurnOverSummarizeResponse.setSumDebit(item[20] == null ? null : Double.parseDouble(item[20].toString()));
                accountTurnOverSummarizeResponse.setSumCredit(item[21] == null ? null : Double.parseDouble(item[21].toString()));
                accountTurnOverSummarizeResponse.setSummarizeDebit(item[22] == null ? null : Double.parseDouble(item[22].toString()));
                accountTurnOverSummarizeResponse.setSummarizeCredit(item[23] == null ? null : Double.parseDouble(item[23].toString()));
                accountTurnOverSummarizeResponse.setSummarizeAmount(item[24] == null ? null : Double.parseDouble(item[24].toString()));
                accountTurnOverSummarizeResponse.setRecordType(item[25] == null ? null : Long.parseLong(item[25].toString()));
                outputResponse.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
                response.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
            }
        });
        financialAccountCentricTurnOverOutputResponses.add(response);
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialAccountCentricTurnOverOutputResponses);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private FinancialDocumentCentricTurnOverRequest setParameterCentricTurnOver(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest = new FinancialDocumentCentricTurnOverRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialAccountId":
                    checkFinancialAccountIdSet(financialDocumentCentricTurnOverRequest, item);
                    break;

                case "fromDate":
                    checkFromDateForDate(financialDocumentCentricTurnOverRequest, item);
                    break;

                case "toDate":
                    checkToDateForDate(financialDocumentCentricTurnOverRequest, item);
                    break;

                case "documentNumberingTypeId":
                    checkDocumentNumberingTypeIdSet(financialDocumentCentricTurnOverRequest, item);
                    break;

                case "centricAccountId1":
                    checkCentricAccountId1Set(financialDocumentCentricTurnOverRequest, item);
                    break;
                case "centricAccountId2":
                    checkCentricAccountId2Set(financialDocumentCentricTurnOverRequest, item);
                    break;

                case "fromNumber":
                    checkFromNumberSet(financialDocumentCentricTurnOverRequest, item);
                    break;
                case "toNumber":
                    checkToNumberSet(financialDocumentCentricTurnOverRequest, item);
                    break;

                case "ledgerTypeId":
                    checkLedgerTypeIdSet(financialDocumentCentricTurnOverRequest, item);
                    break;

                case "dateFilterFlg":
                    checkDateFilterFlgSet(financialDocumentCentricTurnOverRequest, item);
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
                default:

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

    private void checkFinancialAccountIdSet(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setFinancialAccountId(Long.parseLong(item.getValue().toString()));
        } else {
            financialDocumentCentricTurnOverRequest.setFinancialAccountId(0L);
            financialDocumentCentricTurnOverRequest.setFinancialAccount(null);
        }
    }

    private void checkFromDateForDate(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            throw new RuleException("fin.request.fromDate.is.null");
        }
    }

    private void checkToDateForDate(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            throw new RuleException("fin.request.fromDate.is.null");
        }
    }

    private void checkDocumentNumberingTypeIdSet(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setDocumentNumberingTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.documentNumberingType.is.null");
        }
    }

    private void checkCentricAccountId1Set(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setCentricAccountId1(Long.parseLong(item.getValue().toString()));
        } else {
            financialDocumentCentricTurnOverRequest.setCentricAccountId1(0L);
            financialDocumentCentricTurnOverRequest.setCentricAccount1(null);
        }
    }

    private void checkCentricAccountId2Set(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setCentricAccountId2(Long.parseLong(item.getValue().toString()));
        } else {
            financialDocumentCentricTurnOverRequest.setCentricAccountId2(0L);
            financialDocumentCentricTurnOverRequest.setCentricAccount2(null);
        }
    }

    private void checkFromNumberSet(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setFromNumber(item.getValue().toString());
        } else {
            throw new RuleException("fin.document.fromNumber.is.null");
        }
    }

    private void checkToNumberSet(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setToNumber(item.getValue().toString());
        } else {
            throw new RuleException("fin.document.fromNumber.is.null");
        }
    }

    private void checkLedgerTypeIdSet(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.ledgerType.is.null");
        }
    }

    private void checkDateFilterFlgSet(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setDateFilterFlg(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.dateFilterFlg.is.null");
        }
    }

    private void getFinancialDocumentByNumberingTypeAndFromNumber(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest) {
        if (financialDocumentCentricTurnOverRequest.getDateFilterFlg() == 0) {
            setFromDateAndToDateCentricTurnOver(financialDocumentCentricTurnOverRequest);
        } else {
            setFromNumberAndToNumberCentricTurnOver(financialDocumentCentricTurnOverRequest);
        }

        LocalDateTime startDate = financialDocumentCentricTurnOverRequest.getFromDate();
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

        financialDocumentCentricTurnOverRequest.setPeriodStartDate(periodStartDate);
    }

    private void setFromDateAndToDateCentricTurnOver(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId()
                , financialDocumentCentricTurnOverRequest.getFromNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentCentricTurnOverRequest.setFromDate(fromDate);
        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId()
                , financialDocumentCentricTurnOverRequest.getToNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentCentricTurnOverRequest.setToDate(toDate);
        if (fromDate == null || toDate == null) {
            throw new RuleException("fin.financialAccount.notCorrectDocumentNumber");
        }

    }

    private void setFromNumberAndToNumberCentricTurnOver(FinancialDocumentCentricTurnOverRequest financialDocumentCentricTurnOverRequest) {
        String fromNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId(),
                financialDocumentCentricTurnOverRequest.getFromDate(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentCentricTurnOverRequest.setFromNumber(fromNumber);

        String toNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId(),
                financialDocumentCentricTurnOverRequest.getToDate(), SecurityHelper.getCurrentUser().getOrganizationId());
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
                financialAccountBalanceRequest.setFromFinancialAccountCode("fin.financialAccount.fromOrToFinancialAccountCode");
            }
            if (financialAccountBalanceRequest.getToFinancialAccountCode() == null) {
                financialAccountBalanceRequest.setToFinancialAccountCode("fin.financialAccount.fromOrToFinancialAccountCode");
            }
            if (financialAccountBalanceRequest.getFromFinancialAccountCode().length() != financialAccountBalanceRequest.getToFinancialAccountCode().length()) {
                throw new RuleException("fin.financialAccount.financialAccountBalance");
            }
            length = financialAccountBalanceRequest.getFromFinancialAccountCode().length();
        }
        getFinancialDocumentByNumberingTypeAndFromNumberBalance(financialAccountBalanceRequest);
        if (financialAccountBalanceRequest.getDocumentNumberingTypeId() == null) {
            throw new RuleException("fin.financialAccount.insertDocumentNumberingType");
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
                SecurityHelper.getCurrentUser().getOrganizationId(),
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
                    checkFromDateForDate(financialAccountBalanceRequest, item);
                    break;
                case "toDate":
                    checkToDateForDate(financialAccountBalanceRequest, item);
                    break;

                case "fromNumber":
                    checkFromNumberSet(financialAccountBalanceRequest, item);
                    break;
                case "toNumber":
                    checkToNumberSet(financialAccountBalanceRequest, item);
                    break;
                case "documentNumberingTypeId":
                    checkDocumentNumberingTypeIdSet(financialAccountBalanceRequest, item);
                    break;
                case "ledgerTypeId":
                    checkLedgerTypeIdSet(financialAccountBalanceRequest, item);
                    break;
                case "structureLevel":
                    checkStructureLevelIdSet(financialAccountBalanceRequest, item);
                    break;
                case "hasRemain":
                    checkHasRemainSet(financialAccountBalanceRequest, item);
                    break;
                case "showHigherLevels":
                    checkShowHigherLevelsSet(financialAccountBalanceRequest, item);
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
                default:

                    break;
            }
        }

        return financialAccountBalanceRequest;

    }

    private void checkFromDateForDate(FinancialAccountBalanceRequest financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            throw new RuleException("fin.request.fromDate.is.null");
        }
    }

    private void checkToDateForDate(FinancialAccountBalanceRequest financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            throw new RuleException("fin.request.fromDate.is.null");
        }
    }

    private void checkFromNumberSet(FinancialAccountBalanceRequest financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setFromNumber(item.getValue().toString());
        } else {
            throw new RuleException("fin.document.fromNumber.is.null");
        }
    }

    private void checkToNumberSet(FinancialAccountBalanceRequest financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setToNumber(item.getValue().toString());
        } else {
            throw new RuleException("fin.document.fromNumber.is.null");
        }
    }

    private void checkDocumentNumberingTypeIdSet(FinancialAccountBalanceRequest financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setDocumentNumberingTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.documentNumberingType.is.null");
        }
    }

    private void checkLedgerTypeIdSet(FinancialAccountBalanceRequest financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.ledgerType.is.null");
        }
    }

    private void checkStructureLevelIdSet(FinancialAccountBalanceRequest financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setStructureLevel(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.structureLevel.is.null");
        }
    }

    private void checkHasRemainSet(FinancialAccountBalanceRequest financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setHasRemain((Boolean) item.getValue());
        } else {
            throw new RuleException("fin.document.hasRemain.is.null");
        }
    }

    private void checkShowHigherLevelsSet(FinancialAccountBalanceRequest financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setShowHigherLevels((Boolean) item.getValue());
        } else {
            throw new RuleException("fin.document.showHigherLevels.is.null");
        }
    }

    private void getFinancialDocumentByNumberingTypeAndFromNumberBalance(FinancialAccountBalanceRequest financialAccountBalanceRequest) {

        if (financialAccountBalanceRequest.getDateFilterFlg() == 0) {
            setFromDateAndToDateCentricTurnOverBalance(financialAccountBalanceRequest);
        } else {
            setFromNumberAndToNumberCentricTurnOverBalance(financialAccountBalanceRequest);
        }

        LocalDateTime startDate = financialAccountBalanceRequest.getFromDate();
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

        financialAccountBalanceRequest.setPeriodStartDate(periodStartDate);
    }

    private void setFromDateAndToDateCentricTurnOverBalance(FinancialAccountBalanceRequest financialAccountBalanceRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialAccountBalanceRequest.getDocumentNumberingTypeId()
                , financialAccountBalanceRequest.getFromNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialAccountBalanceRequest.setFromDate(fromDate);
        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialAccountBalanceRequest.getDocumentNumberingTypeId()
                , financialAccountBalanceRequest.getToNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialAccountBalanceRequest.setToDate(toDate);
        if (fromDate == null || toDate == null) {
            throw new RuleException("fin.financialAccount.notCorrectDocumentNumber");
        }

    }

    private void setFromNumberAndToNumberCentricTurnOverBalance(FinancialAccountBalanceRequest financialAccountBalanceRequest) {
        String fromNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialAccountBalanceRequest.getDocumentNumberingTypeId(),
                financialAccountBalanceRequest.getFromDate(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialAccountBalanceRequest.setFromNumber(fromNumber);
        String toNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(financialAccountBalanceRequest.getDocumentNumberingTypeId(),
                financialAccountBalanceRequest.getToDate(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialAccountBalanceRequest.setToNumber(toNumber);
        if (fromNumber == null || toNumber == null) {
            throw new RuleException("fin.financialAccount.notExistDocumentInDate");
        }
    }
}


