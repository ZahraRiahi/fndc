package ir.demisco.cfs.model.entity;

import ir.demisco.cloud.basic.model.entity.domain.AuditModel;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="NUMBERING_FORMAT_SERIAL" , schema = "fndc")
public class NumberingFormatSerial extends AuditModel<Long> {

    private FinancialNumberingFormat financialNumberingFormat;
    private Long lastSerial;
    private String serialReseter;
    private Long   serialLength;
    private LocalDateTime deletedDate;


    @Override
    @Id
    @SequenceGenerator(schema = "fndc", name = "numbering_format_serial_generator", sequenceName = "sq_numbering_format_serial", allocationSize = 50)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "numbering_format_serial_generator")

    public Long getId() {
        return super.getId();
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NUMBERING_FORMAT_ID")
    public FinancialNumberingFormat getFinancialNumberingFormat() {
        return financialNumberingFormat;
    }

    public void setFinancialNumberingFormat(FinancialNumberingFormat financialNumberingFormat) {
        this.financialNumberingFormat = financialNumberingFormat;
    }

    public Long getLastSerial() {
        return lastSerial;
    }

    public void setLastSerial(Long lastSerial) {
        this.lastSerial = lastSerial;
    }

    public String getSerialReseter() {
        return serialReseter;
    }

    public void setSerialReseter(String serialReseter) {
        this.serialReseter = serialReseter;
    }

    public Long getSerialLength() {
        return serialLength;
    }

    public void setSerialLength(Long serialLength) {
        this.serialLength = serialLength;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }
}
