package ir.demisco.cfs.model.dto.response;

public class FinancialSecurityOutputResponse {
    private String permissionMessage;
    private Long hasPermissionStatus;
    private Long activityId;
    private String activityDescription;
    private Long financialSystemId;
    private Long financialSystemSubjectId;

    public String getPermissionMessage() {
        return permissionMessage;
    }

    public void setPermissionMessage(String permissionMessage) {
        this.permissionMessage = permissionMessage;
    }

    public Long getHasPermissionStatus() {
        return hasPermissionStatus;
    }

    public void setHasPermissionStatus(Long hasPermissionStatus) {
        this.hasPermissionStatus = hasPermissionStatus;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public Long getFinancialSystemId() {
        return financialSystemId;
    }

    public void setFinancialSystemId(Long financialSystemId) {
        this.financialSystemId = financialSystemId;
    }

    public Long getFinancialSystemSubjectId() {
        return financialSystemSubjectId;
    }

    public void setFinancialSystemSubjectId(Long financialSystemSubjectId) {
        this.financialSystemSubjectId = financialSystemSubjectId;
    }

    public static FinancialSecurityOutputResponse.Builder builder() {
        return new FinancialSecurityOutputResponse.Builder();
    }

    public static final class Builder {
        private FinancialSecurityOutputResponse financialSecurityOutputResponse;

        private Builder() {
            financialSecurityOutputResponse = new FinancialSecurityOutputResponse();
        }

        public static Builder financialSecurityOutputResponse() {
            return new Builder();
        }

        public Builder permissionMessage(String permissionMessage) {
            financialSecurityOutputResponse.setPermissionMessage(permissionMessage);
            return this;
        }

        public Builder hasPermissionStatus(Long hasPermissionStatus) {
            financialSecurityOutputResponse.setHasPermissionStatus(hasPermissionStatus);
            return this;
        }

        public Builder activityId(Long activityId) {
            financialSecurityOutputResponse.setActivityId(activityId);
            return this;
        }

        public Builder activityDescription(String activityDescription) {
            financialSecurityOutputResponse.setActivityDescription(activityDescription);
            return this;
        }

        public Builder financialSystemId(Long financialSystemId) {
            financialSecurityOutputResponse.setFinancialSystemId(financialSystemId);
            return this;
        }

        public Builder financialSystemSubjectId(Long financialSystemSubjectId) {
            financialSecurityOutputResponse.setFinancialSystemSubjectId(financialSystemSubjectId);
            return this;
        }

        public FinancialSecurityOutputResponse build() {
            return financialSecurityOutputResponse;
        }
    }
}
