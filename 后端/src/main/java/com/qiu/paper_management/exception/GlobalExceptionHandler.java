package com.qiu.paper_management.exception;

import com.qiu.paper_management.pojo.Result;
import jakarta.validation.ConstraintViolationException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 违反Validation.pattern的参数错误异常
    @ExceptionHandler(ConstraintViolationException.class)
    public Result ValueErrorHandler(ConstraintViolationException e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"违反约束条件");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result InvalidArgs(MethodArgumentNotValidException e){
        e.printStackTrace();
        return Result.error("抱歉，参数缺失或不合规！");
    }


    @ExceptionHandler(Exception.class)
    public Result ExceptionHandler(Exception e){
        e.printStackTrace();
        return Result.error(StringUtils.hasLength(e.getMessage())?e.getMessage():"反正就是操作失败");
    }
}
