package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;

@Entity
@Table(name = "FIANANCIAL_SYSTEM_SUBJECT", schema = "fnsc")
public class FinancialSystemSubject extends AuditModel<Long> {
    private Long id;
    private String code;
    private String description;
    private FinancialSystem financialSystem;

    @Override
    @Id
    @SequenceGenerator(schema = "fnsc", name = "financial_system_subject_generator", sequenceName = "sq_financial_system_subject")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_system_subject_generator")
    public Long getId() {
        return id;
    }

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_SYSTEM_ID")
    public FinancialSystem getFinancialSystem() {
        return financialSystem;
    }

    public void setFinancialSystem(FinancialSystem financialSystem) {
        this.financialSystem = financialSystem;
    }
}
