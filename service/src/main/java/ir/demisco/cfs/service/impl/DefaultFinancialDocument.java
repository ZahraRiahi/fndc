package ir.demisco.cfs.service.impl;


import com.fasterxml.jackson.databind.util.ISO8601Utils;
import ir.demisco.cfs.model.dto.request.ControlFinancialAccountNatureTypeInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentSecurityInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.response.*;
import ir.demisco.cfs.model.entity.*;
import ir.demisco.cfs.service.api.ControlFinancialAccountNatureTypeService;
import ir.demisco.cfs.service.api.FinancialDocumentSecurityService;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static ir.demisco.cloud.core.middle.service.system.impl.MessageBundleImpl.message;

@Service
public class DefaultFinancialDocument implements FinancialDocumentService {


    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialDocumentStatusRepository documentStatusRepository;
    private final FinancialDocumentItemRepository financialDocumentItemRepository;
    private final FinancialDocumentReferenceRepository financialDocumentReferenceRepository;
    private final FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository;
    private final FinancialAccountRepository financialAccountRepository;
    private final EntityManager entityManager;
    private final CentricAccountRepository centricAccountRepository;
    private final FinancialNumberingFormatRepository financialNumberingFormatRepository;
    private final NumberingFormatSerialRepository numberingFormatSerialRepository;
    private final FinancialDocumentNumberRepository financialDocumentNumberRepository;
    private final FinancialNumberingTypeRepository financialNumberingTypeRepository;
    private final FinancialDocumentItemCurrencyRepository financialDocumentItemCurrencyRepository;
    private final ControlFinancialAccountNatureTypeService controlFinancialAccountNatureTypeService;
    private final FinancialPeriodService financialPeriodService;
    private final FinancialDocumentSecurityService financialDocumentSecurityService;

    public DefaultFinancialDocument(FinancialDocumentRepository financialDocumentRepository, FinancialDocumentStatusRepository documentStatusRepository,
                                    FinancialDocumentItemRepository financialDocumentItemRepository,
                                    FinancialDocumentReferenceRepository financialDocumentReferenceRepository,
                                    FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository, FinancialAccountRepository financialAccountRepository,
                                    EntityManager entityManager, CentricAccountRepository centricAccountRepository, AccountDefaultValueRepository accountDefaultValueRepository, FinancialNumberingFormatRepository financialNumberingFormatRepository, NumberingFormatSerialRepository numberingFormatSerialRepository, FinancialDocumentNumberRepository financialDocumentNumberRepository, FinancialNumberingTypeRepository financialNumberingTypeRepository, FinancialDocumentItemCurrencyRepository financialDocumentItemCurrencyRepository, ControlFinancialAccountNatureTypeService controlFinancialAccountNatureTypeService, FinancialPeriodService financialPeriodService, FinancialDocumentSecurityService financialDocumentSecurityService) {
        this.financialDocumentRepository = financialDocumentRepository;
        this.documentStatusRepository = documentStatusRepository;
        this.financialDocumentItemRepository = financialDocumentItemRepository;
        this.financialDocumentReferenceRepository = financialDocumentReferenceRepository;
        this.documentItemCurrencyRepository = documentItemCurrencyRepository;
        this.financialAccountRepository = financialAccountRepository;
        this.entityManager = entityManager;
        this.centricAccountRepository = centricAccountRepository;
        this.financialNumberingFormatRepository = financialNumberingFormatRepository;
        this.numberingFormatSerialRepository = numberingFormatSerialRepository;
        this.financialDocumentNumberRepository = financialDocumentNumberRepository;
        this.financialNumberingTypeRepository = financialNumberingTypeRepository;
        this.financialDocumentItemCurrencyRepository = financialDocumentItemCurrencyRepository;
        this.controlFinancialAccountNatureTypeService = controlFinancialAccountNatureTypeService;
        this.financialPeriodService = financialPeriodService;
        this.financialDocumentSecurityService = financialDocumentSecurityService;
    }


