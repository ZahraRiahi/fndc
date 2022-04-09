package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="FINANCIAL_NUMBERING_TYPE" , schema = "fndc")
public class FinancialNumberingType  extends AuditModel<Long> {


    private String   description;
    private LocalDateTime deletedDate;
    private Long typeStatus;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_numbering_type_generator", sequenceName = "sq_financial_numbering_type")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_numbering_type_generator")
    public Long getId() {
        return super.getId();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(Long typeStatus) {
        this.typeStatus = typeStatus;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

}
