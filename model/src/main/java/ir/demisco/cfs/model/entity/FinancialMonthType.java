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
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_month_type", schema = "fnpr")
public class FinancialMonthType extends AuditModel<Long> {

    private Long id;
    private FinancialPeriodType financialPeriodType;
    private String description;
    private Long monthNumber;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fnpr", name = "financial_month_type_generator", sequenceName = "sq_financial_month_type", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_month_type_generator")
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_PERIOD_TYPE_ID")
    public FinancialPeriodType getFinancialPeriodType() {
        return financialPeriodType;
    }

    public void setFinancialPeriodType(FinancialPeriodType financialPeriodType) {
        this.financialPeriodType = financialPeriodType;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "MONTH_NUMBER")
    public Long getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Long monthNumber) {
        this.monthNumber = monthNumber;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
