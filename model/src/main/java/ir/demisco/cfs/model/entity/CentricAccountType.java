package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "centric_account_type", schema = "fnac")
public class CentricAccountType extends AuditModel<Long> {

    private Long id;
    private String code;
    private String description;
    private Long autoCodeGenerateFlag;
    private Long autoInsertFlag;
    private Long fixFlag;
    private String preCode;
    private String startCode;
    private Long activeFlag;
    private Long documentRelatedFlag;
    private Long parrentFlag;
    private LocalDateTime deletedDate;

    @Override
    @Id
    @SequenceGenerator(schema = "fnac", name = "centric_account_type_generator", sequenceName = "sq_centric_account_type", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "centric_account_type_generator")
    public Long getId() {
        return id;
    }

    @Override
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

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "AUTO_CODE_GENERATE_FLAG")
    public Long getAutoCodeGenerateFlag() {
        return autoCodeGenerateFlag;
    }

    public void setAutoCodeGenerateFlag(Long autoCodeGenerateFlag) {
        this.autoCodeGenerateFlag = autoCodeGenerateFlag;
    }

    @Column(name = "AUTO_INSERT_FLAG")
    public Long getAutoInsertFlag() {
        return autoInsertFlag;
    }

    public void setAutoInsertFlag(Long autoInsertFlag) {
        this.autoInsertFlag = autoInsertFlag;
    }

    @Column(name = "FIX_FLAG")
    public Long getFixFlag() {
        return fixFlag;
    }

    public void setFixFlag(Long fixFlag) {
        this.fixFlag = fixFlag;
    }

    @Column(name = "PRE_CODE")
    public String getPreCode() {
        return preCode;
    }

    public void setPreCode(String preCode) {
        this.preCode = preCode;
    }

    @Column(name = "START_CODE")
    public String getStartCode() {
        return startCode;
    }

    public void setStartCode(String startCode) {
        this.startCode = startCode;
    }

    @Column(name = "ACTIVE_FLAG")
    public Long getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Long activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Column(name = "DOCUMENT_RELATED_FLAG")
    public Long getDocumentRelatedFlag() {
        return documentRelatedFlag;
    }

    public void setDocumentRelatedFlag(Long documentRelatedFlag) {
        this.documentRelatedFlag = documentRelatedFlag;
    }

    @Column(name = "PARRENT_FLAG")
    public Long getParrentFlag() {
        return parrentFlag;
    }

    public void setParrentFlag(Long parrentFlag) {
        this.parrentFlag = parrentFlag;
    }

    @Column(name = "DELETED_DATE")
    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

}
