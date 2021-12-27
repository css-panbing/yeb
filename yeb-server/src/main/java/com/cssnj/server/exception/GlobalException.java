package com.cssnj.server.exception;

import com.cssnj.server.common.response.ResponseData;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * @author panbing
 * @date 2021/12/27 15:28
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public ResponseData mySqlException(SQLException sqlException){
        if(sqlException instanceof SQLIntegrityConstraintViolationException){
            return ResponseData.error("该数据有关联数据，操作失败！");
        }
        return ResponseData.error("数据库异常，操作失败！");
    }

}