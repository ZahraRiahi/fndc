package ir.demisco.cfs.service.impl;

import ir.demisco.cfs.model.dto.request.FinancialDepartmentLedgerRequest;
import ir.demisco.cfs.model.entity.FinancialDepartmentLedger;
import ir.demisco.cfs.service.api.FinancialDepartmentLedgerService;
import ir.demisco.cfs.service.repository.DepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialDepartmentLedgerRepository;
import ir.demisco.cfs.service.repository.FinancialDepartmentRepository;
import ir.demisco.cfs.service.repository.FinancialDocumentRepository;
import ir.demisco.cfs.service.repository.FinancialLedgerTypeRepository;
import ir.demisco.cloud.basic.model.entity.org.Department;
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
    private final DepartmentRepository departmentRepository;


    public DefaultFinancialDepartmentLedger(FinancialDepartmentLedgerRepository financialDepartmentLedgerRepository,
                                            FinancialDepartmentRepository financialDepartmentRepository,
                                            FinancialLedgerTypeRepository financialLedgerTypeRepository,
                                            FinancialDocumentRepository financialDocumentRepository, DepartmentRepository departmentRepository) {
        this.financialDepartmentLedgerRepository = financialDepartmentLedgerRepository;
        this.financialDepartmentRepository = financialDepartmentRepository;
        this.financialLedgerTypeRepository = financialLedgerTypeRepository;
        this.financialDocumentRepository = financialDocumentRepository;
        this.departmentRepository = departmentRepository;
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

        Department department = departmentRepository.findById(financialDepartmentLedgerObject.getDepartmentId())
                .orElseThrow(() -> new RuleException("fin.financialDepartmentLedger.insertDepartmentNumber"));
        FinancialDepartmentLedger departmentLedger =
                financialDepartmentLedgerRepository.getByLedgerTypeIdAndDepartmentIdAndDeleteDate(financialDepartmentLedgerObject.getDepartmentId(),
                        financialDepartmentLedgerObject.getFinancialLedgerTypeId(), financialDepartmentLedgerObject.getFinancialDepartmentLedgerId());
        if (departmentLedger != null) {
            throw new RuleException("fin.financialDepartmentLedger.departmentLedgerExist");
        }

        if (financialDepartmentLedgerObject.getFinancialDepartmentLedgerId() == null) {
            FinancialDepartmentLedger financialDepartmentLedger = new FinancialDepartmentLedger();
            financialDepartmentLedger.setDepartment(departmentRepository.getOne(department.getId()));
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
