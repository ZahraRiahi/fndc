package ir.demisco.cfs.model.dto.request;

public class GetDocFromoldSystemInputRequest {
    private Long dchdId;
    private Long dchdNum;

    public Long getDchdId() {
        return dchdId;
    }

    public void setDchdId(Long dchdId) {
        this.dchdId = dchdId;
    }

    public Long getDchdNum() {
        return dchdNum;
    }

    public void setDchdNum(Long dchdNum) {
        this.dchdNum = dchdNum;
    }
}
