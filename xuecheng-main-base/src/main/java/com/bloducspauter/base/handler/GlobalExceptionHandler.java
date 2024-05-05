package com.bloducspauter.base.handler;

import com.bloducspauter.base.enums.CommonError;
import com.bloducspauter.base.exception.IllegalParamException;
import com.bloducspauter.base.exception.InsertEntityFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //对项目的自定义异常类型进行处理
    @ExceptionHandler(IllegalParamException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(IllegalParamException e){

        //记录异常
        log.error(e.getMessage());
        //..

        //解析出异常信息
        String errMessage = e.getMessage();
        return new RestErrorResponse(errMessage);
    }
    @ExceptionHandler(InsertEntityFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(Exception e){
        log.error(e.getMessage());
        String errMessage = "添加数据失败,请检查后再试";
        return new RestErrorResponse(errMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception e){

        //记录异常
        log.error("系统异常{}",e.getMessage(),e);

        //解析出异常信息
        return new RestErrorResponse(CommonError.UNKNOWN_ERROR.getErrMessage());
    }
}
