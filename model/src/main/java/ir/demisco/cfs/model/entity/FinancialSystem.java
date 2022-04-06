package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "FINANCIAL_SYSTEM", schema = "fnsc")
public class FinancialSystem extends AuditModel<Long> {
    private String code;
    private String description;


    @Override
    @Id
    @SequenceGenerator(schema = "fnsc", name = "financial_system_generator", sequenceName = "sq_financial_system")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_system_generator")
    public Long getId() {
        return super.getId();
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
