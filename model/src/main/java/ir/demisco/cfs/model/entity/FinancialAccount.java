package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Organization;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "financial_account", schema = "fnac")
public class FinancialAccount extends AuditModel<Long> {
    private Long id;
    private Organization organization;
    private FinancialAccountStructure financialAccountStructure;
    private String fullDescription;
    private String code;
    private String description;
    private String latinDescription;
    private AccountNatureType accountNatureType;
    private Boolean relatedToOthersFlag;
    private AccountRelationType accountRelationType;
    private FinancialAccount financialAccountParent;
    private String relatedToFundType;
    private Boolean referenceFlag;
    private Boolean convertFlag;
    private Boolean exchangeFlag;
    private FinancialAccount accountAdjustment;
    private LocalDateTime deletedDate;
    private Boolean hasChild;
    private AccountStatus accountStatus;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @JoinColumn(name = "FINANCIAL_ACCOUNT_STRUCTURE_ID")
    public FinancialAccountStructure getFinancialAccountStructure() {
        return financialAccountStructure;
    }

    public void setFinancialAccountStructure(FinancialAccountStructure financialAccountStructure) {
        this.financialAccountStructure = financialAccountStructure;
    }

    @Column(name = "FULL_DESCRIPTION")
    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
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

    @Column(name = "LATIN_DESCRIPTION")
    public String getLatinDescription() {
        return latinDescription;
    }

    public void setLatinDescription(String latinDescription) {
        this.latinDescription = latinDescription;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_NATURE_TYPE_ID")
    public AccountNatureType getAccountNatureType() {
        return accountNatureType;
    }

    public void setAccountNatureType(AccountNatureType accountNatureType) {
        this.accountNatureType = accountNatureType;
    }

    @Column(name = "RELATED_TO_OTHERS_FLAG")
    public Boolean getRelatedToOthersFlag() {
        return relatedToOthersFlag;
    }

    public void setRelatedToOthersFlag(Boolean relatedToOthersFlag) {
        this.relatedToOthersFlag = relatedToOthersFlag;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_RELATION_TYPE_ID")
    public AccountRelationType getAccountRelationType() {
        return accountRelationType;
    }

    public void setAccountRelationType(AccountRelationType accountRelationType) {
        this.accountRelationType = accountRelationType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_ACCOUNT_PARENT_ID")
    public FinancialAccount getFinancialAccountParent() {
        return financialAccountParent;
    }

    public void setFinancialAccountParent(FinancialAccount financialAccountParent) {
        this.financialAccountParent = financialAccountParent;
    }

    @Column(name = "RELATED_TO_FUND_TYPE")
    public String getRelatedToFundType() {
        return relatedToFundType;
    }

    public void setRelatedToFundType(String relatedToFundType) {
        this.relatedToFundType = relatedToFundType;
    }

    @Column(name = "REFERENCE_FLAG")
    public Boolean getReferenceFlag() {
        return referenceFlag;
    }

    public void setReferenceFlag(Boolean referenceFlag) {
        this.referenceFlag = referenceFlag;
    }

    @Column(name = "CONVERT_FLAG")
    public Boolean getConvertFlag() {
        return convertFlag;
    }

    public void setConvertFlag(Boolean convertFlag) {
        this.convertFlag = convertFlag;
    }

    @Column(name = "EXCHANGE_FLAG")
    public Boolean getExchangeFlag() {
        return exchangeFlag;
    }

    public void setExchangeFlag(Boolean exchangeFlag) {
        this.exchangeFlag = exchangeFlag;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_ADJUSTMENT_ID")
    public FinancialAccount getAccountAdjustment() {
        return accountAdjustment;
    }

    public void setAccountAdjustment(FinancialAccount accountAdjustment) {
        this.accountAdjustment = accountAdjustment;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_STATUS_ID")
    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Transient
    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }
}
