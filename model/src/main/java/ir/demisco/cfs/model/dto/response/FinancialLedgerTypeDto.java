package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialLedgerTypeDto {

    private Long id;
    private String description;
    private LocalDateTime deletedDate;
    private String code;
    private Boolean disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public static final class Builder {
        private FinancialLedgerTypeDto financialLedgerTypeDto;

        private Builder() {
            financialLedgerTypeDto = new FinancialLedgerTypeDto();
        }

        public static Builder financialLedgerTypeDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialLedgerTypeDto.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialLedgerTypeDto.setDescription(description);
            return this;
        }

        public Builder deletedDate(LocalDateTime deletedDate) {
            financialLedgerTypeDto.setDeletedDate(deletedDate);
            return this;
        }

        public Builder disabled(Boolean disabled) {
            financialLedgerTypeDto.setDisabled(disabled);
            return this;
        }
        public Builder code(String code) {
            financialLedgerTypeDto.setCode(code);
            return this;
        }

        public FinancialLedgerTypeDto build() {
            return financialLedgerTypeDto;
        }
    }
}