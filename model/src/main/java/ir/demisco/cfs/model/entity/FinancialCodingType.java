package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Organization;

import javax.persistence.*;

@Entity
@Table(name = "financial_coding_type", schema = "fnac")
public class FinancialCodingType  extends AuditModel<Long> {

    private Long id;
    private String description;
    private Organization organization;

    @Id
    @SequenceGenerator(schema = "fnac", name = "financial_coding_type_generator", sequenceName = "sq_financial_coding_type", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_coding_type_generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
