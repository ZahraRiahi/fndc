package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Organization;

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
@Table(name = "FINANCIAL_NUMBERING_FORMAT", schema = "fndc")
public class FinancialNumberingFormat extends AuditModel<Long> {

    private Organization organization;
    private FinancialNumberingFormatType financialNumberingFormatType;
    private FinancialNumberingType financialNumberingType;
    private String description;
    private LocalDateTime deletedDate;
    private String reseter;
    private Long serialLength;
    private Long firstSerial;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_numbering_format_generator", sequenceName = "sq_financial_numbering_format")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_numbering_format_generator")
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NUMBERING_FORMAT_TYPE_ID")
    public FinancialNumberingFormatType getFinancialNumberingFormatType() {
        return financialNumberingFormatType;
    }

    public void setFinancialNumberingFormatType(FinancialNumberingFormatType financialNumberingFormatType) {
        this.financialNumberingFormatType = financialNumberingFormatType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_NUMBERING_TYPE_ID")
    public FinancialNumberingType getFinancialNumberingType() {
        return financialNumberingType;
    }

    public void setFinancialNumberingType(FinancialNumberingType financialNumberingType) {
        this.financialNumberingType = financialNumberingType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @Column(name = "RESETER")
    public String getReseter() {
        return reseter;
    }

    public void setReseter(String reseter) {
        this.reseter = reseter;
    }

    @Column(name = "SERIAL_LENGTH")
    public Long getSerialLength() {
        return serialLength;
    }

    public void setSerialLength(Long serialLength) {
        this.serialLength = serialLength;
    }

    @Column(name = "FIRST_SERIAL")
    public Long getFirstSerial() {
        return firstSerial;
    }

    public void setFirstSerial(Long firstSerial) {
        this.firstSerial = firstSerial;
    }
}
