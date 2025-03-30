package com.example.server.response;

public class BaseResponse {
    protected boolean success;
    protected String msg;

    public BaseResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public BaseResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
