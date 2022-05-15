package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FINANCIAL_ACTIVITY_TYPE", schema = "fnsc")
public class FinancialActivityType extends AuditModel<Long> {
    private Long id;
    private String code;
    private String description;
    private Boolean insertFlag;
    private Boolean updateFlag;
    private Boolean deleteFlag;
    private Boolean queryFlag;
    private FinancialSystem financialSystem;
    private FinancialSystemSubject financialSystemSubject;

    @Override
    @Id
    @SequenceGenerator(schema = "fnsc", name = "financial_activity_type_generator", sequenceName = "sq_financial_activity_type")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_activity_type_generator")
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "INSERT_FLAG")
    public Boolean getInsertFlag() {
        return insertFlag;
    }

    public void setInsertFlag(Boolean insertFlag) {
        this.insertFlag = insertFlag;
    }

    @Column(name = "UPDATE_FLAG")
    public Boolean getUpdateFlag() {
        return updateFlag;
    }

    public void setUpdateFlag(Boolean updateFlag) {
        this.updateFlag = updateFlag;
    }

    @Column(name = "DELETE_FLAG")
    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Column(name = "QUERY_FLAG")
    public Boolean getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(Boolean queryFlag) {
        this.queryFlag = queryFlag;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_SYSTEM_ID")
    public FinancialSystem getFinancialSystem() {
        return financialSystem;
    }

    public void setFinancialSystem(FinancialSystem financialSystem) {
        this.financialSystem = financialSystem;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_SYSTEM_SUBJECT_ID")
    public FinancialSystemSubject getFinancialSystemSubject() {
        return financialSystemSubject;
    }

    public void setFinancialSystemSubject(FinancialSystemSubject financialSystemSubject) {
        this.financialSystemSubject = financialSystemSubject;
    }
}
