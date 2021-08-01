package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_account_structure", schema = "fnac")
public class FinancialAccountStructure extends AuditModel<Long> {

    private Long id;
    private Long sequence;
    private Long digitCount;
    private String description;
    private Long sumDigit;
    private FinancialCodingType financialCodingType;
    private String color;
    private LocalDateTime deletedDate;

    @Id
    @SequenceGenerator(schema = "fnac", name = "financial_account_structure_generator", sequenceName = "sq_financial_account_structure", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_account_structure_generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "SEQUENCE")
    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    @Column(name = "DIGIT_COUNT")
    public Long getDigitCount() {
        return digitCount;
    }

    public void setDigitCount(Long digitCount) {
        this.digitCount = digitCount;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "SUM_DIGIT")
    public Long getSumDigit() {
        return sumDigit;
    }

    public void setSumDigit(Long sumDigit) {
        this.sumDigit = sumDigit;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_CODING_TYPE_ID")
    public FinancialCodingType getFinancialCodingType() {
        return financialCodingType;
    }

    public void setFinancialCodingType(FinancialCodingType financialCodingType) {
        this.financialCodingType = financialCodingType;
    }

    @Column(name = "COLOR")
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
