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
        FinancialDocument financialDocument = saveFinancialDocument(requestFinancialDocumentSaveDto);
        responseDocumentSaveDto = convertDocumentToDto(financialDocument);
        List<ResponseFinancialDocumentItemDto> financialDocumentItemDtoList = new ArrayList<>();
        List<FinancialDocumentReferenceDto> documentReferenceList = new ArrayList<>();
        List<FinancialDocumentItemCurrencyDto> responseDocumentItemCurrencyList = new ArrayList<>();
        requestFinancialDocumentSaveDto.getFinancialDocumentItemDtoList().forEach(documentItem -> {
            FinancialDocumentItem financialDocumentItem=saveFinancialDocumentItem(financialDocument,documentItem);
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

    private FinancialDocumentItem saveFinancialDocumentItem(FinancialDocument financialDocument, ResponseFinancialDocumentItemDto documentItem) {

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
        return financialDocumentItemRepository.save(financialDocumentItem);
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

    private FinancialDocument saveFinancialDocument(FinancialDocumentSaveDto financialDocumentSaveDto) {
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
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
        financialDocument.setAutomaticFlag(financialDocumentSaveDto.getAutomaticFlag());
        financialDocument.setOrganization(organizationRepository.getOne(organizationId));
        financialDocument.setFinancialDocumentType(financialDocumentTypeRepository.getOne(financialDocumentSaveDto.getFinancialDocumentTypeId()));
        financialDocument.setFinancialPeriod(financialPeriodRepository.getOne(financialDocumentSaveDto.getFinancialPeriodId()));
        financialDocument.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialDocumentSaveDto.getFinancialLedgerTypeId()));
        financialDocument.setFinancialDepartment(financialDepartmentRepository.getOne(financialDocumentSaveDto.getDepartmentId()));
        financialDocument.setDocumentNumber(financialDocumentService.creatDocumentNumber(financialDocumentNumberDto));
        return financialDocumentRepository.save(financialDocument);
    }

    private FinancialDocument updateFinancialDocument(FinancialDocumentSaveDto requestFinancialDocumentSaveDto) {

        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        FinancialDocument financialDocument = financialDocumentRepository.
                findById(requestFinancialDocumentSaveDto.getFinancialDocumentId()).orElseThrow(() -> new RuleException("هیچ سندی یافت نشد."));
        FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
        financialDocumentNumberDto.setDate(requestFinancialDocumentSaveDto.getDocumentDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        financialDocumentNumberDto.setFinancialPeriodId(requestFinancialDocumentSaveDto.getFinancialPeriodId());
        financialDocument.setDocumentDate(requestFinancialDocumentSaveDto.getDocumentDate());
        financialDocument.setDescription(requestFinancialDocumentSaveDto.getDescription());
        financialDocument.setFinancialDocumentStatus(documentStatusRepository.getOne(requestFinancialDocumentSaveDto.getFinancialDocumentStatusId()));
        financialDocument.setAutomaticFlag(requestFinancialDocumentSaveDto.getAutomaticFlag());
        financialDocument.setOrganization(organizationRepository.getOne(organizationId));
        financialDocument.setFinancialDocumentType(financialDocumentTypeRepository.getOne(requestFinancialDocumentSaveDto.getFinancialDocumentTypeId()));
        financialDocument.setFinancialPeriod(financialPeriodRepository.getOne(requestFinancialDocumentSaveDto.getFinancialPeriodId()));
        financialDocument.setFinancialLedgerType(financialLedgerTypeRepository.getOne(requestFinancialDocumentSaveDto.getFinancialLedgerTypeId()));
        financialDocument.setFinancialDepartment(financialDepartmentRepository.getOne(requestFinancialDocumentSaveDto.getDepartmentId()));
        financialDocument.setDocumentNumber(financialDocumentService.creatDocumentNumber(financialDocumentNumberDto));
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
                .moneyTypeId(documentItemCurrency.getMoneyType().getId())
                .moneyTypeDescription(documentItemCurrency.getMoneyType().getDescription())
                .moneyPricingReferenceId(documentItemCurrency.getMoneyPricingReference().getId())
                .moneyPricingReferenceDescription(documentItemCurrency.getMoneyPricingReference().getDescription())
                .build();
    }
}
