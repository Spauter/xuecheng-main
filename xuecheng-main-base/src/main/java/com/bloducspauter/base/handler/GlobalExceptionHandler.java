package com.bloducspauter.base.handler;

import com.bloducspauter.base.enums.CommonError;
import com.bloducspauter.base.exceptions.IllegalParamException;
import com.bloducspauter.base.exceptions.InsertEntityFailedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义异常
 * <p>
 * {@code RestControllerAdvice}表示是控制增强
 * </p>
 * <p>
 * {@code Order} 表示触发集别，值越低表示这个类去捕获其他异常的优先级越高
 * </p>
 * {@code ExceptionHandler} 表示余姚处理的异常类型
 * <p>
 * {@code  @ResponseStatus}表示返回的Http状态码响应
 * </p>
 *
 * @author Bloduc Spauter
 */
@Slf4j
@RestControllerAdvice
@Order(-100)
public class GlobalExceptionHandler {

    /**
     * 对项目的自定义异常类型进行处理
     *
     * @param e 被捕获的异常{@code IllegalParamException}
     * @return
     */
    @ExceptionHandler(IllegalParamException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(IllegalParamException e) {
        //记录异常
        log.error(e.getMessage());
        //解析出异常信息
        String errMessage = e.getMessage();
        return new RestErrorResponse(errMessage);
    }

    @ExceptionHandler(InsertEntityFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(Exception e) {
        log.error(e.getMessage());
        String errMessage = "添加数据失败,请检查后再试";
        return new RestErrorResponse(errMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        //存储错误信息
        List<String> errors = new ArrayList<>();
        bindingResult.getFieldErrors().forEach(item -> {
            errors.add(item.getDefaultMessage());
        });

        //将list中的错误信息拼接起来
        String errMessage = StringUtils.join(errors, ',');
        //记录异常
        log.error("系统异常{},原因{}", exception.getMessage(), errMessage);

        //解析出异常信息
        return new RestErrorResponse(errMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception e) {
        //记录异常
        log.error("系统异常{}", e.getMessage(), e);
        //解析出异常信息
        return new RestErrorResponse(CommonError.UNKNOWN_ERROR.getErrMessage());
    }
}
