package ir.demisco.cfs.service.impl;

import com.fasterxml.jackson.databind.util.ISO8601Utils;
import ir.demisco.cfs.model.dto.request.FinancialDocumentReportRequest;
import ir.demisco.cfs.model.dto.response.FinancialAccountReportResponse;
import ir.demisco.cfs.service.api.FinancialAccountService;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
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
            throw new RuleException("لطفا شناسه ی حساب مالی را وارد نمایید.");
        }
        if (financialDocumentReportRequest.getDocumentNumberingTypeId() == null) {
            throw new RuleException("لطفا شناسه ی انواع شماره گذاری سند مالی را وارد نمایید.");
        }
        if (financialDocumentReportRequest.getSummarizingType() == null) {
            throw new RuleException("لطفا نوع جمع بندی را وارد نمایید.");
        }
        if (financialDocumentReportRequest.getOrganizationId() == null) {
            throw new RuleException("لطفا شناسه ی واحد سازمانی را وارد نمایید.");
        }
        if (financialDocumentReportRequest.getDateFilterFlg() == null) {
            throw new RuleException("لطفا فیلتر بر اساس تاریخ یا شماره را انتخاب نمایید.");
        }
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list = financialPeriodRepository.findByFinancialPeriodByParam(100L,
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
        periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganization(100L);

        if (startDate.isBefore(periodStartDate)) {
            periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganizationStartDate(100L, startDate);
        }

        if (periodStartDate == null) {
            periodStartDate = financialPeriodRepository.findByFinancialPeriodByOrganization2(100L);
        }

        if (periodStartDate == null) {
            throw new RuleException("هیچ دوره ی مالی در سازمان یافت نشد.");
        }

        financialDocumentReportRequest.setPeriodStartDate(periodStartDate);
    }

    private void setFromNumberAndToNumber(FinancialDocumentReportRequest financialDocumentReportRequest) {
        String fromNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialDocumentReportRequest.getDocumentNumberingTypeId(),
                financialDocumentReportRequest.getFromDate(), 100L);
        financialDocumentReportRequest.setFromNumber(fromNumber);

        String toNumber = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromDateAndOrganization(financialDocumentReportRequest.getDocumentNumberingTypeId(),
                financialDocumentReportRequest.getToDate(), 100L);
        financialDocumentReportRequest.setFromNumber(toNumber);
    }

    private void setFromDateAndToDate(FinancialDocumentReportRequest financialDocumentReportRequest) {
        LocalDateTime fromDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentReportRequest.getDocumentNumberingTypeId()
                , financialDocumentReportRequest.getFromNumber(), 100L);
        financialDocumentReportRequest.setFromDate(fromDate);

        LocalDateTime toDate = financialDocumentRepository.findByFinancialDocumentByNumberingTypeAndFromNumber(financialDocumentReportRequest.getDocumentNumberingTypeId()
                , financialDocumentReportRequest.getToNumber(), 100L);
        financialDocumentReportRequest.setToDate(toDate);
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
//                        financialDocumentReportRequest.setFromDate(DateUtil.convertStringToDate(item.getValue().toString()));
                        financialDocumentReportRequest.setFromDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));

                    }
                    break;

                case "toDate":
                    if (item.getValue() != null) {
//                        financialDocumentReportRequest.setToDate(DateUtil.convertStringToDate(item.getValue().toString()));
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
}


