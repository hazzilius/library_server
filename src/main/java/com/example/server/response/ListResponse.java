package com.example.server.response;

import java.util.List;

public class ListResponse<T> extends BaseResponse {
    private List<T> data;

    public ListResponse(boolean success, String msg, List<T> data) {
        super(success, msg);
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
