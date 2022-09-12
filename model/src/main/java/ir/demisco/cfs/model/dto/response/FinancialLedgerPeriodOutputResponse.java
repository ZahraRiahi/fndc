package ir.demisco.cfs.model.dto.response;

import java.util.Date;

public class FinancialLedgerPeriodOutputResponse {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Long openMonthCount;
    private String description;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getOpenMonthCount() {
        return openMonthCount;
    }

    public void setOpenMonthCount(Long openMonthCount) {
        this.openMonthCount = openMonthCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public static FinancialLedgerPeriodOutputResponse.Builder builder() {
        return new FinancialLedgerPeriodOutputResponse.Builder();
    }

    public static final class Builder {
        private FinancialLedgerPeriodOutputResponse financialLedgerPeriodOutputResponse;

        private Builder() {
            financialLedgerPeriodOutputResponse = new FinancialLedgerPeriodOutputResponse();
        }

        public static Builder financialLedgerPeriodOutputResponse() {
            return new Builder();
        }

        public Builder id(Long id) {
            financialLedgerPeriodOutputResponse.setId(id);
            return this;
        }

        public Builder startDate(Date startDate) {
            financialLedgerPeriodOutputResponse.setStartDate(startDate);
            return this;
        }

        public Builder endDate(Date endDate) {
            financialLedgerPeriodOutputResponse.setEndDate(endDate);
            return this;
        }

        public Builder openMonthCount(Long openMonthCount) {
            financialLedgerPeriodOutputResponse.setOpenMonthCount(openMonthCount);
            return this;
        }

        public Builder description(String description) {
            financialLedgerPeriodOutputResponse.setDescription(description);
            return this;
        }

        public Builder name(String name) {
            financialLedgerPeriodOutputResponse.setName(name);
            return this;
        }

        public FinancialLedgerPeriodOutputResponse build() {
            return financialLedgerPeriodOutputResponse;
        }
    }
}
