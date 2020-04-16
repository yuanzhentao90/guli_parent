package com.atguigu.servicebase.exceptionHandler;

import com.atguigu.commonutils.StandardResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //全异常处理类
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public StandardResult error(Exception e){
        e.printStackTrace();
        return StandardResult.error().message("执行了全局异常处理！");
    }

    //特定异常处理类
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public StandardResult error(ArithmeticException e){
        e.printStackTrace();
        return StandardResult.error().message("执行了ArithmeticException异常处理！");
    }


    //自定义异常处理类
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public StandardResult error(GuliException e){
        e.printStackTrace();
        log.error(e.getMessage());
        return StandardResult.error().code(e.getCode()).message(e.getMsg());
    }
}
