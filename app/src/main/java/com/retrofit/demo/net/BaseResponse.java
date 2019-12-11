package com.retrofit.demo.net;

/**
 * 统一响应
 * @param <T>
 */
public class BaseResponse<T> {
    private int resultCode;
    private String errorMsg;
    private T message;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
