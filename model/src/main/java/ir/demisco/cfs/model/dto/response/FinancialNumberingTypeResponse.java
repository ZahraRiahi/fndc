package ir.demisco.cfs.model.dto.response;

public class FinancialNumberingTypeResponse {

    private Long id;
    private String description;
    private Long flgExists;

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

    public Long getFlgExists() {
        return flgExists;
    }

    public void setFlgExists(Long flgExists) {
        this.flgExists = flgExists;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private FinancialNumberingTypeResponse ledgerNumberingTypeDto;

        private Builder() {
            ledgerNumberingTypeDto = new FinancialNumberingTypeResponse();
        }

        public static Builder ledgerNumberingTypeDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            ledgerNumberingTypeDto.setId(id);
            return this;
        }

        public Builder description(String description) {
            ledgerNumberingTypeDto.setDescription(description);
            return this;
        }

        public Builder flgExists(Long flgExists) {
            ledgerNumberingTypeDto.setFlgExists(flgExists);
            return this;
        }

        public FinancialNumberingTypeResponse build() {
            return ledgerNumberingTypeDto;
        }
    }
}
