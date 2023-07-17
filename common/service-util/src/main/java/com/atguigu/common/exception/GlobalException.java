package com.atguigu.common.exception;

import com.atguigu.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * GlobalException
 *
 * @author Jia Yaoyi
 * @date 2023/07/17
 */
@ControllerAdvice
public class GlobalException {
    //全局异常处理执行的方法
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail().message("执行了全局异常处理");
    }

    //特定异常处理
    @ResponseBody
    @ExceptionHandler(ArithmeticException.class)
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().message("执行了特定异常处理");
    }

    //自定义异常处理

}
