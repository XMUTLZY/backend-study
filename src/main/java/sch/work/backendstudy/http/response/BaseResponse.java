package sch.work.backendstudy.http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    public static final Integer SUCCESS_CODE = 200;
    public static final Integer FAILD_CODE = 500;
    public static final String SUCCESS_STATUS = "success";
    public static final String FAILD_STATUS = "error";
    @JsonProperty("status_code")
    private Integer statusCode = SUCCESS_CODE;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T vo;

    public BaseResponse(Integer statusCode, String status) {
        this.statusCode = statusCode;
        this.status = status;
    }

    public BaseResponse() {

    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getVo() {
        return vo;
    }

    public void setVo(T vo) {
        this.vo = vo;
    }
}