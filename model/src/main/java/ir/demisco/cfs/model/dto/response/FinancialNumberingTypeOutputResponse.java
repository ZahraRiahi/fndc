package ir.demisco.cfs.model.dto.response;

public class FinancialNumberingTypeOutputResponse {
    private Long id;
    private String description;
    private String fromCode;
    private String toCode;

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

    public String getFromCode() {
        return fromCode;
    }

    public void setFromCode(String fromCode) {
        this.fromCode = fromCode;
    }

    public String getToCode() {
        return toCode;
    }

    public void setToCode(String toCode) {
        this.toCode = toCode;
    }

    public static FinancialNumberingTypeOutputResponse.Builder builder() {
        return new FinancialNumberingTypeOutputResponse.Builder();
    }

    public static final class Builder {
        private FinancialNumberingTypeOutputResponse financialNumberingTypeOutputResponse;

        private Builder() {
            financialNumberingTypeOutputResponse = new FinancialNumberingTypeOutputResponse();
        }

        public static Builder aFinancialNumberingTypeOutputResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialNumberingTypeOutputResponse.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialNumberingTypeOutputResponse.setDescription(description);
            return this;
        }

        public Builder fromCode(String fromCode) {
            financialNumberingTypeOutputResponse.setFromCode(fromCode);
            return this;
        }

        public Builder toCode(String toCode) {
            financialNumberingTypeOutputResponse.setToCode(toCode);
            return this;
        }

        public FinancialNumberingTypeOutputResponse build() {
            return financialNumberingTypeOutputResponse;
        }
    }
}
