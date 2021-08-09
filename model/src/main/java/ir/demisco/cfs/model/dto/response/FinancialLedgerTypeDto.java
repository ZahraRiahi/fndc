package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class FinancialLedgerTypeDto {

    private Long id;
    private String description;
    private LocalDateTime DeletedDate;

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
        return DeletedDate;
    }

    public void setDeletedDate(LocalDateTime deletedDate) {
        DeletedDate = deletedDate;
    }

    public static Builder builder() {
        return new Builder();
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

        public Builder DeletedDate(LocalDateTime DeletedDate) {
            financialLedgerTypeDto.setDeletedDate(DeletedDate);
            return this;
        }

        public FinancialLedgerTypeDto build() {
            return financialLedgerTypeDto;
        }
    }
}