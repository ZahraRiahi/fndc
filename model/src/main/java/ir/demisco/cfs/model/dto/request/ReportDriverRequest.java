package ir.demisco.cfs.model.dto.request;

import java.util.Map;

public class ReportDriverRequest {
    private String path;
    private String reportName;
    private String reportType;
    private String mainReportId;
    private String subReportId;
    private Map<String, Object> params;

    public ReportDriverRequest() {
    }

    public static ReportDriverRequest.builder builder() {
        return new ReportDriverRequest.builder();
    }

    public String getReportName() {
        return this.reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportType() {
        return this.reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMainReportId() {
        return this.mainReportId;
    }

    public void setMainReportId(String mainReportId) {
        this.mainReportId = mainReportId;
    }

    public String getSubReportId() {
        return this.subReportId;
    }

    public void setSubReportId(String subReportId) {
        this.subReportId = subReportId;
    }

    public static final class builder {
        private ReportDriverRequest reportDriverRequest;

        private builder() {
            this.reportDriverRequest = new ReportDriverRequest();
        }

        public ReportDriverRequest.builder path(String path) {
            this.reportDriverRequest.setPath(path);
            return this;
        }

        public ReportDriverRequest.builder reportName(String reportName) {
            this.reportDriverRequest.setReportName(reportName);
            return this;
        }

        public ReportDriverRequest.builder reportType(String reportType) {
            this.reportDriverRequest.setReportType(reportType);
            return this;
        }

        public ReportDriverRequest.builder mainReportId(String mainReportId) {
            this.reportDriverRequest.setMainReportId(mainReportId);
            return this;
        }

        public ReportDriverRequest.builder subReportId(String subReportId) {
            this.reportDriverRequest.setSubReportId(subReportId);
            return this;
        }

        public ReportDriverRequest.builder params(Map<String, Object> params) {
            this.reportDriverRequest.setParams(params);
            return this;
        }

        public ReportDriverRequest build() {
            return this.reportDriverRequest;
        }
    }
}
