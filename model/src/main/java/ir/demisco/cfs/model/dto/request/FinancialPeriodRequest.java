package ir.demisco.cfs.model.dto.request;

import java.time.LocalDateTime;

public class FinancialPeriodRequest {
    private LocalDateTime date;
    private Long organizationId;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }
}
