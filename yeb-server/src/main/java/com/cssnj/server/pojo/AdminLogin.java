package com.cssnj.server.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登录实体类
 * @author panbing
 * @date 2021/12/16 17:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)    //setter方法返回当前对象
@ApiModel(value = "AdminLogin对象", description = "")
public class AdminLogin {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "密码", required = true)
    private String password;

}
