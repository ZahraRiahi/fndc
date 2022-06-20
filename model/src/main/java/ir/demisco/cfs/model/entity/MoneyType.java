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
@Table(name = "money_type", schema = "fncr")
public class MoneyType extends AuditModel<Long> {

    private Long id;
    private String code;
    private String description;
    private String shortDescription;
    private Boolean activeFlag;
    private Boolean nationalCurrencyFlag;
    private String symbol;
    private Boolean isBaseFlag;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fncr", name = "money_type_generator", sequenceName = "sq_money_type", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "money_type_generator")
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

    @Column(name = "SHORT_DESCRIPTION")
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Column(name = "SYMBOL")
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Column(name = "ACTIVE_FLAG")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Column(name = "NATIONAL_CURRENCY_FLAG")
    public Boolean getNationalCurrencyFlag() {
        return nationalCurrencyFlag;
    }

    public void setNationalCurrencyFlag(Boolean nationalCurrencyFlag) {
        this.nationalCurrencyFlag = nationalCurrencyFlag;
    }

    @Column(name = "IS_BASE_FLAG")
    public Boolean getIsBaseFlag() {
        return isBaseFlag;
    }

    public void setIsBaseFlag(Boolean isBaseFlag) {
        this.isBaseFlag = isBaseFlag;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
