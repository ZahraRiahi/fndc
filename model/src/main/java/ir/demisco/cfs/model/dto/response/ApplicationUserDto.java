package ir.demisco.cfs.model.dto.response;

import java.time.LocalDateTime;

public class ApplicationUserDto {

    private Long id;
    private String userName;
    private String nickName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }



    public static Builder builder(){
        return new Builder();
    }


    public static final class Builder {
        private ApplicationUserDto applicationUserDto;

        private Builder() {
            applicationUserDto = new ApplicationUserDto();
        }

        public static Builder anApplicationUserDto() {
            return new Builder();
        }

        public Builder id(Long id) {
            applicationUserDto.setId(id);
            return this;
        }

        public Builder userName(String userName) {
            applicationUserDto.setUserName(userName);
            return this;
        }


        public Builder nickName(String nickName) {
            applicationUserDto.setNickName(nickName);
            return this;
        }

        public ApplicationUserDto build() {
            return applicationUserDto;
        }
    }
}
