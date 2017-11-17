package com.xdchen.netty.client.model.common;

public class RetCode {
    private RetCode() {}
    public static final int SUCCESS_STATUS = 200,
            ACCESS_DENIED_CODE = 401,
            INVALID_PARAM_STATUS = 403,
            NOT_FOUND_STATUS = 404,
            SERVER_ERROR_STATUS = 500;
}
