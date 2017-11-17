package com.xdchen.netty.exception;

public class BusiException extends RuntimeException {
    private String msg;
    public BusiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
