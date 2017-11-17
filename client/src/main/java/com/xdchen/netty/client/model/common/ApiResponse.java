package com.xdchen.netty.client.model.common;

public class ApiResponse {
    private int status = RetCode.SUCCESS_STATUS;
    private int code = ApiRet.SUCCESS_CODE;
    private String msg = ApiRet.ERR_MSG_MAP.get(ApiRet.SUCCESS_CODE);
    private Object data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
