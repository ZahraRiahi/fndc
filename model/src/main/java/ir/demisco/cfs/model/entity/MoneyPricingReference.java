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
@Table(name = "money_pricing_refrence", schema = "fncr")
public class MoneyPricingReference extends AuditModel<Long> {

    private Long id;
    private String code;
    private String description;
    private Boolean interNationalFlag;
    private Boolean activeFlag;
    private LocalDateTime deletedDate;
    @Override
    @Id
    @SequenceGenerator(schema = "fncr", name = "money_pricing_refrence_generator", sequenceName = "sq_money_pricing_refrence", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "money_pricing_refrence_generator")
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "INTERNATIONAL_FLAG")
    public Boolean getInterNationalFlag() {
        return interNationalFlag;
    }

    public void setInterNationalFlag(Boolean interNationalFlag) {
        this.interNationalFlag = interNationalFlag;
    }

    @Column(name = "ACTIVE_FLAG")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
