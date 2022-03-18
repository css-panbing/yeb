package com.cssnj.server.exception;

import com.cssnj.server.common.response.ResponseData;
import org.apache.log4j.Logger;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * @RestControllerAdvice:对Controller进行增强的，可以全局捕获springmvc抛的异常
 * @ExceptionHandler:可以用来统一处理方法抛出的异常
 * @author panbing
 * @date 2021/12/27 15:28
 */
@RestControllerAdvice
public class GlobalException {

    static final Logger logger = Logger.getLogger(GlobalException.class);

    @ExceptionHandler(SQLException.class)
    public ResponseData mySqlException(SQLException sqlException){
        if(sqlException instanceof SQLIntegrityConstraintViolationException){
            logger.error("数据库操作异常："+sqlException);
            return ResponseData.error("该数据有关联数据，操作失败！");
        }
        logger.error("数据库操作异常："+sqlException);
        return ResponseData.error("数据库异常，操作失败！");
    }

    @ExceptionHandler(RedisConnectionFailureException.class)
    public ResponseData redisConnectionFailureException(RedisConnectionFailureException redisException){
        logger.error("Redis连接异常："+redisException);
        return ResponseData.error("Redis连接异常");
    }

}
