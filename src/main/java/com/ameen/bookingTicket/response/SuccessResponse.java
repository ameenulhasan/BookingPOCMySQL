package com.ameen.bookingTicket.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse<T> {

    private String message;
    private boolean success;
    private T data;

    public SuccessResponse(String message, boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }
    public SuccessResponse() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
