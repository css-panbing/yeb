package com.cssnj.server.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 * @author panbing
 * @date 2021/12/16 16:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespData {
    private long code;
    private String message;
    private Object data;

    /**
     * 成功返回结果
     * @param message
     * @return
     */
    public static RespData success(String message){
        return new RespData(200, message, null);
    }

    /**
     * 成功返回结果
     * @param message
     * @param data
     * @return
     */
    public static RespData success(String message, Object data){
        return new RespData(200, message, data);
    }

    /**
     * 失败返回结果
     * @param message
     * @return
     */
    public static RespData error(String message){
        return new RespData(500, message, null);
    }

    /**
     * 失败返回结果
     * @param message
     * @param data
     * @return
     */
    public static RespData error(String message, Object data){
        return new RespData(500, message, data);
    }

}
