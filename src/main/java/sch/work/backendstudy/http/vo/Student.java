package sch.work.backendstudy.http.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class Student {
    private Integer id;
    @JsonProperty("user_name")
    private String userName;
    private String email;
    private String password;
    @JsonProperty("create_time")
    private Date createTime;
    @JsonProperty("status_str")
    private String statusStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}
