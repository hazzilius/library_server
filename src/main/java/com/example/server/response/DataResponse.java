package com.example.server.response;

public class DataResponse<T> extends BaseResponse {
    private T data;

    public DataResponse(boolean success, String msg, T data) {
        super(success, msg);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
