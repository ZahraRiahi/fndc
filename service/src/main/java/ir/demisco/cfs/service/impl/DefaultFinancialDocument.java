package ir.demisco.cfs.service.impl;


import ir.demisco.cfs.model.dto.response.*;
import ir.demisco.cfs.model.entity.*;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.codehaus.jackson.map.util.ISO8601Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
    private final OrganizationRepository organizationRepository;
    private final FinancialDocumentTypeRepository financialDocumentTypeRepository;
    private final FinancialPeriodRepository financialPeriodRepository;
    private final FinancialLedgerTypeRepository financialLedgerTypeRepository;
    private final FinancialDepartmentRepository financialDepartmentRepository;
    private final MoneyTypeRepository moneyTypeRepository;
    private final MoneyPrisingReferenceRepository prisingReferenceRepository;

    public DefaultFinancialDocument(FinancialDocumentRepository financialDocumentRepository, FinancialDocumentStatusRepository documentStatusRepository, FinancialDocumentItemRepository financialDocumentItemRepository, FinancialDocumentReferenceRepository financialDocumentReferenceRepository, FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository, FinancialAccountRepository financialAccountRepository, EntityManager entityManager, CentricAccountRepository centricAccountRepository, OrganizationRepository organizationRepository, FinancialDocumentTypeRepository financialDocumentTypeRepository, FinancialPeriodRepository financialPeriodRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository, FinancialDepartmentRepository financialDepartmentRepository, MoneyTypeRepository moneyTypeRepository, MoneyPrisingReferenceRepository prisingReferenceRepository) {
        this.financialDocumentRepository = financialDocumentRepository;
        this.documentStatusRepository = documentStatusRepository;
        this.financialDocumentItemRepository = financialDocumentItemRepository;
        this.financialDocumentReferenceRepository = financialDocumentReferenceRepository;
        this.documentItemCurrencyRepository = documentItemCurrencyRepository;
        this.financialAccountRepository = financialAccountRepository;
        this.entityManager = entityManager;
        this.centricAccountRepository = centricAccountRepository;
        this.organizationRepository = organizationRepository;
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialDepartmentRepository = financialDepartmentRepository;
        this.moneyTypeRepository = moneyTypeRepository;
        this.prisingReferenceRepository = prisingReferenceRepository;
    }


    @Override
    @Transactional
    public DataSourceResult getFinancialDocumentList(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        ResponseFinancialDocumentDto paramSearch = setParameter(filters);
        Map<String, Object> paramMap = paramSearch.getParamMap();
        Pageable pageable = PageRequest.of(dataSourceRequest.getSkip(), dataSourceRequest.getTake());
        Page<Object[]> list = financialDocumentRepository.getFinancialDocumentList(paramSearch.getStartDate(),
                paramSearch.getEndDate(), paramSearch.getFinancialNumberingTypeId(), paramMap.get("fromNumber"), paramSearch.getFromNumber(),
                paramMap.get("toNumber"), paramSearch.getToNumber(), paramSearch.getDescription(), paramMap.get("fromAccount"), paramSearch.getFromAccountCode(),
                paramMap.get("toAccountCode"), paramSearch.getToAccountCode(), paramMap.get("centricAccount"), paramSearch.getCentricAccountId()
                , paramMap.get("centricAccountType"), paramSearch.getCentricAccountTypeId(), paramMap.get("user"), paramSearch.getUserId()
                , paramMap.get("priceType"), paramSearch.getPriceTypeId(), paramMap.get("fromPrice"), paramSearch.getFromPrice(), paramMap.get("toPrice"),
                paramSearch.getToPrice(), paramSearch.getTolerance(), paramSearch.getFinancialDocumentStatusDtoListId(), pageable);
        List<FinancialDocumentDto> documentDtoList = list.stream().map(item ->
                FinancialDocumentDto.builder()
                        .id(((BigDecimal) item[0]).longValue())
                        .documentDate((Date) item[1])
                        .description(item[2].toString())
                        .documentNumber(Long.parseLong(item[3].toString()))
                        .financialDocumentTypeId(Long.parseLong(item[4].toString()))
                        .financialDocumentTypeDescription(item[5].toString())
                        .fullDescription(item[6].toString())
                        .debitAmount(((BigDecimal) item[7]).doubleValue())
                        .creditAmount(((BigDecimal) item[8]).doubleValue())
                        .userId(Long.parseLong(item[9].toString()))
                        .userName(item[10].toString())
                        .build()).collect(Collectors.toList());
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(documentDtoList);
        dataSourceResult.setTotal(list.getTotalElements());
        return dataSourceResult;
    }

    private ResponseFinancialDocumentDto setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        ResponseFinancialDocumentDto responseFinancialDocumentDto = new ResponseFinancialDocumentDto();
        Map<String, Object> map = new HashMap<>();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {

                case "startDate":
                    responseFinancialDocumentDto.setStartDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
                    break;

                case "endDate":
                    responseFinancialDocumentDto.setEndDate(parseStringToLocalDateTime(String.valueOf(item.getValue()), false));
                    break;

                case "financialNumberingType.id":
                    responseFinancialDocumentDto.setFinancialNumberingTypeId(Long.parseLong(item.getValue().toString()));
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
                        responseFinancialDocumentDto.setFromAccountCode(item.getValue().toString());
                    } else {
                        map.put("fromAccount", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setFromAccountCode("");
                    }
                    break;

                case "toAccount.code":
                    if (item.getValue() != null) {
                        map.put("toAccount", "toAccount");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToAccountCode(item.getValue().toString());
                    } else {
                        map.put("toAccount", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setToAccountCode("");
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

                case "user.id":
                    if (item.getValue() != null) {
                        map.put("user", "user");
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setUserId(Long.parseLong(item.getValue().toString()));
                    } else {
                        map.put("user", null);
                        responseFinancialDocumentDto.setParamMap(map);
                        responseFinancialDocumentDto.setUserId(0L);
                    }
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
            }
        }

        return responseFinancialDocumentDto;
    }

    private LocalDateTime parseStringToLocalDateTime(Object input, boolean truncateDate) {
        if (input instanceof String) {
            try {
                Date date = ISO8601Utils.parse((String) input);
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
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentDto changeStatus(ResponseFinancialDocumentStatusDto responseFinancialDocumentStatusDto) {
        FinancialDocument financialDocument = financialDocumentRepository.findById(responseFinancialDocumentStatusDto.getId()).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        validationSetStatus(financialDocument);
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(responseFinancialDocumentStatusDto.getFinancialDocumentStatusId()));
        financialDocumentRepository.save(financialDocument);
        return financialDocumentToDto(financialDocument);
    }

    private void validationSetStatus(FinancialDocument financialDocument) {

        if (financialDocument.getDescription() == null) {
            throw new RuleException("سند بدون شرح است.");
        }

        List<FinancialDocumentItem> documentItemList = financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocument.getId());
        if (documentItemList == null) {
            throw new RuleException("سند بدون ردیف است.");
        } else {
            documentItemList.forEach(documentItem -> {
                double creditAmount = documentItem.getCreditAmount() % 1;
                double debitAmount = documentItem.getDebitAmount() % 1;

                if ((creditAmount != 000) || (debitAmount != 000)) {
                    throw new RuleException("مبلغ در ردیف اعشاری است.");
                }

                if ((documentItem.getCreditAmount() == 0) && (documentItem.getDebitAmount() == 0)) {

                    throw new RuleException("در ردیف سند مبلغ بستانکار و بدهکار صفر می باشد.");
                }

                if ((documentItem.getCreditAmount() != 0) && (documentItem.getDebitAmount() != 0)) {

                    throw new RuleException("در ردیف سند مبلغ بستانکار و بدهکار هر دو دارای مقدار می باشد.");
                }

                if ((documentItem.getCreditAmount() < 0) && (documentItem.getDebitAmount() < 0)) {
                    throw new RuleException("در ردیف سند مبلغ بستانکار یا بدهکار  منفی می باشد.");
                }

                if (documentItem.getDescription() == null) {

                    throw new RuleException("در سند ردیف بدون شرح وجود دارد.");
                }

            });
        }


        List<Object[]> cost = financialDocumentItemRepository.getCostDocument(financialDocument.getId());
        if (cost == null) {
            throw new RuleException("مجموع مبالغ بستانکار و بدهکار در ردیف های سند بالانس نیست.");
        }

        FinancialDocument documentPeriod = financialDocumentRepository.getActivePeriodInDocument(financialDocument.getId());
        if (documentPeriod == null) {
            throw new RuleException("وضعیت دوره مالی مربوط به سند بسته است.");
        }

        FinancialDocumentItem documentItemMoneyType = financialDocumentItemRepository.getDocumentMoneyType(financialDocument.getId());
        if (documentItemMoneyType != null) {
            throw new RuleException("نوع ارز ریال می باشد.");
        }
//        List<FinancialDocumentItem>  documentReference=financialDocumentItemRepository.getDocumentRefernce(financialDocument.getId());
//        if(documentReference!= null){
//            throw new RuleException("تاریخ و شرح در مدارک مرجع پر نشده.");
//        }


    }

    private FinancialDocumentDto financialDocumentToDto(FinancialDocument financialDocument) {
        Object[] objects = financialDocumentItemRepository.getParamByDocumentId(financialDocument.getId());
        return FinancialDocumentDto.builder()
                .id(financialDocument.getId())
                .documentDate(financialDocument.getDocumentDate())
                .documentNumber(financialDocument.getDocumentNumber())
                .financialDocumentTypeId(financialDocument.getFinancialDocumentType().getId())
                .financialDocumentTypeDescription(financialDocument.getFinancialDocumentType().getDescription())
                .userId(financialDocument.getCreator().getId())
                .userName(financialDocument.getCreator().getUsername())
                .description(financialDocument.getDescription())
//                .creditAmount(Long.parseLong(objects[1].toString()))
//                .debitAmount(Long.parseLong(objects[0].toString()))
//                .fullDescription(objects[2].toString())

                .build();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Long creatDocumentNumber(FinancialDocumentNumberDto financialDocumentNumberDto) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Long documentNumber = financialDocumentRepository.getDocumentNumber(organizationId, financialDocumentNumberDto.getDate(),
                financialDocumentNumberDto.getFinancialPeriodId());
        if (documentNumber == null) {
            throw new RuleException(" شماره سند یافت نشد.");
        }
        return documentNumber;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public String changeDescription(FinancialDocumentChengDescriptionDto financialDocumentDto) {
        FinancialDocument financialDocument = financialDocumentRepository.getActiveDocumentById(financialDocumentDto.getId());
        if (financialDocument == null) {
            throw new RuleException("سند یافت نشد.");
        }
        List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.getDocumentDescription(financialDocumentDto.getId(), financialDocumentDto.getOldDescription());
        if (financialDocumentItemList.isEmpty()) {
            throw new RuleException("ردیفی با پارامترهای ارسالی یافت نشد");
        }
        entityManager.createNativeQuery(" update fndc.financial_document_item " +
                "   set description = replace(description,:description,:newDescription) " +
                "   where financial_document_id =:FinancialDocumentId " +
                "   And description like '%'|| :description ||'%'").setParameter("description", financialDocumentDto.getOldDescription())
                .setParameter("newDescription", financialDocumentDto.getNewDescription())
                .setParameter("FinancialDocumentId", financialDocumentDto.getId()).executeUpdate();

        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
        financialDocumentRepository.save(financialDocument);
        return "عملیات با موفقیت انجام شد.";
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public boolean deleteFinancialDocumentById(Long financialDocumentId) {
        FinancialDocument document = financialDocumentRepository.findById(financialDocumentId).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        FinancialDocument financialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(document.getId());
        if (financialDocument == null) {
            throw new RuleException("دوره / ماه عملیاتی میبایست در وضعیت باز باشد.");
        } else {
            financialDocument.setDeletedDate(LocalDateTime.now());
            financialDocumentRepository.save(financialDocument);
            List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocumentId);
            financialDocumentItemList.forEach(documentItem -> {
                documentItem.setDeletedDate(LocalDateTime.now());
                financialDocumentItemRepository.save(documentItem);
                FinancialDocumentReference financialDocumentReference = financialDocumentReferenceRepository.getByDocumentItemId(documentItem.getId());
                if (financialDocumentReference != null) {
                    financialDocumentReference.setDeletedDate(LocalDateTime.now());
                    financialDocumentReferenceRepository.save(financialDocumentReference);
                }
                FinancialDocumentItemCurrency financialDocumentItemCurrency = documentItemCurrencyRepository.getByDocumentItemId(documentItem.getId());
                if (financialDocumentItemCurrency != null) {
                    financialDocumentItemCurrency.setDeletedDate(LocalDateTime.now());
                    documentItemCurrencyRepository.save(financialDocumentItemCurrency);
                }
            });
        }

        return true;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentAccountMessageDto changeAccountDocument(FinancialDocumentAccountDto financialDocumentAccountDto) {
        FinancialDocument financialDocument = financialDocumentRepository.findById(financialDocumentAccountDto.getId()).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        if (financialDocumentAccountDto.getFinancialAccountId().equals(financialDocumentAccountDto.getNewFinancialAccountId())) {
            throw new RuleException("حساب های ارسالی یکسان است.");
        }
        List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.getItemByDocumentIdAndAccountId(financialDocumentAccountDto.getId(),
                financialDocumentAccountDto.getFinancialAccountId());
        financialDocumentItemList.forEach(documentItem -> {
            documentItem.setFinancialAccount(financialAccountRepository.getOne(financialDocumentAccountDto.getNewFinancialAccountId()));
            financialDocumentItemRepository.save(documentItem);
        });

        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
        financialDocumentRepository.save(financialDocument);
        FinancialAccount financialAccount = financialAccountRepository.getFinancialAccountRelationType(financialDocumentAccountDto.getFinancialAccountId(),
                financialDocumentAccountDto.getNewFinancialAccountId());
        if (financialAccount == null) {
            List<FinancialDocumentItem> centricDocumentItem = financialDocumentItemRepository.getByNewAccount(financialDocumentAccountDto.getId(),
                    financialDocumentAccountDto.getNewFinancialAccountId());
            centricDocumentItem.forEach(centricItem -> {
                centricItem.setCentricAccountId1(null);
                centricItem.setCentricAccountId2(null);
                centricItem.setCentricAccountId3(null);
                centricItem.setCentricAccountId4(null);
                centricItem.setCentricAccountId5(null);
                centricItem.setCentricAccountId6(null);
                financialDocumentItemRepository.save(centricItem);
            });
            return FinancialDocumentAccountMessageDto.builder()
                    .id(financialDocument.getId())
                    .message("به دلیل انواع تفاوت در وابستگی حساب جدید با حساب قبلی،تمام تمرکز های مربوطه حذف شده.لطفا تمرکز های مربوطه را درج نمایید.")
                    .build();
        } else {
            return FinancialDocumentAccountMessageDto.builder()
                    .id(financialDocument.getId())
                    .message("عملیات موفقیت امیز بود.")
                    .build();
        }

    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public String changeCentricAccount(FinancialCentricAccountDto financialCentricAccountDto) {

        FinancialDocument document = financialDocumentRepository.findById(financialCentricAccountDto.getId()).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        CentricAccount centricAccount = centricAccountRepository.findById(financialCentricAccountDto.getCentricAccountId()).orElseThrow(() -> new RuleException("کد تمرکز یافت نشد."));
        CentricAccount newCentricAccount = centricAccountRepository.findById(financialCentricAccountDto.getNewCentricAccountId()).orElseThrow(() -> new RuleException("کد تمرکز یافت نشد."));
        if (centricAccount.getCentricAccountType().getId().equals(newCentricAccount.getCentricAccountType().getId())) {
            List<FinancialDocumentItem> financialDocumentItemList =
                    financialDocumentItemRepository.getByDocumentIdAndCentricAccount(document.getId(), financialCentricAccountDto.getAccountId(), financialCentricAccountDto.getNewCentricAccountId());
            if (financialDocumentItemList.isEmpty()) {
                throw new RuleException("ردیفی یافت نشد.");
            }
            Long centricAccountId = financialCentricAccountDto.getCentricAccountId();
            financialDocumentItemList.forEach(documentItem -> {
                if ((documentItem.getCentricAccountId1() != null) && (centricAccountId.equals(documentItem.getCentricAccountId1().getId()))) {
                    documentItem.setCentricAccountId1(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                    documentItem.setCentricAccountId2(null);
                    documentItem.setCentricAccountId3(null);
                    documentItem.setCentricAccountId4(null);
                    documentItem.setCentricAccountId5(null);
                    documentItem.setCentricAccountId6(null);
                } else if (documentItem.getCentricAccountId2() != null && centricAccountId.equals(documentItem.getCentricAccountId2().getId())) {
                    documentItem.setCentricAccountId2(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                    documentItem.setCentricAccountId3(null);
                    documentItem.setCentricAccountId4(null);
                    documentItem.setCentricAccountId5(null);
                    documentItem.setCentricAccountId6(null);
                } else if (documentItem.getCentricAccountId3() != null && centricAccountId.equals(documentItem.getCentricAccountId3().getId())) {
                    documentItem.setCentricAccountId3(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                    documentItem.setCentricAccountId4(null);
                    documentItem.setCentricAccountId5(null);
                    documentItem.setCentricAccountId6(null);
                } else if (documentItem.getCentricAccountId4() != null && centricAccountId.equals(documentItem.getCentricAccountId4().getId())) {
                    documentItem.setCentricAccountId4(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                    documentItem.setCentricAccountId5(null);
                    documentItem.setCentricAccountId6(null);
                } else if (documentItem.getCentricAccountId5() != null && centricAccountId.equals(documentItem.getCentricAccountId5().getId())) {
                    documentItem.setCentricAccountId5(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                    documentItem.setCentricAccountId6(null);
                } else if (documentItem.getCentricAccountId6() != null && centricAccountId.equals(documentItem.getCentricAccountId6().getId())) {
                    documentItem.setCentricAccountId6(centricAccountRepository.getOne(financialCentricAccountDto.getNewCentricAccountId()));
                }
                financialDocumentItemRepository.save(documentItem);
            });

        } else {
            throw new RuleException("نوع کد تمرکز جدید و قبلی یکسان نیست.");
        }

        return "تمامی تمرکز های سطوح بعد از تمرکز تغییر یافته حذف شدند. لطفا مجددا نسبت به انتخاب تمرکز ها اقدام فرمایید.";
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean changeAmountDocument(FinancialCentricAccountDto financialCentricAccountDto) {
        FinancialDocument document = financialDocumentRepository.findById(financialCentricAccountDto.getId()).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        FinancialDocument financialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(document.getId());
        if (financialDocument == null) {
            throw new RuleException("دوره / ماه عملیاتی میبایست در وضعیت باز باشد.");
        } else {
            List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialCentricAccountDto.getId());
            financialDocumentItemList.forEach(documentItem -> {
                Double newAmount = documentItem.getCreditAmount();
                documentItem.setCreditAmount(documentItem.getDebitAmount());
                documentItem.setDebitAmount(newAmount);
                financialDocumentItemRepository.save(documentItem);
            });
            financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
            financialDocumentRepository.save(financialDocument);

        }
        return true;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean setAmountDocument(FinancialCentricAccountDto financialCentricAccountDto) {
        FinancialDocument document = financialDocumentRepository.findById(financialCentricAccountDto.getId()).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        FinancialDocument financialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(document.getId());
        if (financialDocument == null) {
            throw new RuleException("دوره / ماه عملیاتی میبایست در وضعیت باز باشد.");
        } else {
            List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialCentricAccountDto.getId());
            financialDocumentItemList.forEach(documentItem -> {
                documentItem.setCreditAmount(0D);
                documentItem.setDebitAmount(0D);
                financialDocumentItemRepository.save(documentItem);
            });
            financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
            financialDocumentRepository.save(financialDocument);

        }
        return true;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentSaveDto saveDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto) {

        FinancialDocumentSaveDto responseDocumentSaveDto;
        FinancialDocument financialDocument = saveFinancialDocument(requestFinancialDocumentSaveDto);
        responseDocumentSaveDto = convertDocumentToDto(financialDocument);
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList = new ArrayList<>();
        List<FinancialDocumentReferenceDto> documentReferenceList = new ArrayList<>();
        List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyList = new ArrayList<>();
        requestFinancialDocumentSaveDto.getFinancialDocumentItemDtoList().forEach(documentItem -> {
            FinancialDocumentItem financialDocumentItem = new FinancialDocumentItem();
            financialDocumentItem.setFinancialDocument(financialDocumentRepository.getOne(financialDocument.getId()));
            financialDocumentItem.setSequenceNumber(documentItem.getSequenceNumber());
            financialDocumentItem.setDebitAmount(documentItem.getDebitAmount());
            financialDocumentItem.setCreditAmount(documentItem.getCreditAmount());
            financialDocumentItem.setDescription(documentItem.getDescription());
            financialDocumentItem.setFinancialAccount(financialAccountRepository.getOne(documentItem.getFinancialAccountId()));
            financialDocumentItem.setCentricAccountId1(centricAccountRepository.getOne(documentItem.getCentricAccountId1()));
            financialDocumentItem.setCentricAccountId2(centricAccountRepository.getOne(documentItem.getCentricAccountId2()));
            financialDocumentItem.setCentricAccountId3(centricAccountRepository.getOne(documentItem.getCentricAccountId3()));
            financialDocumentItem.setCentricAccountId4(centricAccountRepository.getOne(documentItem.getCentricAccountId4()));
            financialDocumentItem.setCentricAccountId5(centricAccountRepository.getOne(documentItem.getCentricAccountId5()));
            financialDocumentItem.setCentricAccountId6(centricAccountRepository.getOne(documentItem.getCentricAccountId6()));
            financialDocumentItem = financialDocumentItemRepository.save(financialDocumentItem);
            FinancialDocumentItem finalFinancialDocumentItem = financialDocumentItem;
            ResponseFinancialDocumentItemDto documentItemToList = convertDocumentItemToList(financialDocumentItem);
            if (documentItem.getDocumentReferenceList() != null) {
                documentItem.getDocumentReferenceList().forEach(documentReference -> {
                    FinancialDocumentReference financialDocumentReference = new FinancialDocumentReference();
                    financialDocumentReference.setFinancialDocumentItem(finalFinancialDocumentItem);
                    financialDocumentReference.setReferenceNumber(documentReference.getReferenceNumber());
                    financialDocumentReference.setReferenceDate(documentReference.getReferenceDate());
                    financialDocumentReference.setReferenceDescription(documentReference.getReferenceDescription());
                    financialDocumentReference = financialDocumentReferenceRepository.save(financialDocumentReference);
                    documentReferenceList.add(convertFinancialDocumentItemToDto(financialDocumentReference));
                    documentItemToList.setDocumentReferenceList(documentReferenceList);
                });
            }
            if (documentItem.getDocumentItemCurrencyList() != null) {
                documentItem.getDocumentItemCurrencyList().forEach(itemCurrency -> {
                    FinancialDocumentItemCurrency documentItemCurrency = new FinancialDocumentItemCurrency();
                    documentItemCurrency.setFinancialDocumentItem(finalFinancialDocumentItem);
                    documentItemCurrency.setForeignCreditAmount(itemCurrency.getForeignCreditAmount());
                    documentItemCurrency.setForeignDebitAmount(itemCurrency.getForeignDebitAmount());
                    documentItemCurrency.setExchangeRate(itemCurrency.getExchangeRate());
                    documentItemCurrency.setMoneyType(moneyTypeRepository.getOne(itemCurrency.getMoneyTypeId()));
                    documentItemCurrency.setMoneyPricingReference(prisingReferenceRepository.getOne(itemCurrency.getMoneyPricingReferenceId()));
                    documentItemCurrency = documentItemCurrencyRepository.save(documentItemCurrency);
                    responseDocumentItemCurrencyList.add(convertDocumentItemCurrency(documentItemCurrency));
                    documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyList);
                });
            }
            financialDocumentItemDtoList.add(documentItemToList);
            responseDocumentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
        });

        return responseDocumentSaveDto;
    }

    private FinancialDocumentItemCurrencyDto convertDocumentItemCurrency(FinancialDocumentItemCurrency documentItemCurrency) {
        return FinancialDocumentItemCurrencyDto.builder()
                .financialDocumentItemCurrencyId(documentItemCurrency.getId())
                .financialDocumentItemId(documentItemCurrency.getFinancialDocumentItem().getId())
                .foreignDebitAmount(documentItemCurrency.getForeignDebitAmount())
                .foreignCreditAmount(documentItemCurrency.getForeignCreditAmount())
                .moneyTypeId(documentItemCurrency.getMoneyType().getId())
                .moneyTypeDescription(documentItemCurrency.getMoneyType().getDescription())
                .moneyPricingReferenceId(documentItemCurrency.getMoneyPricingReference().getId())
                .moneyPricingReferenceDescription(documentItemCurrency.getMoneyPricingReference().getDescription())
                .build();
    }

    private FinancialDocumentReferenceDto convertFinancialDocumentItemToDto(FinancialDocumentReference financialDocumentReference) {
        return FinancialDocumentReferenceDto.builder()
                .financialDocumentReferenceId(financialDocumentReference.getId())
                .financialDocumentItemId(financialDocumentReference.getFinancialDocumentItem().getId())
                .referenceNumber(financialDocumentReference.getReferenceNumber())
                .referenceDate(financialDocumentReference.getReferenceDate())
                .referenceDescription(financialDocumentReference.getReferenceDescription())
                .build();
    }

    private ResponseFinancialDocumentItemDto convertDocumentItemToList(FinancialDocumentItem financialDocumentItem) {

        return ResponseFinancialDocumentItemDto.builder()
                .financialDocumentItemId(financialDocumentItem.getId())
                .sequenceNumber(financialDocumentItem.getSequenceNumber())
                .financialAccountId(financialDocumentItem.getFinancialAccount().getId())
                .debitAmount(financialDocumentItem.getDebitAmount())
                .creditAmount(financialDocumentItem.getCreditAmount())
                .description(financialDocumentItem.getDescription())
                .centricAccountId1(financialDocumentItem.getCentricAccountId1().getId())
                .centricAccountId2(financialDocumentItem.getCentricAccountId2().getId())
                .centricAccountId3(financialDocumentItem.getCentricAccountId3().getId())
                .centricAccountId4(financialDocumentItem.getCentricAccountId4().getId())
                .centricAccountId5(financialDocumentItem.getCentricAccountId5().getId())
                .centricAccountId6(financialDocumentItem.getCentricAccountId6().getId())
                .build();
    }


    private FinancialDocumentSaveDto convertDocumentToDto(FinancialDocument financialDocument) {
        return FinancialDocumentSaveDto.builder()
                .financialDocumentId(financialDocument.getId())
                .documentDate(financialDocument.getDocumentDate())
                .documentNumber(financialDocument.getDocumentNumber())
                .financialDocumentTypeId(financialDocument.getFinancialDocumentType().getId())
                .financialDocumentTypeDescription(financialDocument.getFinancialDocumentType().getDescription())
                .financialDocumentStatusId(financialDocument.getFinancialDocumentStatus().getId())
                .automaticFlag(financialDocument.getAutomaticFlag())
                .description(financialDocument.getDescription())
                .organizationId(financialDocument.getOrganization().getId())
//                .financialDocumentDescriptionId()
                .financialLedgerTypeId(financialDocument.getFinancialLedgerType().getId())
                .financialLedgerTypeDescription(financialDocument.getFinancialLedgerType().getDescription())
                .departmentId(financialDocument.getFinancialDepartment().getId())
                .departmentName(financialDocument.getFinancialDepartment().getName())
                .build();
    }

    private FinancialDocument saveFinancialDocument(FinancialDocumentSaveDto financialDocumentSaveDto) {
        //        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Long organizationId = 100L;
        FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
        financialDocumentNumberDto.setDate(financialDocumentSaveDto.getDocumentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        financialDocumentNumberDto.setFinancialPeriodId(financialDocumentSaveDto.getFinancialPeriodId());
        if (financialDocumentSaveDto.getFinancialDocumentItemDtoList().isEmpty()) {
            throw new RuleException("لطفا یک ردیف وارد کنید.");
        }
        FinancialDocument financialDocument = financialDocumentRepository.
                findById(financialDocumentSaveDto.getFinancialDocumentId() == null ? 0L : financialDocumentSaveDto.getFinancialDocumentId()).orElse(new FinancialDocument());
        financialDocument.setDocumentDate(financialDocumentSaveDto.getDocumentDate());
        financialDocument.setDescription(financialDocumentSaveDto.getDescription());
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(financialDocumentSaveDto.getFinancialDocumentStatusId()));
        financialDocument.setPermanentDocumentNumber(financialDocumentSaveDto.getPermanentDocumentNumber());
        financialDocument.setAutomaticFlag(financialDocumentSaveDto.getAutomaticFlag());
        financialDocument.setOrganization(organizationRepository.getOne(organizationId));
        financialDocument.setFinancialDocumentType(financialDocumentTypeRepository.getOne(financialDocumentSaveDto.getFinancialDocumentTypeId()));
        financialDocument.setFinancialPeriod(financialPeriodRepository.getOne(financialDocumentSaveDto.getFinancialPeriodId()));
        financialDocument.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialDocumentSaveDto.getFinancialLedgerTypeId()));
        financialDocument.setFinancialDepartment(financialDepartmentRepository.getOne(financialDocumentSaveDto.getDepartmentId()));
        financialDocument.setDocumentNumber(creatDocumentNumber(financialDocumentNumberDto));
        return financialDocumentRepository.save(financialDocument);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentSaveDto updateDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto) {
        FinancialDocumentSaveDto responseDocumentSaveDto;
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList = new ArrayList<>();
        List<FinancialDocumentReferenceDto> documentReferenceList = new ArrayList<>();
        List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyList = new ArrayList<>();
        FinancialDocument updateFinancialDocument = updateFinancialDocument(requestFinancialDocumentSaveDto);
        responseDocumentSaveDto = convertDocumentToDto(updateFinancialDocument);
        financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(updateFinancialDocument.getId())
                .forEach(financialDocumentItem -> {
                    requestFinancialDocumentSaveDto.getFinancialDocumentItemDtoList().stream().filter(e -> e.getFinancialDocumentItemId()
                            .equals(financialDocumentItem.getId()))
                            .findAny().ifPresent(responseFinancialDocumentItemDto ->
                    {
                        financialDocumentItem.setSequenceNumber(responseFinancialDocumentItemDto.getSequenceNumber());
                        financialDocumentItem.setDebitAmount(responseFinancialDocumentItemDto.getDebitAmount());
                        financialDocumentItem.setCreditAmount(responseFinancialDocumentItemDto.getCreditAmount());
                        financialDocumentItem.setDescription(responseFinancialDocumentItemDto.getDescription());
                        financialDocumentItem.setFinancialAccount(financialAccountRepository.getOne(responseFinancialDocumentItemDto.getFinancialAccountId()));
                        financialDocumentItem.setCentricAccountId1(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId1()));
                        financialDocumentItem.setCentricAccountId2(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId2()));
                        financialDocumentItem.setCentricAccountId3(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId3()));
                        financialDocumentItem.setCentricAccountId4(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId4()));
                        financialDocumentItem.setCentricAccountId5(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId5()));
                        financialDocumentItem.setCentricAccountId6(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId6()));
                        ResponseFinancialDocumentItemDto documentItemToList = convertDocumentItemToList(financialDocumentItem);
                        financialDocumentReferenceRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(financialDocumentItem.getId())
                                .forEach(documentReference -> {
                                    responseFinancialDocumentItemDto.getDocumentReferenceList()
                                            .stream().filter(r -> r.getFinancialDocumentReferenceId().equals(documentReference.getId()))
                                            .findAny().ifPresent(responseReference -> {
                                        documentReference.setReferenceNumber(responseReference.getReferenceNumber());
                                        documentReference.setReferenceDate(responseReference.getReferenceDate());
                                        documentReference.setReferenceDescription(responseReference.getReferenceDescription());
                                        documentReferenceList.add(convertFinancialDocumentItemToDto(documentReference));
                                        documentItemToList.setDocumentReferenceList(documentReferenceList);

                                    });

                                });
                        documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(financialDocumentItem.getId())
                                .forEach(itemCurrency -> {
                                    responseFinancialDocumentItemDto.getDocumentItemCurrencyList().stream().filter(c -> c.getFinancialDocumentItemCurrencyId()
                                            .equals(itemCurrency.getId())).findAny().ifPresent(financialItemCurrency -> {
                                        itemCurrency.setForeignCreditAmount(financialItemCurrency.getForeignCreditAmount());
                                        itemCurrency.setForeignDebitAmount(financialItemCurrency.getForeignDebitAmount());
                                        itemCurrency.setExchangeRate(financialItemCurrency.getExchangeRate());
                                        itemCurrency.setMoneyType(moneyTypeRepository.getOne(itemCurrency.getMoneyType().getId()));
                                        itemCurrency.setMoneyPricingReference(prisingReferenceRepository.getOne(financialItemCurrency.getMoneyPricingReferenceId()));
                                        responseDocumentItemCurrencyList.add(convertDocumentItemCurrency(itemCurrency));
                                        documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyList);

                                    });
                                });

                        financialDocumentItemDtoList.add(documentItemToList);
                    });

                    responseDocumentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
                });

        return responseDocumentSaveDto;
    }

    private FinancialDocument updateFinancialDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto) {


        //        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Long organizationId = 100L;
        FinancialDocument financialDocument = financialDocumentRepository.
                findById(requestFinancialDocumentSaveDto.getFinancialDocumentId()).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
        financialDocumentNumberDto.setDate(requestFinancialDocumentSaveDto.getDocumentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        financialDocumentNumberDto.setFinancialPeriodId(requestFinancialDocumentSaveDto.getFinancialPeriodId());
        financialDocument.setDocumentDate(requestFinancialDocumentSaveDto.getDocumentDate());
        financialDocument.setDescription(requestFinancialDocumentSaveDto.getDescription());
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(requestFinancialDocumentSaveDto.getFinancialDocumentStatusId()));
        financialDocument.setPermanentDocumentNumber(requestFinancialDocumentSaveDto.getPermanentDocumentNumber());
        financialDocument.setAutomaticFlag(requestFinancialDocumentSaveDto.getAutomaticFlag());
        financialDocument.setOrganization(organizationRepository.getOne(organizationId));
        financialDocument.setFinancialDocumentType(financialDocumentTypeRepository.getOne(requestFinancialDocumentSaveDto.getFinancialDocumentTypeId()));
        financialDocument.setFinancialPeriod(financialPeriodRepository.getOne(requestFinancialDocumentSaveDto.getFinancialPeriodId()));
        financialDocument.setFinancialLedgerType(financialLedgerTypeRepository.getOne(requestFinancialDocumentSaveDto.getFinancialLedgerTypeId()));
        financialDocument.setFinancialDepartment(financialDepartmentRepository.getOne(requestFinancialDocumentSaveDto.getDepartmentId()));
        financialDocument.setDocumentNumber(creatDocumentNumber(financialDocumentNumberDto));
        return financialDocumentRepository.save(financialDocument);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public Boolean setArrangeSequence(FinancialDocumentDto financialDocumentDto) {
        FinancialDocument document = financialDocumentRepository.findById(financialDocumentDto.getId()).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        entityManager.createNativeQuery("update fndc.financial_document_item  fndi_outer " +
                "   set fndi_outer.sequence_number = " +
                "       (select rn " +
                "          from (select ROW_NUMBER() over(order by fndi.credit_amount - fndi.debit_amount desc, fnc.code, cnt1.code, cnt2.code, cnt3.code, cnt4.code, cnt5.code, cnt6.code) as rn, " +
                "                       fndi.id " +
                "                  from fndc.financial_document_item fndi " +
                "                 inner join fnac.financial_account fnc " +
                "                    on fnc.id = fndi.financial_account_id " +
                "                 inner join fnac.centric_account cnt1 " +
                "                    on fndi.centric_account_id_1 = cnt1.id " +
                "                 inner join fnac.centric_account cnt2 " +
                "                    on fndi.centric_account_id_2 = cnt2.id " +
                "                 inner join fnac.centric_account cnt3 " +
                "                    on fndi.centric_account_id_3 = cnt3.id " +
                "                 inner join fnac.centric_account cnt4 " +
                "                    on fndi.centric_account_id_4 = cnt4.id " +
                "                 inner join fnac.centric_account cnt5 " +
                "                    on fndi.centric_account_id_5 = cnt5.id " +
                "                 inner join fnac.centric_account cnt6 " +
                "                    on fndi.centric_account_id_6 = cnt6.id " +
                "                 where fndi.financial_document_id = :financialDocumentId " +
                "                 ) qry " +
                "         where      " +
                "         qry.id = fndi_outer.id) " +
                " where fndi_outer.financial_document_id = :financialDocumentId ").setParameter("financialDocumentId",financialDocumentDto.getId()).executeUpdate();
        return true;
    }
}
