package ir.demisco.cfs.model.dto.response;


public class FinancialNumberingFormatTypeDto {

    private Long id;
    private String description;
    private String format;
    private String defaultReset;


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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDefaultReset() {
        return defaultReset;
    }

    public void setDefaultReset(String defaultReset) {
        this.defaultReset = defaultReset;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private FinancialNumberingFormatTypeDto financialNumberingFormatTypeDto;

        private Builder() {
            financialNumberingFormatTypeDto = new FinancialNumberingFormatTypeDto();
        }

        public static Builder financialNumberingFormatTypeDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialNumberingFormatTypeDto.setId(id);
            return this;
        }

        public Builder description(String description) {
            financialNumberingFormatTypeDto.setDescription(description);
            return this;
        }

        public Builder format(String format) {
            financialNumberingFormatTypeDto.setFormat(format);
            return this;
        }

        public Builder defaultReset(String defaultReset) {
            financialNumberingFormatTypeDto.setDefaultReset(defaultReset);
            return this;
        }

        public FinancialNumberingFormatTypeDto build() {
            return financialNumberingFormatTypeDto;
        }
    }
}
