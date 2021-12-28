package ir.demisco.cfs.model.dto.response;


import java.time.LocalDateTime;

public class FinancialNumberingFormatDto {

    private Long id;
    private Long organizationId;
    private Long financialNumberingFormatTypeId;
    private String financialNumberingFormatTypeDescription;
    private Long financialNumberingTypeId;
    private String financialNumberingTypeDescription;
    private String description;
    private String reseter;
    private int serialLength;
    private Long firstSerial;
    private LocalDateTime deletedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public Long getFinancialNumberingFormatTypeId() {
        return financialNumberingFormatTypeId;
    }

    public void setFinancialNumberingFormatTypeId(Long financialNumberingFormatTypeId) {
        this.financialNumberingFormatTypeId = financialNumberingFormatTypeId;
    }

    public String getFinancialNumberingFormatTypeDescription() {
        return financialNumberingFormatTypeDescription;
    }

    public void setFinancialNumberingFormatTypeDescription(String financialNumberingFormatTypeDescription) {
        this.financialNumberingFormatTypeDescription = financialNumberingFormatTypeDescription;
    }

    public Long getFinancialNumberingTypeId() {
        return financialNumberingTypeId;
    }

    public void setFinancialNumberingTypeId(Long financialNumberingTypeId) {
        this.financialNumberingTypeId = financialNumberingTypeId;
    }

    public String getFinancialNumberingTypeDescription() {
        return financialNumberingTypeDescription;
    }

    public void setFinancialNumberingTypeDescription(String financialNumberingTypeDescription) {
        this.financialNumberingTypeDescription = financialNumberingTypeDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        this.deletedDate = deletedDate;
    }

    public String getReseter() {
        return reseter;
    }

    public void setReseter(String reseter) {
        this.reseter = reseter;
    }

    public int getSerialLength() {
        return serialLength;
    }

    public void setSerialLength(int serialLength) {
        this.serialLength = serialLength;
    }

    public Long getFirstSerial() {
        return firstSerial;
    }

    public void setFirstSerial(Long firstSerial) {
        this.firstSerial = firstSerial;
    }

    public static FinancialNumberingFormatDto.Builder builder() {
        return new FinancialNumberingFormatDto.Builder();
    }

    public static final class Builder {
        private FinancialNumberingFormatDto financialNumberingFormatDto;

        private Builder() {
            financialNumberingFormatDto = new FinancialNumberingFormatDto();
        }

        public static Builder financialNumberingFormatDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialNumberingFormatDto.setId(id);
            return this;
        }

        public Builder organizationId(Long organizationId) {
            financialNumberingFormatDto.setOrganizationId(organizationId);
            return this;
        }

        public Builder financialNumberingFormatTypeId(Long financialNumberingFormatTypeId) {
            financialNumberingFormatDto.setFinancialNumberingFormatTypeId(financialNumberingFormatTypeId);
            return this;
        }

        public Builder financialNumberingFormatTypeDescription(String financialNumberingFormatTypeDescription) {
            financialNumberingFormatDto.setFinancialNumberingFormatTypeDescription(financialNumberingFormatTypeDescription);
            return this;
        }

        public Builder financialNumberingTypeId(Long financialNumberingTypeId) {
            financialNumberingFormatDto.setFinancialNumberingTypeId(financialNumberingTypeId);
            return this;
        }

        public Builder financialNumberingTypeDescription(String financialNumberingTypeDescription) {
            financialNumberingFormatDto.setFinancialNumberingTypeDescription(financialNumberingTypeDescription);
            return this;
        }

        public Builder description(String description) {
            financialNumberingFormatDto.setDescription(description);
            return this;
        }

        public Builder reseter(String reseter) {
            financialNumberingFormatDto.setReseter(reseter);
            return this;
        }

        public Builder serialLength(int serialLength) {
            financialNumberingFormatDto.setSerialLength(serialLength);
            return this;
        }

        public Builder firstSerial(Long firstSerial) {
            financialNumberingFormatDto.setFirstSerial(firstSerial);
            return this;
        }

        public Builder deletedDate(LocalDateTime deletedDate) {
            financialNumberingFormatDto.setDeletedDate(deletedDate);
            return this;
        }

        public FinancialNumberingFormatDto build() {
            return financialNumberingFormatDto;
        }
    }
}
