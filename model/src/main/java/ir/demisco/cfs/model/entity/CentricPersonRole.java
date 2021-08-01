package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "centric_person_role", schema = "fnac")
public class CentricPersonRole  extends AuditModel<Long> {

    private Long id;
    private PersonRoleType personRoleType;
    private CentricAccount centricAccount;
    private LocalDateTime deletedDate;

    @Id
    @SequenceGenerator(schema = "fnac", name = "centric_person_role_generator", sequenceName = "sq_centric_person_role", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "centric_person_role_generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ROLE_TYPE_ID")
    public PersonRoleType getPersonRoleType() {
        return personRoleType;
    }

    public void setPersonRoleType(PersonRoleType personRoleType) {
        this.personRoleType = personRoleType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CENTRIC_ACCOUNT_ID")
    public CentricAccount getCentricAccount() {
        return centricAccount;
    }

    public void setCentricAccount(CentricAccount centricAccount) {
        this.centricAccount = centricAccount;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
