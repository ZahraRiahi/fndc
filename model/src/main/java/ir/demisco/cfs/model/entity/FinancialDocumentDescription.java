package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;
import ir.demisco.cloud.basic.model.entity.org.Organization;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FINANCIAL_DOCUMENT_DESCRIPTION", schema = "fndc")
public class FinancialDocumentDescription extends AuditModel<Long> {


    private Organization organization;
    private String    description;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "financial_document_description_generator", sequenceName = "sq_financial_document_description")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "financial_document_description_generator")
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
}
