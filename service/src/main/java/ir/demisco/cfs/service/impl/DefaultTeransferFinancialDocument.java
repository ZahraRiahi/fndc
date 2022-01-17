package ir.demisco.cfs.service.impl;


import ir.demisco.cfs.model.dto.request.FinancialDocumentTransferRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.response.*;
import ir.demisco.cfs.model.entity.*;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.api.TransferFinancialDocumentService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.hibernate.internal.util.SerializationHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class DefaultTeransferFinancialDocument implements TransferFinancialDocumentService {

    private final FinancialDocumentService financialDocumentService;
    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialDocumentItemRepository financialDocumentItemRepository;
    private final FinancialDocumentReferenceRepository financialDocumentReferenceRepository;
    private final FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository;
    private final FinancialPeriodService financialPeriodService;
    private final FinancialDocumentStatusRepository financialDocumentStatusRepository;
    private final OrganizationRepository organizationRepository;
    private final FinancialDocumentNumberRepository financialDocumentNumberRepository;
    private final FinancialPeriodRepository financialPeriodRepository;


    public DefaultTeransferFinancialDocument(FinancialDocumentService financialDocumentService, FinancialDocumentRepository financialDocumentRepository, FinancialDocumentItemRepository financialDocumentItemRepository,
                                             FinancialDocumentReferenceRepository financialDocumentReferenceRepository,
                                             FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository,
                                             FinancialPeriodRepository financialPeriodRepository, FinancialPeriodService financialPeriodService, FinancialDocumentStatusRepository financialDocumentStatusRepository, OrganizationRepository organizationRepository, FinancialDocumentTypeRepository financialDocumentTypeRepository, FinancialDocumentNumberRepository financialDocumentNumberRepository, FinancialPeriodRepository financialPeriodRepository1) {
        this.financialDocumentService = financialDocumentService;
        this.financialDocumentRepository = financialDocumentRepository;
        this.financialDocumentItemRepository = financialDocumentItemRepository;
        this.financialDocumentReferenceRepository = financialDocumentReferenceRepository;
        this.documentItemCurrencyRepository = documentItemCurrencyRepository;
        this.financialPeriodService = financialPeriodService;
        this.financialDocumentStatusRepository = financialDocumentStatusRepository;
        this.organizationRepository = organizationRepository;
        this.financialDocumentNumberRepository = financialDocumentNumberRepository;
        this.financialPeriodRepository = financialPeriodRepository1;
    }

//    @Override
//    @Transactional(rollbackOn = Throwable.class)
//    public boolean transferDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
//
//        switch (financialDocumentTransferDto.getTransferType()) {
//            case 1:
//                copyInOtherDocument(financialDocumentTransferDto);
//                break;
//            case 2:
//                transferToOtherDocument(financialDocumentTransferDto);
//                break;
//            case 3:
//                copyInNewDocument(financialDocumentTransferDto);
//                break;
//            case 4:
//                transferToNewDocument(financialDocumentTransferDto);
//                break;
//            case 5:
//                changDate(financialDocumentTransferDto);
//                break;
//            case 6:
//                displacementWithOtherDocument(financialDocumentTransferDto);
//                break;
//            case 7:
//                copyDocument(financialDocumentTransferDto);
//                break;
//        }
//
//        return true;
//    }

//    private void copyInOtherDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
//
//        FinancialDocument financialDocument;
//        if (financialDocumentTransferDto.getDocumentNumber() != null) {
//            financialDocument = financialDocumentRepository.getFinancialDocumentByDocumentNumber(financialDocumentTransferDto.getDocumentNumber());
//            if (financialDocument == null) {
//                throw new RuleException("fin.financialDocument.notExistDocumentInOrgan");
//            } else {
//                getDocumentIdList(financialDocumentTransferDto)
//                        .forEach(documentItemId -> {
//                            FinancialDocumentItem financialDocumentItem = saveFinancialDocumentItem(documentItemId, financialDocument);
//                            documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItemId)
//                                    .forEach(documentItemCurrency -> saveFinancialDocumentItemCurrency(documentItemCurrency, financialDocumentItem));
//                            financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItemId)
//                                    .forEach(documentReference -> saveFinancialReference(documentReference, financialDocumentItem));
//                        });
//
//            }
//        } else {
//            throw new RuleException("fin.financialDocument.noSelectDocumentNumber");
//        }
//
//    }

    private List<Long> getDocumentIdList(FinancialDocumentTransferDto financialDocumentTransferDto) {
        if (!financialDocumentTransferDto.getAllItemFlag() &&
                financialDocumentTransferDto.getFinancialDocumentItemIdList().isEmpty()) {
            throw new RuleException("fin.financialDocument.noSelectDocumentItem");
        }
        return financialDocumentTransferDto.getAllItemFlag() ?
                financialDocumentItemRepository.findByFinancialDocumentIdByDocumentId(financialDocumentTransferDto.getId()) :
                financialDocumentTransferDto.getFinancialDocumentItemIdList();
    }

//    private void transferToOtherDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
//
//        if (financialDocumentTransferDto.getDocumentNumber() != null) {
//            FinancialDocument financialDocument = financialDocumentRepository.getFinancialDocumentByDocumentNumber(financialDocumentTransferDto.getDocumentNumber());
//            if (financialDocument == null) {
//                throw new RuleException("fin.financialDocument.notExistDocumentNumberInOrgan");
//            } else {
//                getDocumentIdList(financialDocumentTransferDto)
//                        .forEach(documentItemId -> {
//                            FinancialDocumentItem financialDocumentItem = saveTransferFinancialDocumentItem(documentItemId, financialDocument);
//                            documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItemId)
//                                    .forEach(documentItemCurrency -> saveTransferFinancialDocumentItemCurrency(documentItemCurrency, financialDocumentItem));
//                            financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItemId)
//                                    .forEach(documentReference -> saveTransferFinancialReference(documentReference, financialDocumentItem));
//
//                        });
//                deleteDocument(financialDocumentTransferDto);
//            }
//        } else {
//            throw new RuleException("fin.financialDocument.noSelectDocumentNumber");
//        }
//    }

    private void copyInNewDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
        FinancialDocument newFinancialDocument = saveNewFinancialDocument(financialDocumentTransferDto);
        List<Long> financialDocumentItemIdList = getDocumentIdList(financialDocumentTransferDto);
        financialDocumentItemIdList.forEach(documentItemId -> {
            FinancialDocumentItem financialDocumentItem = saveFinancialDocumentItem(documentItemId, newFinancialDocument);
            documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItemId)
                    .forEach(documentItemCurrency -> saveFinancialDocumentItemCurrency(documentItemCurrency, financialDocumentItem));
            financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItemId)
                    .forEach(documentReference -> saveFinancialReference(documentReference, financialDocumentItem));

        });
    }

    private void transferToNewDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {


        FinancialDocument newFinancialDocument = saveNewFinancialDocument(financialDocumentTransferDto);
        List<Long> financialDocumentItemIdList = getDocumentIdList(financialDocumentTransferDto);
        financialDocumentItemIdList.forEach(documentItemId -> {
            FinancialDocumentItem financialDocumentItem = saveTransferFinancialDocumentItem(documentItemId, newFinancialDocument);
            documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItemId)
                    .forEach(documentItemCurrency -> saveTransferFinancialDocumentItemCurrency(documentItemCurrency, financialDocumentItem));
            financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItemId)
                    .forEach(documentReference -> saveTransferFinancialReference(documentReference, financialDocumentItem));

        });
        deleteDocument(financialDocumentTransferDto);
    }

    private void deleteDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
        List<FinancialDocumentItem> financialDocumentItem =
                financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocumentTransferDto.getId());
        if (financialDocumentItem.isEmpty()) {
            FinancialDocument oldFinancialDocument = financialDocumentRepository.findById(financialDocumentTransferDto.getId())
                    .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
            oldFinancialDocument.setDeletedDate(LocalDateTime.now());
            financialDocumentRepository.save(oldFinancialDocument);
        }
    }

