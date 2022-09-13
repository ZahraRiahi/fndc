package ir.demisco.cfs.model.dto.response;

public class FinancialPeriodOutputResponse {
    private Long id;
    private String description;

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
    public static FinancialPeriodOutputResponse.Builder builder() {
        return new FinancialPeriodOutputResponse.Builder();
    }
    public static final class Builder {
        private FinancialPeriodOutputResponse financialPeriodOutputResponse;

        private Builder() {
            financialPeriodOutputResponse = new FinancialPeriodOutputResponse();
        }

        public static Builder financialPeriodOutputResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialPeriodOutputResponse.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialPeriodOutputResponse.setDescription(description);
            return this;
        }

        public FinancialPeriodOutputResponse build() {
            return financialPeriodOutputResponse;
        }
    }
}
