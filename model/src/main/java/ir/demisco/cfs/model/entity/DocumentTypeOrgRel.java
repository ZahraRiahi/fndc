package ir.demisco.cfs.model.entity;

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


@Entity
@Table(name = "document_type_org_rel", schema = "fndc")
public class DocumentTypeOrgRel {
    private Long id;
    private FinancialDocumentType financialDocumentType;
    private Organization organization;
    private Long activeFlag;

    @Id
    @SequenceGenerator(schema = "fndc", name = "document_type_org_rel_generator", sequenceName = "sq_document_type_org_rel", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "document_type_org_rel_generator")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FINANCIAL_DOCUMENT_TYPE_ID")
    public FinancialDocumentType getFinancialDocumentType() {
        return financialDocumentType;
    }

    public void setFinancialDocumentType(FinancialDocumentType financialDocumentType) {
        this.financialDocumentType = financialDocumentType;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZATION_ID")
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Column(name = "ACTIVE_FLAG")
    public Long getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Long activeFlag) {
        this.activeFlag = activeFlag;
    }
}
