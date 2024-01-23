package com.cheese.handler;

import com.cheese.constant.MessageConstant;
import com.cheese.exception.BaseException;
import com.cheese.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler
{

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
    /**
     * 处理SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(PSQLException ex) {
        String message = ex.getMessage();
        if (message.contains("唯一约束")) {
            //根据空格划分成数组取得用户名
            String[] split = message.split("=");
            String username = split[1];
            String msg = username;// + MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
