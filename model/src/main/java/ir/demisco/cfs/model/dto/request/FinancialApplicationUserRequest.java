package ir.demisco.cfs.model.dto.request;

import java.util.Map;

public class FinancialApplicationUserRequest {
    private String nickName;
    Map<String, Object> paramMap;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }
}
