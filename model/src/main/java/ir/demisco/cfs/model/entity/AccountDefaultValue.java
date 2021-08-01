package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_default_value", schema = "fnac")
public class AccountDefaultValue  extends AuditModel<Long> {

    private Long id;
    private FinancialAccount financialAccount;
    private CentricAccount centricAccount;
    private AccountRelationTypeDetail accountRelationTypeDetail;
    private LocalDateTime deletedDate;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_ACCOUNT_ID")
    public FinancialAccount getFinancialAccount() {
        return financialAccount;
    }

    public void setFinancialAccount(FinancialAccount financialAccount) {
        this.financialAccount = financialAccount;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CENTRIC_ACCOUNT_ID")
    public CentricAccount getCentricAccount() {
        return centricAccount;
    }

    public void setCentricAccount(CentricAccount centricAccount) {
        this.centricAccount = centricAccount;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ACCOUNT_RELATION_TYP_DETAIL_ID")
    public AccountRelationTypeDetail getAccountRelationTypeDetail() {
        return accountRelationTypeDetail;
    }

    public void setAccountRelationTypeDetail(AccountRelationTypeDetail accountRelationTypeDetail) {
        this.accountRelationTypeDetail = accountRelationTypeDetail;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
