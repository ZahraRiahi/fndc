package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_relation_type_detail", schema = "fnac")
public class AccountRelationTypeDetail  extends AuditModel<Long> {

    private Long id;
    private AccountRelationType accountRelationType;
    private CentricAccountType centricAccountType;
    private PersonRoleType personRoleType;
    private Long sequence;
    private LocalDateTime deletedDate;

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    @JoinColumn(name = "CENTRIC_ACCOUNT_TYPE_ID")
    public CentricAccountType getCentricAccountType() {
        return centricAccountType;
    }

    public void setCentricAccountType(CentricAccountType centricAccountType) {
        this.centricAccountType = centricAccountType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ROLE_TYPE_ID")
    public PersonRoleType getPersonRoleType() {
        return personRoleType;
    }

    public void setPersonRoleType(PersonRoleType personRoleType) {
        this.personRoleType = personRoleType;
    }

    @Column(name = "SEQUENCE")
    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