//    private void changDate(FinancialDocumentTransferDto financialDocumentTransferDto) {
//
//        FinancialDocument updateFinancialDocument = financialDocumentRepository.findById(financialDocumentTransferDto.getId())
//                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
//        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
//        FinancialDocument financialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(financialDocumentTransferDto.getId());
//        if (financialDocument == null) {
//            throw new RuleException("fin.financialDocument.openStatusPeriod");
//        } else {
//            LocalDate dateTime = financialDocumentTransferDto.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            List<FinancialPeriod> financialPeriodList = financialPeriodRepository
//                    .getPeriodByParam(dateTime.toString(), organizationId);
//            if (financialPeriodList.isEmpty()) {
//                throw new RuleException("fin.financialDocument.openStatusPeriodInDate");
//            } else {
//                FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
//                financialDocumentNumberDto.setFinancialDocumentId(updateFinancialDocument.getId());
//                financialDocumentNumberDto.setOrganizationId(updateFinancialDocument.getOrganization().getId());
//                financialDocumentNumberDto.setNumberingType(1L);
//                updateFinancialDocument.setDocumentDate(financialDocumentTransferDto.getDate());
//                updateFinancialDocument.setFinancialPeriod(financialPeriodRepository.getOne(financialPeriodList.get(0).getId()));
//                updateFinancialDocument.setDocumentNumber(financialDocumentService.creatDocumentNumber(financialDocumentNumberDto));
//                financialDocumentRepository.save(updateFinancialDocument);
//            }
//
//        }
//    }
//
//    private void displacementWithOtherDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
//        if (financialDocumentTransferDto.getDocumentNumber() != null) {
//            FinancialDocument findFinancialDocument = financialDocumentRepository.getFinancialDocumentByDocumentNumber(financialDocumentTransferDto.getDocumentNumber());
//            if (findFinancialDocument == null) {
//                throw new RuleException("fin.financialDocument.notExistDocumentInOrgan");
//            } else {
//                FinancialDocument activeFinancialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(financialDocumentTransferDto.getId());
//                if ((activeFinancialDocument == null)) {
//                    throw new RuleException("fin.financialDocument.openStatusPeriod");
//                } else {
//                    FinancialDocument newFinancialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(findFinancialDocument.getId());
//                    if ((newFinancialDocument == null)) {
//                        throw new RuleException("fin.financialDocument.openStatusPeriodDestinationDocument");
//                    } else {
//                        FinancialDocument financialDocumentMove = (FinancialDocument) SerializationHelper.clone(activeFinancialDocument);
//                        activeFinancialDocument.setDocumentNumber(newFinancialDocument.getDocumentNumber());
//                        activeFinancialDocument.setDocumentDate(newFinancialDocument.getDocumentDate());
//                        activeFinancialDocument.setFinancialPeriod(newFinancialDocument.getFinancialPeriod());
//                        financialDocumentRepository.save(activeFinancialDocument);
//                        newFinancialDocument.setDocumentNumber(financialDocumentMove.getDocumentNumber());
//                        newFinancialDocument.setDocumentDate(financialDocumentMove.getDocumentDate());
//                        newFinancialDocument.setFinancialPeriod(financialDocumentMove.getFinancialPeriod());
//                        financialDocumentRepository.save(newFinancialDocument);
//
//                    }
//                }
//            }
//        }
//    }

    private void copyDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
        FinancialDocument newFinancialDocument = saveNewFinancialDocument(financialDocumentTransferDto);
        List<Long> financialDocumentItemIdList = financialDocumentItemRepository.findByFinancialDocumentIdByDocumentId(financialDocumentTransferDto.getId());
        financialDocumentItemIdList.forEach(documentItemId -> {
            FinancialDocumentItem financialDocumentItem = saveTransferFinancialDocumentItem(documentItemId, newFinancialDocument);
            documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItemId)
                    .forEach(documentItemCurrency -> saveTransferFinancialDocumentItemCurrency(documentItemCurrency, financialDocumentItem));
            financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItemId)
                    .forEach(documentReference -> saveTransferFinancialReference(documentReference, financialDocumentItem));

        });
        FinancialDocument oldFinancialDocument = financialDocumentRepository.findById(financialDocumentTransferDto.getId())
                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        oldFinancialDocument.setDeletedDate(LocalDateTime.now());
        financialDocumentRepository.save(oldFinancialDocument);
    }

    private FinancialDocumentItem saveFinancialDocumentItem(Long documentItemId, FinancialDocument financialDocument) {
        FinancialDocumentItem oldFinancialDocumentItem = financialDocumentItemRepository.findById(documentItemId).
                orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocumentItem"));
        return getFinancialDocumentItem(financialDocument, oldFinancialDocumentItem);
    }

    private FinancialDocumentItem getFinancialDocumentItem(FinancialDocument financialDocument, FinancialDocumentItem oldFinancialDocumentItem) {
        FinancialDocumentItem newFinancialDocumentItem = new FinancialDocumentItem();
        newFinancialDocumentItem.setFinancialDocument(financialDocument);
        newFinancialDocumentItem.setSequenceNumber(oldFinancialDocumentItem.getSequenceNumber());
        newFinancialDocumentItem.setDebitAmount(oldFinancialDocumentItem.getDebitAmount());
        newFinancialDocumentItem.setCreditAmount(oldFinancialDocumentItem.getCreditAmount());
        newFinancialDocumentItem.setDescription(oldFinancialDocumentItem.getDescription());
        newFinancialDocumentItem.setFinancialAccount(oldFinancialDocumentItem.getFinancialAccount());
        newFinancialDocumentItem.setCentricAccountId1(oldFinancialDocumentItem.getCentricAccountId1());
        newFinancialDocumentItem.setCentricAccountId2(oldFinancialDocumentItem.getCentricAccountId2());
        newFinancialDocumentItem.setCentricAccountId3(oldFinancialDocumentItem.getCentricAccountId3());
        newFinancialDocumentItem.setCentricAccountId4(oldFinancialDocumentItem.getCentricAccountId4());
        newFinancialDocumentItem.setCentricAccountId5(oldFinancialDocumentItem.getCentricAccountId5());
        newFinancialDocumentItem.setCentricAccountId6(oldFinancialDocumentItem.getCentricAccountId6());
        return financialDocumentItemRepository.save(newFinancialDocumentItem);
    }

    private void saveFinancialDocumentItemCurrency(FinancialDocumentItemCurrency documentItemCurrency,
                                                   FinancialDocumentItem financialDocumentItem) {
        FinancialDocumentItemCurrency newFinancialDocumentItemCurrency = new FinancialDocumentItemCurrency();
        newFinancialDocumentItemCurrency.setFinancialDocumentItem(financialDocumentItem);
        newFinancialDocumentItemCurrency.setForeignDebitAmount(documentItemCurrency.getForeignDebitAmount());
        newFinancialDocumentItemCurrency.setExchangeRate(documentItemCurrency.getExchangeRate());
        newFinancialDocumentItemCurrency.setMoneyType(documentItemCurrency.getMoneyType());
        newFinancialDocumentItemCurrency.setMoneyPricingReference(documentItemCurrency.getMoneyPricingReference());
        newFinancialDocumentItemCurrency.setForeignCreditAmount(documentItemCurrency.getForeignCreditAmount());
        documentItemCurrencyRepository.save(newFinancialDocumentItemCurrency);
    }

    private void saveFinancialReference(FinancialDocumentReference documentReference, FinancialDocumentItem financialDocumentItem) {
        FinancialDocumentReference financialDocumentReference = new FinancialDocumentReference();
        financialDocumentReference.setFinancialDocumentItem(financialDocumentItem);
        financialDocumentReference.setReferenceNumber(documentReference.getReferenceNumber());
        financialDocumentReference.setReferenceDescription(documentReference.getReferenceDescription());
        financialDocumentReference.setReferenceDate(documentReference.getReferenceDate());
        financialDocumentReferenceRepository.save(financialDocumentReference);
    }

    private FinancialDocumentItem saveTransferFinancialDocumentItem(Long documentItemId, FinancialDocument financialDocument) {
        FinancialDocumentItem oldFinancialDocumentItem = financialDocumentItemRepository.findById(documentItemId).
                orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocumentItem"));
        oldFinancialDocumentItem.setDeletedDate(LocalDateTime.now());
        financialDocumentItemRepository.save(oldFinancialDocumentItem);
        return getFinancialDocumentItem(financialDocument, oldFinancialDocumentItem);
    }

    private void saveTransferFinancialDocumentItemCurrency(FinancialDocumentItemCurrency documentItemCurrency, FinancialDocumentItem financialDocumentItem) {
        documentItemCurrency.setDeletedDate(LocalDateTime.now());
        documentItemCurrencyRepository.save(documentItemCurrency);
        FinancialDocumentItemCurrency newFinancialDocumentItemCurrency = new FinancialDocumentItemCurrency();
        newFinancialDocumentItemCurrency.setFinancialDocumentItem(financialDocumentItem);
        newFinancialDocumentItemCurrency.setForeignDebitAmount(documentItemCurrency.getForeignDebitAmount());
        newFinancialDocumentItemCurrency.setExchangeRate(documentItemCurrency.getExchangeRate());
        newFinancialDocumentItemCurrency.setMoneyType(documentItemCurrency.getMoneyType());
        newFinancialDocumentItemCurrency.setMoneyPricingReference(documentItemCurrency.getMoneyPricingReference());
        newFinancialDocumentItemCurrency.setForeignCreditAmount(documentItemCurrency.getForeignCreditAmount());
        documentItemCurrencyRepository.save(newFinancialDocumentItemCurrency);
    }

    private void saveTransferFinancialReference(FinancialDocumentReference documentReference, FinancialDocumentItem financialDocumentItem) {
        documentReference.setDeletedDate(LocalDateTime.now());
        financialDocumentReferenceRepository.save(documentReference);
        FinancialDocumentReference financialDocumentReference = new FinancialDocumentReference();
        financialDocumentReference.setFinancialDocumentItem(financialDocumentItem);
        financialDocumentReference.setReferenceNumber(documentReference.getReferenceNumber());
        financialDocumentReference.setReferenceDescription(documentReference.getReferenceDescription());
        financialDocumentReference.setReferenceDate(documentReference.getReferenceDate());
        financialDocumentReferenceRepository.save(financialDocumentReference);
    }

    private FinancialDocument saveNewFinancialDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
        FinancialDocument oldFinancialDocument = financialDocumentRepository.findById(financialDocumentTransferDto.getId())
                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialDocument financialDocument = new FinancialDocument();
        financialDocument.setDocumentDate(financialDocumentTransferDto.getDate());
        financialDocument.setDescription(oldFinancialDocument.getDescription());
        financialDocument.setFinancialDocumentStatus(oldFinancialDocument.getFinancialDocumentStatus());
        financialDocument.setDocumentNumber(oldFinancialDocument.getDocumentNumber());
        financialDocument.setAutomaticFlag(oldFinancialDocument.getAutomaticFlag());
        financialDocument.setOrganization(oldFinancialDocument.getOrganization());
        financialDocument.setFinancialDocumentType(oldFinancialDocument.getFinancialDocumentType());
        financialDocument.setFinancialPeriod(oldFinancialDocument.getFinancialPeriod());
        financialDocument.setFinancialLedgerType(oldFinancialDocument.getFinancialLedgerType());
        financialDocument.setFinancialDepartment(oldFinancialDocument.getFinancialDepartment());
        financialDocument.setPermanentDocumentNumber(oldFinancialDocument.getPermanentDocumentNumber());
        return financialDocumentRepository.save(financialDocument);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentTransferOutputResponse transferDocument(FinancialDocumentTransferRequest financialDocumentTransferRequest) {
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        FinancialDocumentTransferOutputResponse financialDocumentTransferOutputResponse = new FinancialDocumentTransferOutputResponse();
        Long targetDocumentId = null;
        if (financialDocumentTransferRequest.getTransferType() == 1 || financialDocumentTransferRequest.getTransferType() == 2 || financialDocumentTransferRequest.getTransferType() == 6) {
            List<Object[]> financialDocument = financialDocumentRepository.findDocumentByDocumentNumberAndCode(financialDocumentTransferRequest.getTargetDocumentNumber());
//            if (financialDocument.get(0)[0] == null) {
            if (financialDocument.size() == 0) {
                throw new RuleException("اشکال در بدست آوردن اطلاعات سند مقصد");
            }
            targetDocumentId = Long.parseLong(financialDocument.get(0)[0].toString());
            String targetDocumentStatus = financialDocument.get(0)[1].toString();
            if (!targetDocumentStatus.equals("10") && !targetDocumentStatus.equals("20")) {
                throw new RuleException("سند مقصد میبایست در وضعیت ایجاد یا تائید کاربر باشد");
            }

            financialPeriodStatusRequest.setFinancialDocumentId(Long.parseLong(financialDocument.get(0)[0].toString()));
            FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
            if (financialPeriodStatus.getPeriodStatus() == null || financialPeriodStatus.getMonthStatus() == null) {
                throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
            }
            FinancialDocument financialDocumentUpdate = financialDocumentRepository.findById(targetDocumentId)
                    .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
            financialDocumentUpdate.setFinancialDocumentStatus(financialDocumentStatusRepository.getOne(1L));
        }
        if (financialDocumentTransferRequest.getTransferType() == 2 || financialDocumentTransferRequest.getTransferType() == 4 || financialDocumentTransferRequest.getTransferType() == 5 || financialDocumentTransferRequest.getTransferType() == 6) {
            String sourceDocumentStatus = financialDocumentRepository.findByFinancialDocumentByDocumentId(financialDocumentTransferRequest.getId());
            String sourceDocumentCode = sourceDocumentStatus.toString();
            if (!sourceDocumentCode.equals("10") && !sourceDocumentCode.equals("20")) {
                throw new RuleException("سند مبداء میبایست در وضعیت ایجاد یا تائید کاربر باشد");
            }
            FinancialDocument financialDocumentUpdate = financialDocumentRepository.findById(financialDocumentTransferRequest.getId())
                    .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
            financialDocumentUpdate.setFinancialDocumentStatus(financialDocumentStatusRepository.getOne(1L));
        }
        if (financialDocumentTransferRequest.getTransferType() == 2 || financialDocumentTransferRequest.getTransferType() == 4 || financialDocumentTransferRequest.getTransferType() == 6) {
            financialPeriodStatusRequest.setFinancialDocumentId(financialDocumentTransferRequest.getId());
            FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
            if (financialPeriodStatus.getPeriodStatus() == null || financialPeriodStatus.getMonthStatus() == null) {
                throw new RuleException("دوره مالی و ماه عملیاتی سند مبدا میبایست در وضعیت باز باشند");
            }
        }
        if (financialDocumentTransferRequest.getTransferType() == 5) {
            financialPeriodStatusRequest.setFinancialDocumentId(financialDocumentTransferRequest.getId());
            FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
            if (financialPeriodStatus.getPeriodStatus() == null || financialPeriodStatus.getMonthStatus() == null) {
                throw new RuleException("دوره مالی و ماه عملیاتی سند مبداء میبایست در وضعیت باز باشند");
            }
            financialPeriodStatusRequest.setDate(financialDocumentTransferRequest.getDate());
            financialPeriodStatusRequest.setOrganizationId(financialDocumentTransferRequest.getOrganizationId());
            FinancialPeriodStatusResponse financialPeriodStatusOutPut = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
            if (financialPeriodStatusOutPut.getPeriodStatus() == null || financialPeriodStatusOutPut.getMonthStatus() == null) {
                throw new RuleException("دوره مالی و ماه عملیاتی در تاریخ انتخاب شده میبایست در وضعیت باز باشند");
            }
        }
        if (financialDocumentTransferRequest.getAllItemFlag().equals(true) || financialDocumentTransferRequest.getTransferType() == 7) {
            financialDocumentTransferRequest.setFinancialDocumentItemIdList(null);
            financialDocumentTransferRequest.setFinancialDocumentItemIdList(
                    financialDocumentItemRepository.findByFinancialDocumentIdByDocumentId(financialDocumentTransferRequest.getId()));

        }
        if (financialDocumentTransferRequest.getTransferType() == 3 || financialDocumentTransferRequest.getTransferType() == 4 || financialDocumentTransferRequest.getTransferType() == 7) {
            financialPeriodStatusRequest.setDate(financialDocumentTransferRequest.getDate());
            financialPeriodStatusRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
            FinancialPeriodStatusResponse financialPeriodStatusOutPut = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
            if (financialPeriodStatusOutPut.getPeriodStatus() == null || financialPeriodStatusOutPut.getMonthStatus() == null) {
                throw new RuleException("دوره مالی و ماه عملیاتی در تاریخ انتخاب شده میبایست در وضعیت باز باشند");
            }
            FinancialDocument financialDocument = financialDocumentRepository.findById(financialDocumentTransferRequest.getId() == null ? 0L : financialDocumentTransferRequest.getId()).orElse(new FinancialDocument());
            FinancialDocument financialDocumentSave;
            financialDocumentSave = (FinancialDocument) SerializationHelper.clone(financialDocument);
            financialDocumentSave.setId(null);
            financialDocumentSave.setDocumentDate(DateUtil.convertStringToDate(financialDocumentTransferRequest.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
            financialDocumentSave.setFinancialDocumentStatus(financialDocumentStatusRepository.getOne(1L));
            financialDocumentSave.setOrganization(organizationRepository.getOne(SecurityHelper.getCurrentUser().getOrganizationId()));
            financialDocumentSave.setDocumentNumber("X9999999X");
            financialDocumentSave = financialDocumentRepository.save(financialDocumentSave);
            financialDocumentRepository.flush();

            String newNumber;
            FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
            financialDocumentNumberDto.setOrganizationId(financialDocumentTransferRequest.getOrganizationId());
            financialDocumentNumberDto.setFinancialDocumentId(financialDocumentSave.getId());
            financialDocumentNumberDto.setNumberingType(1L);
            newNumber = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
            financialDocumentSave.setDocumentNumber(newNumber);
            financialDocumentSave = financialDocumentRepository.save(financialDocumentSave);
            financialDocumentTransferOutputResponse = FinancialDocumentTransferOutputResponse.builder()
                    .documentId(financialDocumentSave.getId())
                    .date(DateUtil.convertStringToDate(financialPeriodStatusRequest.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))))
                    .documentNumber(newNumber)
                    .build();
        }
        if (financialDocumentTransferRequest.getTransferType() != 5 && financialDocumentTransferRequest.getTransferType() != 6) {

            Long finalTargetDocumentId1 = targetDocumentId;
            FinancialDocumentTransferOutputResponse finalFinancialDocumentTransferOutputResponse = financialDocumentTransferOutputResponse;
            financialDocumentTransferRequest.getFinancialDocumentItemIdList().forEach(aLong -> {
                AtomicReference<FinancialDocumentItem> documentItem = new AtomicReference<>();
                financialDocumentItemRepository.findById(aLong)
                        .ifPresent(financialDocumentItem1 -> {
                            FinancialDocumentItem financialDocumentItemSave;
                            financialDocumentItemSave = (FinancialDocumentItem) SerializationHelper.clone(financialDocumentItem1);
                            financialDocumentItemSave.setId(null);
                            if (financialDocumentTransferRequest.getTransferType() == 3 || financialDocumentTransferRequest.getTransferType() == 4 || financialDocumentTransferRequest.getTransferType() == 7) {
                                financialDocumentItemSave.setFinancialDocument(financialDocumentRepository.getOne(finalFinancialDocumentTransferOutputResponse.getDocumentId()));
                            } else {
                                financialDocumentItemSave.setFinancialDocument(financialDocumentRepository.getOne(finalTargetDocumentId1));
                            }
                            documentItem.set(financialDocumentItemRepository.save(financialDocumentItemSave));
                        });
                documentItemCurrencyRepository.findByFinancialDocumentItemId(aLong)
                        .ifPresent(financialDocumentItemCurrency -> {
                            FinancialDocumentItemCurrency documentItemCurrency;
                            documentItemCurrency = (FinancialDocumentItemCurrency) SerializationHelper.clone(financialDocumentItemCurrency);
                            documentItemCurrency.setId(null);
                            documentItemCurrency.setFinancialDocumentItem(documentItem.get());
                            documentItemCurrencyRepository.save(documentItemCurrency);
                        });
                financialDocumentReferenceRepository.findByFinancialDocumentItemId(aLong)
                        .forEach(documentReference -> saveFinancialReference(documentReference, documentItem.get()));
            });

        }
        if (financialDocumentTransferRequest.getTransferType() == 2 || financialDocumentTransferRequest.getTransferType() == 4) {

            List<FinancialDocumentItemCurrency> financialDocumentItemCurrencyList =
                    documentItemCurrencyRepository.findByFinancialDocumentItemCurrencyIdList(financialDocumentTransferRequest.getFinancialDocumentItemIdList());
            financialDocumentItemCurrencyList.forEach(documentItemCurrencyRepository::delete);
            List<FinancialDocumentReference> financialDocumentReferenceList =
                    financialDocumentReferenceRepository.findByFinancialDocumentItemReferenceIdList(financialDocumentTransferRequest.getFinancialDocumentItemIdList());
            financialDocumentReferenceList.forEach(financialDocumentReferenceRepository::delete);
            List<FinancialDocumentItem> financialDocumentItemList =
                    financialDocumentItemRepository.findByFinancialDocumentItemIdList(financialDocumentTransferRequest.getFinancialDocumentItemIdList());
            financialDocumentItemList.forEach(financialDocumentItemRepository::delete);
        }

        if (financialDocumentTransferRequest.getTransferType() == 5) {
            FinancialPeriodRequest financialPeriodRequest = new FinancialPeriodRequest();
            financialPeriodRequest.setOrganizationId(financialDocumentTransferRequest.getOrganizationId());
            financialPeriodRequest.setDate(financialDocumentTransferRequest.getDate());
            List<FinancialPeriodResponse> newFinancialPeriodId = financialPeriodService.getFinancialAccountByDateAndOrgan(financialPeriodRequest, financialDocumentTransferRequest.getOrganizationId());
            if (newFinancialPeriodId.size() == 0) {
                throw new RuleException("اشکال در واکشی دوره مالی تاریخ جدید");
            }

            financialDocumentNumberRepository.findByFinancialDocumentNumberAndFinancialDocumentId(financialDocumentTransferRequest.getId())
                    .forEach(financialDocumentNumberRepository::delete);

            FinancialDocument financialDocument = financialDocumentRepository.findById(financialDocumentTransferRequest.getId() == null ? 0L : financialDocumentTransferRequest.getId()).orElse(new FinancialDocument());
            FinancialDocument financialDocumentUpdate;
            financialDocumentUpdate = financialDocument;
            financialDocumentUpdate.setDocumentDate(DateUtil.convertStringToDate(financialDocumentTransferRequest.getDate().format(DateTimeFormatter.ofPattern("YYYY/MM/DD"))));
            financialDocumentUpdate.setFinancialPeriod(financialPeriodRepository.getOne(newFinancialPeriodId.get(0).getId()));
            financialDocumentUpdate = financialDocumentRepository.save(financialDocumentUpdate);
            String newNumber;
            FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
            financialDocumentNumberDto.setOrganizationId(financialDocumentTransferRequest.getOrganizationId());
            financialDocumentNumberDto.setFinancialDocumentId(financialDocumentTransferRequest.getId());
            financialDocumentNumberDto.setNumberingType(1L);
            newNumber = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
            financialDocumentUpdate.setDocumentNumber(newNumber);
            financialDocumentTransferOutputResponse = FinancialDocumentTransferOutputResponse.builder()
                    .documentId(financialDocumentUpdate.getId())
                    .date(DateUtil.convertStringToDate(financialPeriodStatusRequest.getDate().format(DateTimeFormatter.ofPattern("YYYY/MM/DD"))))
                    .documentNumber(newNumber)
                    .build();

        }
        if (financialDocumentTransferRequest.getTransferType() == 6) {
            List<Object[]> financialDocumentSource = financialDocumentRepository.findFinancialDocumentById(financialDocumentTransferRequest.getId());
            List<Object[]> financialDocumentTarget = financialDocumentRepository.findFinancialDocumentById(targetDocumentId);

            FinancialDocument financialDocument = financialDocumentRepository.findById(targetDocumentId).orElse(new FinancialDocument());
            financialDocument.setDocumentDate(financialDocumentSource.get(0)[0] == null ? null : DateUtil.convertStringToDate(financialDocumentSource.get(0)[0].toString().replace('-', '/')));
            financialDocument.setDocumentNumber(financialDocumentSource.get(0)[1] == null ? null : financialDocumentSource.get(0)[1].toString());
            financialDocument.setFinancialPeriod(financialPeriodRepository.getOne(Long.parseLong(financialDocumentSource.get(0)[2].toString())));
            financialDocument.setDescription(financialDocumentSource.get(0)[3] == null ? null : financialDocumentSource.get(0)[3].toString());
            financialDocumentRepository.save(financialDocument);

            FinancialDocument financialDocumentUpdateTarget = financialDocumentRepository.findById(financialDocumentTransferRequest.getId()).orElse(new FinancialDocument());
            financialDocumentUpdateTarget.setDocumentDate(financialDocumentTarget.get(0)[0] == null ? null : DateUtil.convertStringToDate(financialDocumentTarget.get(0)[0].toString().replace('-', '/')));
            financialDocumentUpdateTarget.setDocumentNumber(financialDocumentTarget.get(0)[1] == null ? null : financialDocumentTarget.get(0)[1].toString());
            financialDocumentUpdateTarget.setFinancialPeriod(financialPeriodRepository.getOne(Long.parseLong(financialDocumentTarget.get(0)[2].toString())));
            financialDocumentUpdateTarget.setDescription(financialDocumentTarget.get(0)[3] == null ? null : financialDocumentTarget.get(0)[3].toString());
            financialDocumentRepository.save(financialDocumentUpdateTarget);
            List<FinancialDocumentNumber> financialDocumentNumberList = financialDocumentNumberRepository.findByFinancialDocumentNumberAndFinancialDocumentIdAndTarget(targetDocumentId, financialDocumentTransferRequest.getId());
            Long finalTargetDocumentId = targetDocumentId;
            financialDocumentNumberList.forEach(e -> {
                if (e.getFinancialDocument().getId().equals(financialDocumentTransferRequest.getId())) {
                    e.setFinancialDocument(financialDocumentRepository.getOne(finalTargetDocumentId));
                } else {
                    e.setFinancialDocument(financialDocumentRepository.getOne(financialDocumentTransferRequest.getId()));
                }
                financialDocumentNumberRepository.save(e);
            });
        }
        return financialDocumentTransferOutputResponse;
    }
}


