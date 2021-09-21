package ir.demisco.cfs.model.dto.response;

public class FinancialCentricAccountDto {

    private Long id;
    private Long accountId;
    private Long centricAccountId;
    private Long newCentricAccountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCentricAccountId() {
        return centricAccountId;
    }

    public void setCentricAccountId(Long centricAccountId) {
        this.centricAccountId = centricAccountId;
    }

    public Long getNewCentricAccountId() {
        return newCentricAccountId;
    }

    public void setNewCentricAccountId(Long newCentricAccountId) {
        this.newCentricAccountId = newCentricAccountId;
    }
}
