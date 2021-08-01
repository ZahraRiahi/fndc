package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Organization;
import ir.demisco.cloud.basic.model.entity.prs.Person;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "centric_account", schema = "fnac")
public class CentricAccount extends AuditModel<Long> {

    private Long id;
    private String code;
    private String name;
    private Boolean activeFlag;
    private String abbreviationName;
    private String latinName;
    private CentricAccountType centricAccountType;
    private Organization organization;
    private Person person;
    private List<CentricPersonRole> centricPersonRoleList;
    private LocalDateTime deletedDate;
    private List<AccountDefaultValue> accountDefaultValues;

    @Id
    @SequenceGenerator(schema = "fnac", name = "centric_account_generator", sequenceName = "sq_centric_account", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "centric_account_generator")
    public Long getId() {
        return id;
    }

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

    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ACTIVE_FLAG")
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Column(name = "ABBREVIATION_NAME")
    public String getAbbreviationName() {
        return abbreviationName;
    }

    public void setAbbreviationName(String abbreviationName) {
        this.abbreviationName = abbreviationName;
    }

    @Column(name = "LATIN_NAME")
    public String getLatinName() {
        return latinName;
    }

    public void setLatinName(String latinName) {
        this.latinName = latinName;
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
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PERSON_ID")
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "centricAccount")
    public List<CentricPersonRole> getCentricPersonRoleList() {
        return centricPersonRoleList;
    }

    public void setCentricPersonRoleList(List<CentricPersonRole> centricPersonRoleList) {
        this.centricPersonRoleList = centricPersonRoleList;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    @OneToMany(mappedBy = "centricAccount",fetch = FetchType.LAZY)
    public List<AccountDefaultValue> getAccountDefaultValues() {
        return accountDefaultValues;
    }

    public void setAccountDefaultValues(List<AccountDefaultValue> accountDefaultValues) {
        this.accountDefaultValues = accountDefaultValues;
    }
}
