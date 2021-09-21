package ir.demisco.cfs.model.dto.response;

public class FinancialSystemDto {
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

    public static FinancialSystemDto.Builder builder() {
        return new FinancialSystemDto.Builder();
    }
    public static final class Builder {
        private FinancialSystemDto financialSystemDto;

        private Builder() {
            financialSystemDto = new FinancialSystemDto();
        }

        public static Builder financialSystemDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialSystemDto.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialSystemDto.setDescription(description);
            return this;
        }

        public FinancialSystemDto build() {
            return financialSystemDto;
        }
    }

}
