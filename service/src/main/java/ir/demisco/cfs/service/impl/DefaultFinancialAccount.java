package ir.demisco.cfs.service.impl;

import com.fasterxml.jackson.databind.util.StdDateFormat;
import ir.demisco.cfs.model.dto.request.FinancialAccountBalanceRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentCentricBalanceReportRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentCentricTurnOverRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentReportRequest;
import ir.demisco.cfs.model.dto.response.FinancialAccountBalanceResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountCentricBalanceResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountCentricTurnOverOutputResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountCentricTurnOverRecordsResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountListBalanceResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountTurnOverOutputResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountTurnOverRecordsResponse;
import ir.demisco.cfs.model.dto.response.FinancialAccountTurnOverSummarizeResponse;
import ir.demisco.cfs.model.dto.response.FinancialDocumentCentricBalanceResponse;
import ir.demisco.cfs.service.api.FinancialAccountService;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DefaultFinancialAccount implements FinancialAccountService {
    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialPeriodRepository financialPeriodRepository;


    public DefaultFinancialAccount(FinancialDocumentRepository financialDocumentRepository, FinancialPeriodRepository financialPeriodRepository) {
        this.financialDocumentRepository = financialDocumentRepository;
        this.financialPeriodRepository = financialPeriodRepository;
    }

    private List<Object[]> getTurnOverReportList(FinancialDocumentReportRequest financialDocumentReportRequest) {
        return financialPeriodRepository.findByFinancialPeriodByParam(SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentReportRequest.getLedgerTypeId(), financialDocumentReportRequest.getPeriodStartDate(), financialDocumentReportRequest.getDateFilterFlg(),
                financialDocumentReportRequest.getFromDate(), financialDocumentReportRequest.getDocumentNumberingTypeId(), financialDocumentReportRequest.getFromNumber(), financialDocumentReportRequest.getCentricAccount1(), financialDocumentReportRequest.getCentricAccountId1(),
                financialDocumentReportRequest.getCentricAccount2(), financialDocumentReportRequest.getCentricAccountId2(), financialDocumentReportRequest.getReferenceNumberObject(), financialDocumentReportRequest.getReferenceNumber(),
                financialDocumentReportRequest.getToNumber(), financialDocumentReportRequest.getFinancialAccountId(), financialDocumentReportRequest.getSummarizingType(), financialDocumentReportRequest.getToDate());
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
        List<Object[]> list = getTurnOverReportList(financialDocumentReportRequest);
        List<FinancialAccountTurnOverOutputResponse> financialAccountTurnOverOutputResponses = new ArrayList<>();
        List<FinancialAccountTurnOverRecordsResponse> recordsResponseList = new ArrayList<>();
        FinancialAccountTurnOverOutputResponse response = new FinancialAccountTurnOverOutputResponse();
        list.forEach((Object[] item) -> {

            if (item[19] != null && (Long.parseLong(item[19].toString()) == 1 || Long.parseLong(item[19].toString()) == 2)) {
                FinancialAccountTurnOverRecordsResponse recordsResponse = new FinancialAccountTurnOverRecordsResponse();
                recordsResponse.setDocumentDate(getItemForDate(item, 0));
                recordsResponse.setDocumentNumber(getItemForString(item, 2));
                recordsResponse.setFinancialDocumentId(getItemForLong(item, 3));
                recordsResponse.setFinancialDocumentItemId(getItemForLong(item, 4));
                recordsResponse.setDescription(getItemForString(item, 5));
                recordsResponse.setDebitAmount(getItemForString(item, 9));
                recordsResponse.setCreditAmount(getItemForString(item, 10));
                recordsResponse.setRemainDebit(getItemForString(item, 11));
                recordsResponse.setRemainCredit(getItemForString(item, 12));
                recordsResponse.setRemainAmount(getItemForString(item, 13));
                recordsResponse.setRecordType(getItemForLong(item, 19));
                recordsResponseList.add(recordsResponse);
                response.setFinancialAccountTurnOverRecordsResponseModel(recordsResponseList);
            } else {
                FinancialAccountTurnOverSummarizeResponse accountTurnOverSummarizeResponse = new FinancialAccountTurnOverSummarizeResponse();
                FinancialAccountTurnOverOutputResponse outputResponse = new FinancialAccountTurnOverOutputResponse();
                accountTurnOverSummarizeResponse.setSumDebit(getItemForLong(item, 14));
                accountTurnOverSummarizeResponse.setSumCredit(getItemForLong(item, 15));
                accountTurnOverSummarizeResponse.setSummarizeDebit(getItemForString(item, 16));
                accountTurnOverSummarizeResponse.setSummarizeCredit(getItemForString(item, 17));
                accountTurnOverSummarizeResponse.setSummarizeAmount(getItemForString(item, 18));
                accountTurnOverSummarizeResponse.setRecordType(getItemForLong(item, 19));
                outputResponse.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
                response.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
            }
        });
        financialAccountTurnOverOutputResponses.add(response);

        DataSourceResult dataSourceResult = new DataSourceResult();
        List<FinancialAccountTurnOverRecordsResponse> collect = financialAccountTurnOverOutputResponses.get(0).getFinancialAccountTurnOverRecordsResponseModel()
                .stream()
                .limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip())
                .skip(dataSourceRequest.getSkip())
                .collect(Collectors.toList());

        FinancialAccountTurnOverRecordsResponse recordsResponse = new FinancialAccountTurnOverRecordsResponse();
        recordsResponse.setFinancialAccountTurnOverSummarizeModel(financialAccountTurnOverOutputResponses.get(0).getFinancialAccountTurnOverSummarizeModel());
        collect.add(0, recordsResponse);
        dataSourceResult.setData(collect);
        dataSourceResult.setTotal(list.size());
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
        }
        if (financialDocumentReportRequest.getDateFilterFlg() == 1 || financialDocumentReportRequest.getFromNumber() == null) {
            String fromNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialDocumentReportRequest.getDocumentNumberingTypeId(),
                    financialDocumentReportRequest.getFromDate(), SecurityHelper.getCurrentUser().getOrganizationId());
            if (fromNumber == null) {
                throw new RuleException("fin.financialAccount.notInformation");
            }
            financialDocumentReportRequest.setFromNumber(fromNumber);
        }
        if (financialDocumentReportRequest.getDateFilterFlg() == 1 || financialDocumentReportRequest.getToNumber() == null) {
            String toNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(financialDocumentReportRequest.getDocumentNumberingTypeId(),
                    financialDocumentReportRequest.getToDate(), SecurityHelper.getCurrentUser().getOrganizationId());
            if (toNumber == null) {
                throw new RuleException("fin.financialAccount.notInformation");
            }
            financialDocumentReportRequest.setToNumber(toNumber);
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

    private void setFromDateAndToDate(FinancialDocumentReportRequest financialDocumentReportRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentReportRequest.getDocumentNumberingTypeId()
                , financialDocumentReportRequest.getFromNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentReportRequest.setFromDate(fromDate);
        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingAndToNumber(financialDocumentReportRequest.getDocumentNumberingTypeId()
                , financialDocumentReportRequest.getToNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
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

    private void checkDocumentNumberingTypeIdSet(FinancialDocumentReportRequest
                                                         financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        financialDocumentReportRequest.setDocumentNumberingTypeId(Long.parseLong(item.getValue().toString()));
    }

    private void checkFromDateForDate(FinancialDocumentReportRequest
                                              financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("fromDate", "fromDate");
            financialDocumentReportRequest.setParamMap(map);
            financialDocumentReportRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            map.put("fromDate", null);
            financialDocumentReportRequest.setParamMap(map);
            financialDocumentReportRequest.setFromDate(null);
        }
    }

    private void checkToDateForDate(FinancialDocumentReportRequest
                                            financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("toDate", "toDate");
            financialDocumentReportRequest.setParamMap(map);
            financialDocumentReportRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            map.put("toDate", null);
            financialDocumentReportRequest.setParamMap(map);
            financialDocumentReportRequest.setToDate(null);
        }
    }

    private void checkFinancialAccountIdSet(FinancialDocumentReportRequest
                                                    financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setFinancialAccountId(Long.parseLong(item.getValue().toString()));
        } else {
            financialDocumentReportRequest.setFinancialAccountId(null);
        }
    }

    private void checkCentricAccountId1Set(FinancialDocumentReportRequest
                                                   financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("centricAccount1", "centricAccount1");
            financialDocumentReportRequest.setParamMap(map);
            financialDocumentReportRequest.setCentricAccountId1(Long.parseLong(item.getValue().toString()));
            financialDocumentReportRequest.setCentricAccount1(financialDocumentReportRequest.getCentricAccountId1());
        } else {
            map.put("centricAccount1", null);
            financialDocumentReportRequest.setParamMap(map);
            financialDocumentReportRequest.setCentricAccount1(null);
            financialDocumentReportRequest.setCentricAccountId1(0L);
        }

    }

    private void checkCentricAccountId2Set(FinancialDocumentReportRequest
                                                   financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("centricAccount2", "centricAccount2");
            financialDocumentReportRequest.setParamMap(map);
            financialDocumentReportRequest.setCentricAccountId2(Long.parseLong(item.getValue().toString()));
            financialDocumentReportRequest.setCentricAccount2(financialDocumentReportRequest.getCentricAccountId2());
        } else {
            map.put("centricAccount2", null);
            financialDocumentReportRequest.setParamMap(map);
            financialDocumentReportRequest.setCentricAccount2(null);
            financialDocumentReportRequest.setCentricAccountId2(0L);
        }

    }

    private void checkReferenceNumberSet(FinancialDocumentReportRequest
                                                 financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("referenceNumberObject", "referenceNumberObject");
            financialDocumentReportRequest.setParamMap(map);
            financialDocumentReportRequest.setReferenceNumber(Long.parseLong(item.getValue().toString()));
            financialDocumentReportRequest.setReferenceNumberObject(financialDocumentReportRequest.getReferenceNumber());
        } else {
            map.put("referenceNumberObject", null);
            financialDocumentReportRequest.setParamMap(map);
            financialDocumentReportRequest.setReferenceNumberObject(null);
            financialDocumentReportRequest.setReferenceNumber(null);
        }
    }

    private void checkFromNumberSet(FinancialDocumentReportRequest
                                            financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setFromNumber(item.getValue().toString());
        } else {
            financialDocumentReportRequest.setFromNumber(null);
        }
    }

    private void checkToNumberSet(FinancialDocumentReportRequest
                                          financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setToNumber(item.getValue().toString());
        } else {
            financialDocumentReportRequest.setToNumber(null);
        }
    }

    private void checkSummarizingTypeSet(FinancialDocumentReportRequest
                                                 financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        financialDocumentReportRequest.setSummarizingType(Long.parseLong(item.getValue().toString()));
    }

    private void checkLedgerTypeIdSet(FinancialDocumentReportRequest
                                              financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            financialDocumentReportRequest.setLedgerTypeId(null);
        }
    }

    private void checkOrganizationIdSet(FinancialDocumentReportRequest
                                                financialDocumentReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentReportRequest.setOrganizationId(Long.parseLong(item.getValue().toString()));
        } else {
            throw new RuleException("fin.document.organization.is.null");
        }
    }

    private LocalDateTime parseStringToLocalDateTime(Object input, boolean truncateDate) {
        if (input instanceof String) {
            try {
                Date date = StdDateFormat.instance.parse((String) input);
                return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            } catch (ParseException var4) {
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
        financialDocumentCentricTurnOverRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        getFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentCentricTurnOverRequest);
        if (financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId() == null) {
            throw new RuleException("fin.financialAccount.insertDocumentNumberingType");
        }
        if (financialDocumentCentricTurnOverRequest.getDateFilterFlg() == null) {
            throw new RuleException("fin.financialAccount.selectDateFilterFlg");
        }

        List<Object[]> list = getCentricTurnOverReportList(financialDocumentCentricTurnOverRequest);
        List<FinancialAccountCentricTurnOverOutputResponse> financialAccountCentricTurnOverOutputResponses = new ArrayList<>();
        List<FinancialAccountCentricTurnOverRecordsResponse> recordsResponseList = new ArrayList<>();
        FinancialAccountCentricTurnOverOutputResponse response = new FinancialAccountCentricTurnOverOutputResponse();
        list.forEach((Object[] item) -> {

            if (item[30] != null && (Long.parseLong(item[30].toString()) == 1 || Long.parseLong(item[30].toString()) == 2)) {
                FinancialAccountCentricTurnOverRecordsResponse recordsResponse = new FinancialAccountCentricTurnOverRecordsResponse();
                recordsResponse.setDocumentNumber(getItemForLong(item, 0));
                recordsResponse.setDocumentDate((Date) item[1]);
                recordsResponse.setDocumentDescriptionItem(getItemForString(item, 2));
                recordsResponse.setFinancialDocumentItemId(getItemForLong(item, 3));
                recordsResponse.setFinancialDocumentId(getItemForLong(item, 4));
                recordsResponse.setAccountId(getItemForLong(item, 5));
                recordsResponse.setAccountCode(getItemForString(item, 6));
                recordsResponse.setAccountDescription(getItemForString(item, 7));
                recordsResponse.setCentricAccountId1(getItemForLong(item, 8));
                recordsResponse.setCentricAccountId2(getItemForLong(item, 9));
                recordsResponse.setCentricAccountId3(getItemForLong(item, 10));
                recordsResponse.setCentricAccountId4(getItemForLong(item, 11));
                recordsResponse.setCentricAccountId5(getItemForLong(item, 12));
                recordsResponse.setCentricAccountId6(getItemForLong(item, 13));
                recordsResponse.setCentricAccountDes1(getItemForString(item, 14));
                recordsResponse.setCentricAccountDes2(getItemForString(item, 15));
                recordsResponse.setCentricAccountDes3(getItemForString(item, 16));
                recordsResponse.setCentricAccountDes4(getItemForString(item, 17));
                recordsResponse.setCentricAccountDes5(getItemForString(item, 18));
                recordsResponse.setCentricAccountDes6(getItemForString(item, 19));
                recordsResponse.setDebitAmount(getItemForString(item, 20));
                recordsResponse.setCreditAmount(getItemForString(item, 21));
                recordsResponse.setRemainDebit(getItemForString(item, 22));
                recordsResponse.setRemainCredit(getItemForString(item, 23));
                recordsResponse.setRemainAmount(getItemForString(item, 24));
                recordsResponseList.add(recordsResponse);
                response.setFinancialAccountCentricTurnOverRecordsModel(recordsResponseList);
            } else {
                FinancialAccountTurnOverSummarizeResponse accountTurnOverSummarizeResponse = new FinancialAccountTurnOverSummarizeResponse();
                FinancialAccountTurnOverOutputResponse outputResponse = new FinancialAccountTurnOverOutputResponse();
                accountTurnOverSummarizeResponse.setSumDebit(getItemForLong(item, 25));
                accountTurnOverSummarizeResponse.setSumCredit(getItemForLong(item, 26));
                accountTurnOverSummarizeResponse.setSummarizeDebit(getItemForString(item, 27));
                accountTurnOverSummarizeResponse.setSummarizeCredit(getItemForString(item, 28));
                accountTurnOverSummarizeResponse.setSummarizeAmount(getItemForString(item, 29));
                accountTurnOverSummarizeResponse.setRemainDebit(getItemForString(item, 22));
                accountTurnOverSummarizeResponse.setRemainCredit(getItemForString(item, 23));
                accountTurnOverSummarizeResponse.setRecordType(getItemForLong(item, 30));
                outputResponse.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
                response.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
            }
        });
        financialAccountCentricTurnOverOutputResponses.add(response);

        DataSourceResult dataSourceResult = new DataSourceResult();

        List<FinancialAccountCentricTurnOverRecordsResponse> collect = financialAccountCentricTurnOverOutputResponses.get(0).getFinancialAccountCentricTurnOverRecordsModel()
                .stream()
                .limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip())
                .skip(dataSourceRequest.getSkip())
                .collect(Collectors.toList());
        FinancialAccountCentricTurnOverRecordsResponse recordsResponse = new FinancialAccountCentricTurnOverRecordsResponse();
        recordsResponse.setFinancialAccountTurnOverSummarizeModel(financialAccountCentricTurnOverOutputResponses.get(0).getFinancialAccountTurnOverSummarizeModel());
        collect.add(0, recordsResponse);
        dataSourceResult.setData(collect);
        dataSourceResult.setTotal(list.size());
        return dataSourceResult;
    }

    private List<Object[]> getCentricTurnOverReportList(FinancialDocumentCentricTurnOverRequest
                                                                financialDocumentCentricTurnOverRequest) {
        if (financialDocumentCentricTurnOverRequest.getFinancialAccountId() != 0 && financialDocumentCentricTurnOverRequest.getCnacId1() == 0
                && financialDocumentCentricTurnOverRequest.getCnacId2() == 0
                && financialDocumentCentricTurnOverRequest.getCnatId1() == 0
                && financialDocumentCentricTurnOverRequest.getCnatId2() == 0) {
            return financialPeriodRepository
                    .findByFinancialAccountCentricTurnOver2(SecurityHelper.getCurrentUser().getOrganizationId(),
                            financialDocumentCentricTurnOverRequest.getLedgerTypeId(),
                            financialDocumentCentricTurnOverRequest.getPeriodStartDate()
                            , financialDocumentCentricTurnOverRequest.getDateFilterFlg(),
                            financialDocumentCentricTurnOverRequest.getFromDate(),
                            financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId(),
                            financialDocumentCentricTurnOverRequest.getFromNumber(),
                            financialDocumentCentricTurnOverRequest.getCnacIdObj1(),
                            financialDocumentCentricTurnOverRequest.getCnacIdObj2(),
                            financialDocumentCentricTurnOverRequest.getCnacId1(),
                            financialDocumentCentricTurnOverRequest.getCnacId2(),
                            financialDocumentCentricTurnOverRequest.getCnatIdObj1(),
                            financialDocumentCentricTurnOverRequest.getCnatIdObj2(),
                            financialDocumentCentricTurnOverRequest.getCnatId1(),
                            financialDocumentCentricTurnOverRequest.getCnatId2(),
                            financialDocumentCentricTurnOverRequest.getReferenceNumberObject(),
                            financialDocumentCentricTurnOverRequest.getReferenceNumber()
                            , financialDocumentCentricTurnOverRequest.getFinancialAccountId()
                            , financialDocumentCentricTurnOverRequest.getToDate(),
                            financialDocumentCentricTurnOverRequest.getToNumber());
        } else {
            return financialPeriodRepository
                    .findByFinancialAccountCentricTurnOver(SecurityHelper.getCurrentUser().getOrganizationId(),
                            financialDocumentCentricTurnOverRequest.getLedgerTypeId(),
                            financialDocumentCentricTurnOverRequest.getPeriodStartDate()
                            , financialDocumentCentricTurnOverRequest.getDateFilterFlg(),
                            financialDocumentCentricTurnOverRequest.getFromDate(),
                            financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId(),
                            financialDocumentCentricTurnOverRequest.getFromNumber(),
                            financialDocumentCentricTurnOverRequest.getCnacIdObj1(),
                            financialDocumentCentricTurnOverRequest.getCnacIdObj2(),
                            financialDocumentCentricTurnOverRequest.getCnacId1(),
                            financialDocumentCentricTurnOverRequest.getCnacId2(),
                            financialDocumentCentricTurnOverRequest.getCnatIdObj1(),
                            financialDocumentCentricTurnOverRequest.getCnatIdObj2(),
                            financialDocumentCentricTurnOverRequest.getCnatId1(),
                            financialDocumentCentricTurnOverRequest.getCnatId2(),
                            financialDocumentCentricTurnOverRequest.getReferenceNumberObject(),
                            financialDocumentCentricTurnOverRequest.getReferenceNumber()
                            , financialDocumentCentricTurnOverRequest.getFinancialAccount()
                            , financialDocumentCentricTurnOverRequest.getFinancialAccountId()
                            , financialDocumentCentricTurnOverRequest.getToDate(),
                            financialDocumentCentricTurnOverRequest.getToNumber());
        }
    }

    private FinancialDocumentCentricTurnOverRequest setParameterCentricTurnOver
            (List<DataSourceRequest.FilterDescriptor> filters) {
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
                    checkFlgHasRemindSet(financialDocumentCentricTurnOverRequest, item);
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
                    checkReferenceNumberSet(financialDocumentCentricTurnOverRequest, item);
                    break;
                case "cnatId1":
                    checkCnatId1Set(financialDocumentCentricTurnOverRequest, item);
                    break;
                case "cnatId2":
                    checkCnatId2Set(financialDocumentCentricTurnOverRequest, item);
                    break;
                case "cnacId1":
                    checkCnacId1CentricTurnOverSet(financialDocumentCentricTurnOverRequest, item);
                    break;

                case "cnacId2":
                    checkCnacId2CentricTurnOverSet(financialDocumentCentricTurnOverRequest, item);
                    break;

                default:

                    break;
            }
        }
        if (financialDocumentCentricTurnOverRequest.getCnatId1() == null) {
            financialDocumentCentricTurnOverRequest.setCnatIdObj1(null);
            financialDocumentCentricTurnOverRequest.setCnatId1(0L);
        }

        if (financialDocumentCentricTurnOverRequest.getCnatId2() == null) {
            financialDocumentCentricTurnOverRequest.setCnatIdObj2(null);
            financialDocumentCentricTurnOverRequest.setCnatId2(0L);
        }
        if (financialDocumentCentricTurnOverRequest.getCnacId1() == null) {
            financialDocumentCentricTurnOverRequest.setCnacIdObj1(null);
            financialDocumentCentricTurnOverRequest.setCnacId1(0L);
        }

        if (financialDocumentCentricTurnOverRequest.getCnacId2() == null) {
            financialDocumentCentricTurnOverRequest.setCnacIdObj2(null);
            financialDocumentCentricTurnOverRequest.setCnacId2(0L);
        }
        if (financialDocumentCentricTurnOverRequest.getReferenceNumber() == null) {
            financialDocumentCentricTurnOverRequest.setReferenceNumberObject(null);
            financialDocumentCentricTurnOverRequest.setReferenceNumber(0L);
        }
        if (financialDocumentCentricTurnOverRequest.getFinancialAccountId() == null) {
            financialDocumentCentricTurnOverRequest.setFinancialAccount(null);
            financialDocumentCentricTurnOverRequest.setFinancialAccountId(0L);
        }
        return financialDocumentCentricTurnOverRequest;
    }

    private void checkFinancialAccountIdSet(FinancialDocumentCentricTurnOverRequest
                                                    financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("financialAccount", "financialAccount");
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setFinancialAccountId(Long.parseLong(item.getValue().toString()));
            financialDocumentCentricTurnOverRequest.setFinancialAccount(item.getValue().toString());
        } else {
            map.put("financialAccount", null);
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setFinancialAccountId(null);
        }
    }

    private void checkFromDateForDate(FinancialDocumentCentricTurnOverRequest
                                              financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("fromDate", "fromDate");
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            map.put("fromDate", null);
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setFromDate(null);
        }
    }

    private void checkToDateForDate(FinancialDocumentCentricTurnOverRequest
                                            financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("toDate", "toDate");
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            map.put("toDate", null);
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setToDate(null);
        }
    }

    private void checkDocumentNumberingTypeIdSet(FinancialDocumentCentricTurnOverRequest
                                                         financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        financialDocumentCentricTurnOverRequest.setDocumentNumberingTypeId(Long.parseLong(item.getValue().toString()));
    }

    private void checkReferenceNumberSet(FinancialDocumentCentricTurnOverRequest
                                                 financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("referenceNumberObject", "referenceNumberObject");
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setReferenceNumber(Long.parseLong(item.getValue().toString()));
            financialDocumentCentricTurnOverRequest.setReferenceNumberObject(item.getValue().toString());
        } else {
            map.put("referenceNumberObject", null);
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setReferenceNumber(0L);
        }

    }

    private void checkCnatId1Set(FinancialDocumentCentricTurnOverRequest
                                         financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("cnatIdObj1", "cnatIdObj1");
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setCnatId1(Long.parseLong(item.getValue().toString()));
            financialDocumentCentricTurnOverRequest.setCnatIdObj1(financialDocumentCentricTurnOverRequest.getCnatId1());
        } else {
            map.put("cnatIdObj1", null);
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setCnatId1(0L);
        }
    }

    private void checkCnatId2Set(FinancialDocumentCentricTurnOverRequest
                                         financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("cnatIdObj2", "cnatIdObj2");
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setCnatId2(Long.parseLong(item.getValue().toString()));
            financialDocumentCentricTurnOverRequest.setCnatIdObj2(financialDocumentCentricTurnOverRequest.getCnatId2());
        } else {
            map.put("cnatIdObj2", null);
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setCnatId2(0L);
        }
    }

    private void checkCnacId1CentricTurnOverSet(FinancialDocumentCentricTurnOverRequest
                                                        financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("cnacIdObj1", "cnacIdObj1");
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setCnacId1(Long.parseLong(item.getValue().toString()));
            financialDocumentCentricTurnOverRequest.setCnacIdObj1(financialDocumentCentricTurnOverRequest.getCnacId1());
        } else {
            map.put("cnacIdObj1", null);
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setCnacId1(0L);
        }
    }

    private void checkCnacId2CentricTurnOverSet(FinancialDocumentCentricTurnOverRequest
                                                        financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("cnacIdObj2", "cnacIdObj2");
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setCnacId2(Long.parseLong(item.getValue().toString()));
            financialDocumentCentricTurnOverRequest.setCnacIdObj2(financialDocumentCentricTurnOverRequest.getCnacId2());
        } else {
            map.put("cnacIdObj2", null);
            financialDocumentCentricTurnOverRequest.setParamMap(map);
            financialDocumentCentricTurnOverRequest.setCnacId2(0L);
        }
    }

    private void checkFromNumberSet(FinancialDocumentCentricTurnOverRequest
                                            financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setFromNumber(item.getValue().toString());
        } else {
            financialDocumentCentricTurnOverRequest.setFromNumber(null);
        }
    }

    private void checkToNumberSet(FinancialDocumentCentricTurnOverRequest
                                          financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setToNumber(item.getValue().toString());
        } else {
            financialDocumentCentricTurnOverRequest.setToNumber(null);
        }
    }

    private void checkLedgerTypeIdSet(FinancialDocumentCentricTurnOverRequest
                                              financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        financialDocumentCentricTurnOverRequest.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
    }

    private void checkDateFilterFlgSet(FinancialDocumentCentricTurnOverRequest
                                               financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        financialDocumentCentricTurnOverRequest.setDateFilterFlg(Long.parseLong(item.getValue().toString()));
    }

    private void checkFlgHasRemindSet(FinancialDocumentCentricTurnOverRequest
                                              financialDocumentCentricTurnOverRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricTurnOverRequest.setFlgHasRemind((Boolean) (item.getValue()));
        } else {
            financialDocumentCentricTurnOverRequest.setFlgHasRemind(null);
        }
    }

    private void getFinancialDocumentByNumberingTypeAndFromNumber(FinancialDocumentCentricTurnOverRequest
                                                                          financialDocumentCentricTurnOverRequest) {
        if (financialDocumentCentricTurnOverRequest.getDateFilterFlg() == 0) {
            setFromDateAndToDateCentricTurnOver(financialDocumentCentricTurnOverRequest);
        }
        if (financialDocumentCentricTurnOverRequest.getDateFilterFlg() == 1 || financialDocumentCentricTurnOverRequest.getFromNumber() == null) {
            String fromNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId(),
                    financialDocumentCentricTurnOverRequest.getFromDate(), SecurityHelper.getCurrentUser().getOrganizationId());
            if (fromNumber == null) {
                throw new RuleException("fin.financialAccount.notInformation");
            }
            financialDocumentCentricTurnOverRequest.setFromNumber(fromNumber);
        }
        if (financialDocumentCentricTurnOverRequest.getDateFilterFlg() == 1 || financialDocumentCentricTurnOverRequest.getToNumber() == null) {
            String toNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId(),
                    financialDocumentCentricTurnOverRequest.getToDate(), SecurityHelper.getCurrentUser().getOrganizationId());
            if (toNumber == null) {
                throw new RuleException("fin.financialAccount.notInformation");
            }
            financialDocumentCentricTurnOverRequest.setToNumber(toNumber);
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

    private void setFromDateAndToDateCentricTurnOver(FinancialDocumentCentricTurnOverRequest
                                                             financialDocumentCentricTurnOverRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId()
                , financialDocumentCentricTurnOverRequest.getFromNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentCentricTurnOverRequest.setFromDate(fromDate);
        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingAndToNumber(financialDocumentCentricTurnOverRequest.getDocumentNumberingTypeId()
                , financialDocumentCentricTurnOverRequest.getToNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentCentricTurnOverRequest.setToDate(toDate);
        if (fromDate == null || toDate == null) {
            throw new RuleException("fin.financialAccount.notCorrectDocumentNumber");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public DataSourceResult getFinancialDocumentBalanceReport(DataSourceRequest dataSourceRequest) {
        int length = 0;
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialAccountBalanceRequest financialAccountBalanceRequest = setParameterBalanceReport(filters);
        if (financialAccountBalanceRequest.getFromFinancialAccountCode() != null || financialAccountBalanceRequest.getToFinancialAccountCode() != null) {
            checkFinancialAccountBalanceSet(financialAccountBalanceRequest);
            length = financialAccountBalanceRequest.getFromFinancialAccountCode().length();
        }
        getFinancialDocumentByNumberingTypeAndFromNumberBalance(financialAccountBalanceRequest);
        if (financialAccountBalanceRequest.getDocumentNumberingTypeId() == null) {
            throw new RuleException("fin.financialAccount.insertDocumentNumberingType");
        }

        if (financialAccountBalanceRequest.getDateFilterFlg() == null) {
            throw new RuleException("fin.financialAccount.selectDateFilterFlg");
        }
        List<Object[]> list = getBalanceReportList(financialAccountBalanceRequest, length);
        List<FinancialAccountListBalanceResponse> financialAccountListBalanceResponses = new ArrayList<>();
        List<FinancialAccountBalanceResponse> recordsResponseList = new ArrayList<>();
        FinancialAccountListBalanceResponse response = new FinancialAccountListBalanceResponse();
        list.forEach((Object[] item) -> {

            if (item[13] != null && (Long.parseLong(item[13].toString()) == 1 || Long.parseLong(item[13].toString()) == 2)) {
                FinancialAccountBalanceResponse recordsResponse = new FinancialAccountBalanceResponse();
                recordsResponse.setFinancialAccountParentId(getItemForLong(item, 0));
                recordsResponse.setFinancialAccountId(getItemForLong(item, 1));
                recordsResponse.setFinancialAccountCode(getItemForString(item, 2));
                recordsResponse.setFinancialAccountDescription(getItemForString(item, 3));
                recordsResponse.setFinancialAccountLevel(getItemForLong(item, 4));
                recordsResponse.setSumDebit(getItemForString(item, 5));
                recordsResponse.setSumCredit(getItemForString(item, 6));
                recordsResponse.setBefDebit(getItemForString(item, 7));
                recordsResponse.setBefCredit(getItemForString(item, 8));
                recordsResponse.setRemDebit(getItemForString(item, 9));
                recordsResponse.setRemCredit(getItemForString(item, 10));
                recordsResponse.setColor(getItemForString(item, 11));
                recordsResponseList.add(recordsResponse);
                response.setFinancialAccountBalanceRecordsModel(recordsResponseList);
            } else {
                FinancialAccountTurnOverSummarizeResponse accountTurnOverSummarizeResponse = new FinancialAccountTurnOverSummarizeResponse();
                FinancialAccountTurnOverOutputResponse outputResponse = new FinancialAccountTurnOverOutputResponse();
                accountTurnOverSummarizeResponse.setSumDebit(getItemForLong(item, 1));
                accountTurnOverSummarizeResponse.setSumCredit(getItemForLong(item, 2));
                accountTurnOverSummarizeResponse.setSummarizeDebit(getItemForString(item, 5));
                accountTurnOverSummarizeResponse.setSummarizeCredit(getItemForString(item, 6));
                accountTurnOverSummarizeResponse.setBefDebit(getItemForString(item, 7));
                accountTurnOverSummarizeResponse.setBefCredit(getItemForString(item, 8));
                accountTurnOverSummarizeResponse.setRemainDebit(getItemForString(item, 9));
                accountTurnOverSummarizeResponse.setRemainCredit(getItemForString(item, 10));
                accountTurnOverSummarizeResponse.setSummarizeAmount(getItemForString(item, 12));
                accountTurnOverSummarizeResponse.setRecordType(getItemForLong(item, 13));
                outputResponse.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
                response.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
            }
        });
        financialAccountListBalanceResponses.add(response);
        if (financialAccountListBalanceResponses.get(0).getFinancialAccountBalanceRecordsModel() == null) {
            throw new RuleException("fin.financialAccount.financialAccountCentricBalanceRecordsModel");
        }
        DataSourceResult dataSourceResult = new DataSourceResult();

        List<FinancialAccountBalanceResponse> collect = financialAccountListBalanceResponses.get(0).getFinancialAccountBalanceRecordsModel()
                .stream()
                .limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip())
                .skip(dataSourceRequest.getSkip())
                .collect(Collectors.toList());

        FinancialAccountBalanceResponse recordsResponse = new FinancialAccountBalanceResponse();
        recordsResponse.setFinancialAccountTurnOverSummarizeModel(financialAccountListBalanceResponses.get(0).getFinancialAccountTurnOverSummarizeModel());
        collect.add(0, recordsResponse);
        dataSourceResult.setData(collect);
        dataSourceResult.setTotal(list.size());
        return dataSourceResult;
    }

    private List<Object[]> getBalanceReportList(FinancialAccountBalanceRequest financialAccountBalanceRequest, int length) {
        return financialPeriodRepository.findByFinancialPeriodByBalanceReport(financialAccountBalanceRequest.getFromDate(),
                financialAccountBalanceRequest.getToDate(), financialAccountBalanceRequest.getFromNumber(),
                financialAccountBalanceRequest.getToNumber(), financialAccountBalanceRequest.getDocumentNumberingTypeId(),
                financialAccountBalanceRequest.getLedgerTypeId(),
                financialAccountBalanceRequest.getStructureLevel(),
                financialAccountBalanceRequest.getShowHigherLevels(),
                financialAccountBalanceRequest.getPeriodStartDate(), length
                , financialAccountBalanceRequest.getFromFinancialAccountCode(), financialAccountBalanceRequest.getToFinancialAccountCode()
                , SecurityHelper.getCurrentUser().getOrganizationId()
                , financialAccountBalanceRequest.getHasRemain());
    }

    private void checkFinancialAccountBalanceSet(FinancialAccountBalanceRequest financialAccountBalanceRequest) {
        if (financialAccountBalanceRequest.getFromFinancialAccountCode() == null) {
            financialAccountBalanceRequest.setFromFinancialAccountCode("fin.financialAccount.fromOrToFinancialAccountCode");
        }
        if (financialAccountBalanceRequest.getToFinancialAccountCode() == null) {
            financialAccountBalanceRequest.setToFinancialAccountCode("fin.financialAccount.fromOrToFinancialAccountCode");
        }
        if (financialAccountBalanceRequest.getFromFinancialAccountCode().length() != financialAccountBalanceRequest.getToFinancialAccountCode().length()) {
            throw new RuleException("fin.financialAccount.financialAccountBalance");
        }
    }

    private Long getItemForLong(Object[] item, int i) {
        return item[i] == null ? null : Long.parseLong(String.format("%.0f", Double.parseDouble(item[i].toString())));
    }

    private String getItemForString(Object[] item, int i) {
        return item[i] == null ? null : item[i].toString();
    }

    private Date getItemForDate(Object[] item, int i) {
        return item[i] == null ? null : convertDate(item[i].toString());
    }

    private FinancialAccountBalanceRequest setParameterBalanceReport
            (List<DataSourceRequest.FilterDescriptor> filters) {
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

    private void checkFromDateForDate(FinancialAccountBalanceRequest
                                              financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            financialAccountBalanceRequest.setFromDate(null);
        }

    }

    private void checkToDateForDate(FinancialAccountBalanceRequest
                                            financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {

        if (item.getValue() != null) {
            financialAccountBalanceRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            financialAccountBalanceRequest.setToDate(null);
        }

    }

    private void checkFromNumberSet(FinancialAccountBalanceRequest
                                            financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setFromNumber(item.getValue().toString());
        } else {
            financialAccountBalanceRequest.setFromNumber(null);
        }
    }

    private void checkToNumberSet(FinancialAccountBalanceRequest
                                          financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialAccountBalanceRequest.setToNumber(item.getValue().toString());
        } else {
            financialAccountBalanceRequest.setToNumber(null);
        }
    }

    private void checkDocumentNumberingTypeIdSet(FinancialAccountBalanceRequest
                                                         financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        financialAccountBalanceRequest.setDocumentNumberingTypeId(Long.parseLong(item.getValue().toString()));
    }

    private void checkLedgerTypeIdSet(FinancialAccountBalanceRequest
                                              financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        financialAccountBalanceRequest.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
    }

    private void checkStructureLevelIdSet(FinancialAccountBalanceRequest
                                                  financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        financialAccountBalanceRequest.setStructureLevel(Long.parseLong(item.getValue().toString()));
    }

    private void checkHasRemainSet(FinancialAccountBalanceRequest
                                           financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        financialAccountBalanceRequest.setHasRemain((Boolean) item.getValue());
    }

    private void checkShowHigherLevelsSet(FinancialAccountBalanceRequest
                                                  financialAccountBalanceRequest, DataSourceRequest.FilterDescriptor item) {
        financialAccountBalanceRequest.setShowHigherLevels((Boolean) item.getValue());
    }

    private void getFinancialDocumentByNumberingTypeAndFromNumberBalance(FinancialAccountBalanceRequest
                                                                                 financialAccountBalanceRequest) {

        if (financialAccountBalanceRequest.getDateFilterFlg() == 0) {
            setFromDateAndToDateCentricTurnOverBalance(financialAccountBalanceRequest);
        }
        if (financialAccountBalanceRequest.getDateFilterFlg() == 1 || financialAccountBalanceRequest.getFromNumber() == null) {
            String fromNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialAccountBalanceRequest.getDocumentNumberingTypeId(),
                    financialAccountBalanceRequest.getFromDate()
                    , SecurityHelper.getCurrentUser().getOrganizationId());
            if (fromNumber == null) {
                throw new RuleException("fin.financialAccount.notInformation");
            }
            financialAccountBalanceRequest.setFromNumber(fromNumber);
        }
        if (financialAccountBalanceRequest.getDateFilterFlg() == 1 || financialAccountBalanceRequest.getToNumber() == null) {
            String toNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(financialAccountBalanceRequest.getDocumentNumberingTypeId(),
                    financialAccountBalanceRequest.getToDate(), SecurityHelper.getCurrentUser().getOrganizationId());
            if (toNumber == null) {
                throw new RuleException("fin.financialAccount.notInformation");
            }
            financialAccountBalanceRequest.setToNumber(toNumber);
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

    private void setFromDateAndToDateCentricTurnOverBalance(FinancialAccountBalanceRequest
                                                                    financialAccountBalanceRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialAccountBalanceRequest.getDocumentNumberingTypeId()
                , financialAccountBalanceRequest.getFromNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialAccountBalanceRequest.setFromDate(fromDate);
        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingAndToNumber(financialAccountBalanceRequest.getDocumentNumberingTypeId()
                , financialAccountBalanceRequest.getToNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialAccountBalanceRequest.setToDate(toDate);
        if (fromDate == null || toDate == null) {
            throw new RuleException("fin.financialAccount.notCorrectDocumentNumber");
        }

    }


    @Override
    @Transactional(readOnly = true)
    public DataSourceResult getFinancialDocumentCentricBalanceReport(DataSourceRequest dataSourceRequest) {
        int length = 0;
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialDocumentCentricBalanceReportRequest financialDocumentCentricBalanceReportRequest = setParameterCentricBalanceReport(filters);
        if (financialDocumentCentricBalanceReportRequest.getFromFinancialAccountCode() != null || financialDocumentCentricBalanceReportRequest.getToFinancialAccountCode() != null) {
            checkFinancialAccountCentricBalanceSet(financialDocumentCentricBalanceReportRequest);
            length = financialDocumentCentricBalanceReportRequest.getFromFinancialAccountCode().length();
        }
        getFinancialDocumentByNumberingTypeAndFromNumberCentricBalance(financialDocumentCentricBalanceReportRequest);
        if (financialDocumentCentricBalanceReportRequest.getDocumentNumberingTypeId() == null) {
            throw new RuleException("fin.financialAccount.insertDocumentNumberingType");
        }

        if (financialDocumentCentricBalanceReportRequest.getDateFilterFlg() == null) {
            throw new RuleException("fin.financialAccount.selectDateFilterFlg");
        }
        financialDocumentCentricBalanceReportRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        List<Object[]> list = getCentricBalanceReportList(financialDocumentCentricBalanceReportRequest, length);
        List<FinancialAccountCentricBalanceResponse> financialAccountCentricBalanceResponses = new ArrayList<>();
        List<FinancialDocumentCentricBalanceResponse> recordsResponseList = new ArrayList<>();
        FinancialAccountCentricBalanceResponse response = new FinancialAccountCentricBalanceResponse();
        list.forEach((Object[] item) -> {

            if (item[10] != null && (Long.parseLong(item[10].toString()) == 1 || Long.parseLong(item[10].toString()) == 2)) {
                FinancialDocumentCentricBalanceResponse recordsResponse = new FinancialDocumentCentricBalanceResponse();
                recordsResponse.setFinancialAccountDescription(getItemForString(item, 0));
                recordsResponse.setFinancialAccountId(getItemForLong(item, 1));

                recordsResponse.setSumDebit(getItemForString(item, 2));
                recordsResponse.setSumCredit(getItemForString(item, 3));
                recordsResponse.setBefDebit(getItemForString(item, 4));
                recordsResponse.setBefCredit(getItemForString(item, 5));
                recordsResponse.setRemDebit(getItemForString(item, 6));
                recordsResponse.setRemCredit(getItemForString(item, 7));
                recordsResponse.setCentricAccountDescription(getItemForString(item, 8));
                recordsResponse.setRecordType(getItemForLong(item, 10));
                recordsResponseList.add(recordsResponse);
                response.setFinancialAccountCentricBalanceRecordsModel(recordsResponseList);
            } else {
                FinancialAccountTurnOverSummarizeResponse accountTurnOverSummarizeResponse = new FinancialAccountTurnOverSummarizeResponse();
                FinancialAccountTurnOverOutputResponse outputResponse = new FinancialAccountTurnOverOutputResponse();
                accountTurnOverSummarizeResponse.setSumDebit(getItemForLong(item, 2));
                accountTurnOverSummarizeResponse.setSumCredit(getItemForLong(item, 3));
                accountTurnOverSummarizeResponse.setBefDebit(getItemForString(item, 4));
                accountTurnOverSummarizeResponse.setBefCredit(getItemForString(item, 5));
                accountTurnOverSummarizeResponse.setRemainDebit(getItemForString(item, 6));
                accountTurnOverSummarizeResponse.setRemainCredit(getItemForString(item, 7));
                accountTurnOverSummarizeResponse.setCentricAccountDescription(getItemForString(item, 8));
                accountTurnOverSummarizeResponse.setSummarizeAmount(getItemForString(item, 9));
                accountTurnOverSummarizeResponse.setRecordType(getItemForLong(item, 10));
                outputResponse.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
                response.setFinancialAccountTurnOverSummarizeModel(accountTurnOverSummarizeResponse);
            }
        });
        financialAccountCentricBalanceResponses.add(response);
        if (financialAccountCentricBalanceResponses.get(0).getFinancialAccountCentricBalanceRecordsModel() == null) {
            throw new RuleException("fin.financialAccount.financialAccountCentricBalanceRecordsModel");
        }
        DataSourceResult dataSourceResult = new DataSourceResult();
        List<FinancialDocumentCentricBalanceResponse> collect = financialAccountCentricBalanceResponses.get(0).getFinancialAccountCentricBalanceRecordsModel()
                .stream()
                .limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip())
                .skip(dataSourceRequest.getSkip())
                .collect(Collectors.toList());

        FinancialDocumentCentricBalanceResponse recordsResponse = new FinancialDocumentCentricBalanceResponse();
        recordsResponse.setFinancialAccountTurnOverSummarizeModel(financialAccountCentricBalanceResponses.get(0).getFinancialAccountTurnOverSummarizeModel());
        collect.add(0, recordsResponse);
        dataSourceResult.setData(collect);
        dataSourceResult.setTotal(list.size());
        return dataSourceResult;

    }

    private FinancialDocumentCentricBalanceReportRequest setParameterCentricBalanceReport
            (List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialDocumentCentricBalanceReportRequest financialDocumentCentricBalanceReportRequest = new FinancialDocumentCentricBalanceReportRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "fromDate":
                    checkFromDateForDateCentricBalance(financialDocumentCentricBalanceReportRequest, item);
                    break;
                case "toDate":
                    checkToDateForDateCentricBalance(financialDocumentCentricBalanceReportRequest, item);
                    break;
                case "documentNumberingTypeId":
                    checkDocumentNumberingTypeIdCentricBalance(financialDocumentCentricBalanceReportRequest, item);
                    break;
                case "fromNumber":
                    checkFromNumberCentricBalanceReport(financialDocumentCentricBalanceReportRequest, item);
                    break;
                case "toNumber":
                    checkToNumberCentricBalanceReport(financialDocumentCentricBalanceReportRequest, item);
                    break;
                case "ledgerTypeId":
                    checkLedgerTypeCentricBalanceReport(financialDocumentCentricBalanceReportRequest, item);
                    break;
                case "remainOption":
                    checkRemindOption(financialDocumentCentricBalanceReportRequest, item);
                    break;
                case "organizationId":
                    if (item.getValue() != null) {
                        financialDocumentCentricBalanceReportRequest.setOrganizationId(Long.parseLong(item.getValue().toString()));
                    }
                    break;

                case "fromFinancialAccountCode":
                    if (item.getValue() != null) {
                        financialDocumentCentricBalanceReportRequest.setFromFinancialAccountCode(item.getValue().toString());
                    }
                    break;
                case "toFinancialAccountCode":
                    if (item.getValue() != null) {
                        financialDocumentCentricBalanceReportRequest.setToFinancialAccountCode(item.getValue().toString());
                    }
                    break;
                case "dateFilterFlg":
                    if (item.getValue() != null) {
                        financialDocumentCentricBalanceReportRequest.setDateFilterFlg(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "cnacId1":
                    checkCnacId1Set(financialDocumentCentricBalanceReportRequest, item);
                    break;
                case "cnacId2":
                    checkCnacId2Set(financialDocumentCentricBalanceReportRequest, item);
                    break;
                case "cnatId1":
                    checkCnacId1CentricBalanceSet(financialDocumentCentricBalanceReportRequest, item);
                    break;

                case "cnatId2":
                    checkCnacId2CentricBalanceSet(financialDocumentCentricBalanceReportRequest, item);
                    break;

                default:
                    break;
            }
        }

        return financialDocumentCentricBalanceReportRequest;

    }

    private void checkFromDateForDateCentricBalance(FinancialDocumentCentricBalanceReportRequest
                                                            financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricBalanceReportRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            financialDocumentCentricBalanceReportRequest.setFromDate(null);
        }
    }

    private void checkToDateForDateCentricBalance(FinancialDocumentCentricBalanceReportRequest
                                                          financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {

        if (item.getValue() != null) {
            financialDocumentCentricBalanceReportRequest.setToDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
        } else {
            financialDocumentCentricBalanceReportRequest.setToDate(null);
        }

    }

    private void checkDocumentNumberingTypeIdCentricBalance(FinancialDocumentCentricBalanceReportRequest
                                                                    financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {
        financialDocumentCentricBalanceReportRequest.setDocumentNumberingTypeId(Long.parseLong(item.getValue().toString()));
    }

    private void checkFromNumberCentricBalanceReport(FinancialDocumentCentricBalanceReportRequest
                                                             financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricBalanceReportRequest.setFromNumber(item.getValue().toString());
        } else {
            financialDocumentCentricBalanceReportRequest.setFromNumber(null);
        }
    }

    private void checkToNumberCentricBalanceReport(FinancialDocumentCentricBalanceReportRequest
                                                           financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricBalanceReportRequest.setToNumber(item.getValue().toString());
        } else {
            financialDocumentCentricBalanceReportRequest.setToNumber(null);
        }
    }

    private void checkLedgerTypeCentricBalanceReport(FinancialDocumentCentricBalanceReportRequest
                                                             financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricBalanceReportRequest.setLedgerTypeId(Long.parseLong(item.getValue().toString()));
        } else {
            financialDocumentCentricBalanceReportRequest.setLedgerTypeId(null);
        }
    }

    private void checkRemindOption(FinancialDocumentCentricBalanceReportRequest
                                           financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {
        if (item.getValue() != null) {
            financialDocumentCentricBalanceReportRequest.setRemainOption(Long.parseLong(item.getValue().toString()));
        } else {
            financialDocumentCentricBalanceReportRequest.setRemainOption(null);
        }
    }

    private void checkCnacId1Set(FinancialDocumentCentricBalanceReportRequest
                                         financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("cnacIdObj1", "cnacIdObj1");
            financialDocumentCentricBalanceReportRequest.setParamMap(map);
            financialDocumentCentricBalanceReportRequest.setCnacId1(Long.parseLong(item.getValue().toString()));
            financialDocumentCentricBalanceReportRequest.setCnacIdObj1(financialDocumentCentricBalanceReportRequest.getCnacId1());
        } else {
            map.put("cnacIdObj1", null);
            financialDocumentCentricBalanceReportRequest.setParamMap(map);
            financialDocumentCentricBalanceReportRequest.setCnacId1(0L);
        }
    }

    private void checkCnacId2Set(FinancialDocumentCentricBalanceReportRequest
                                         financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("cnacIdObj2", "cnacIdObj2");
            financialDocumentCentricBalanceReportRequest.setParamMap(map);
            financialDocumentCentricBalanceReportRequest.setCnacId2(Long.parseLong(item.getValue().toString()));
            financialDocumentCentricBalanceReportRequest.setCnacIdObj2(financialDocumentCentricBalanceReportRequest.getCnacId2());
        } else {
            map.put("cnacIdObj2", null);
            financialDocumentCentricBalanceReportRequest.setParamMap(map);
            financialDocumentCentricBalanceReportRequest.setCnacId2(0L);
        }
    }

    private void checkCnacId1CentricBalanceSet(FinancialDocumentCentricBalanceReportRequest
                                                       financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("cnatIdObj1", "cnatIdObj1");
            financialDocumentCentricBalanceReportRequest.setParamMap(map);
            financialDocumentCentricBalanceReportRequest.setCnatId1(Long.parseLong(item.getValue().toString()));
            financialDocumentCentricBalanceReportRequest.setCnatIdObj1(financialDocumentCentricBalanceReportRequest.getCnatId1());
        } else {
            map.put("cnatIdObj1", null);
            financialDocumentCentricBalanceReportRequest.setParamMap(map);
            financialDocumentCentricBalanceReportRequest.setCnatId1(0L);
        }
    }

    private void checkCnacId2CentricBalanceSet(FinancialDocumentCentricBalanceReportRequest
                                                       financialDocumentCentricBalanceReportRequest, DataSourceRequest.FilterDescriptor item) {
        Map<String, Object> map = new HashMap<>();
        if (item.getValue() != null) {
            map.put("cnatIdObj2", "cnatIdObj2");
            financialDocumentCentricBalanceReportRequest.setParamMap(map);
            financialDocumentCentricBalanceReportRequest.setCnatId2(Long.parseLong(item.getValue().toString()));
            financialDocumentCentricBalanceReportRequest.setCnatIdObj2(financialDocumentCentricBalanceReportRequest.getCnatId2());
        } else {
            map.put("cnatIdObj2", null);
            financialDocumentCentricBalanceReportRequest.setParamMap(map);
            financialDocumentCentricBalanceReportRequest.setCnatId2(0L);
        }
    }

    private void checkFinancialAccountCentricBalanceSet(FinancialDocumentCentricBalanceReportRequest financialDocumentCentricBalanceReportRequest) {
        if (financialDocumentCentricBalanceReportRequest.getFromFinancialAccountCode() == null) {
            financialDocumentCentricBalanceReportRequest.setFromFinancialAccountCode("fin.financialAccount.fromOrToFinancialAccountCode");
        }
        if (financialDocumentCentricBalanceReportRequest.getToFinancialAccountCode() == null) {
            financialDocumentCentricBalanceReportRequest.setToFinancialAccountCode("fin.financialAccount.fromOrToFinancialAccountCode");
        }
        if (financialDocumentCentricBalanceReportRequest.getFromFinancialAccountCode().length() != financialDocumentCentricBalanceReportRequest.getToFinancialAccountCode().length()) {
            throw new RuleException("fin.financialAccount.financialAccountBalance");
        }
    }

    private void getFinancialDocumentByNumberingTypeAndFromNumberCentricBalance(FinancialDocumentCentricBalanceReportRequest
                                                                                        financialDocumentCentricBalanceReportRequest) {

        if (financialDocumentCentricBalanceReportRequest.getDateFilterFlg() == 0) {
            setFromDateAndToDateCentricTurnOverCentricBalance(financialDocumentCentricBalanceReportRequest);
        }
        if (financialDocumentCentricBalanceReportRequest.getDateFilterFlg() == 1 || financialDocumentCentricBalanceReportRequest.getFromNumber() == null) {
            String fromNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialDocumentCentricBalanceReportRequest.getDocumentNumberingTypeId(),
                    financialDocumentCentricBalanceReportRequest.getFromDate()
                    , SecurityHelper.getCurrentUser().getOrganizationId());
            if (fromNumber == null) {
                throw new RuleException("fin.financialAccount.notInformation");
            }
            financialDocumentCentricBalanceReportRequest.setFromNumber(fromNumber);

        }
        if (financialDocumentCentricBalanceReportRequest.getDateFilterFlg() == 1 || financialDocumentCentricBalanceReportRequest.getToNumber() == null) {
            String toNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndToDateAndOrganization(financialDocumentCentricBalanceReportRequest.getDocumentNumberingTypeId(),
                    financialDocumentCentricBalanceReportRequest.getToDate(), SecurityHelper.getCurrentUser().getOrganizationId());
            if (toNumber == null) {
                throw new RuleException("fin.financialAccount.notInformation");
            }
            financialDocumentCentricBalanceReportRequest.setToNumber(toNumber);
        }

        LocalDateTime startDate = financialDocumentCentricBalanceReportRequest.getFromDate();
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

        financialDocumentCentricBalanceReportRequest.setPeriodStartDate(periodStartDate);
    }

    private void setFromDateAndToDateCentricTurnOverCentricBalance(FinancialDocumentCentricBalanceReportRequest
                                                                           financialDocumentCentricBalanceReportRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentCentricBalanceReportRequest.getDocumentNumberingTypeId()
                , financialDocumentCentricBalanceReportRequest.getFromNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentCentricBalanceReportRequest.setFromDate(fromDate);
        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingAndToNumber(financialDocumentCentricBalanceReportRequest.getDocumentNumberingTypeId()
                , financialDocumentCentricBalanceReportRequest.getToNumber(), SecurityHelper.getCurrentUser().getOrganizationId());
        financialDocumentCentricBalanceReportRequest.setToDate(toDate);
        if (fromDate == null || toDate == null) {
            throw new RuleException("fin.financialAccount.notCorrectDocumentNumber");
        }

    }

    private List<Object[]> getCentricBalanceReportList(FinancialDocumentCentricBalanceReportRequest financialDocumentCentricBalanceReportRequest, int length) {
        return financialPeriodRepository.findByFinancialPeriodByCentricBalanceReport(financialDocumentCentricBalanceReportRequest.getFromDate(),
                financialDocumentCentricBalanceReportRequest.getToDate(), financialDocumentCentricBalanceReportRequest.getFromNumber(),
                financialDocumentCentricBalanceReportRequest.getToNumber(), financialDocumentCentricBalanceReportRequest.getDocumentNumberingTypeId(),
                financialDocumentCentricBalanceReportRequest.getLedgerTypeId(),
                financialDocumentCentricBalanceReportRequest.getPeriodStartDate(), length
                , financialDocumentCentricBalanceReportRequest.getFromFinancialAccountCode(), financialDocumentCentricBalanceReportRequest.getToFinancialAccountCode()
                , SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentCentricBalanceReportRequest.getCnacIdObj1(), financialDocumentCentricBalanceReportRequest.getCnacId1(), financialDocumentCentricBalanceReportRequest.getCnacIdObj2(), financialDocumentCentricBalanceReportRequest.getCnacId2()
                , financialDocumentCentricBalanceReportRequest.getCnatIdObj1(), financialDocumentCentricBalanceReportRequest.getCnatId1(),
                financialDocumentCentricBalanceReportRequest.getCnatIdObj2(), financialDocumentCentricBalanceReportRequest.getCnatId2()
                , financialDocumentCentricBalanceReportRequest.getRemainOption());
    }
}


