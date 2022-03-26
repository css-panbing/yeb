package com.cssnj.server.common.utils;

import com.cssnj.server.pojo.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 操作员工具类
 * @author panbing
 * @date 2022/3/22 20:03
 */
public class AdminUtils {

    /**
     * 获取当前操作人员
     * @return
     */
    public static Admin getCurrentAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
