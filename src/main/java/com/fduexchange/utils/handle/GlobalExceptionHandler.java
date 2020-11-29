package com.fduexchange.utils.handle;

import com.fduexchange.utils.error.BaseException;
import com.fduexchange.utils.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 全局统一异常返回信息
     */
    public static final String DEFAULT_ERROR_MESSAGE = "系统维护，请稍后访问";

    /**
     * 500的所有异常会被这个方法捕获
     *
     * @param req 请求
     * @param e   异常
     * @return 输出
     * @throws Exception 未知异常
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse handleAllError(HttpServletRequest req, Exception e) {
        System.out.printf(e.getMessage());
        return BaseResponse.fail();
    }

    /**
     * 500的事务异常会被这个方法捕获
     *
     * @param req 请求
     * @param e   异常
     * @return 输出
     * @throws Exception 未知异常
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public BaseResponse handleBizError(HttpServletRequest req, BaseException e) {
        System.out.printf(e.getMessage());
        return BaseResponse.fail(e.getMessage());
    }
}
