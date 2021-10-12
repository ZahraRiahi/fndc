package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.response.*;
import ir.demisco.cfs.model.entity.FinancialDocument;
import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import ir.demisco.cfs.model.entity.FinancialDocumentItemCurrency;
import ir.demisco.cfs.model.entity.FinancialDocumentReference;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.SaveFinancialDocumentService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

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

    public DefaultSaveFinancialDocument(FinancialAccountRepository financialAccountRepository, CentricAccountRepository centricAccountRepository,
                                        FinancialDocumentRepository financialDocumentRepository, FinancialDocumentItemRepository financialDocumentItemRepository,
                                        FinancialDocumentReferenceRepository financialDocumentReferenceRepository, MoneyTypeRepository moneyTypeRepository,
                                        OrganizationRepository organizationRepository, FinancialDocumentTypeRepository financialDocumentTypeRepository,
                                        FinancialPeriodRepository financialPeriodRepository, FinancialLedgerTypeRepository financialLedgerTypeRepository,
                                        FinancialDepartmentRepository financialDepartmentRepository, MoneyPrisingReferenceRepository prisingReferenceRepository,
                                        FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository, FinancialDocumentStatusRepository documentStatusRepository,
                                        FinancialDocumentService financialDocumentService) {
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
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentSaveDto saveDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto) {

        FinancialDocumentSaveDto responseDocumentSaveDto;
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList = new ArrayList<>();
        List<FinancialDocumentReferenceDto> documentReferenceList = new ArrayList<>();
        List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyList = new ArrayList<>();
        FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
        String documentNumber;

        FinancialDocument financialDocument = saveFinancialDocument(requestFinancialDocumentSaveDto);

        financialDocumentNumberDto.setOrganizationId(financialDocument.getOrganization().getId());
        financialDocumentNumberDto.setFinancialDocumentId(financialDocument.getId());
        
        financialDocumentNumberDto.setNumberingType(1L);
        documentNumber=financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);

        financialDocument.setDocumentNumber(documentNumber);
        financialDocumentRepository.save(financialDocument);
        responseDocumentSaveDto = convertDocumentToDto(financialDocument);
        requestFinancialDocumentSaveDto.getFinancialDocumentItemDtoList().forEach(documentItem -> {
            FinancialDocumentItem financialDocumentItem = saveFinancialDocumentItem(financialDocument, documentItem);
            FinancialDocumentItem finalFinancialDocumentItem = financialDocumentItem;
            ResponseFinancialDocumentItemDto documentItemToList = convertDocumentItemToList(financialDocumentItem);
            if (documentItem.getDocumentReferenceList() != null) {
                documentItem.getDocumentReferenceList().forEach(documentReference -> {
                    FinancialDocumentReference financialDocumentReference = saveDocumentReference(finalFinancialDocumentItem, documentReference);
                    documentReferenceList.add(convertFinancialDocumentItemToDto(financialDocumentReference));
                    documentItemToList.setDocumentReferenceList(documentReferenceList);
                });
            }
            if (documentItem.getDocumentItemCurrencyList() != null) {
                documentItem.getDocumentItemCurrencyList().forEach(itemCurrency -> {
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
        documentItemCurrency.setFinancialDocumentItem(finalFinancialDocumentItem);
        documentItemCurrency.setForeignCreditAmount(itemCurrency.getForeignCreditAmount());
        documentItemCurrency.setForeignDebitAmount(itemCurrency.getForeignDebitAmount());
        documentItemCurrency.setExchangeRate(itemCurrency.getExchangeRate());
        documentItemCurrency.setMoneyType(moneyTypeRepository.getOne(itemCurrency.getMoneyTypeId()));
        documentItemCurrency.setMoneyPricingReference(prisingReferenceRepository.getOne(itemCurrency.getMoneyPricingReferenceId()));
        return documentItemCurrencyRepository.save(documentItemCurrency);
    }

    private FinancialDocumentReference saveDocumentReference(FinancialDocumentItem finalFinancialDocumentItem, FinancialDocumentReferenceDto documentReference) {
        FinancialDocumentReference financialDocumentReference = new FinancialDocumentReference();
        financialDocumentReference.setFinancialDocumentItem(finalFinancialDocumentItem);
        financialDocumentReference.setReferenceNumber(documentReference.getReferenceNumber());
        financialDocumentReference.setReferenceDate(documentReference.getReferenceDate());
        financialDocumentReference.setReferenceDescription(documentReference.getReferenceDescription());
        return financialDocumentReferenceRepository.save(financialDocumentReference);

    }

    private FinancialDocumentItem saveFinancialDocumentItem(FinancialDocument financialDocument, ResponseFinancialDocumentItemDto documentItem) {

        double creditAmount = documentItem.getCreditAmount() % 1;
        double debitAmount = documentItem.getDebitAmount() % 1;
        FinancialDocumentItem financialDocumentItem = new FinancialDocumentItem();
        financialDocumentItem.setFinancialDocument(financialDocument);
        financialDocumentItem.setSequenceNumber(documentItem.getSequenceNumber());
        if ((creditAmount != 000)) {
            financialDocumentItem.setCreditAmount(Math.ceil(documentItem.getCreditAmount()));
        }else{
            financialDocumentItem.setCreditAmount(documentItem.getCreditAmount());
        }
        if ((debitAmount != 000)) {
            financialDocumentItem.setDebitAmount(Math.ceil(documentItem.getDebitAmount()));
        }else{
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
        List<ResponseFinancialDocumentItemDto> newFinancialDocumentItem = new ArrayList<>();
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList = new ArrayList<>();
        List<FinancialDocumentReferenceDto> newDocumentReferenceList = new ArrayList<>();
        List<FinancialDocumentReferenceDto> updateDocumentReferenceList = new ArrayList<>();
        List<FinancialDocumentReferenceDto> documentReferenceList = new ArrayList<>();
        List<FinancialDocumentItemCurrencyDto> newResponseDocumentItemCurrencyList = new ArrayList<>();
        List<FinancialDocumentItemCurrencyDto> updateResponseDocumentItemCurrencyList = new ArrayList<>();
        List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyList = new ArrayList<>();
        FinancialDocument updateFinancialDocument = updateFinancialDocument(requestFinancialDocumentSaveDto);
        responseDocumentSaveDto = convertDocumentToDto(updateFinancialDocument);
        requestFinancialDocumentSaveDto.getFinancialDocumentItemDtoList().forEach(e -> {
            if (e.getId() != null) {
                updateFinancialDocumentItemDto.add(e);
                if (e.getDocumentReferenceList() != null) {
                    e.getDocumentReferenceList().forEach(referenceDocument -> {
                        if (referenceDocument.getFinancialDocumentReferenceId() == null) {
                            referenceDocument.setFinancialDocumentItemId(e.getId());
                            newDocumentReferenceList.add(referenceDocument);
                        } else {
                            referenceDocument.setFinancialDocumentItemId(e.getId());
                            updateDocumentReferenceList.add(referenceDocument);
                        }

                    });
                }
                if (e.getDocumentItemCurrencyList() != null) {
                    e.getDocumentItemCurrencyList().forEach(itemCurrency -> {
                        if (itemCurrency.getFinancialDocumentItemCurrencyId() == null) {
                            itemCurrency.setFinancialDocumentItemId(e.getId());
                            newResponseDocumentItemCurrencyList.add(itemCurrency);
                        } else {
                            itemCurrency.setFinancialDocumentItemId(e.getId());
                            updateResponseDocumentItemCurrencyList.add(itemCurrency);
                        }

                    });
                }
            } else {
                newFinancialDocumentItem.add(e);
            }
        });
        newFinancialDocumentItem.forEach(newFinancialDocument -> {
            FinancialDocumentItem financialDocumentItem = saveFinancialDocumentItem(updateFinancialDocument, newFinancialDocument);
            FinancialDocumentItem finalFinancialDocumentItem = financialDocumentItem;
            ResponseFinancialDocumentItemDto documentItemToList = convertDocumentItemToList(financialDocumentItem);
            if (newFinancialDocument.getDocumentReferenceList() != null) {
                newFinancialDocument.getDocumentReferenceList().forEach(newDocumentReference -> {
                    FinancialDocumentReference financialDocumentReference = saveDocumentReference(finalFinancialDocumentItem, newDocumentReference);
                    documentReferenceList.add(convertFinancialDocumentItemToDto(financialDocumentReference));
                    documentItemToList.setDocumentReferenceList(documentReferenceList);
                });
            }
            if (newFinancialDocument.getDocumentItemCurrencyList() != null) {
                newFinancialDocument.getDocumentItemCurrencyList().forEach(newItemCurrency -> {
                    FinancialDocumentItemCurrency documentItemCurrency = saveDocumentItemCurrency(finalFinancialDocumentItem, newItemCurrency);
                    responseDocumentItemCurrencyList.add(convertDocumentItemCurrency(documentItemCurrency));
                    documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyList);
                });
            }
            financialDocumentItemDtoList.add(documentItemToList);
            responseDocumentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);

        });
        financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(updateFinancialDocument.getId())
                .forEach(financialDocumentItem -> {
                    updateFinancialDocumentItemDto.stream().filter(e -> e.getId()
                            .equals(financialDocumentItem.getId()))
                            .findAny().ifPresent(responseFinancialDocumentItemDto ->
                    {
                        updateFinancialDocumentItem(financialDocumentItem, responseFinancialDocumentItemDto);
                        ResponseFinancialDocumentItemDto documentItemToList = convertDocumentItemToList(financialDocumentItem);
                        financialDocumentReferenceRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(financialDocumentItem.getId())
                                .forEach(documentReference -> {
                                    updateDocumentReferenceList
                                            .stream().filter(r -> r.getFinancialDocumentReferenceId().equals(documentReference.getId()))
                                            .findAny().ifPresent(responseReference -> {
                                        updateDocumentReferenc(documentReference, responseReference);
                                        documentReferenceList.add(convertFinancialDocumentItemToDto(documentReference));
                                        documentItemToList.setDocumentReferenceList(documentReferenceList);

                                    });

                                });
                        newDocumentReferenceList.stream().filter(newReference -> newReference.getFinancialDocumentItemId().equals(financialDocumentItem.getId()))
                                .findAny().ifPresent(responseReference -> {
                            FinancialDocumentReference financialDocumentReference = saveDocumentReference(financialDocumentItem, responseReference);
                            List<FinancialDocumentReferenceDto>   documentReferenceNewList=new ArrayList<>() ;
                            documentReferenceNewList.add(convertFinancialDocumentItemToDto(financialDocumentReference));
                            documentItemToList.setDocumentReferenceList(documentReferenceNewList);
                        });
                        documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(financialDocumentItem.getId())
                                .forEach(itemCurrency -> {
                                    updateResponseDocumentItemCurrencyList.stream().filter(c -> c.getFinancialDocumentItemCurrencyId()
                                            .equals(itemCurrency.getId())).findAny().ifPresent(financialItemCurrency -> {
                                        updateDocumentItemCurrency(itemCurrency, financialItemCurrency);
                                        responseDocumentItemCurrencyList.add(convertDocumentItemCurrency(itemCurrency));
                                        documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyList);

                                    });

                                });
                        newResponseDocumentItemCurrencyList.stream().filter(newCurrency -> newCurrency.getFinancialDocumentItemId()
                                .equals(financialDocumentItem.getId()))
                                .findAny().ifPresent(newFinancialItemCurrency -> {
                            FinancialDocumentItemCurrency documentItemCurrency = saveDocumentItemCurrency(financialDocumentItem, newFinancialItemCurrency);
                            List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyNewList=new ArrayList<>();
                                responseDocumentItemCurrencyNewList.add(convertDocumentItemCurrency(documentItemCurrency));
                                documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyNewList);
                        });

                        financialDocumentItemDtoList.add(documentItemToList);
                    });

                    responseDocumentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
                });

        return responseDocumentSaveDto;
    }

    private void updateDocumentItemCurrency(FinancialDocumentItemCurrency itemCurrency, FinancialDocumentItemCurrencyDto financialItemCurrency) {
        itemCurrency.setForeignCreditAmount(financialItemCurrency.getForeignCreditAmount());
        itemCurrency.setForeignDebitAmount(financialItemCurrency.getForeignDebitAmount());
        itemCurrency.setExchangeRate(financialItemCurrency.getExchangeRate());
        itemCurrency.setMoneyType(moneyTypeRepository.getOne(itemCurrency.getMoneyType().getId()));
        itemCurrency.setMoneyPricingReference(prisingReferenceRepository.getOne(financialItemCurrency.getMoneyPricingReferenceId()));
    }

    private void updateDocumentReferenc(FinancialDocumentReference documentReference, FinancialDocumentReferenceDto responseReference) {
        documentReference.setReferenceNumber(responseReference.getReferenceNumber());
        documentReference.setReferenceDate(responseReference.getReferenceDate());
        documentReference.setReferenceDescription(responseReference.getReferenceDescription());
    }

    private void updateFinancialDocumentItem(FinancialDocumentItem financialDocumentItem, ResponseFinancialDocumentItemDto responseFinancialDocumentItemDto) {

        double creditAmount = responseFinancialDocumentItemDto.getCreditAmount() % 1;
        double debitAmount = responseFinancialDocumentItemDto.getDebitAmount() % 1;

        financialDocumentItem.setSequenceNumber(responseFinancialDocumentItemDto.getSequenceNumber());

        if ((creditAmount != 000)) {
            financialDocumentItem.setCreditAmount(Math.ceil(responseFinancialDocumentItemDto.getCreditAmount()));
        }else{
            financialDocumentItem.setCreditAmount(responseFinancialDocumentItemDto.getCreditAmount());
        }
        if ((debitAmount != 000)) {
            financialDocumentItem.setDebitAmount(Math.ceil(responseFinancialDocumentItemDto.getDebitAmount()));
        }else{
            financialDocumentItem.setDebitAmount(responseFinancialDocumentItemDto.getDebitAmount());
        }

        financialDocumentItem.setDescription(responseFinancialDocumentItemDto.getDescription());
        financialDocumentItem.setFinancialAccount(financialAccountRepository.getOne(responseFinancialDocumentItemDto.getFinancialAccountId()));
        if(responseFinancialDocumentItemDto.getCentricAccountId1() != null) {
            financialDocumentItem.setCentricAccountId1(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId1()));
        }
        if(responseFinancialDocumentItemDto.getCentricAccountId2() != null) {
            financialDocumentItem.setCentricAccountId2(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId2()));
        }
        if(responseFinancialDocumentItemDto.getCentricAccountId3() != null) {
            financialDocumentItem.setCentricAccountId3(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId3()));
        }
        if(responseFinancialDocumentItemDto.getCentricAccountId4() != null) {
            financialDocumentItem.setCentricAccountId4(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId4()));
        }
        if(responseFinancialDocumentItemDto.getCentricAccountId5() != null) {
            financialDocumentItem.setCentricAccountId5(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId5()));
        }
        if(responseFinancialDocumentItemDto.getCentricAccountId6() != null) {
            financialDocumentItem.setCentricAccountId6(centricAccountRepository.getOne(responseFinancialDocumentItemDto.getCentricAccountId6()));
        }
    }

    private FinancialDocument saveFinancialDocument(FinancialDocumentSaveDto financialDocumentSaveDto) {
        Long organizationId = 100L;

        if (financialDocumentSaveDto.getFinancialDocumentItemDtoList().isEmpty()) {
            throw new RuleException("لطفا یک ردیف وارد کنید.");
        }
        FinancialDocument financialDocument = financialDocumentRepository.
                findById(financialDocumentSaveDto.getFinancialDocumentId() == null ? 0L : financialDocumentSaveDto.getFinancialDocumentId()).orElse(new FinancialDocument());
        financialDocument.setDocumentDate(financialDocumentSaveDto.getDocumentDate());
        financialDocument.setDescription(financialDocumentSaveDto.getDescription());
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(financialDocumentSaveDto.getFinancialDocumentStatusId()));
        financialDocument.setAutomaticFlag(financialDocumentSaveDto.getAutomaticFlag());
        financialDocument.setOrganization(organizationRepository.getOne(organizationId));
        financialDocument.setFinancialDocumentType(financialDocumentTypeRepository.getOne(financialDocumentSaveDto.getFinancialDocumentTypeId()));
        financialDocument.setFinancialPeriod(financialPeriodRepository.getOne(financialDocumentSaveDto.getFinancialPeriodId()));
        financialDocument.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialDocumentSaveDto.getFinancialLedgerTypeId()));
        financialDocument.setFinancialDepartment(financialDepartmentRepository.getOne(financialDocumentSaveDto.getDepartmentId()));
        financialDocument.setDocumentNumber("9999");
        return financialDocumentRepository.save(financialDocument);
    }

    private FinancialDocument updateFinancialDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto) {

        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        FinancialDocument financialDocument = financialDocumentRepository.
                findById(requestFinancialDocumentSaveDto.getFinancialDocumentId()).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        financialDocument.setDocumentDate(requestFinancialDocumentSaveDto.getDocumentDate());
        financialDocument.setDescription(requestFinancialDocumentSaveDto.getDescription());
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(requestFinancialDocumentSaveDto.getFinancialDocumentStatusId()));
        financialDocument.setAutomaticFlag(requestFinancialDocumentSaveDto.getAutomaticFlag());
        financialDocument.setOrganization(organizationRepository.getOne(organizationId));
        financialDocument.setFinancialDocumentType(financialDocumentTypeRepository.getOne(requestFinancialDocumentSaveDto.getFinancialDocumentTypeId()));
        financialDocument.setFinancialPeriod(financialPeriodRepository.getOne(requestFinancialDocumentSaveDto.getFinancialPeriodId()));
        financialDocument.setFinancialLedgerType(financialLedgerTypeRepository.getOne(requestFinancialDocumentSaveDto.getFinancialLedgerTypeId()));
        financialDocument.setFinancialDepartment(financialDepartmentRepository.getOne(requestFinancialDocumentSaveDto.getDepartmentId()));
        financialDocument.setDocumentNumber(financialDocument.getDocumentNumber());
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
                .automaticFlag(financialDocument.getAutomaticFlag())
                .description(financialDocument.getDescription())
                .organizationId(financialDocument.getOrganization().getId())
                .financialLedgerTypeId(financialDocument.getFinancialLedgerType().getId())
                .financialLedgerTypeDescription(financialDocument.getFinancialLedgerType().getDescription())
                .departmentId(financialDocument.getFinancialDepartment().getId())
                .departmentName(financialDocument.getFinancialDepartment().getName())
                .financialPeriodId(financialDocument.getFinancialPeriod().getId())
                .financialPeriodDescription(financialDocument.getFinancialPeriod().getDescription())
                .build();
    }

    private ResponseFinancialDocumentItemDto convertDocumentItemToList(FinancialDocumentItem financialDocumentItem) {

        return ResponseFinancialDocumentItemDto.builder()
                .id(financialDocumentItem.getId())
                .sequenceNumber(financialDocumentItem.getSequenceNumber())
                .financialAccountId(financialDocumentItem.getFinancialAccount().getId())
                .financialAccountDescription(financialDocumentItem.getFinancialAccount().getDescription() == null ? null :financialDocumentItem.getFinancialAccount().getDescription())
                .debitAmount(financialDocumentItem.getDebitAmount())
                .creditAmount(financialDocumentItem.getCreditAmount())
                .description(financialDocumentItem.getDescription())
                .centricAccountId1(financialDocumentItem.getCentricAccountId1() == null ? null : financialDocumentItem.getCentricAccountId1().getId())
                .centricAccountId2(financialDocumentItem.getCentricAccountId2() == null ? null : financialDocumentItem.getCentricAccountId2().getId())
                .centricAccountId3(financialDocumentItem.getCentricAccountId3() == null ? null : financialDocumentItem.getCentricAccountId3().getId())
                .centricAccountId4(financialDocumentItem.getCentricAccountId4() == null ? null : financialDocumentItem.getCentricAccountId4().getId())
                .centricAccountId5(financialDocumentItem.getCentricAccountId5() == null ? null : financialDocumentItem.getCentricAccountId5().getId())
                .centricAccountId6(financialDocumentItem.getCentricAccountId6() == null ? null : financialDocumentItem.getCentricAccountId6().getId())
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

    private FinancialDocumentItemCurrencyDto convertDocumentItemCurrency(FinancialDocumentItemCurrency documentItemCurrency) {
        return FinancialDocumentItemCurrencyDto.builder()
                .financialDocumentItemCurrencyId(documentItemCurrency.getId())
                .financialDocumentItemId(documentItemCurrency.getFinancialDocumentItem().getId())
                .foreignDebitAmount(documentItemCurrency.getForeignDebitAmount())
                .foreignCreditAmount(documentItemCurrency.getForeignCreditAmount())
                .exchangeRate(documentItemCurrency.getExchangeRate())
                .moneyTypeId(documentItemCurrency.getMoneyType().getId())
                .moneyTypeDescription(documentItemCurrency.getMoneyType().getDescription())
                .moneyPricingReferenceId(documentItemCurrency.getMoneyPricingReference().getId())
                .moneyPricingReferenceDescription(documentItemCurrency.getMoneyPricingReference().getDescription())
                .build();
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentSaveDto getFinancialDocumentInfo(FinancialDocumentDto financialDocumentDto) {
        FinancialDocumentSaveDto responseDocumentSaveDto;
        List<ResponseFinancialDocumentItemDto>  financialDocumentItemDtoList=new ArrayList<>();
        List<FinancialDocumentReferenceDto> documentReferenceList = new ArrayList<>();
        List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyList = new ArrayList<>();
        FinancialDocument financialDocument=financialDocumentRepository.findById(financialDocumentDto.getId()).orElseThrow(() -> new RuleException("سند یافت نشد"));
        responseDocumentSaveDto = convertDocumentToDto(financialDocument);
        List<FinancialDocumentItem>  financialDocumentItemList=financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocument.getId());
        financialDocumentItemList.forEach(documentItem ->{
            ResponseFinancialDocumentItemDto documentItemToList=convertDocumentItemToList(documentItem);
            financialDocumentReferenceRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItem.getId())
                    .forEach(documentReference ->{
                        documentReferenceList.add(convertFinancialDocumentItemToDto(documentReference));
                        documentItemToList.setDocumentReferenceList(documentReferenceList);
                    });
            documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItem.getId())
                    .forEach(documentItemCurrency ->{
                        responseDocumentItemCurrencyList.add(convertDocumentItemCurrency(documentItemCurrency));
                        documentItemToList.setDocumentItemCurrencyList(responseDocumentItemCurrencyList);
                    });
            financialDocumentItemDtoList.add(documentItemToList);
        });
        responseDocumentSaveDto.setFinancialDocumentItemDtoList(financialDocumentItemDtoList);
        return responseDocumentSaveDto;
    }
}
