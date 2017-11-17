package com.xdchen.netty.client.model.common;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class ApiRet {
    public static final String SUCCESS_MSG = "成功";

    public static final int SUCCESS_STATUS = 200,
            ACCESS_DENIED_CODE = 401,
            INVALID_PARAM_STATUS = 403,
            NOT_FOUND_STATUS = 404,
            SERVER_ERROR_STATUS = 500;
    public static final int SUCCESS_CODE = 0,
            LOGIN_FAIL = 20001,

            PERMISSION_DENIED = 40100,

            INVALID_PARAM = 40300,

            USER_NOT_FOUND = 40401,

            UNKONW_ERR = 50000;

    public static Map<Integer, String> ERR_MSG_MAP = new ImmutableMap.Builder<Integer, String>()
            .put(LOGIN_FAIL, "帐号或密码错误")

            .put(PERMISSION_DENIED, "没有权限")

            .put(INVALID_PARAM, "参数错误")

            .put(USER_NOT_FOUND, "用户不存在")

            .put(UNKONW_ERR, "未知错误")
            .build();
}
