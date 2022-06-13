package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDocumentSecurityInputRequest;
import ir.demisco.cfs.model.dto.request.FinancialDocumentTransferRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodRequest;
import ir.demisco.cfs.model.dto.request.FinancialPeriodStatusRequest;
import ir.demisco.cfs.model.dto.request.FinancialSecurityFilterRequest;
import ir.demisco.cfs.model.dto.response.FinancialDocumentHeaderOutputResponse;
import ir.demisco.cfs.model.dto.response.FinancialDocumentNumberDto;
import ir.demisco.cfs.model.dto.response.FinancialDocumentTransferOutputResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodResponse;
import ir.demisco.cfs.model.dto.response.FinancialPeriodStatusResponse;
import ir.demisco.cfs.model.entity.FinancialDocument;
import ir.demisco.cfs.model.entity.FinancialDocumentItem;
import ir.demisco.cfs.model.entity.FinancialDocumentItemCurrency;
import ir.demisco.cfs.model.entity.FinancialDocumentNumber;
import ir.demisco.cfs.model.entity.FinancialDocumentReference;
import ir.demisco.cfs.service.api.FinancialDocumentHeaderService;
import ir.demisco.cfs.service.api.FinancialDocumentSecurityService;
import ir.demisco.cfs.service.api.FinancialDocumentService;
import ir.demisco.cfs.service.api.FinancialPeriodService;
import ir.demisco.cfs.service.api.TransferFinancialDocumentService;
import ir.demisco.cfs.service.repository.FinancialDocumentItemCurrencyRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentItemRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentNumberRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentReferenceRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentStatusRepository;
import ir.demisco.cfs.service.repository.FinancialPeriodRepository;
import ir.demisco.cfs.service.repository.OrganizationRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import ir.demisco.cloud.core.security.util.SecurityHelper;
import ir.demisco.core.utils.DateUtil;
import org.hibernate.internal.util.SerializationHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final FinancialDocumentSecurityService financialDocumentSecurityService;
    private final FinancialDocumentHeaderService financialDocumentHeaderService;


    public DefaultTeransferFinancialDocument(FinancialDocumentService financialDocumentService, FinancialDocumentRepository financialDocumentRepository, FinancialDocumentItemRepository financialDocumentItemRepository,
                                             FinancialDocumentReferenceRepository financialDocumentReferenceRepository,
                                             FinancialDocumentItemCurrencyRepository documentItemCurrencyRepository,
                                              FinancialPeriodService financialPeriodService, FinancialDocumentStatusRepository financialDocumentStatusRepository, OrganizationRepository organizationRepository,  FinancialDocumentNumberRepository financialDocumentNumberRepository, FinancialPeriodRepository financialPeriodRepository1, FinancialDocumentSecurityService financialDocumentSecurityService, FinancialDocumentHeaderService financialDocumentHeaderService) {
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
        this.financialDocumentSecurityService = financialDocumentSecurityService;
        this.financialDocumentHeaderService = financialDocumentHeaderService;
    }

    private void saveFinancialReference(FinancialDocumentReference documentReference, FinancialDocumentItem financialDocumentItem) {
        FinancialDocumentReference financialDocumentReference = new FinancialDocumentReference();
        financialDocumentReference.setFinancialDocumentItem(financialDocumentItem);
        financialDocumentReference.setReferenceNumber(documentReference.getReferenceNumber());
        financialDocumentReference.setReferenceDescription(documentReference.getReferenceDescription());
        financialDocumentReference.setReferenceDate(documentReference.getReferenceDate());
        financialDocumentReferenceRepository.save(financialDocumentReference);
    }

    @Override
    @Transactional(rollbackOn = Throwable.class)
    public FinancialDocumentTransferOutputResponse transferDocument(FinancialDocumentTransferRequest financialDocumentTransferRequest) {
        FinancialPeriodStatusRequest financialPeriodStatusRequest = new FinancialPeriodStatusRequest();
        FinancialDocumentTransferOutputResponse financialDocumentTransferOutputResponse = new FinancialDocumentTransferOutputResponse();
        String activityCode = "FNDC_DOCUMENT_TRANSFER";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequest.setActivityCode(activityCode);
        financialDocumentSecurityInputRequest.setFinancialDocumentId(financialDocumentTransferRequest.getId());
        financialDocumentSecurityInputRequest.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequest.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);
        Long targetDocumentId = null;

        if (financialDocumentTransferRequest.getTransferType() == 1 || financialDocumentTransferRequest.getTransferType() == 2 || financialDocumentTransferRequest.getTransferType() == 6) {
            targetDocumentId=check1(financialDocumentTransferRequest,financialDocumentSecurityInputRequest,financialPeriodStatusRequest);
        }

        if (financialDocumentTransferRequest.getTransferType() == 2 || financialDocumentTransferRequest.getTransferType() == 4 || financialDocumentTransferRequest.getTransferType() == 5 || financialDocumentTransferRequest.getTransferType() == 6) {
          check2(financialDocumentTransferRequest,financialDocumentSecurityInputRequest);
        }

        if (financialDocumentTransferRequest.getTransferType() == 2 || financialDocumentTransferRequest.getTransferType() == 4 || financialDocumentTransferRequest.getTransferType() == 6) {
            financialPeriodStatusRequest=check3(financialPeriodStatusRequest,financialDocumentTransferRequest);
        }

        if (financialDocumentTransferRequest.getTransferType() == 5) {
            financialPeriodStatusRequest=check4(financialPeriodStatusRequest,financialDocumentTransferRequest);
        }

        if (financialDocumentTransferRequest.getAllItemFlag().equals(true) || financialDocumentTransferRequest.getTransferType() == 7) {
            financialDocumentTransferRequest= check5(financialDocumentTransferRequest);
        }

        if (financialDocumentTransferRequest.getTransferType() == 3 || financialDocumentTransferRequest.getTransferType() == 4 || financialDocumentTransferRequest.getTransferType() == 7) {
            financialDocumentTransferOutputResponse=check6(financialDocumentTransferRequest,financialDocumentSecurityInputRequest,financialPeriodStatusRequest,financialDocumentTransferOutputResponse);
        }

             checkAndSave(financialDocumentTransferRequest,targetDocumentId,financialDocumentTransferOutputResponse);

        if (financialDocumentTransferRequest.getTransferType() == 5) {
            financialDocumentTransferOutputResponse=check9(financialDocumentTransferRequest);

        }

        if (financialDocumentTransferRequest.getTransferType() == 6) {
           check10(financialDocumentTransferRequest,targetDocumentId);
        }

        return financialDocumentTransferOutputResponse;
    }

    private void checkAndSave(FinancialDocumentTransferRequest financialDocumentTransferRequest, Long targetDocumentId, FinancialDocumentTransferOutputResponse financialDocumentTransferOutputResponse) {
        if (financialDocumentTransferRequest.getTransferType() != 5 && financialDocumentTransferRequest.getTransferType() != 6) {
            check7(targetDocumentId,financialDocumentTransferOutputResponse,financialDocumentTransferRequest);

        }

        if (financialDocumentTransferRequest.getTransferType() == 2 || financialDocumentTransferRequest.getTransferType() == 4) {

            check8(financialDocumentTransferRequest);
        }
    }

    private void check10(FinancialDocumentTransferRequest financialDocumentTransferRequest, Long targetDocumentId) {
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

    private FinancialDocumentTransferOutputResponse check9(FinancialDocumentTransferRequest financialDocumentTransferRequest) {
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
        financialDocumentUpdate.setDocumentDate(DateUtil.convertStringToDate(financialDocumentTransferRequest.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))));
        financialDocumentUpdate.setFinancialPeriod(financialPeriodRepository.getOne(newFinancialPeriodId.get(0).getId()));
        financialDocumentUpdate = financialDocumentRepository.save(financialDocumentUpdate);
        String newNumber;
        FinancialDocumentNumberDto financialDocumentNumberDto = new FinancialDocumentNumberDto();
        financialDocumentNumberDto.setOrganizationId(financialDocumentTransferRequest.getOrganizationId());
        financialDocumentNumberDto.setFinancialDocumentId(financialDocumentTransferRequest.getId());
        financialDocumentNumberDto.setNumberingType(1L);
        newNumber = financialDocumentService.creatDocumentNumber(financialDocumentNumberDto);
        financialDocumentUpdate.setDocumentNumber(newNumber);
        return FinancialDocumentTransferOutputResponse.builder()
                .documentId(financialDocumentUpdate.getId())
                .date(DateUtil.convertStringToDate(financialDocumentTransferRequest.getDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))))
                .documentNumber(newNumber)
                .build();
    }

    private void check8(FinancialDocumentTransferRequest financialDocumentTransferRequest) {
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

    private void check7(Long targetDocumentId, FinancialDocumentTransferOutputResponse financialDocumentTransferOutputResponse, FinancialDocumentTransferRequest financialDocumentTransferRequest) {

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

    private FinancialDocumentTransferOutputResponse check6(FinancialDocumentTransferRequest financialDocumentTransferRequest, FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest, FinancialPeriodStatusRequest financialPeriodStatusRequest, FinancialDocumentTransferOutputResponse financialDocumentTransferOutputResponse) {
        String activityCodeTransfer = "FNDC_DOCUMENT_CREATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequestTransfer = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequestTransfer.setActivityCode(activityCodeTransfer);
        financialDocumentSecurityInputRequestTransfer.setFinancialDocumentId(financialDocumentTransferRequest.getId());
        financialDocumentSecurityInputRequestTransfer.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequestTransfer.setSecurityModelRequest(null);
        FinancialSecurityFilterRequest financialSecurityFilterRequest = new FinancialSecurityFilterRequest();
        FinancialDocumentHeaderOutputResponse
                financialDocumentHeaderOutputResponse = financialDocumentHeaderService.getFinancialDocumentHeaderBytId(financialDocumentSecurityInputRequest.getFinancialDocumentId());

        financialSecurityFilterRequest.setOrganizationId(SecurityHelper.getCurrentUser().getOrganizationId());
        financialSecurityFilterRequest.setUserId(SecurityHelper.getCurrentUser().getUserId());
        financialSecurityFilterRequest.setDepartmentId(financialDocumentHeaderOutputResponse.getDepartmentId());
        financialSecurityFilterRequest.setFinancialDepartmentId(financialDocumentHeaderOutputResponse.getFinancialDepartmentId());
        financialSecurityFilterRequest.setFinancialLedgerId(financialDocumentHeaderOutputResponse.getFinancialLedgerTypeId());
        financialSecurityFilterRequest.setFinancialPeriodId(financialDocumentHeaderOutputResponse.getFinancialPeriodId());
        financialSecurityFilterRequest.setDocumentTypeId(financialDocumentHeaderOutputResponse.getFinancialDocumentTypeId());
        financialSecurityFilterRequest.setSubjectId(null);
        financialSecurityFilterRequest.setActivityCode(activityCodeTransfer);
        financialSecurityFilterRequest.setInputFromConfigFlag(false);
        financialSecurityFilterRequest.setCreatorUserId(financialDocumentHeaderOutputResponse.getCreatorId());
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

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
        return financialDocumentTransferOutputResponse;
    }

    private FinancialDocumentTransferRequest check5(FinancialDocumentTransferRequest financialDocumentTransferRequest) {
        financialDocumentTransferRequest.setFinancialDocumentItemIdList(null);
        financialDocumentTransferRequest.setFinancialDocumentItemIdList(financialDocumentItemRepository.findByFinancialDocumentIdByDocumentId(financialDocumentTransferRequest.getId()));
        return financialDocumentTransferRequest;
    }

    private FinancialPeriodStatusRequest check4(FinancialPeriodStatusRequest financialPeriodStatusRequest, FinancialDocumentTransferRequest financialDocumentTransferRequest) {
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
        return financialPeriodStatusRequest;
    }

    private FinancialPeriodStatusRequest check3(FinancialPeriodStatusRequest financialPeriodStatusRequest, FinancialDocumentTransferRequest financialDocumentTransferRequest) {
        financialPeriodStatusRequest.setFinancialDocumentId(financialDocumentTransferRequest.getId());
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == null || financialPeriodStatus.getMonthStatus() == null) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مبدا میبایست در وضعیت باز باشند");
        }
        return financialPeriodStatusRequest;
    }

    private void check2(FinancialDocumentTransferRequest financialDocumentTransferRequest, FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest) {
        String activityCodeTransfer = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequestTransfer = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequestTransfer.setActivityCode(activityCodeTransfer);
        financialDocumentSecurityInputRequestTransfer.setFinancialDocumentId(financialDocumentTransferRequest.getId());
        financialDocumentSecurityInputRequestTransfer.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequestTransfer.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);


        String sourceDocumentStatus = financialDocumentRepository.findByFinancialDocumentByDocumentId(financialDocumentTransferRequest.getId());
        String sourceDocumentCode = sourceDocumentStatus.toString();
        if (!sourceDocumentCode.equals("10") && !sourceDocumentCode.equals("20")) {
            throw new RuleException("سند مبداء میبایست در وضعیت ایجاد یا تائید کاربر باشد");
        }
        FinancialDocument financialDocumentUpdate = financialDocumentRepository.findById(financialDocumentTransferRequest.getId())
                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        financialDocumentUpdate.setFinancialDocumentStatus(financialDocumentStatusRepository.getOne(1L));

    }

    private Long check1(FinancialDocumentTransferRequest financialDocumentTransferRequest, FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequest, FinancialPeriodStatusRequest financialPeriodStatusRequest) {
        List<Object[]> financialDocumentFlag = financialDocumentRepository.findDocumentByFlagAndOrganAndPeriodId(financialDocumentTransferRequest.getId());
        if (Long.parseLong(financialDocumentFlag.get(0)[0].toString()) == 1) {
            throw new RuleException("امکان عملیات بر روی سند اتوماتیک وجود ندارد");
        }
        Long ledgerTypeId = Long.parseLong(financialDocumentFlag.get(0)[4].toString());
        Long financialDepartmentId = Long.parseLong(financialDocumentFlag.get(0)[5].toString());
        Long departmentId = financialDocumentFlag.get(0)[6] == null ? null : Long.parseLong(financialDocumentFlag.get(0)[6].toString());
        Object department;
        if (departmentId != null) {
            department = "department";
        } else {
            departmentId = 0L;
            department = null;
        }
        List<Object[]> financialDocument = financialDocumentRepository.findDocumentByDocumentNumberAndCode(financialDocumentTransferRequest.getTargetDocumentNumber(), SecurityHelper.getCurrentUser().getOrganizationId(), ledgerTypeId, financialDepartmentId, department, departmentId);
        if (financialDocument.size() == 0) {
            throw new RuleException("اشکال در بدست آوردن اطلاعات سند مقصد در سازمان، واحد و دفتر جاری");
        }
        Long targetDocumentId = Long.parseLong(financialDocument.get(0)[0].toString());
        String targetDocumentStatus = financialDocument.get(0)[1].toString();

        if (!targetDocumentStatus.equals("10") && !targetDocumentStatus.equals("20")) {
            throw new RuleException("سند مقصد میبایست در وضعیت ایجاد یا تائید کاربر باشد");
        }
        if (Long.parseLong(financialDocumentFlag.get(0)[2].toString()) == 1) {
            throw new RuleException("امکان تغییر در اسناد اتوماتیک وجود ندارد");
        }
        String activityCodeTransfer = "FNDC_DOCUMENT_UPDATE";
        FinancialDocumentSecurityInputRequest financialDocumentSecurityInputRequestTransfer = new FinancialDocumentSecurityInputRequest();
        financialDocumentSecurityInputRequestTransfer.setActivityCode(activityCodeTransfer);
        financialDocumentSecurityInputRequestTransfer.setFinancialDocumentId(targetDocumentId);
        financialDocumentSecurityInputRequestTransfer.setFinancialDocumentItemId(null);
        financialDocumentSecurityInputRequestTransfer.setSecurityModelRequest(null);
        financialDocumentSecurityService.getFinancialDocumentSecurity(financialDocumentSecurityInputRequest);

        financialPeriodStatusRequest.setFinancialDocumentId(Long.parseLong(financialDocument.get(0)[0].toString()));
        FinancialPeriodStatusResponse financialPeriodStatus = financialPeriodService.getFinancialPeriodStatus(financialPeriodStatusRequest);
        if (financialPeriodStatus.getPeriodStatus() == null || financialPeriodStatus.getMonthStatus() == null) {
            throw new RuleException("دوره مالی و ماه عملیاتی سند مقصد میبایست در وضعیت باز باشند");
        }
        FinancialDocument financialDocumentUpdate = financialDocumentRepository.findById(targetDocumentId)
                .orElseThrow(() -> new RuleException("fin.financialDocument.notExistDocument"));
        financialDocumentUpdate.setFinancialDocumentStatus(financialDocumentStatusRepository.getOne(1L));
        return targetDocumentId;
    }

}


