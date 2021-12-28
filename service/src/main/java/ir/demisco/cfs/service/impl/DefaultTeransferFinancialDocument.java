package ir.demisco.cfs.service.impl;


import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentTransferDto;
import ir.demisco.cfs.model.entity.*;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.TransferFinancialDocumentService;
import ir.demisco.cfs.service.repository.*;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import org.hibernate.internal.util.SerializationHelper;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class DefaultTeransferFinancialDocument  implements TransferFinancialDocumentService {

    private final FinancialDocumentService financialDocumentService;
    private final FinancialDocumentRepository financialDocumentRepository;
    private final FinancialDocumentItemRepository financialDocumentItemRepository;
    private final FinancialDocumentReferenceRepository financialDocumentReferenceRepository;
    private final FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository;
    private final FinancialPeriodRepository financialPeriodRepository;

    public DefaultTeransferFinancialDocument(FinancialDocumentService financialDocumentService, FinancialDocumentRepository financialDocumentRepository, FinancialDocumentItemRepository financialDocumentItemRepository,
                                             FinancialDocumentReferenceRepository financialDocumentReferenceRepository,
                                             FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository,
                                             FinancialPeriodRepository financialPeriodRepository) {
        this.financialDocumentService = financialDocumentService;
        this.financialDocumentRepository = financialDocumentRepository;
        this.financialDocumentItemRepository = financialDocumentItemRepository;
        this.financialDocumentReferenceRepository = financialDocumentReferenceRepository;
        this.documentItemCurrencyRepository = documentItemCurrencyRepository;
        this.financialPeriodRepository = financialPeriodRepository;
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public boolean transferDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {

        switch (financialDocumentTransferDto.getTransferType()) {
            case 1:
                copyInOtherDocument(financialDocumentTransferDto);
                break;
            case 2:
                transferToOtherDocument(financialDocumentTransferDto);
                break;
            case 3:
                copyInNewDocument(financialDocumentTransferDto);
                break;
            case 4:
                transferToNewDocument(financialDocumentTransferDto);
                break;
            case 5:
                changDate(financialDocumentTransferDto);
                break;
            case 6:
                displacementWithOtherDocument(financialDocumentTransferDto);
                break;
            case 7:
                copyDocument(financialDocumentTransferDto);
                break;
        }

        return true;
    }

    private void copyInOtherDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {

        FinancialDocument financialDocument;
        if (financialDocumentTransferDto.getDocumentNumber() != null) {
            financialDocument = financialDocumentRepository.getFinancialDocumentByDocumentNumber(financialDocumentTransferDto.getDocumentNumber());
            if (financialDocument == null) {
                throw new RuleException("fin.financialDocument.notExistDocumentInOrgan");
            } else {
                getDocumentIdList(financialDocumentTransferDto)
                .forEach(documentItemId -> {
                    FinancialDocumentItem financialDocumentItem = saveFinancialDocumentItem(documentItemId, financialDocument);
                    documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItemId)
                            .forEach(documentItemCurrency -> saveFinancialDocumentItemCurrency(documentItemCurrency, financialDocumentItem));
                    financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItemId)
                            .forEach(documentReference -> saveFinancialReference(documentReference, financialDocumentItem));
                });

            }
        } else {
            throw new RuleException("fin.financialDocument.noSelectDocumentNumber");
        }

    }

    private List<Long> getDocumentIdList(FinancialDocumentTransferDto financialDocumentTransferDto) {
        if(!financialDocumentTransferDto.getAllItemFlag() &&
                financialDocumentTransferDto.getFinancialDocumentItemIdList().isEmpty()){
            throw new RuleException("fin.financialDocument.noSelectDocumentItem");
        }
        return financialDocumentTransferDto.getAllItemFlag() ?
                financialDocumentItemRepository.findByFinancialDocumentIdByDocumentId(financialDocumentTransferDto.getId()) :
                financialDocumentTransferDto.getFinancialDocumentItemIdList();
    }

    private void transferToOtherDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {

        if (financialDocumentTransferDto.getDocumentNumber() != null) {
            FinancialDocument financialDocument = financialDocumentRepository.getFinancialDocumentByDocumentNumber(financialDocumentTransferDto.getDocumentNumber());
            if (financialDocument == null) {
                throw new RuleException("fin.financialDocument.notExistDocumentNumberInOrgan");
            } else {
              getDocumentIdList(financialDocumentTransferDto)
                .forEach(documentItemId -> {
                    FinancialDocumentItem financialDocumentItem = saveTransferFinancialDocumentItem(documentItemId, financialDocument);
                    documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItemId)
                            .forEach(documentItemCurrency -> saveTransferFinancialDocumentItemCurrency(documentItemCurrency, financialDocumentItem));
                    financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItemId)
                            .forEach(documentReference -> saveTransferFinancialReference(documentReference, financialDocumentItem));

                });
                deleteDocument(financialDocumentTransferDto);
            }
        }else {
            throw new RuleException("fin.financialDocument.noSelectDocumentNumber");
        }
    }

    private void copyInNewDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
        FinancialDocument newFinancialDocument=saveNewFinancialDocument(financialDocumentTransferDto);
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


        FinancialDocument newFinancialDocument=saveNewFinancialDocument(financialDocumentTransferDto);
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
        List<FinancialDocumentItem> financialDocumentItem=
                financialDocumentItemRepository.findByFinancialDocumentIdAndDeletedDateIsNull(financialDocumentTransferDto.getId());
        if(financialDocumentItem.isEmpty()){
            FinancialDocument oldFinancialDocument=financialDocumentRepository.findById(financialDocumentTransferDto.getId())
                    .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
            oldFinancialDocument.setDeletedDate(LocalDateTime.now());
            financialDocumentRepository.save(oldFinancialDocument);
        }
    }

    private void changDate(FinancialDocumentTransferDto financialDocumentTransferDto) {

        FinancialDocument  updateFinancialDocument=financialDocumentRepository.findById(financialDocumentTransferDto.getId())
                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        Long organizationId = SecurityHelper.getCurrentUser().getOrganizationId();
        FinancialDocument financialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(financialDocumentTransferDto.getId());
        if (financialDocument == null) {
            throw new RuleException("fin.financialDocument.openStatusPeriod");
        } else {
            LocalDate dateTime=financialDocumentTransferDto.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            List<FinancialPeriod> financialPeriodList=financialPeriodRepository
                    .getPeriodByParam(dateTime.toString(),organizationId);
            if(financialPeriodList.isEmpty()){
                throw new RuleException("fin.financialDocument.openStatusPeriodInDate");
            }else{
                FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
                financialDocumentNumberDto.setFinancialDocumentId(updateFinancialDocument.getId());
                financialDocumentNumberDto.setOrganizationId(updateFinancialDocument.getOrganization().getId());
                financialDocumentNumberDto.setNumberingType(1L);
                updateFinancialDocument.setDocumentDate(financialDocumentTransferDto.getDate());
                updateFinancialDocument.setFinancialPeriod(financialPeriodRepository.getOne(financialPeriodList.get(0).getId()));
                updateFinancialDocument.setDocumentNumber(financialDocumentService.creatDocumentNumber(financialDocumentNumberDto));
                financialDocumentRepository.save(updateFinancialDocument);
            }

        }
    }

    private void displacementWithOtherDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
        if (financialDocumentTransferDto.getDocumentNumber() != null) {
            FinancialDocument findFinancialDocument = financialDocumentRepository.getFinancialDocumentByDocumentNumber(financialDocumentTransferDto.getDocumentNumber());
            if (findFinancialDocument == null) {
                throw new RuleException("fin.financialDocument.notExistDocumentInOrgan");
            } else {
                FinancialDocument activeFinancialDocument = financialDocumentRepository.getActivePeriodAndMontInDocument(financialDocumentTransferDto.getId());
                if ((activeFinancialDocument == null)) {
                    throw new RuleException("fin.financialDocument.openStatusPeriod");
                } else {
                    FinancialDocument newFinancialDocument= financialDocumentRepository.getActivePeriodAndMontInDocument(findFinancialDocument.getId());
                    if ((newFinancialDocument == null)) {
                        throw new RuleException("fin.financialDocument.openStatusPeriodDestinationDocument");
                    } else {
                        FinancialDocument financialDocumentMove = (FinancialDocument) SerializationHelper.clone(activeFinancialDocument);
                        activeFinancialDocument.setDocumentNumber(newFinancialDocument.getDocumentNumber());
                        activeFinancialDocument.setDocumentDate(newFinancialDocument.getDocumentDate());
                        activeFinancialDocument.setFinancialPeriod(newFinancialDocument.getFinancialPeriod());
                        financialDocumentRepository.save(activeFinancialDocument);
                        newFinancialDocument.setDocumentNumber(financialDocumentMove.getDocumentNumber());
                        newFinancialDocument.setDocumentDate(financialDocumentMove.getDocumentDate());
                        newFinancialDocument.setFinancialPeriod(financialDocumentMove.getFinancialPeriod());
                        financialDocumentRepository.save(newFinancialDocument);

                    }
                }
            }
        }
    }

    private void copyDocument(FinancialDocumentTransferDto financialDocumentTransferDto) {
        FinancialDocument newFinancialDocument=saveNewFinancialDocument(financialDocumentTransferDto);
        List<Long> financialDocumentItemIdList =financialDocumentItemRepository.findByFinancialDocumentIdByDocumentId(financialDocumentTransferDto.getId());
        financialDocumentItemIdList.forEach(documentItemId -> {
            FinancialDocumentItem financialDocumentItem = saveTransferFinancialDocumentItem(documentItemId, newFinancialDocument);
            documentItemCurrencyRepository.findByFinancialDocumentItemIdAndDeletedDateIsNull(documentItemId)
                    .forEach(documentItemCurrency -> saveTransferFinancialDocumentItemCurrency(documentItemCurrency, financialDocumentItem));
            financialDocumentReferenceRepository.findByFinancialDocumentItemId(documentItemId)
                    .forEach(documentReference -> saveTransferFinancialReference(documentReference, financialDocumentItem));

        });
        FinancialDocument oldFinancialDocument=financialDocumentRepository.findById(financialDocumentTransferDto.getId())
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
        FinancialDocument oldFinancialDocument=financialDocumentRepository.findById(financialDocumentTransferDto.getId())
                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        FinancialDocument financialDocument=new FinancialDocument();
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

}
