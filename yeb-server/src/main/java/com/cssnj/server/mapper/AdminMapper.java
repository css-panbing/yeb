package com.cssnj.server.mapper;

import com.cssnj.server.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 *  用户 Mapper 接口
 *
 * @author panbing
 * @since 2021-12-16
 */
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 通过关键字查询操作员信息
     * @param id 当前登录操作员id
     * @param keywords
     * @return
     */
    List<Admin> getAdminsByKeywords(Integer id, String keywords);


}
