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
@Table(name = "financial_period_type", schema = "fnpr")
public class FinancialPeriodType extends AuditModel<Long> {

    private Long id;
    private String description;
    private Long fromMonth;
    private Long toMonth;
    private Long calendarTypeId;
    private Boolean calendarYearFlag;
    private LocalDateTime deletedDate;

    @Id
    @SequenceGenerator(schema = "fnpr", name = "financial_period_type_generator", sequenceName = "sq_financial_period_type", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_period_type_generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "FROM_MONTH")
    public Long getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(Long fromMonth) {
        this.fromMonth = fromMonth;
    }

    @Column(name = "TO_MONTH")
    public Long getToMonth() {
        return toMonth;
    }

    public void setToMonth(Long toMonth) {
        this.toMonth = toMonth;
    }

    @Column(name = "CALENDAR_TYPE_ID")
    public Long getCalendarTypeId() {
        return calendarTypeId;
    }

    public void setCalendarTypeId(Long calendarTypeId) {
        this.calendarTypeId = calendarTypeId;
    }

    @Column(name = "CURRENT_YEAR_FLAG")
    public Boolean getCalendarYearFlag() {
        return calendarYearFlag;
    }

    public void setCalendarYearFlag(Boolean calendarYearFlag) {
        this.calendarYearFlag = calendarYearFlag;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
