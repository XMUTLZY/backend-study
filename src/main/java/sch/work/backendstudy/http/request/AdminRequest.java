package sch.work.backendstudy.http.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminRequest {
    @JsonProperty("account_name")
    private String accountName;
    private String password;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