    @Override
    @Transactional
    public DataSourceResult getFinancialDocumentList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        ResponseFinancialDocumentDto paramSearch = setParameter(filters);
        Map<String, Object> paramMap = paramSearch.getParamMap();
//        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip() / (dataSourceRequest.getTake() / 2)  , dataSourceRequest.getTake() + dataSourceRequest.getSkip());
        List<Object[]> list = financialDocumentRepository.getFinancialDocumentList(SecurityHelper.getCurrentUser().getOrganizationId(), paramSearch.getActivityCode(), SecurityHelper.getCurrentUser().getUserId(), paramSearch.getDepartmentId(), SecurityHelper.getCurrentUser().getUserId(), paramSearch.getStartDate(),
                paramSearch.getEndDate(), paramSearch.getPriceTypeId(), paramSearch.getFinancialNumberingTypeId(), paramMap.get("fromNumber"), paramSearch.getFromNumber(),
                paramMap.get("toNumber"), paramSearch.getToNumber(), paramSearch.getDescription(), paramMap.get("fromAccount"), paramSearch.getFromAccountCode(),
                paramMap.get("toAccount"), paramSearch.getToAccountCode(), paramMap.get("centricAccount"), paramSearch.getCentricAccountId()
                , paramMap.get("centricAccountType"), paramSearch.getCentricAccountTypeId(), paramMap.get("documentUser"), paramSearch.getDocumentUserId()
                , paramMap.get("priceType"), paramMap.get("fromPrice"), paramSearch.getFromPrice(), paramMap.get("toPrice"),
                paramSearch.getToPrice(), paramSearch.getTolerance(), paramSearch.getFinancialDocumentStatusDtoListId(), paramMap.get("financialDocumentType"), paramSearch.getFinancialDocumentTypeId());
        List<FinancialDocumentDto> documentDtoList = list.stream().map(item ->
                FinancialDocumentDto.builder()
                        .id(((BigDecimal) item[0]).longValue())
                        .documentDate((Date) item[1])
                        .description(item[2].toString())
                        .documentNumber(item[3].toString())
                        .financialDocumentTypeId(Long.parseLong(item[4].toString()))
                        .financialDocumentTypeDescription(item[5].toString())
                        .fullDescription(item[6].toString())
                        .debitAmount(Long.parseLong(String.format("%.0f", Double.parseDouble(item[7].toString()))))
                        .creditAmount(Long.parseLong(String.format("%.0f", Double.parseDouble(item[8].toString()))))
                        .userId(Long.parseLong(item[9].toString()))
                        .userName(item[10].toString())
                        .financialDocumentStatusId(Long.parseLong(item[11].toString()))
                        .financialDocumentStatusName(item[12].toString())
                        .financialDocumentStatusCode(item[13].toString())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(documentDtoList.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setTotal(list.size());
        return dataSourceResult;
    }

    private ResponseFinancialDocumentDto setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        ResponseFinancialDocumentDto responseFinancialDocumentDto = new ResponseFinancialDocumentDto();
        Map<String, Object> map = new HashMap<>();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "activityCode":
                    responseFinancialDocumentDto.setActivityCode(item.getValue().toString());
                    break;
                case "departmentId":
                    responseFinancialDocumentDto.setDepartmentId(Long.parseLong(item.getValue().toString()));
                    break;


                case "startDate":
                    responseFinancialDocumentDto.setStartDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
                    break;

                case "endDate":
                    responseFinancialDocumentDto.setEndDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
                    break;

                case "financialNumberingType.id":
                    responseFinancialDocumentDto.setFinancialNumberingTypeId(Long.parseLong(item.getValue().toString()));
                    break;

                case "priceType.id":
                    if (item.getValue() != null) {
                        map.put("priceType", "priceType");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setPriceTypeId(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("priceType", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setPriceTypeId(0L);
                    }
                    break;

                case "fromNumber.id":
                    if (item.getValue() != null) {
                        map.put("fromNumber", "fromNumber");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromNumber(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("fromNumber", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromNumber(0L);
                    }
                    break;

                case "toNumber.id":
                    if (item.getValue() != null) {
                        map.put("toNumber", "toNumber");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToNumber(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("toNumber", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToNumber(0L);
                    }
                    break;

                case "financialDocumentStatusDtoList":
                    responseFinancialDocumentDto.setFinancialDocumentStatusDtoListId((List<Long>) item.getValue());
                    break;

                case "description":
                    if (item.getValue() != null) {
                        responseFinancialDocumentDto.setDescription(item.getValue().toString());
                    } else {
                        responseFinancialDocumentDto.setDescription("");
                    }
                    break;

                case "fromAccount.code":
                    if (item.getValue() != null) {
                        map.put("fromAccount", "fromAccount");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromAccountCode(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("fromAccount", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromAccountCode(0L);
                    }
                    break;

                case "toAccount.code":
                    if (item.getValue() != null) {
                        map.put("toAccount", "toAccount");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToAccountCode(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("toAccount", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToAccountCode(0L);
                    }
                    break;

                case "centricAccount.id":
                    if (item.getValue() != null) {
                        map.put("centricAccount", "centricAccount");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setCentricAccountId(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("centricAccount", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setCentricAccountId(0L);
                    }
                    break;
                case "centricAccountType.id":
                    if (item.getValue() != null) {
                        map.put("centricAccountType", "centricAccountType");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setCentricAccountTypeId(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("centricAccountType", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setCentricAccountTypeId(0L);
                    }
                    break;

                case "documentUser.id":
                    if (item.getValue() != null) {
                        map.put("documentUser", "documentUser");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setDocumentUserId(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("documentUser", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setDocumentUserId(0L);
                    }
                    break;

//                case "priceType.id":
//                    if (item.getValue() != null) {
//                        map.put("priceType", "priceType");
//                        responseFinancialDocumentDto.setParamMap(map);
//                        responseFinancialDocumentDto.setPriceTypeId(Long.parseLong(item.getValue().toString()));
//                    } else {
//                        map.put("priceType", null);
//                        responseFinancialDocumentDto.setParamMap(map);
//                        responseFinancialDocumentDto.setPriceTypeId(0L);
//                    }
//                    break;

                case "fromPriceAmount":
                    if (item.getValue() != null) {
                        map.put("fromPrice", "fromPrice");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromPrice(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("fromPrice", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromPrice(0L);
                    }
                    break;

                case "toPriceAmount":
                    if (item.getValue() != null) {
                        map.put("toPrice", "toPrice");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToPrice(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("toPrice", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToPrice(0L);
                    }
                    break;

                case "tolerance":
                    if (item.getValue() != null) {
                        responseFinancialDocumentDto.setTolerance(Double.parseDouble(item.getValue().toString()));
                    } else {
                        responseFinancialDocumentDto.setTolerance(0D);
                    }

                    break;

                case "financialDocumentType.id":
                    if (item.getValue() != null) {
                        map.put("financialDocumentType", "financialDocumentType");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFinancialDocumentTypeId(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("financialDocumentType", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFinancialDocumentTypeId(0L);
                    }
                    break;
            }
        }
        return responseFinancialDocumentDto;
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
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<ResponseFinancialDocumentSetStatusDto> changeStatus(ResponseFinancialDocumentStatusDto responseFinancialDocumentStatusDto) {
        String activityCode = "FNDC_DOCUMENT_CONF";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(responseFinancialDocumentStatusDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        ResponseFinancialDocumentSetStatusDto responseFinancialDocumentSetStatusDto = new ResponseFinancialDocumentSetStatusDto();
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(responseFinancialDocumentStatusDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }
        FinancialDocument financialDocument = financialDocumentRepository.findById(responseFinancialDocumentStatusDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialDocumentStatus financialDocumentStatus =
                documentStatusRepository.findFinancialDocumentStatusByCode(responseFinancialDocumentStatusDto.getFinancialDocumentStatusCode());
        if (responseFinancialDocumentStatusDto.getFinancialDocumentStatusCode().equals("20") ||
                responseFinancialDocumentStatusDto.getFinancialDocumentStatusCode().equals("30")) {
            List<FinancialDocumentErrorDto> financialDocumentErrorDtoList = validationSetStatus(financialDocument);
            if (financialDocumentErrorDtoList.isEmpty()) {
                financialDocument.setFinancialDocumentStatus(financialDocumentStatus);
                financialDocumentRepository.save(financialDocument);
                responseFinancialDocumentSetStatusDto = convertFinancialDocumentToDto(financialDocument);
                return ResponseEntity.ok(responseFinancialDocumentSetStatusDto);
            } else {
                responseFinancialDocumentSetStatusDto.setFinancialDocumentErrorDtoList(financialDocumentErrorDtoList);
                responseFinancialDocumentSetStatusDto.setErrorFoundFlag(true);
                return ResponseEntity.status(HttpStatus.CONFLICT).body(responseFinancialDocumentSetStatusDto);
            }
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseFinancialDocumentSetStatusDto);
        } else {

            financialDocument.setFinancialDocumentStatus(financialDocumentStatus);
            financialDocumentRepository.save(financialDocument);
            responseFinancialDocumentSetStatusDto = convertFinancialDocumentToDto(financialDocument);
            return ResponseEntity.ok(responseFinancialDocumentSetStatusDto);
        }

//        return responseFinancialDocumentSetStatusDto;
    }

    private List<FinancialDocumentErrorDto> validationSetStatus(FinancialDocument financialDocument) {
        List<FinancialDocumentErrorDto> financialDocumentErrorDtoList = new ArrayList<>();
        List<FinancialDocumentItem> documentItemList = financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocument.getId());
        if (documentItemList.isEmpty()) {
            FinancialDocumentErrorDto documentItem = new FinancialDocumentErrorDto();
            documentItem.setFinancialDocumentId(financialDocument.getId());
            documentItem.setMessage("سند بدون ردیف است.");
            financialDocumentErrorDtoList.add(documentItem);
        } else {
            documentItemList.forEach(documentItem -> {
                double creditAmount = documentItem.getCreditAmount() % 1;
                double debitAmount = documentItem.getDebitAmount() % 1;

                if ((documentItem.getCreditAmount() == 0) && (documentItem.getDebitAmount() == 0)) {
                    FinancialDocumentErrorDto checkZeroAmount = new FinancialDocumentErrorDto();
                    checkZeroAmount.setFinancialDocumentId(financialDocument.getId());
                    checkZeroAmount.setFinancialDocumentItemId(documentItem.getId());
                    checkZeroAmount.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
                    checkZeroAmount.setMessage("در ردیف  مبلغ بستانکار و بدهکار صفر می باشد");
                    financialDocumentErrorDtoList.add(checkZeroAmount);
                }

                if ((documentItem.getCreditAmount() != 0) && (documentItem.getDebitAmount() != 0)) {
                    FinancialDocumentErrorDto checkCostAmount = new FinancialDocumentErrorDto();
                    checkCostAmount.setFinancialDocumentId(financialDocument.getId());
                    checkCostAmount.setFinancialDocumentItemId(documentItem.getId());
                    checkCostAmount.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
                    checkCostAmount.setMessage("در ردیف  مبلغ بستانکار و بدهکار هر دو دارای مقدار می باشد.");
                    financialDocumentErrorDtoList.add(checkCostAmount);
                }

                if (documentItem.getDescription() == null) {
                    FinancialDocumentErrorDto documentItemDescription = new FinancialDocumentErrorDto();
                    documentItemDescription.setFinancialDocumentId(financialDocument.getId());
                    documentItemDescription.setFinancialDocumentItemId(documentItem.getId());
                    documentItemDescription.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
                    documentItemDescription.setMessage("در سند ردیف بدون شرح وجود دارد.");
                    financialDocumentErrorDtoList.add(documentItemDescription);
                }

                if ((creditAmount != 0) || (debitAmount != 0)) {
                    FinancialDocumentErrorDto itemAmount = new FinancialDocumentErrorDto();
                    itemAmount.setFinancialDocumentId(financialDocument.getId());
                    itemAmount.setFinancialDocumentItemId(documentItem.getId());
                    itemAmount.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
                    itemAmount.setMessage("مبلغ در ردیف اعشاری است.");
                    financialDocumentErrorDtoList.add(itemAmount);
                }

                if ((documentItem.getCreditAmount() < 0) || (documentItem.getDebitAmount() < 0)) {
                    FinancialDocumentErrorDto documentItemAmount = new FinancialDocumentErrorDto();
                    documentItemAmount.setFinancialDocumentId(financialDocument.getId());
                    documentItemAmount.setFinancialDocumentItemId(documentItem.getId());
                    documentItemAmount.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
                    documentItemAmount.setMessage("در ردیف سند مبلغ بستانکار یا بدهکار  منفی می باشد.");
                    financialDocumentErrorDtoList.add(documentItemAmount);
                }

                Long documentReference = financialDocumentReferenceRepository.getDocumentReference(documentItem.getId());
                if (documentReference != null) {
                    FinancialDocumentErrorDto FinancialDocumentReference = new FinancialDocumentErrorDto();
                    FinancialDocumentReference.setFinancialDocumentId(financialDocument.getId());
                    FinancialDocumentReference.setFinancialDocumentItemId(documentItem.getId());
                    FinancialDocumentReference.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
                    FinancialDocumentReference.setMessage("تاریخ و شرح در مدارک مرجع پر نشده.");
                    financialDocumentErrorDtoList.add(FinancialDocumentReference);
                }

                Long financialDocumentItemInfoCurrency = financialDocumentItemRepository.getInfoCurrency(documentItem.getId());
                if (financialDocumentItemInfoCurrency != null) {
                    FinancialDocumentErrorDto infoCurrency = new FinancialDocumentErrorDto();
                    infoCurrency.setFinancialDocumentId(financialDocument.getId());
                    infoCurrency.setFinancialDocumentItemId(documentItem.getId());
                    infoCurrency.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
                    infoCurrency.setMessage("لطفا اطلاعات ارزی را برای ردیفهای ارزی ، به صورت کامل وارد نمایید");
                    financialDocumentErrorDtoList.add(infoCurrency);
                }

                Long financialDocumentEqualCurrency = financialDocumentItemRepository.equalCurrency(documentItem.getId());
                if (financialDocumentEqualCurrency != null) {
                    FinancialDocumentErrorDto equalCurrency = new FinancialDocumentErrorDto();
                    equalCurrency.setFinancialDocumentId(financialDocument.getId());
                    equalCurrency.setFinancialDocumentItemId(documentItem.getId());
                    equalCurrency.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
                    equalCurrency.setMessage("نوع ارز انتخاب شده در ردیفهای ارزی سند ، با نوع ارز یکسان نمیباشد");
                    financialDocumentErrorDtoList.add(equalCurrency);
                }
//                List<AccountDefaultValue> accountDefaultValueList = accountDefaultValueRepository.getAccountDefaultValueByDocumentItemId(documentItem.getId());
//                accountDefaultValueList.forEach(defaultValue -> {
//                    if ((documentItem.getCentricAccountId1() != defaultValue.getCentricAccount()) ||
//                            (documentItem.getCentricAccountId2() != defaultValue.getCentricAccount()) ||
//                            (documentItem.getCentricAccountId3() != defaultValue.getCentricAccount()) ||
//                            (documentItem.getCentricAccountId4() != defaultValue.getCentricAccount()) ||
//                            (documentItem.getCentricAccountId5() != defaultValue.getCentricAccount()) ||
//                            (documentItem.getCentricAccountId6() != defaultValue.getCentricAccount())) {
//                        FinancialDocumentErrorDto centricAccount = new FinancialDocumentErrorDto();
//                        centricAccount.setFinancialDocumentId(financialDocument.getId());
//                        centricAccount.setFinancialDocumentItemId(documentItem.getId());
//                        centricAccount.setFinancialDocumentItemSequence(documentItem.getSequenceNumber());
//                        centricAccount.setMessage("کد / کدهای تمرکز با تمرکز های حساب ، همخوانی ندارد");
//                        financialDocumentErrorDtoList.add(centricAccount);
//                    }
//                });

            });
        }

        FinancialDocument document = financialDocumentRepository.getActivePeriodAndMontInDocument(financialDocument.getId());
        if (document == null) {
            FinancialDocumentErrorDto activeMountStatus = new FinancialDocumentErrorDto();
            activeMountStatus.setFinancialDocumentId(financialDocument.getId());
            activeMountStatus.setMessage("دوره / ماه عملیاتی میبایست در وضعیت باز باشد");
            financialDocumentErrorDtoList.add(activeMountStatus);
        }

        Long cost = financialDocumentItemRepository.getCostDocument(financialDocument.getId());
        if (cost == null) {
            FinancialDocumentErrorDto financialDocumentCost = new FinancialDocumentErrorDto();
            financialDocumentCost.setFinancialDocumentId(financialDocument.getId());
            financialDocumentCost.setMessage("مجموع مبالغ بستانکار و بدهکار در ردیف های سند بالانس نیست.");
            financialDocumentErrorDtoList.add(financialDocumentCost);
        }

        FinancialDocument documentPeriod = financialDocumentRepository.getActivePeriodInDocument(financialDocument.getId());
        if (documentPeriod == null) {
            FinancialDocumentErrorDto financialDocumentCost = new FinancialDocumentErrorDto();
            financialDocumentCost.setFinancialDocumentId(financialDocument.getId());
            financialDocumentCost.setMessage("وضعیت دوره مالی مربوط به سند بسته است.");
            financialDocumentErrorDtoList.add(financialDocumentCost);
        }

        ControlFinancialAccountNatureTypeInputRequest controlFinancialAccountNatureTypeInputRequest = new ControlFinancialAccountNatureTypeInputRequest();
        controlFinancialAccountNatureTypeInputRequest.setFinancialDocumentId(financialDocument.getId());
        List<ControlFinancialAccountNatureTypeOutputResponse> controlFinancialAccountNatureTypeList = controlFinancialAccountNatureTypeService.getControlFinancialAccountNatureType(controlFinancialAccountNatureTypeInputRequest);

        controlFinancialAccountNatureTypeList.forEach(e -> {
            FinancialDocumentErrorDto financialDocumentCost = new FinancialDocumentErrorDto();
            financialDocumentCost.setMessage(e.getResultMessage());
            financialDocumentCost.setFinancialDocumentId(financialDocument.getId());
            financialDocumentErrorDtoList.add(financialDocumentCost);
        });


        Long financialDocumentItemAccount = financialDocumentItemRepository.getFinancialAccount(financialDocument.getId());
        if (financialDocumentItemAccount == null) {
            FinancialDocumentErrorDto financialAccount = new FinancialDocumentErrorDto();
            financialAccount.setFinancialDocumentId(financialDocument.getId());
            financialAccount.setMessage(" حساب انتخاب شده  روی یک / چند ردیف از سند ، آخرین سطح حساب نمی باشد");
            financialDocumentErrorDtoList.add(financialAccount);
        }

        Long financialDocumentItemCostHarmony = financialDocumentItemRepository.costHarmony(financialDocument.getId());
        if (financialDocumentItemCostHarmony != null) {
            FinancialDocumentErrorDto costHarmony = new FinancialDocumentErrorDto();
            costHarmony.setFinancialDocumentId(financialDocument.getId());
            costHarmony.setMessage(" مبالغ بدهکار یا بستانکار ردیف یا ردیفها با مبالغ بدهکار یا بستانکار ارزی همخوانی ندارد.");
            financialDocumentErrorDtoList.add(costHarmony);
        }

        Long financialDocumentItemReferenceCode = financialDocumentItemRepository.referenceCode(financialDocument.getId());
        if (financialDocumentItemReferenceCode != null) {
            FinancialDocumentErrorDto referenceCode = new FinancialDocumentErrorDto();
            referenceCode.setFinancialDocumentId(financialDocument.getId());
            referenceCode.setMessage(" کدهای تمرکز ثبت شده باید با کد تمرکز مرجع خود همخوانی داشته باشد");
            financialDocumentErrorDtoList.add(referenceCode);
        }

        if (financialDocument.getDescription() == null) {
            FinancialDocumentErrorDto documentDescription = new FinancialDocumentErrorDto();
            documentDescription.setFinancialDocumentId(financialDocument.getId());
            documentDescription.setMessage("سند بدون شرح است.");
            financialDocumentErrorDtoList.add(documentDescription);
        }

        return financialDocumentErrorDtoList;
    }

    private ResponseFinancialDocumentSetStatusDto convertFinancialDocumentToDto(FinancialDocument financialDocument) {
        List<Object[]> objects = financialDocumentItemRepository.findParamByDocumentId(financialDocument.getId());
        return ResponseFinancialDocumentSetStatusDto.builder()
                .id(financialDocument.getId())
                .documentDate(financialDocument.getDocumentDate())
                .documentNumber(financialDocument.getDocumentNumber())
                .financialDocumentTypeId(financialDocument.getFinancialDocumentType().getId())
                .financialDocumentTypeDescription(financialDocument.getFinancialDocumentType().getDescription())
                .userId(financialDocument.getCreator().getId())
                .userName(financialDocument.getCreator().getUsername())
                .description(financialDocument.getDescription())
                .debitAmount(((BigDecimal) objects.get(0)[0]).doubleValue())
                .creditAmount(((BigDecimal) objects.get(0)[1]).doubleValue())
                .fullDescription(objects.get(0)[2] == null ? null : objects.get(0)[2].toString())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String creatDocumentNumber(FinancialDocumentNumberDto financialDocumentNumberDto) {
        List<FinancialNumberingRecordDto> financialNumberingRecordDtoList = new ArrayList<>();
        AtomicReference<String> documentNumber = new AtomicReference<>("");

        List<Object[]> list = financialDocumentRepository.getSerialNumber(SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentNumberDto.getFinancialDocumentId(), financialDocumentNumberDto.getNumberingType());
        if (!list.isEmpty()) {
            list.forEach(objects -> {
                FinancialNumberingFormat financialNumberingFormat =
                        financialNumberingFormatRepository.findById(Long.parseLong(objects[0].toString())).orElseThrow(() -> new RuleException("fin.financialDocument.notExistFinancialNumberingFormat"));
                NumberingFormatSerial searchNumberingFormatSerial = numberingFormatSerialRepository.findByNumberingFormatAndDeletedDate(financialNumberingFormat.getId(), objects[2].toString(), Long.parseLong(objects[3].toString()));
                if (searchNumberingFormatSerial == null) {
                    NumberingFormatSerial numberingFormatSerial = new NumberingFormatSerial();
                    numberingFormatSerial.setFinancialNumberingFormat(financialNumberingFormat);
                    numberingFormatSerial.setLastSerial(Long.parseLong(objects[1].toString()));
                    numberingFormatSerial.setSerialReseter(objects[2].toString());
                    numberingFormatSerial.setSerialLength(Long.parseLong(objects[3].toString()));
                    numberingFormatSerialRepository.save(numberingFormatSerial);
                } else {
                    throw new RuleException("fin.financialDocument.serialNumber");
                }

            });
        }

        List<NumberingFormatSerial> numberingFormatSerialList =
                numberingFormatSerialRepository.findNumberingFormatSerialByParam(SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentNumberDto.getFinancialDocumentId(), financialDocumentNumberDto.getNumberingType());
        numberingFormatSerialList.forEach(numberingFormatSerial -> {
            numberingFormatSerial.setLastSerial(numberingFormatSerial.getLastSerial() + 1);
            numberingFormatSerialRepository.save(numberingFormatSerial);
        });

        List<Object[]> listDocumentNumber =
                financialDocumentRepository.findDocumentNumber(SecurityHelper.getCurrentUser().getOrganizationId(), financialDocumentNumberDto.getFinancialDocumentId(), financialDocumentNumberDto.getNumberingType());
        listDocumentNumber.forEach(documentNumberObject -> {
            FinancialDocumentNumber financialDocumentNumber = new FinancialDocumentNumber();
            FinancialNumberingRecordDto financialNumberingRecordDto = new FinancialNumberingRecordDto();
            financialNumberingRecordDto.setFinancialDocumentId(financialDocumentNumberDto.getFinancialDocumentId());
            financialNumberingRecordDto.setNumberingTypeId(Long.parseLong(documentNumberObject[0].toString()));
            financialNumberingRecordDto.setFinancialDocumentNumber(documentNumberObject[1].toString());
            financialDocumentNumber.setFinancialDocument(financialDocumentRepository.getOne(financialNumberingRecordDto.getFinancialDocumentId()));
            financialDocumentNumber.setFinancialNumberingType(
                    financialNumberingTypeRepository.getOne(financialNumberingRecordDto.getNumberingTypeId()));
            financialDocumentNumber.setDocumentNumber(financialNumberingRecordDto.getFinancialDocumentNumber());
            financialDocumentNumberRepository.save(financialDocumentNumber);
            financialNumberingRecordDtoList.add(financialNumberingRecordDto);

        });

        financialNumberingRecordDtoList.forEach(record -> {
            if (record.getNumberingTypeId() == 2) {
                documentNumber.set(record.getFinancialDocumentNumber());
            }
        });
        return documentNumber.get();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String changeDescription(FinancialDocumentChangeDescriptionDto financialDocumentDto) {
        FinancialDocument financialDocument = financialDocumentRepository.getActiveDocumentById(financialDocumentDto.getId());
        if (financialDocument == null) {
            throw new RuleException("fin.financialDocument.notExistDocument");
        }
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialDocumentDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.getDocumentDescription(financialDocumentDto.getFinancialDocumentItemIdList(), financialDocumentDto.getOldDescription());
        if (financialDocumentItemList.isEmpty()) {
            throw new RuleException("fin.financialDocument.notFoundRecordWithParam");
        }
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(financialDocumentDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }
        entityManager.createNativeQuery(" update fndc.financial_document_item " +
                "   set description = replace(description,:description,:newDescription) " +
                "   where id in (:FinancialDocumentItemIdList) " +
                "   And description like '%'|| :description ||'%'").setParameter("description", financialDocumentDto.getOldDescription())
                .setParameter("newDescription", financialDocumentDto.getNewDescription())
                .setParameter("FinancialDocumentItemIdList", financialDocumentDto.getFinancialDocumentItemIdList()).executeUpdate();

        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
        financialDocumentRepository.save(financialDocument);
        return "عملیات با موفقیت انجام شد.";
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public boolean deleteFinancialDocumentById(Long financialDocumentId) {
        FinancialDocument document = financialDocumentRepository.findById(financialDocumentId).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        String activityCode = "FNDC_DOCUMENT_DELETE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialDocumentId);
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        FinancialDocument financialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(document.getId());
        if (financialDocument == null) {
            throw new RuleException("fin.financialDocument.openStatusPeriod");
        } else {
            List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocumentId);
            deleteDocumentItem(financialDocumentItemList);
            List<FinancialDocumentNumber> financialDocumentNumberList = financialDocumentNumberRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocumentId);
            financialDocumentNumberList.forEach(financialDocumentNumber -> financialDocumentNumberRepository.deleteById((financialDocumentNumber.getId())));
            financialDocumentNumberRepository.flush();
            financialDocumentRepository.deleteById(financialDocument.getId());
        }
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteDocumentItem(List<FinancialDocumentItem> financialDocumentItemList) {
        financialDocumentItemList.forEach(documentItem -> {
            Long documentItemByIdForDelete = financialDocumentItemRepository.getDocumentItemByIdForDelete(documentItem.getId());
            if (documentItemByIdForDelete > 0) {
                FinancialDocumentReference financialDocumentReference = financialDocumentReferenceRepository.getByDocumentItemId(documentItem.getId());
                if (financialDocumentReference != null) {
                    financialDocumentReferenceRepository.deleteById(financialDocumentReference.getId());
                }
                FinancialDocumentItemCurrency financialDocumentItemCurrency = documentItemCurrencyRepository.getByDocumentItemId(documentItem.getId());
                if (financialDocumentItemCurrency != null) {
                    documentItemCurrencyRepository.deleteById(financialDocumentItemCurrency.getId());
                }
                financialDocumentItemRepository.deleteById(documentItem.getId());

            } else {
                financialDocumentItemRepository.deleteById(documentItem.getId());
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String changeAccountDocument(FinancialDocumentAccountDto financialDocumentAccountDto) {
        FinancialDocument financialDocument = financialDocumentRepository.findById(financialDocumentAccountDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(financialDocumentAccountDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }
        AtomicReference<String> message = new AtomicReference<>("");
        if (financialDocumentAccountDto.getFinancialAccountId().equals(financialDocumentAccountDto.getNewFinancialAccountId())) {
            throw new RuleException("fin.financialDocument.sameDocumentAccount");
        }
        List<FinancialDocumentItem> financialDocumentItemList =
                financialDocumentItemRepository.getItemByDocumentItemIdListAndAccountId(financialDocumentAccountDto.getFinancialDocumentItemIdList(),
                        financialDocumentAccountDto.getFinancialAccountId());
        if (financialDocumentItemList.size() == 0) {
            throw new RuleException("ردیفی با این حساب یافت نشد.");
        }
        financialDocumentItemList.forEach(documentItem -> {
            documentItem.setFinancialAccount(financialAccountRepository.getOne(financialDocumentAccountDto.getNewFinancialAccountId()));
            financialDocumentItemRepository.save(documentItem);
            FinancialAccount financialAccount = financialAccountRepository.getFinancialAccountRelationType(financialDocumentAccountDto.getFinancialAccountId(),
                    financialDocumentAccountDto.getNewFinancialAccountId());
            if (financialAccount == null) {
                documentItem.setCentricAccountId1(null);
                documentItem.setCentricAccountId2(null);
                documentItem.setCentricAccountId3(null);
                documentItem.setCentricAccountId4(null);
                documentItem.setCentricAccountId5(null);
                documentItem.setCentricAccountId6(null);
                message.set(message("به دلیل انواع تفاوت در وابستگی حساب جدید با حساب قبلی،تمام تمرکز های مربوطه حذف شده.لطفا تمرکز های مربوطه را درج نمایید."));
                financialDocumentItemRepository.save(documentItem);
            } else {
                message.set(message("عملیات با موفقیت انجام شد."));
            }

        });
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
        financialDocumentRepository.save(financialDocument);
        return message.get();
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public String changeCentricAccount(FinancialCentricAccountDto financialCentricAccountDto) {
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        FinancialDocument document = financialDocumentRepository.findById(financialCentricAccountDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        CentricAccount centricAccount = centricAccountRepository.findById(financialCentricAccountDto.getCentricAccountId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistCentricAccount"));
        CentricAccount newCentricAccount = centricAccountRepository.findById(financialCentricAccountDto.getNewCentricAccountId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistCentricAccount"));
        AtomicInteger i = new AtomicInteger(0);
        if (centricAccount.getCentricAccountType().getId().equals(newCentricAccount.getCentricAccountType().getId())) {
            List<FinancialDocumentItem> financialDocumentItemList =
                    financialDocumentItemRepository.getByDocumentIdAndCentricAccount(financialCentricAccountDto.getFinancialDocumentItemIdList(), financialCentricAccountDto.getNewCentricAccountId(), financialCentricAccountDto.getFinancialAccountId());
            if (financialDocumentItemList.isEmpty()) {
                throw new RuleException("fin.financialDocument.notExistDocumentItem");
            }
            FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
            financialPeriodStatusRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
            FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
            if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
                throw new RuleException("دوره مالی و ماه عملیاتی سند مبدا میبایست در وضعیت باز باشند");
            }
            Long centricAccountId = financialCentricAccountDto.getCentricAccountId();
            financialDocumentItemList.forEach(documentItem -> {

                if ((documentItem.getCentricAccountId1() != null) && (centricAccountId.equals(documentItem.getCentricAccountId1().getId()))) {
                    i.getAndIncrement();
                    documentItem.setCentricAccountId1(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                    documentItem.setCentricAccountId2(null);
                    documentItem.setCentricAccountId3(null);
                    documentItem.setCentricAccountId4(null);
                    documentItem.setCentricAccountId5(null);
                    documentItem.setCentricAccountId6(null);
                } else if (documentItem.getCentricAccountId2() != null && centricAccountId.equals(documentItem.getCentricAccountId2().getId())) {
                    i.getAndIncrement();
                    documentItem.setCentricAccountId2(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                    documentItem.setCentricAccountId3(null);
                    documentItem.setCentricAccountId4(null);
                    documentItem.setCentricAccountId5(null);
                    documentItem.setCentricAccountId6(null);
                } else if (documentItem.getCentricAccountId3() != null && centricAccountId.equals(documentItem.getCentricAccountId3().getId())) {
                    i.getAndIncrement();
                    documentItem.setCentricAccountId3(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                    documentItem.setCentricAccountId4(null);
                    documentItem.setCentricAccountId5(null);
                    documentItem.setCentricAccountId6(null);
                } else if (documentItem.getCentricAccountId4() != null && centricAccountId.equals(documentItem.getCentricAccountId4().getId())) {
                    i.getAndIncrement();
                    documentItem.setCentricAccountId4(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                    documentItem.setCentricAccountId5(null);
                    documentItem.setCentricAccountId6(null);
                } else if (documentItem.getCentricAccountId5() != null && centricAccountId.equals(documentItem.getCentricAccountId5().getId())) {
                    i.getAndIncrement();
                    documentItem.setCentricAccountId5(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                    documentItem.setCentricAccountId6(null);
                } else if (documentItem.getCentricAccountId6() != null && centricAccountId.equals(documentItem.getCentricAccountId6().getId())) {
                    i.getAndIncrement();
                    documentItem.setCentricAccountId6(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                }
                financialDocumentItemRepository.save(documentItem);
                financialDocumentItemRepository.flush();
            });

        } else {
            throw new RuleException("fin.financialDocument.notExistCentricAccountType");
        }
        if (i.get() > 0) {
            document.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
            financialDocumentRepository.save(document);
            financialDocumentRepository.flush();
            return "تمامی تمرکز های سطوح بعد از تمرکز تغییر یافته حذف شدند. لطفا مجددا نسبت به انتخاب تمرکز ها اقدام فرمایید.";
        } else {
            return "هیچ رکوردی بروز رسانی نشد";
        }

    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean changeAmountDocument(FinancialCentricAccountDto financialCentricAccountDto) {
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);
        FinancialDocument document = financialDocumentRepository.findById(financialCentricAccountDto.getId())
                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialDocument financialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(document.getId());
        if (financialDocument == null) {
            throw new RuleException("fin.financialDocument.openStatusPeriod");
        } else {
            List<FinancialDocumentItem> financialDocumentItemList =
                    financialDocumentItemRepository.findByFinancialDocumentItemIdList(financialCentricAccountDto.getFinancialDocumentItemIdList());
            financialDocumentItemList.forEach(documentItem -> {
                Double newAmount = documentItem.getCreditAmount();
                documentItem.setCreditAmount(documentItem.getDebitAmount());
                documentItem.setDebitAmount(newAmount);
                financialDocumentItemRepository.save(documentItem);
            });
            List<FinancialDocumentItemCurrency> financialDocumentItemCurrencyList =
                    financialDocumentItemCurrencyRepository.findByFinancialDocumentItemCurrencyIdList(financialCentricAccountDto.getFinancialDocumentItemIdList());
            financialDocumentItemCurrencyList.forEach(documentItem -> {
                Double newAmount2 = documentItem.getForeignCreditAmount();
                documentItem.setForeignCreditAmount(documentItem.getForeignDebitAmount());
                documentItem.setForeignDebitAmount(newAmount2);
                financialDocumentItemCurrencyRepository.save(documentItem);
            });
            financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
            financialDocumentRepository.save(financialDocument);

        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean setAmountDocument(FinancialCentricAccountDto financialCentricAccountDto) {
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);
        FinancialDocument document = financialDocumentRepository.findById(financialCentricAccountDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(financialCentricAccountDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }
        FinancialDocument financialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(document.getId());
        if (financialDocument == null) {
            throw new RuleException("fin.financialDocument.openStatusPeriod");
        } else {
            List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.findByFinancialDocumentItemIdList(financialCentricAccountDto.getFinancialDocumentItemIdList());
            financialDocumentItemList.forEach(documentItem -> {
                documentItem.setCreditAmount(0D);
                documentItem.setDebitAmount(0D);
                financialDocumentItemRepository.save(documentItem);
            });

            List<FinancialDocumentItemCurrency> financialDocumentItemCurrencyList =
                    financialDocumentItemCurrencyRepository.findByFinancialDocumentItemCurrencyIdList(financialCentricAccountDto.getFinancialDocumentItemIdList());
            financialDocumentItemCurrencyList.forEach(documentItem -> {
                documentItem.setForeignDebitAmount(0D);
                documentItem.setForeignCreditAmount(0D);
                financialDocumentItemCurrencyRepository.save(documentItem);
            });
            financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
            financialDocumentRepository.save(financialDocument);

        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Boolean setArrangeSequence(FinancialDocumentDto financialDocumentDto) {
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialDocumentDto.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        FinancialDocument document = financialDocumentRepository.findById(financialDocumentDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setFinancialDocumentId(financialDocumentDto.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مبدا میبایست در وضعیت باز باشند");
        }
        entityManager.createNativeQuery("update fndc.financial_document_item  fndi_outer " +
                "   set fndi_outer.sequence_number = " +
                "       (select rn " +
                "          from (select ROW_NUMBER() over(order by fndi.credit_amount - fndi.debit_amount desc, fnc.code, cnt1.code, cnt2.code, cnt3.code, cnt4.code, cnt5.code, cnt6.code) as rn, " +
                "                       fndi.id " +
                "                  from fndc.financial_document_item fndi " +
                "                 left join fnac.financial_account fnc " +
                "                    on fnc.id = fndi.financial_account_id " +
                "                 left join fnac.centric_account cnt1 " +
                "                    on fndi.centric_account_id_1 = cnt1.id " +
                "                 left join fnac.centric_account cnt2 " +
                "                    on fndi.centric_account_id_2 = cnt2.id " +
                "                 left join fnac.centric_account cnt3 " +
                "                    on fndi.centric_account_id_3 = cnt3.id " +
                "                 left join fnac.centric_account cnt4 " +
                "                    on fndi.centric_account_id_4 = cnt4.id " +
                "                 left join fnac.centric_account cnt5 " +
                "                    on fndi.centric_account_id_5 = cnt5.id " +
                "                 left join fnac.centric_account cnt6 " +
                "                    on fndi.centric_account_id_6 = cnt6.id " +
                "                 where fndi.financial_document_id = :financialDocumentId " +
                "                 ) qry " +
                "         where      " +
                "         qry.id = fndi_outer.id) " +
                " where fndi_outer.financial_document_id = :financialDocumentId ").setParameter("financialDocumentId", document.getId()).executeUpdate();
        return true;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public DataSourceResult documentByStructure(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        RequestDocumentStructureDto paramSearch = setParamDocumentStructure(filters);
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list = financialDocumentItemRepository.getDocumentByStructure(paramSearch.getFinancialDocumentId(),
                paramSearch.getFinancialStructureId(), pageable);
        List<ResponseDocumentStructureDto> documentStructureDtoList = list.stream().map(item ->
                ResponseDocumentStructureDto.builder()
                        .debit(((BigDecimal) item[0]).doubleValue())
                        .credit(((BigDecimal) item[1]).doubleValue())
                        .financialAccountCode(item[2].toString())
                        .financialAccountDescription(item[3].toString())
                        .financialAccountId(Long.parseLong(item[4].toString()))
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(documentStructureDtoList);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private RequestDocumentStructureDto setParamDocumentStructure
            (List<DataSourceRequest.FilterDescriptor> filters) {
        RequestDocumentStructureDto requestDocumentStructureDto = new RequestDocumentStructureDto();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialDocument.id":
                    requestDocumentStructureDto.setFinancialDocumentId(Long.parseLong(item.getValue().toString()));
                    break;
                case "financialStructure.id":
                    requestDocumentStructureDto.setFinancialStructureId(Long.parseLong(item.getValue().toString()));
                    break;
            }
        }
        return requestDocumentStructureDto;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public List<ResponseFinancialDocumentStructureDto> getDocumentStructure(RequestDocumentStructureDto
                                                                                    requestDocumentStructureDto) {
        List<Object[]> documentStructure = financialDocumentItemRepository.getDocumentStructurList(requestDocumentStructureDto.getFinancialDocumentId());
        return documentStructure.stream().map(objects ->
                ResponseFinancialDocumentStructureDto.builder()
                        .DocumentStructureId(Long.parseLong(objects[0].toString()))
                        .sequence(Long.parseLong(objects[1].toString()))
                        .description(objects[2].toString())
                        .FinancialCodingTypeId(Long.parseLong(objects[3].toString()))
                        .build()
        ).collect(Collectors.toList());
    }


}
