package com.xdchen.netty.client.controller.exception;

import com.alibaba.fastjson.JSON;
import com.xdchen.netty.client.exception.ApiException;
import com.xdchen.netty.client.model.common.ApiResponse;
import com.xdchen.netty.client.model.common.ApiRet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class DefaultExceptionHandler {
    private Logger logger =  LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler({ApiException.class})
    @ResponseStatus(HttpStatus.OK)
    public void processException(HttpServletResponse response, ApiException ex) {
        ApiResponse apiResponse = new ApiResponse();
        int status = ex.getErrCode() != ApiRet.SUCCESS_CODE ? ex.getErrCode() / 100 : ApiRet.SUCCESS_CODE;
        apiResponse.setStatus(status);
        apiResponse.setCode(ex.getErrCode());
        apiResponse.setMsg(ex.getMessage());
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            writer = response.getWriter();
            writer.write(JSON.toJSONString(apiResponse));
            writer.flush();
        } catch (IOException e) {
            logger.error("fail to write error message to response");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
