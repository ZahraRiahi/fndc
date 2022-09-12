package ir.demisco.cfs.model.dto.request;

public class GetDocFromoldSystemInputRequest {
    private Long dchdId;
    private String dchdNum;

    public Long getDchdId() {
        return dchdId;
    }

    public void setDchdId(Long dchdId) {
        this.dchdId = dchdId;
    }

    public String getDchdNum() {
        return dchdNum;
    }

    public void setDchdNum(String dchdNum) {
        this.dchdNum = dchdNum;
    }
}
