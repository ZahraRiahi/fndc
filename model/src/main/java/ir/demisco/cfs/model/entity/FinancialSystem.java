package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;

@Entity
@Table(name="FINANCIAL_SYSTEM" , schema = "fndc")
public class FinancialSystem  extends AuditModel<Long> {

    private String   description;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_system_generator", sequenceName = "sq_financial_system")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_system_generator")
    public Long getId() {
        return super.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
