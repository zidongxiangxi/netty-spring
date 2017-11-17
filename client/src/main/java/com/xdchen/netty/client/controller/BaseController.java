package com.xdchen.netty.client.controller;

import com.xdchen.netty.client.model.common.ApiResponse;
import com.xdchen.netty.client.model.common.ApiRet;
import com.xdchen.netty.client.model.common.RetCode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.ParameterizedType;

@SuppressWarnings(value = { "unchecked" })
public class BaseController<T> {
    protected Logger logger = null;
    private Class<T> clazzT;

    public BaseController() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        clazzT = (Class<T>) type.getActualTypeArguments()[0];
        logger = LoggerFactory.getLogger(clazzT);
    }

    protected Object getRet(String jspPath) {
        return this.getRet(jspPath, null);
    }

    protected Object getRet(String jspPath, Object data) {
        if (StringUtils.isNotBlank(jspPath)) {
            ModelAndView modelAndView = new ModelAndView(jspPath);
            modelAndView.addObject("data", data);
            return modelAndView;
        } else {
            ApiResponse response = new ApiResponse();
            response.setStatus(RetCode.SUCCESS_STATUS);
            response.setCode(ApiRet.SUCCESS_CODE);
            response.setMsg(ApiRet.SUCCESS_MSG);
            response.setData(data);
            return response;
        }
    }

    protected Object getAjaxRet(Object data) {
        return this.getRet(null, data);
    }
}
