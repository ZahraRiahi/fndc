package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDepartmentLedgerRequest;
import ir.demisco.cfs.model.entity.FinancialDepartment;
import ir.demisco.cfs.model.entity.FinancialDepartmentLedger;
import ir.demisco.cfs.service.api.FinancialDepartmentLedgerService;
import ir.demisco.cfs.service.repository.FinancialDepartmentLedgerRepository;
import ir.demisco.cfs.service.repository.FinancialDepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cloud.core.middle.exception.RuleException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DefaultFinancialDepartmentLedger implements FinancialDepartmentLedgerService {

    private final FinancialDepartmentLedgerRepository financialDepartmentLedgerRepository;
    private final FinancialDepartmentRepository financialDepartmentRepository;
    private final FinancialLedgerTypeRepository financialLedgerTypeRepository;
    private final FinancialDocumentRepository financialDocumentRepository;


    public DefaultFinancialDepartmentLedger(FinancialDepartmentLedgerRepository financialDepartmentLedgerRepository,
                                            FinancialDepartmentRepository financialDepartmentRepository,
                                            FinancialLedgerTypeRepository financialLedgerTypeRepository,
                                            FinancialDocumentRepository financialDocumentRepository) {
        this.financialDepartmentLedgerRepository = financialDepartmentLedgerRepository;
        this.financialDepartmentRepository = financialDepartmentRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialDocumentRepository = financialDocumentRepository;
    }

    @Override
    @Transactional
    public Boolean saveFinancialDepartmentLedger(List<FinancialDepartmentLedgerRequest> financialDepartmentLedgerRequest) {

        financialDepartmentLedgerRequest.forEach(financialDepartmentLedgerObject -> {
            if (financialDepartmentLedgerObject.getFinancialLedgerTypeId() != null) {
                saveFinancialDepartmentLedgerType(financialDepartmentLedgerObject);
            } else {
                updateFinancialDepartmentLedgerType(financialDepartmentLedgerObject);
            }
        });
        return true;
    }

    private void saveFinancialDepartmentLedgerType(FinancialDepartmentLedgerRequest financialDepartmentLedgerObject) {

        FinancialDepartment financialDepartment = financialDepartmentRepository.findById(financialDepartmentLedgerObject.getFinancialDepartmentId())
                .orElseThrow(() -> new RuleException("fin.financialDepartmentLedger.insertDepartmentNumber"));
        FinancialDepartmentLedger departmentLedger =
                financialDepartmentLedgerRepository.getByLedgerTypeIdAndDepartmentIdAndDeleteDate(financialDepartment.getId(),
                        financialDepartmentLedgerObject.getFinancialLedgerTypeId(), financialDepartmentLedgerObject.getFinancialDepartmentLedgerId());
        if (departmentLedger != null) {
            throw new RuleException("fin.financialDepartmentLedger.departmentLedgerExist");
        }

        if (financialDepartmentLedgerObject.getFinancialDepartmentLedgerId() == null) {
            FinancialDepartmentLedger financialDepartmentLedger = new FinancialDepartmentLedger();
            financialDepartmentLedger.setFinancialDepartment(financialDepartmentRepository.getOne(financialDepartment.getId()));
            financialDepartmentLedger.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialDepartmentLedgerObject.getFinancialLedgerTypeId()));
            financialDepartmentLedgerRepository.save(financialDepartmentLedger);
        } else if (financialDocumentRepository.usedInFinancialDocument(financialDepartmentLedgerObject.getFinancialDepartmentLedgerId()).size() == 0) {
            FinancialDepartmentLedger financialDepartmentLedger = financialDepartmentLedgerRepository.findById(financialDepartmentLedgerObject.getFinancialDepartmentLedgerId())
                    .orElseThrow(() -> new RuleException("fin.financialDepartmentLedger.notExistDepartmentLedger"));
            financialDepartmentLedger.setFinancialLedgerType(financialLedgerTypeRepository.getOne(financialDepartmentLedgerObject.getFinancialLedgerTypeId()));
            financialDepartmentLedgerRepository.save(financialDepartmentLedger);
        } else {
            throw new RuleException("fin.financialDepartmentLedger.usedDepartmentLedger");
        }
    }

    private void updateFinancialDepartmentLedgerType(FinancialDepartmentLedgerRequest financialDepartmentLedgerObject) {

        if (financialDepartmentLedgerObject.getFinancialDepartmentLedgerId() != null &&
                financialDocumentRepository.usedInFinancialDocument(financialDepartmentLedgerObject.getFinancialDepartmentLedgerId()).size() == 0) {
            FinancialDepartmentLedger financialDepartmentLedger = financialDepartmentLedgerRepository.findById(financialDepartmentLedgerObject.getFinancialDepartmentLedgerId())
                    .orElseThrow(() -> new RuleException("fin.financialDepartmentLedger.notExistDepartmentLedger"));
            financialDepartmentLedgerRepository.deleteById(financialDepartmentLedger.getId());
        } else {
            throw new RuleException("fin.financialDepartmentLedger.notPossibleDeleteDepartmentLedger");
        }
    }
}
