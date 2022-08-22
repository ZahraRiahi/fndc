package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDocumentItemRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentSecurityInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodLedgerStatusRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.request.SecurityModelRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentItemCurrencyDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentItemCurrencyOutPutModel;
import ir.demisco.cfs.model.dto.response.FinancialDocumentItemResponse;
import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentReferenceDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentReferenceOutPutModel;
import ir.demisco.cfs.model.dto.response.FinancialDocumentSaveDto;
import ir.demisco.cfs.model.dto.response.FinancialPeriodStatusResponse;
import ir.demisco.cfs.model.dto.response.ResponseFinancialDocumentItemDto;
import ir.demisco.cfs.model.entity.FinancialDocument;
import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import ir.demisco.cfs.model.entity.FinancialDocumentItemCurrency;
import ir.demisco.cfs.model.entity.FinancialDocumentReference;
import ir.demisco.cfs.service.api.FinancialDocumentSecurityService;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.api.SaveFinancialDocumentService;
import ir.demisco.cfs.service.repository.CentricAccountRepository;
import ir.demisco.cfs.service.repository.DepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialAccountRepository;
import ir.demisco.cfs.service.repository.FinancialDepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentItemCurrencyRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentReferenceRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentStatusRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentTypeRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cfs.service.repository.MoneyPrisingReferenceRepository;
import ir.demisco.cfs.service.repository.MoneyTypeRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.middle.model.dto.DataSourceRequest;
import ir.demisco.cloud.core.middle.model.dto.DataSourceResult;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultSaveFinancialDocument implements SaveFinancialDocumentService {

    private final FinancialAccountRepository financialAccountRepository;
    private final CentricAccountRepository centricAccountRepository;
    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialDocumentItemRepository financialDocumentItemRepository;
    private final FinancialDocumentReferenceRepository financialDocumentReferenceRepository;
    private final MoneyTypeRepository moneyTypeRepository;
    private final OrganizationRepository organizationRepository;
    private final FinancialDocumentTypeRepository financialDocumentTypeRepository;
    private final FinancialPeriodRepository financialPeriodRepository;
    private final FinancialLedgerTypeRepository financialLedgerTypeRepository;
    private final FinancialDepartmentRepository financialDepartmentRepository;
    private final MoneyPrisingReferenceRepository prisingReferenceRepository;
    private final FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository;
    private final FinancialDocumentStatusRepository documentStatusRepository;
    private final FinancialDocumentService financialDocumentService;
    private final FinancialPeriodService financialPeriodService;
    private final FinancialDocumentSecurityService financialDocumentSecurityService;
    private final EntityManager entityManager;
    private final DepartmentRepository departmentRepository;

    public DefaultSaveFinancialDocument(FinancialAccountRepository financialAccountRepository, CentricAccountRepository centricAccountRepository,
                                        FinancialDocumentRepository financialDocumentRepository, FinancialDocumentItemRepository financialDocumentItemRepository,
                                        FinancialDocumentReferenceRepository financialDocumentReferenceRepository, MoneyTypeRepository moneyTypeRepository,
                                        OrganizationRepository organizationRepository, FinancialDocumentTypeRepository financialDocumentTypeRepository,
                                        FinancialPeriodRepository financialPeriodRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository,
                                        FinancialDepartmentRepository financialDepartmentRepository, MoneyPrisingReferenceRepository prisingReferenceRepository,
                                        FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository, FinancialDocumentStatusRepository documentStatusRepository,
                                        FinancialDocumentService financialDocumentService, FinancialPeriodService financialPeriodService, FinancialDocumentSecurityService financialDocumentSecurityService, EntityManager entityManager, DepartmentRepository departmentRepository) {
        this.financialAccountRepository = financialAccountRepository;
        this.centricAccountRepository = centricAccountRepository;
        this.financialDocumentRepository = financialDocumentRepository;
        this.financialDocumentItemRepository = financialDocumentItemRepository;
        this.financialDocumentReferenceRepository = financialDocumentReferenceRepository;
        this.moneyTypeRepository = moneyTypeRepository;
        this.organizationRepository = organizationRepository;
        this.financialDocumentTypeRepository = financialDocumentTypeRepository;
        this.financialPeriodRepository = financialPeriodRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialDepartmentRepository = financialDepartmentRepository;
        this.prisingReferenceRepository = prisingReferenceRepository;
        this.documentItemCurrencyRepository = documentItemCurrencyRepository;
        this.documentStatusRepository = documentStatusRepository;
        this.financialDocumentService = financialDocumentService;
        this.financialPeriodService = financialPeriodService;
        this.financialDocumentSecurityService = financialDocumentSecurityService;
        this.entityManager = entityManager;
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentSaveDto saveDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto) {
        FinancialDocumentSaveDto responseDocumentSaveDto;
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList = new ArrayList<>();
        FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
        String activityCode = "FNDC_DOCUMENT_CREATE";
        SecurityModelRequest securityModelRequest = new SecurityModelRequest();
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(requestFinancialDocumentSaveDto.getFinancialDocumentId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        securityModelRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        securityModelRequest.setUserId(SecurityHelper.getCurrentUser().getUserId());
        securityModelRequest.setDepartmentId(requestFinancialDocumentSaveDto.getDepartmentId());
        securityModelRequest.setFinancialDepartmentId(requestFinancialDocumentSaveDto.getFinancialDepartmentId());
        securityModelRequest.setFinancialLedgerId(requestFinancialDocumentSaveDto.getFinancialLedgerTypeId());
        securityModelRequest.setFinancialPeriodId(requestFinancialDocumentSaveDto.getFinancialPeriodId());
        securityModelRequest.setDocumentTypeId(requestFinancialDocumentSaveDto.getFinancialDocumentTypeId());
        securityModelRequest.setSubjectId(null);
        securityModelRequest.setActivityCode(financialDocumentSecurityInputRequest.getActivityCode());
        securityModelRequest.setInputFromConfigFlag(false);
        securityModelRequest.setCreatorUserId(SecurityHelper.getCurrentUser().getUserId());
        financialDocumentSecurityInputRequest.setSecurityModelRequest(securityModelRequest);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        Long periodDate = financialPeriodRepository.findFinancialPeriodByFinancialPeriodIdAndDocumentDate
                (requestFinancialDocumentSaveDto.getFinancialPeriodId(), LocalDateTime.parse(DateUtil.convertDateToString(requestFinancialDocumentSaveDto.getDocumentDate()).replace("/", "-") + "T00:00"));
        if (periodDate == null) {
            throw new RuleException(" تاریخ وارد شده در محدوده دوره مالی پیش فرض نمیباشد");
        }
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        financialPeriodStatusRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        financialPeriodStatusRequest.setDate(LocalDateTime.parse(DateUtil.convertDateToString(requestFinancialDocumentSaveDto.getDocumentDate()).replace("/", "-") + "T00:00"));
        financialPeriodStatusRequest.setFinancialPeriodId(requestFinancialDocumentSaveDto.getFinancialPeriodId());
        financialPeriodStatusRequest.setFinancialDocumentId(requestFinancialDocumentSaveDto.getFinancialDocumentId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == 0L || financialPeriodStatus.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }


        FinancialPeriodLedgerStatusRequest financialPeriodLedgerStatusRequest = new FinancialPeriodLedgerStatusRequest();
        financialPeriodLedgerStatusRequest.setDate(LocalDateTime.parse(DateUtil.convertDateToString(requestFinancialDocumentSaveDto.getDocumentDate()).replace("/", "-") + "T00:00"));
        financialPeriodLedgerStatusRequest.setFinancialPeriodId(requestFinancialDocumentSaveDto.getFinancialPeriodId());
        financialPeriodLedgerStatusRequest.setFinancialLedgerTypeId(requestFinancialDocumentSaveDto.getFinancialLedgerTypeId());
        FinancialPeriodStatusResponse financialPeriodStatusResponse = financialDocumentService.getFinancialPeriodStatus(financialPeriodLedgerStatusRequest);
        if (financialPeriodStatusResponse.getPeriodStatus() == 0L || financialPeriodStatusResponse.getMonthStatus() == 0L) {
            throw new RuleException("دوره مالی و ماه مربوط به دفتر مالی میبایست در وضعیت باز باشند");
        }


        String documentNumber;
        FinancialDocument financialDocument = saveFinancialDocument(requestFinancialDocumentSaveDto);
        financialDocumentNumberDto.setOrganizationId(financialDocument.getOrganization().getId());
        financialDocumentNumberDto.setFinancialDocumentId(financialDocument.getId());
        financialDocumentNumberDto.setNumberingType(1L);
        documentNumber = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);

        financialDocument.setDocumentNumber(documentNumber);
        financialDocumentRepository.save(financialDocument);
        responseDocumentSaveDto = convertDocumentToDto(financialDocument);
        requestFinancialDocumentSaveDto.getFinancialDocumentItemDtoList().forEach((ResponseFinancialDocumentItemDto documentItem) -> {
            List<FinancialDocumentReferenceDto> documentReferenceList = new ArrayList<>();
            List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyList = new ArrayList<>();
            FinancialDocumentItem financialDocumentItem = saveFinancialDocumentItem(financialDocument, documentItem);
            FinancialDocumentItem finalFinancialDocumentItem = financialDocumentItem;
            ResponseFinancialDocumentItemDto documentItemToList = convertDocumentItemToList(financialDocumentItem);
            if (documentItem.getDocumentReferenceList() != null) {
                documentItem.getDocumentReferenceList().forEach((FinancialDocumentReferenceDto documentReference) -> {
                    FinancialDocumentReference financialDocumentReference = saveDocumentReference(finalFinancialDocumentItem, documentReference);
                    documentReferenceList.add(convertFinancialDocumentItemToDto(financialDocumentReference));
                    documentItemToList.setDocumentReferenceList(documentReferenceList);
                });
            }
            if (documentItem.getDocumentItemCurrencyList() != null) {
                documentItem.getDocumentItemCurrencyList().forEach((FinancialDocumentItemCurrencyDto itemCurrency) -> {
                    FinancialDocumentItemCurrency documentItemCurrency = saveDocumentItemCurrency(finalFinancialDocumentItem, itemCurrency);
                    responseDocumentItemCurrencyList.add(convertDocumentItemCurrency(documentItemCurrency));
                    documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyList);
                });
            }
            financialDocumentItemDtoList.add(documentItemToList);
            responseDocumentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
        });

        return responseDocumentSaveDto;
    }

    private FinancialDocumentItemCurrency saveDocumentItemCurrency(FinancialDocumentItem finalFinancialDocumentItem, FinancialDocumentItemCurrencyDto itemCurrency) {
        FinancialDocumentItemCurrency documentItemCurrency = new FinancialDocumentItemCurrency();
        if (itemCurrency.getForeignCreditAmount().doubleValue() < 0) {
            throw new RuleException("fin.financialDocument.saveDocument.foreignCreditAmountNegative");
        }
        if (itemCurrency.getForeignDebitAmount().doubleValue() < 0) {
            throw new RuleException("fin.financialDocument.saveDocument.foreignDebitAmountNegative");
        }
        if (itemCurrency.getExchangeRate().doubleValue() < 0) {
            throw new RuleException("fin.financialDocument.saveDocument.exchangeRateNegative");
        }
        documentItemCurrency.setFinancialDocumentItem(finalFinancialDocumentItem);
        documentItemCurrency.setForeignCreditAmount(itemCurrency.getForeignCreditAmount().doubleValue());
        documentItemCurrency.setForeignDebitAmount(itemCurrency.getForeignDebitAmount().doubleValue());
        documentItemCurrency.setExchangeRate(itemCurrency.getExchangeRate());
        documentItemCurrency.setMoneyType(moneyTypeRepository.getOne(itemCurrency.getMoneyTypeId()));
        documentItemCurrency.setMoneyPricingReference(prisingReferenceRepository.getOne(itemCurrency.getMoneyPricingReferenceId()));
        return documentItemCurrencyRepository.save(documentItemCurrency);
    }

    private FinancialDocumentReference saveDocumentReference(FinancialDocumentItem finalFinancialDocumentItem, FinancialDocumentReferenceDto documentReference) {
        FinancialDocumentReference financialDocumentReference = new FinancialDocumentReference();
        financialDocumentReference.setFinancialDocumentItem(finalFinancialDocumentItem);
        if (documentReference.getReferenceNumber().length() > 19) {
            throw new RuleException("fin.financialDocumentReference.referenceNumber");
        } else {
            financialDocumentReference.setReferenceNumber(Long.parseLong(documentReference.getReferenceNumber()));
        }
        financialDocumentReference.setReferenceDate(documentReference.getReferenceDate());
        financialDocumentReference.setReferenceDescription(documentReference.getReferenceDescription());
        financialDocumentReference = financialDocumentReferenceRepository.save(financialDocumentReference);
        financialDocumentReferenceRepository.flush();
        return financialDocumentReference;

    }


    private FinancialDocumentItem saveFinancialDocumentItem(FinancialDocument financialDocument, ResponseFinancialDocumentItemDto documentItem) {

        Double creditAmount = documentItem.getCreditAmount() % 1;
        Double debitAmount = documentItem.getDebitAmount() % 1;
        FinancialDocumentItem financialDocumentItem = new FinancialDocumentItem();
        financialDocumentItem.setFinancialDocument(financialDocument);
        financialDocumentItem.setSequenceNumber(documentItem.getSequenceNumber());

        if (documentItem.getCreditAmount() < 0) {
            throw new RuleException("fin.financialDocument.saveDocument.creditAmountNegative");
        }
        if (documentItem.getDebitAmount() < 0) {
            throw new RuleException("fin.financialDocument.saveDocument.deditAmountNegative");
        }
        if ((creditAmount != 0)) {
            financialDocumentItem.setCreditAmount(Math.ceil(documentItem.getCreditAmount()));
        } else {
            financialDocumentItem.setCreditAmount(documentItem.getCreditAmount());
        }
        if ((debitAmount != 0)) {
            financialDocumentItem.setDebitAmount(Math.ceil(documentItem.getDebitAmount()));
        } else {
            financialDocumentItem.setDebitAmount(documentItem.getDebitAmount());
        }
        financialDocumentItem.setDescription(documentItem.getDescription());
        financialDocumentItem.setFinancialAccount(financialAccountRepository.getOne(documentItem.getFinancialAccountId()));
        if (documentItem.getCentricAccountId1() != null) {
            financialDocumentItem.setCentricAccountId1(centricAccountRepository.getOne(documentItem.getCentricAccountId1()));
        }
        if (documentItem.getCentricAccountId2() != null) {
            financialDocumentItem.setCentricAccountId2(centricAccountRepository.getOne(documentItem.getCentricAccountId2()));
        }
        if (documentItem.getCentricAccountId3() != null) {
            financialDocumentItem.setCentricAccountId3(centricAccountRepository.getOne(documentItem.getCentricAccountId3()));
        }
        if (documentItem.getCentricAccountId4() != null) {
            financialDocumentItem.setCentricAccountId4(centricAccountRepository.getOne(documentItem.getCentricAccountId4()));
        }
        if (documentItem.getCentricAccountId5() != null) {
            financialDocumentItem.setCentricAccountId5(centricAccountRepository.getOne(documentItem.getCentricAccountId5()));
        }
        if (documentItem.getCentricAccountId6() != null) {
            financialDocumentItem.setCentricAccountId6(centricAccountRepository.getOne(documentItem.getCentricAccountId6()));
        }
        return financialDocumentItemRepository.save(financialDocumentItem);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentSaveDto updateDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto) {
        FinancialDocumentSaveDto responseDocumentSaveDto;
        List<ResponseFinancialDocumentItemDto> updateFinancialDocumentItemDto = new ArrayList<>();
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList = new ArrayList<>();
        final List<FinancialDocumentReferenceDto>[] newDocumentReferenceList = new List[]{new ArrayList<>()};
        List<FinancialDocumentReferenceDto> documentReferenceList = new ArrayList<>();
        List<ResponseFinancialDocumentItemDto> newFinancialDocumentItem = new ArrayList<>();
        List<FinancialDocumentItemCurrencyDto> newResponseDocumentItemCurrencyList = new ArrayList<>();
        List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyList = new ArrayList<>();
        String activityCode = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(requestFinancialDocumentSaveDto.getFinancialDocumentId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        FinancialDocument updateFinancialDocument = updateFinancialDocument(requestFinancialDocumentSaveDto);
        responseDocumentSaveDto = convertDocumentToDto(updateFinancialDocument);
        if (!requestFinancialDocumentSaveDto.getFinancialDocumentItemDtoList().isEmpty()) {
            requestFinancialDocumentSaveDto.getFinancialDocumentItemDtoList().forEach((ResponseFinancialDocumentItemDto e) -> {
                if (e.getId() != null) {
                    updateFinancialDocumentItemDto.add(e);
                    newDocumentReferenceList[0] = method1(e);
                    if (e.getDocumentItemCurrencyList() != null) {
                        e.getDocumentItemCurrencyList().forEach((FinancialDocumentItemCurrencyDto itemCurrency) -> {
                            itemCurrency.setFinancialDocumentItemId(e.getId());
                            newResponseDocumentItemCurrencyList.add(itemCurrency);
                        });
                    }
                } else {
                    newFinancialDocumentItem.add(e);
                }
            });

            newFinancialDocumentItem.forEach((ResponseFinancialDocumentItemDto newFinancialDocument) -> {
                FinancialDocumentItem financialDocumentItem = saveFinancialDocumentItem(updateFinancialDocument, newFinancialDocument);
                ResponseFinancialDocumentItemDto documentItemToList = convertDocumentItemToList(financialDocumentItem);
                if (newFinancialDocument.getDocumentReferenceList() != null) {
                    newFinancialDocument.getDocumentReferenceList().forEach((FinancialDocumentReferenceDto newDocumentReference) -> {
                        FinancialDocumentReference financialDocumentReference = saveDocumentReference(financialDocumentItem, newDocumentReference);
                        documentReferenceList.add(convertFinancialDocumentItemToDto(financialDocumentReference));
                        documentItemToList.setDocumentReferenceList(documentReferenceList);
                    });
                }
                if (newFinancialDocument.getDocumentItemCurrencyList() != null) {
                    newFinancialDocument.getDocumentItemCurrencyList().forEach((FinancialDocumentItemCurrencyDto newItemCurrency) -> {
                        FinancialDocumentItemCurrency documentItemCurrency = saveDocumentItemCurrency(financialDocumentItem, newItemCurrency);
                        responseDocumentItemCurrencyList.add(convertDocumentItemCurrency(documentItemCurrency));
                        documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyList);
                    });
                }
                financialDocumentItemDtoList.add(documentItemToList);
                responseDocumentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
            });
            financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(updateFinancialDocument.getId())
                    .forEach((FinancialDocumentItem financialDocumentItem) -> {
                        updateFinancialDocumentItemDto.stream().filter(e -> e.getId()
                                .equals(financialDocumentItem.getId()))
                                .findAny().ifPresent((ResponseFinancialDocumentItemDto responseFinancialDocumentItemDto) -> {
                            updateFinancialDocumentItem(financialDocumentItem, responseFinancialDocumentItemDto);
                            ResponseFinancialDocumentItemDto documentItemToList = convertDocumentItemToList(financialDocumentItem);
                            financialDocumentReferenceRepository.findByFinancialDocumentItemId(financialDocumentItem.getId())
                                    .forEach(documentReference -> financialDocumentReferenceRepository.deleteById(documentReference.getId()));
                            List<FinancialDocumentReferenceDto> documentReferenceNewList = new ArrayList<>();
                            newDocumentReferenceList[0].forEach((FinancialDocumentReferenceDto e) -> {
                                if (e.getFinancialDocumentItemId().equals(financialDocumentItem.getId())) {
                                    documentReferenceNewList.add(convertFinancialDocumentItemToDto(saveDocumentReference(financialDocumentItem, e)));
                                }
                                documentItemToList.setDocumentReferenceList(documentReferenceNewList);
                            });
                            documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(financialDocumentItem.getId())
                                    .forEach(itemCurrency -> documentItemCurrencyRepository.deleteById(itemCurrency.getId()));
                            List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyNewList = new ArrayList<>();
                            newResponseDocumentItemCurrencyList.forEach((FinancialDocumentItemCurrencyDto e) -> {
                                if (e.getFinancialDocumentItemId().equals(financialDocumentItem.getId())) {
                                    responseDocumentItemCurrencyNewList.add(convertDocumentItemCurrency(saveDocumentItemCurrency(financialDocumentItem, e)));
                                }
                                documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyNewList);
                            });
                            financialDocumentItemDtoList.add(documentItemToList);
                        });
                        responseDocumentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
                    });
        }
        return responseDocumentSaveDto;
    }

    private List<FinancialDocumentReferenceDto> method1(ResponseFinancialDocumentItemDto e) {
        List<FinancialDocumentReferenceDto> newDocumentReferenceList = new ArrayList<>();
        if (e.getDocumentReferenceList() != null) {
            e.getDocumentReferenceList().forEach((FinancialDocumentReferenceDto referenceDocument) -> {
                referenceDocument.setFinancialDocumentItemId(e.getId());
                newDocumentReferenceList.add(referenceDocument);
            });
        }
        return newDocumentReferenceList;
    }

    private void updateFinancialDocumentItem(FinancialDocumentItem
                                                     financialDocumentItem, ResponseFinancialDocumentItemDto responseFinancialDocumentItemDto) {

        Double creditAmount = responseFinancialDocumentItemDto.getCreditAmount() % 1;
        Double debitAmount = responseFinancialDocumentItemDto.getDebitAmount() % 1;
        if (responseFinancialDocumentItemDto.getCreditAmount() < 0) {
            throw new RuleException("fin.financialDocument.saveDocument.creditAmountNegative");
        }
        if (responseFinancialDocumentItemDto.getDebitAmount() < 0) {
            throw new RuleException("fin.financialDocument.saveDocument.deditAmountNegative");
        }
        financialDocumentItem.setSequenceNumber(responseFinancialDocumentItemDto.getSequenceNumber());

        if ((creditAmount != 0)) {
            financialDocumentItem.setCreditAmount(Math.ceil(responseFinancialDocumentItemDto.getCreditAmount()));
        } else {
            financialDocumentItem.setCreditAmount(responseFinancialDocumentItemDto.getCreditAmount());
        }
        if ((debitAmount != 0)) {
            financialDocumentItem.setDebitAmount(Math.ceil(responseFinancialDocumentItemDto.getDebitAmount()));
        } else {
            financialDocumentItem.setDebitAmount(responseFinancialDocumentItemDto.getDebitAmount());
        }

        financialDocumentItem.setDescription(responseFinancialDocumentItemDto.getDescription());
        financialDocumentItem.setFinancialAccount(financialAccountRepository.getOne(responseFinancialDocumentItemDto.getFinancialAccountId()));
        if (responseFinancialDocumentItemDto.getCentricAccountId1() != null) {
            financialDocumentItem.setCentricAccountId1(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId1()));
        }else{
            financialDocumentItem.setCentricAccountId1(null);
        }
        if (responseFinancialDocumentItemDto.getCentricAccountId2() != null) {
            financialDocumentItem.setCentricAccountId2(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId2()));
        }else{
            financialDocumentItem.setCentricAccountId2(null);
        }
        if (responseFinancialDocumentItemDto.getCentricAccountId3() != null) {
            financialDocumentItem.setCentricAccountId3(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId3()));
        }else{
            financialDocumentItem.setCentricAccountId3(null);
        }
        if (responseFinancialDocumentItemDto.getCentricAccountId4() != null) {
            financialDocumentItem.setCentricAccountId4(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId4()));
        }else{
            financialDocumentItem.setCentricAccountId4(null);
        }
        if (responseFinancialDocumentItemDto.getCentricAccountId5() != null) {
            financialDocumentItem.setCentricAccountId5(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId5()));
        }else{
            financialDocumentItem.setCentricAccountId5(null);
        }
        if (responseFinancialDocumentItemDto.getCentricAccountId6() != null) {
            financialDocumentItem.setCentricAccountId6(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId6()));
        }else{
            financialDocumentItem.setCentricAccountId6(null);
        }
    }

    private FinancialDocument saveFinancialDocument(FinancialDocumentSaveDto financialDocumentSaveDto) {
        if (financialDocumentSaveDto.getFinancialDocumentItemDtoList().isEmpty()) {
            throw new RuleException("fin.financialDocument.insertDocumentItem");
        }
        if (financialDocumentSaveDto.getFinancialDocumentStatusId() != 1) {
            throw new RuleException("fin.financialDocument.documentStatusCreat");
        }
        FinancialDocument financialDocument = financialDocumentRepository.
                findById(financialDocumentSaveDto.getFinancialDocumentId() == null ? 0L : financialDocumentSaveDto.getFinancialDocumentId()).orElse(new FinancialDocument());
        financialDocument.setDocumentDate(financialDocumentSaveDto.getDocumentDate());
        financialDocument.setDescription(financialDocumentSaveDto.getDescription());
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(financialDocumentSaveDto.getFinancialDocumentStatusId()));
        financialDocument.setAutomaticFlag(financialDocumentSaveDto.getAutomaticFlag());
        financialDocument.setOrganization(organizationRepository.getOne(SecurityHelper.getCurrentUser().getOrganizationId()));
        financialDocument.setFinancialDocumentType(financialDocumentTypeRepository.getOne(financialDocumentSaveDto.getFinancialDocumentTypeId()));
        financialDocument.setFinancialPeriod(financialPeriodRepository.getOne(financialDocumentSaveDto.getFinancialPeriodId()));
        financialDocument.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialDocumentSaveDto.getFinancialLedgerTypeId()));
        financialDocument.setFinancialDepartment(financialDepartmentRepository.getOne(financialDocumentSaveDto.getFinancialDepartmentId()));
        financialDocument.setDocumentNumber("X9999999X");
        return financialDocumentRepository.save(financialDocument);
    }

    private FinancialDocument updateFinancialDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        Long documentStatus = financialDocumentRepository.findByFinancialDocumentStatusByDocumentId(requestFinancialDocumentSaveDto.getFinancialDocumentId());
        FinancialDocument financialDocument = financialDocumentRepository.
                findById(requestFinancialDocumentSaveDto.getFinancialDocumentId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        financialDocument.setDocumentDate(requestFinancialDocumentSaveDto.getDocumentDate());
        financialDocument.setDescription(requestFinancialDocumentSaveDto.getDescription());
        if (documentStatus == 2) {
            financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(1L));
        } else {
            financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(requestFinancialDocumentSaveDto.getFinancialDocumentStatusId()));
        }
        financialDocument.setAutomaticFlag(requestFinancialDocumentSaveDto.getAutomaticFlag());
        financialDocument.setOrganization(organizationRepository.getOne(organizationId));
        financialDocument.setFinancialDocumentType(financialDocumentTypeRepository.getOne(requestFinancialDocumentSaveDto.getFinancialDocumentTypeId()));
        financialDocument.setFinancialPeriod(financialPeriodRepository.getOne(requestFinancialDocumentSaveDto.getFinancialPeriodId()));
        financialDocument.setFinancialLedgerType(financialLedgerTypeRepository.getOne(requestFinancialDocumentSaveDto.getFinancialLedgerTypeId()));
        financialDocument.setFinancialDepartment(financialDepartmentRepository.getOne(requestFinancialDocumentSaveDto.getFinancialDepartmentId()));
        financialDocument.setDocumentNumber(financialDocument.getDocumentNumber());
        financialDocument.setDepartment(departmentRepository.getOne(requestFinancialDocumentSaveDto.getDepartmentId()));
        return financialDocumentRepository.save(financialDocument);
    }

    private FinancialDocumentSaveDto convertDocumentToDto(FinancialDocument financialDocument) {
        return FinancialDocumentSaveDto.builder()
                .financialDocumentId(financialDocument.getId())
                .documentDate(financialDocument.getDocumentDate())
                .documentNumber(financialDocument.getDocumentNumber())
                .financialDocumentTypeId(financialDocument.getFinancialDocumentType().getId())
                .financialDocumentTypeDescription(financialDocument.getFinancialDocumentType().getDescription())
                .financialDocumentStatusId(financialDocument.getFinancialDocumentStatus().getId())
                .financialDocumentStatusCode(financialDocument.getFinancialDocumentStatus().getCode())
                .financialDocumentStatusDescription(financialDocument.getFinancialDocumentStatus().getName())
                .automaticFlag(financialDocument.getAutomaticFlag())
                .description(financialDocument.getDescription())
                .organizationId(financialDocument.getOrganization().getId())
                .financialLedgerTypeId(financialDocument.getFinancialLedgerType().getId())
                .financialLedgerTypeDescription(financialDocument.getFinancialLedgerType().getDescription())
                .departmentId(financialDocument.getFinancialDepartment().getId())
                .departmentName(financialDocument.getFinancialDepartment().getDepartment().getName())
                .financialPeriodId(financialDocument.getFinancialPeriod().getId())
                .financialPeriodDescription(financialDocument.getFinancialPeriod().getDescription())
                .build();
    }

    private ResponseFinancialDocumentItemDto convertDocumentItemToList(FinancialDocumentItem financialDocumentItem) {

        return ResponseFinancialDocumentItemDto.builder()
                .id(financialDocumentItem.getId())
                .sequenceNumber(financialDocumentItem.getSequenceNumber())
                .financialAccountId(financialDocumentItem.getFinancialAccount().getId())
                .financialAccountDescription(financialDocumentItem.getFinancialAccount().getDescription())
                .debitAmount(financialDocumentItem.getDebitAmount())
                .creditAmount(financialDocumentItem.getCreditAmount())
                .description(financialDocumentItem.getDescription())
                .centricAccountId1(financialDocumentItem.getCentricAccountId1() == null ? null : financialDocumentItem.getCentricAccountId1().getId())
                .centricAccountId2(financialDocumentItem.getCentricAccountId2() == null ? null : financialDocumentItem.getCentricAccountId2().getId())
                .centricAccountId3(financialDocumentItem.getCentricAccountId3() == null ? null : financialDocumentItem.getCentricAccountId3().getId())
                .centricAccountId4(financialDocumentItem.getCentricAccountId4() == null ? null : financialDocumentItem.getCentricAccountId4().getId())
                .centricAccountId5(financialDocumentItem.getCentricAccountId5() == null ? null : financialDocumentItem.getCentricAccountId5().getId())
                .centricAccountId6(financialDocumentItem.getCentricAccountId6() == null ? null : financialDocumentItem.getCentricAccountId6().getId())
                .accountRelationTypeId(financialDocumentItem.getFinancialAccount() == null ? null : financialDocumentItem.getFinancialAccount().getAccountRelationType().getId())
                .financialAccountCode(financialDocumentItem.getFinancialAccount().getCode())
                .build();
    }

    private FinancialDocumentReferenceDto convertFinancialDocumentItemToDto(FinancialDocumentReference
                                                                                    financialDocumentReference) {
        return FinancialDocumentReferenceDto.builder()
                .financialDocumentReferenceId(financialDocumentReference.getId())
                .financialDocumentItemId(financialDocumentReference.getFinancialDocumentItem().getId())
                .referenceNumber(financialDocumentReference.getReferenceNumber().toString())
                .referenceDate(financialDocumentReference.getReferenceDate())
                .referenceDescription(financialDocumentReference.getReferenceDescription())
                .build();
    }

    private FinancialDocumentItemCurrencyDto convertDocumentItemCurrency(FinancialDocumentItemCurrency
                                                                                 documentItemCurrency) {
        return FinancialDocumentItemCurrencyDto.builder()
                .financialDocumentItemCurrencyId(documentItemCurrency.getId())
                .financialDocumentItemId(documentItemCurrency.getFinancialDocumentItem().getId())
                .exchangeRate(documentItemCurrency.getExchangeRate())
                .moneyTypeId(documentItemCurrency.getMoneyType().getId())
                .moneyTypeDescription(documentItemCurrency.getMoneyType().getDescription())
                .moneyPricingReferenceId(documentItemCurrency.getMoneyPricingReference().getId())
                .moneyPricingReferenceDescription(documentItemCurrency.getMoneyPricingReference().getDescription())
                .foreignCreditAmount(BigDecimal.valueOf(documentItemCurrency.getForeignDebitAmount()))
                .foreignDebitAmount(BigDecimal.valueOf(documentItemCurrency.getForeignCreditAmount()))
                .build();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentSaveDto getFinancialDocumentInfo(FinancialDocumentDto financialDocumentDto) {
        FinancialDocumentSaveDto responseDocumentSaveDto;
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList = new ArrayList<>();
        FinancialDocument financialDocument = financialDocumentRepository.findById(financialDocumentDto.getId()).orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        responseDocumentSaveDto = convertDocumentToDto(financialDocument);
        List<FinancialDocumentItem> financialDocumentItemList = financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocument.getId());
        financialDocumentItemList.forEach((FinancialDocumentItem documentItem) -> {
            List<FinancialDocumentReferenceDto> documentReferenceList = new ArrayList<>();
            List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyList = new ArrayList<>();
            ResponseFinancialDocumentItemDto documentItemToList = convertDocumentItemToList(documentItem);
            financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItem.getId())
                    .forEach((FinancialDocumentReference documentReference) -> {
                        documentReferenceList.add(convertFinancialDocumentItemToDto(documentReference));
                        documentItemToList.setDocumentReferenceList(documentReferenceList);
                    });
            documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItem.getId())
                    .forEach((FinancialDocumentItemCurrency documentItemCurrency) -> {
                        responseDocumentItemCurrencyList.add(convertDocumentItemCurrency(documentItemCurrency));
                        documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyList);
                    });
            financialDocumentItemDtoList.add(documentItemToList);
        });
        responseDocumentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
        return responseDocumentSaveDto;
    }

    @Override
    @Transactional
    public DataSourceResult getFinancialDocumentItem(DataSourceRequest dataSourceRequest) {
        List<DataSourceRequest.FilterDescriptor> filters = dataSourceRequest.getFilter().getFilters();
        FinancialDocumentItemRequest financialDocumentReportRequest = setParameter(filters);
        Object financialDocumentItem = null;
        if (financialDocumentReportRequest.getFinancialDocumentItemId() != null) {
            financialDocumentItem = "financialDocumentItem";
        } else {
            financialDocumentReportRequest.setFinancialDocumentItemId(0L);
        }
        String orderBy;
        if (dataSourceRequest.getSort().isEmpty()) {
            orderBy = "order by FNDI.Creation_Date asc";
        } else {
            orderBy = "order by " + dataSourceRequest.getSort().get(0).getField() + " " + dataSourceRequest.getSort().get(0).getDir();
        }
        String query = " SELECT FNDI.ID, " +
                "       FNDI.FINANCIAL_DOCUMENT_ID, " +
                "       FNDI.SEQUENCE_NUMBER as sequenceNumber," +
                "       FNDI.FINANCIAL_ACCOUNT_ID as financialAccountId," +
                "       FNC.DESCRIPTION as FINANCIAL_ACCOUNT_DESCRIPTION," +
                " FNC.CODE AS FINANCIAL_ACCOUNT_CODE, " +
                "       FNDI.DEBIT_AMOUNT as DEBITAMOUNT," +
                "       FNDI.CREDIT_AMOUNT as CREDITAMOUNT," +
                "       FNDI.DESCRIPTION as description," +
                "       NVL(CNAC1.CODE, '') || NVL(CNAC1.NAME, '') ||" +
                "       NVL2(CNAC2.CODE, '-' || CNAC2.CODE, '') || NVL(CNAC2.NAME, '') ||" +
                "       NVL2(CNAC3.CODE, '-' || CNAC3.CODE, '') || NVL(CNAC3.NAME, '')  ||" +
                "       NVL2(CNAC4.CODE, '-' || CNAC4.CODE, '') || NVL(CNAC4.NAME, '')  ||" +
                "       NVL2(CNAC5.CODE, '-' || CNAC5.CODE, '') || NVL(CNAC5.NAME, '')  ||" +
                "       NVL2(CNAC6.CODE, '-' || CNAC6.CODE, '') || NVL(CNAC6.NAME, '')  AS CENTRICACCOUNTDESCRIPTION," +
                "       FNC.ACCOUNT_RELATION_TYPE_ID," +
                "       FNDI.CENTRIC_ACCOUNT_ID_1," +
                "       CNAC1.NAME as CENTRIC_ACCOUNT_DESCRIPTION_1," +
                "       FNDI.CENTRIC_ACCOUNT_ID_2," +
                "       CNAC2.NAME as CENTRIC_ACCOUNT_DESCRIPTION_2," +
                "       FNDI.CENTRIC_ACCOUNT_ID_3," +
                "       CNAC3.NAME as CENTRIC_ACCOUNT_DESCRIPTION_3," +
                "       FNDI.CENTRIC_ACCOUNT_ID_4," +
                "       CNAC4.NAME as CENTRIC_ACCOUNT_DESCRIPTION_4," +
                "       FNDI.CENTRIC_ACCOUNT_ID_5," +
                "       CNAC5.NAME  as CENTRIC_ACCOUNT_DESCRIPTION_5," +
                "       FNDI.CENTRIC_ACCOUNT_ID_6," +
                "       CNAC6.NAME as CENTRIC_ACCOUNT_DESCRIPTION_6," +
                " FNDI.CREATOR_ID " +
                "  FROM FNDC.FINANCIAL_DOCUMENT_ITEM FNDI" +
                " INNER JOIN FNAC.FINANCIAL_ACCOUNT FNC" +
                "    ON FNC.ID = FNDI.FINANCIAL_ACCOUNT_ID" +
                "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC1" +
                "    ON CNAC1.ID = FNDI.CENTRIC_ACCOUNT_ID_1" +
                "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC2" +
                "    ON CNAC2.ID = FNDI.CENTRIC_ACCOUNT_ID_2" +
                "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC3" +
                "    ON CNAC3.ID = FNDI.CENTRIC_ACCOUNT_ID_3" +
                "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC4" +
                "    ON CNAC4.ID = FNDI.CENTRIC_ACCOUNT_ID_4" +
                "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC5" +
                "    ON CNAC5.ID = FNDI.CENTRIC_ACCOUNT_ID_5" +
                "  LEFT OUTER JOIN FNAC.CENTRIC_ACCOUNT CNAC6" +
                "    ON CNAC6.ID = FNDI.CENTRIC_ACCOUNT_ID_6" +
                " WHERE FNDI.FINANCIAL_DOCUMENT_ID = :financialDocumentId " +
                " and  ( :financialDocumentItem is null or FNDI.ID = :financialDocumentItemId)" + orderBy ;

        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter("financialDocumentId", financialDocumentReportRequest.getFinancialDocumentId());
        nativeQuery.setParameter("financialDocumentItem", financialDocumentItem);
        nativeQuery.setParameter("financialDocumentItemId", financialDocumentReportRequest.getFinancialDocumentItemId());
        List<Object[]> list = nativeQuery.getResultList();
        List<FinancialDocumentItemResponse> financialDocumentItemResponses = new ArrayList<>();
        list.forEach(((Object[] object) -> {
            List<FinancialDocumentReferenceOutPutModel> documentReferenceList = new ArrayList<>();
            List<FinancialDocumentItemCurrencyOutPutModel> responseDocumentItemCurrencyList = new ArrayList<>();
            FinancialDocumentItemResponse documentItemToList = convertDocumentItemToListUpdate(object);
            financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItemToList.getId())
                    .forEach((FinancialDocumentReference documentReference) -> {
                        documentReferenceList.add(convertFinancialDocumentItem(documentReference));
                        documentItemToList.setDocumentReferenceList(documentReferenceList);
                    });
            documentItemCurrencyRepository.findByFinancialDocumentItemCurrency(documentItemToList.getId())
                    .forEach((Object[] documentItemCurrency) -> {
                        responseDocumentItemCurrencyList.add(convertDocumentItemCurrencyOutPut(documentItemCurrency));
                        documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyList);
                    });
            financialDocumentItemResponses.add(documentItemToList);
        }));
        DataSourceResult dataSourceResult = new DataSourceResult();
        dataSourceResult.setData(financialDocumentItemResponses.stream().limit(dataSourceRequest.getTake() + dataSourceRequest.getSkip()).skip(dataSourceRequest.getSkip()).collect(Collectors.toList()));
        dataSourceResult.setTotal(list.size());
        return dataSourceResult;
    }

    private FinancialDocumentItemRequest setParameter(List<DataSourceRequest.FilterDescriptor> filters) {
        FinancialDocumentItemRequest financialDocumentItemRequest = new FinancialDocumentItemRequest();
        for (DataSourceRequest.FilterDescriptor item : filters) {
            switch (item.getField()) {
                case "financialDocumentId":
                    if (item.getValue() != null) {
                        financialDocumentItemRequest.setFinancialDocumentId(Long.parseLong(item.getValue().toString()));
                    }
                    break;
                case "financialDocumentItemId":
                    if (item.getValue() != null) {
                        financialDocumentItemRequest.setFinancialDocumentItemId(Long.parseLong(item.getValue().toString()));

                    }
                    break;
                default:

                    break;
            }
        }

        return financialDocumentItemRequest;
    }

    private FinancialDocumentItemResponse convertDocumentItemToListUpdate(Object[] financialDocumentItem) {
        return FinancialDocumentItemResponse.builder()
                .id(getItemIdForLong(financialDocumentItem, 0))
                .financialDocumentId(getItemForLong(financialDocumentItem, 1))
                .sequenceNumber(getItemForLong(financialDocumentItem, 2))
                .financialAccountId(getItemForLong(financialDocumentItem, 3))
                .financialAccountDescription(financialDocumentItem[4] == null ? null : financialDocumentItem[4].toString())
                .financialAccountCode(financialDocumentItem[5] == null ? null : financialDocumentItem[5].toString())
                .debitAmount(((BigDecimal) financialDocumentItem[6]).doubleValue())
                .creditAmount(((BigDecimal) financialDocumentItem[7]).doubleValue())
                .description(financialDocumentItem[8] == null ? null : financialDocumentItem[8].toString())
                .centricAccountDescription(financialDocumentItem[9] == null ? null : financialDocumentItem[9].toString())
                .accountRelationTypeId(financialDocumentItem[10] == null ? null : Long.parseLong(financialDocumentItem[10].toString()))
                .centricAccountId1(getItemForLong(financialDocumentItem, 11))
                .centricAccountDescription1(financialDocumentItem[12] == null ? null : financialDocumentItem[12].toString())
                .centricAccountId2(getItemForLong(financialDocumentItem, 13))
                .centricAccountDescription2(financialDocumentItem[14] == null ? null : financialDocumentItem[14].toString())
                .centricAccountId3(getItemForLong(financialDocumentItem, 15))
                .centricAccountDescription3(financialDocumentItem[16] == null ? null : financialDocumentItem[16].toString())
                .centricAccountId4(financialDocumentItem[17] == null ? null : Long.parseLong(financialDocumentItem[17].toString()))
                .centricAccountDescription4(financialDocumentItem[18] == null ? null : financialDocumentItem[18].toString())
                .centricAccountId5(financialDocumentItem[19] == null ? null : Long.parseLong(financialDocumentItem[19].toString()))
                .centricAccountDescription5(financialDocumentItem[20] == null ? null : financialDocumentItem[20].toString())
                .centricAccountId6(financialDocumentItem[21] == null ? null : Long.parseLong(financialDocumentItem[21].toString()))
                .centricAccountDescription6(financialDocumentItem[22] == null ? null : financialDocumentItem[22].toString())
                .creatorId(financialDocumentItem[23] == null ? null : Long.parseLong(financialDocumentItem[23].toString()))
                .build();
    }

    private Long getItemIdForLong(Object[] financialDocumentItem, int i) {
        return Long.parseLong(financialDocumentItem[i].toString());
    }

    private Long getItemForLong(Object[] financialDocumentItem, int i) {
        return financialDocumentItem[i] == null ? null : Long.parseLong(financialDocumentItem[i].toString());
    }

    private FinancialDocumentReferenceOutPutModel convertFinancialDocumentItem(FinancialDocumentReference
                                                                                       financialDocumentReference) {
        return FinancialDocumentReferenceOutPutModel.builder()
                .id(financialDocumentReference.getId())
                .financialDocumentItemId(financialDocumentReference.getFinancialDocumentItem().getId())
                .referenceNumber(financialDocumentReference.getReferenceNumber())
                .referenceDate(financialDocumentReference.getReferenceDate())
                .referenceDescription(financialDocumentReference.getReferenceDescription())
                .build();
    }

    private FinancialDocumentItemCurrencyOutPutModel convertDocumentItemCurrencyOutPut(Object[] documentItemCurrency) {
        return FinancialDocumentItemCurrencyOutPutModel.builder()
                .id(Long.parseLong(documentItemCurrency[0].toString()))
                .financialDocumentItemId(documentItemCurrency[1] == null ? null : Long.parseLong(documentItemCurrency[1].toString()))
                .foreignDebitAmount(documentItemCurrency[2] == null ? null : new BigDecimal(documentItemCurrency[2].toString()))
                .foreignCreditAmount(documentItemCurrency[3] == null ? null : new BigDecimal(documentItemCurrency[3].toString()))
                .exchangeRate(documentItemCurrency[4] == null ? null : Long.parseLong(documentItemCurrency[4].toString()))
                .moneyTypeId(documentItemCurrency[5] == null ? null : Long.parseLong(documentItemCurrency[5].toString()))
                .moneyTypeDescription(documentItemCurrency[6] == null ? null : documentItemCurrency[6].toString())
                .moneyPricingReferenceId(documentItemCurrency[7] == null ? null : Long.parseLong(documentItemCurrency[7].toString()))
                .moneyPricingReferenceDescription(documentItemCurrency[8] == null ? null : documentItemCurrency[8].toString())
                .build();
    }

}
